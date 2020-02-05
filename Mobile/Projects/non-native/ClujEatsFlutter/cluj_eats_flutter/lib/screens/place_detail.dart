
import 'package:cluj_eats_flutter/db/DatabaseHelper.dart';
import 'package:cluj_eats_flutter/domain/place.dart';
import 'package:cluj_eats_flutter/network/ApiService.dart';
import 'package:cluj_eats_flutter/repository/PlaceRepository.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class PlaceDetail extends StatefulWidget {

  final String appBarTitle;
  final Place place;

  PlaceDetail(this.place, this.appBarTitle);

  @override
  State<StatefulWidget> createState() {
    return PlaceDetailState(this.place, this.appBarTitle);
  }
}

class PlaceDetailState extends State<PlaceDetail> {

  DatabaseHelper helper = DatabaseHelper();
  PlaceRepository repository = PlaceRepository();

  String appBarTitle;
  Place place;

  TextEditingController nameController = TextEditingController();
  TextEditingController addressController = TextEditingController();

  PlaceDetailState(this.place, this.appBarTitle);

  @override
  Widget build(BuildContext context) {
    TextStyle textStyle = Theme.of(context).textTheme.title;

    nameController.text = place.name;
    addressController.text = place.address;

    return WillPopScope(
      onWillPop: () {
        moveToLastScreen();
      },

      child: Scaffold(
        appBar: AppBar(
          title: Text(appBarTitle),
          leading: IconButton(icon: Icon(
            Icons.arrow_back), onPressed: () {
              moveToLastScreen();
            },
          ),
        ),
        body: Padding(
          padding: EdgeInsets.only(top: 15.0, left: 10.0, right: 10.0),
          child: ListView(
            children: <Widget>[
              Padding(
                padding: EdgeInsets.only(top: 15.0, bottom: 15.0),
                child: TextField(
                  controller: nameController,
                    style: textStyle,
                    onChanged: (value) {
                      debugPrint('Something changed in Name Text Field');
                      updateName();
                    },
                  decoration: InputDecoration(
                    labelText: 'Name',
                    labelStyle: textStyle,
                    border: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(5.0)
                    )
                  ),
                )
              ),

              Padding(
                  padding: EdgeInsets.only(top: 15.0, bottom: 15.0),
                  child: TextField(
                    controller: addressController,
                    style: textStyle,
                    onChanged: (value) {
                      debugPrint('Something changed in Address Text Field');
                      updateAddress();
                    },
                    decoration: InputDecoration(
                        labelText: 'Address',
                        labelStyle: textStyle,
                        border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(5.0)
                        )
                    ),
                  )
              ),

              Padding(
                padding: EdgeInsets.only(top: 15.0, bottom: 15.0),
                child: Row(
                  children: <Widget>[
                    Expanded(
                      child: RaisedButton(
                        color: Theme.of(context).accentColor,
                        textColor: Colors.black,
                        child: Text(
                          'Save',
                          textScaleFactor: 1.0,
                        ),
                        onPressed: () {
                          setState(() {
                            debugPrint("Save button clicked");
                            _save();
                          });
                        },
                      ),
                    ),

                    Container(width: 5.0),

                    Expanded(
                      child: RaisedButton(
                        color: Theme.of(context).accentColor,
                        textColor: Colors.red,
                        child: Text(
                          'Delete',
                          textScaleFactor: 1.0,
                        ),
                        onPressed: () {
                          setState(() {
                            debugPrint("Save button clicked");
                            _delete();
                          });
                        },
                      )
                    )
                  ],
                )
              )
            ],
          ),
        ),
      )
    );
  }

  void moveToLastScreen() {
    Navigator.pop(context, true);
  }

  void updateName() {
    place.name = nameController.text;
  }

  void updateAddress() {
    place.address = addressController.text;
  }

  void _save() async {
    repository.savePlace(place).then((response) {
      _showAlertDialog('Status', response);
    });
  }

  void _delete() async {
    if (place.localId == null) {
      _showAlertDialog('Status', 'Could not delete place');
    }

    APIService.delete(place).then((response) async {
      print(response);
      int result = await helper.deletePlace(place.localId);

      if (result != 0) {
        _showAlertDialog('Status', 'Place Deleted Successfully');
      } else {
        _showAlertDialog('Status', 'Wrror occured while deleting place');
      }

      moveToLastScreen();
    });
  }

  void _showAlertDialog(String title, String message) {
    AlertDialog alertDialog = AlertDialog(
        title: Text(title),
        content: Text(message)
    );

    showDialog(
      context: context,
      builder: (_) => alertDialog
    );
  }

}