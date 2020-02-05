import 'dart:convert';

import 'package:cluj_eats_flutter/db/DatabaseHelper.dart';
import 'package:cluj_eats_flutter/domain/place.dart';
import 'package:cluj_eats_flutter/network/ApiService.dart';
import 'package:cluj_eats_flutter/screens/place_detail.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:sqflite/sqflite.dart';
import 'package:connectivity/connectivity.dart';
import 'package:toast/toast.dart';

class PlaceList extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return PlaceListState();
  }
}

class PlaceListState extends State<PlaceList> {
  DatabaseHelper databaseHelper = DatabaseHelper();
  List<Place> placeList;
  int count = 0;

  @override
  Widget build(BuildContext context) {
    if (placeList == null) {
      placeList = List<Place>();
      updateListView();
    }

    return Scaffold(
      appBar: AppBar(
        title: Text('Places'),
        actions: <Widget>[
          IconButton(
            icon: Icon(Icons.refresh),
            onPressed: () {
              _refresh();
            },
          ),
          IconButton(
            icon: Icon(Icons.delete_forever),
            onPressed: () {
              _clearDB();
            },
          )
        ],
      ),
      body: getPlaceListView(),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          debugPrint('FAB clicked');
          navigateToDetail(Place('', '', ''), 'Add Place');
        },
        tooltip: 'Add Place',
        child: Icon(Icons.add),
      ),
    );
  }

  ListView getPlaceListView() {
    return ListView.builder(
      itemCount: count,
      itemBuilder: (BuildContext context, int position) {
        return Card(
          color: Colors.white,
          elevation: 2.0,
          child: ListTile(
            title: Text(this.placeList[position].name,
              style: TextStyle(fontWeight: FontWeight.bold, color: Colors.black)),
            subtitle: Text(this.placeList[position].address,
                style: TextStyle(color: Colors.black)),
            trailing: Row(
              mainAxisSize: MainAxisSize.min,
              children: <Widget>[
                _getRefreshIcon(this.placeList[position]),
                GestureDetector(
                  child: Icon(Icons.delete, color: Colors.black),
                  onTap: () {
                    debugPrint('Delete Tapped');
                    _delete(context, placeList[position]);
                  },
                )
              ],
            ),
            onTap: () {
              debugPrint("ListTile Tapped");
              navigateToDetail(this.placeList[position], 'Edit Place');
            },
          ),
        );
      },
    );
  }

  IconButton _getRefreshIcon(Place place) {
    if (place.remoteId == null) {
      return IconButton(
        icon: Icon(Icons.data_usage, color: Colors.red,)
      );
    }
    return IconButton(
      icon: Icon(Icons.check)
    );
  }

  void _delete(BuildContext context, Place place) async {
    print('Deleting ' + place.name + ' ' + place.localId.toString() + ' ' + place.remoteId.toString());
    debugPrint('this.placeList: ' + this.placeList.length.toString());
    var connectivityResult = await (Connectivity().checkConnectivity());
    if (connectivityResult == ConnectivityResult.none) {
      debugPrint('No connection');
      _showSnackBar(context, 'Delete is not available while offline.');
    }
    else if (connectivityResult == ConnectivityResult.wifi || connectivityResult == ConnectivityResult.mobile){
      APIService.delete(place).then((response) async {
        final result = await databaseHelper.deletePlace(place.localId);
        if (result != 0) {
          _showSnackBar(context, 'Place Deleted Successfully');
          updateListView();
        }
        else {
          _showSnackBar(context, 'Could not delete Place');
        }
      });
    }
  }

  void _showSnackBar(BuildContext context, String message) {
    final snackBar = SnackBar(content: Text(message));
    Scaffold.of(context).showSnackBar(snackBar);
  }

  void navigateToDetail(Place place, String name) async {
    bool result =
        await Navigator.push(context, MaterialPageRoute(builder: (context) {
          return PlaceDetail(place, name);
        }));
    if (result == true) {
      updateListView();
    }
  }

  Future<bool> updateFromDB() async {
    List<Place> placeList = await databaseHelper.getPlaceList();
    setState(() {
      print("set state " + placeList.length.toString());
      this.placeList = placeList;
      this.count = placeList.length;
    });
    return true;
  }

  void updateListView() async {
    debugPrint('this.placeList: ' + this.placeList.length.toString());
    var connectivityResult = await (Connectivity().checkConnectivity());
    if (connectivityResult == ConnectivityResult.none) {
      debugPrint('No connection');
      await updateFromDB();
    }
    else if (connectivityResult == ConnectivityResult.mobile || connectivityResult == ConnectivityResult.wifi){
      debugPrint('Found a connection');
      APIService.get("places/").then((response) async {
        Iterable list = json.decode(response.body);
        List<Place> remoteList = list.map((place) => Place.fromMapObject(place)).toList();
        debugPrint('Got remoteList ' + remoteList.length.toString() );
        databaseHelper.getPlaceList().then((dbList) async {
          debugPrint('Got dbList ' + dbList.length.toString());

          dynamic loadOfflinePlaces = () async {
            // SELECT WHERE remoteId = null
            for (dynamic dbPlace in dbList){
              if (dbPlace.remoteId == null) {
                dynamic res = await APIService.post(dbPlace);
                dbPlace.remoteId = json.decode(res.body)['remoteId'];
                await databaseHelper.updatePlace(dbPlace);
                debugPrint('Added ' + dbPlace.name + ' remotely');
              }
            }
          };

          await loadOfflinePlaces();
          await updateFromDB();

          remoteList.forEach((remotePlace) async {
            if (!_listContains(dbList, remotePlace, false, true)) {
              await databaseHelper.insertPlace(remotePlace);
              debugPrint('Adding remote place to db ' + remotePlace.name + ' ' + remotePlace.localId.toString() + ' ' + remotePlace.remoteId.toString() );
              setState(() {
                this.placeList.add(remotePlace);
                this.count += 1;
                this.placeList.sort((p1, p2) {
                  return p1.name.compareTo(p2.name);
                });
              });
            }
          });
        });
        await updateFromDB();
        debugPrint("After update DB");
      });
    }
  }

  bool _listContains(List<Place> places, Place place, bool local, bool remote) {
    debugPrint('place: ' + place.name + ' ' + place.localId.toString() + ' ' + place.remoteId.toString());
    for(Place p in places) {
      if (local && p.localId != null && place.localId != null && p.localId == place.localId) {
        return true;
      }
      if (remote && p.remoteId != null && place.remoteId != null && p.remoteId == place.remoteId)
        return true;
    }
    return false;
  }

  void _refresh() async {
    var connectivityResult = await (Connectivity().checkConnectivity());
    if (connectivityResult == ConnectivityResult.none) {
      Toast.show('No internet connection found.',
          context, duration: Toast.LENGTH_LONG, gravity: Toast.BOTTOM);
    }
    else {
      Toast.show('Syncing remote data.',
          context, duration: Toast.LENGTH_LONG, gravity: Toast.BOTTOM);
    }
    updateListView();
    setState(() {
      this.placeList.sort((p1, p2) {
        return p1.name.compareTo(p2.name);
      });
    });

  }

  void _clearDB() {
    databaseHelper.clear();
  }
}