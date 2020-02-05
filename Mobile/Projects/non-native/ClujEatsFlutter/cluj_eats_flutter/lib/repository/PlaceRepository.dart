import 'dart:convert';

import 'package:cluj_eats_flutter/db/DatabaseHelper.dart';
import 'package:cluj_eats_flutter/domain/place.dart';
import 'package:cluj_eats_flutter/network/ApiService.dart';
import 'package:connectivity/connectivity.dart';

class PlaceRepository {

  DatabaseHelper databaseHelper = DatabaseHelper();
  Set<Place> offlinePlaces = Set.identity();

  Future<String> savePlace(Place place) async {
    String response = 'empty';
    place.photo = 'https://syndlab.com/files/view/5db9b150252346nbDR1gKP3OYNuwBhXsHJerdToc5I0SMLfk7qlv951730.jpeg';

    if(place.localId != null) {
      var connectivityResult = await (Connectivity().checkConnectivity());
      if (connectivityResult == ConnectivityResult.mobile || connectivityResult == ConnectivityResult.wifi){
        dynamic res = await APIService.put(place);
        int result = await databaseHelper.updatePlace(place);

        if (result != 0) {
          response = 'Place Updated Successfully';
        } else {
          response = 'Problem Updating Place';
        }
      }
      else {
        response = 'Action unavailable while offline';
      }
      return response;
    } else {
      var connectivityResult = await (Connectivity().checkConnectivity());
      if (connectivityResult == ConnectivityResult.mobile || connectivityResult == ConnectivityResult.wifi){
        dynamic res = await APIService.post(place);
        place.remoteId = json.decode(res.body)['remoteId'];
      }
      int result = await databaseHelper.insertPlace(place);
      if (result != 0) {
        response = 'Place Saved Successfully';
      } else {
        response = 'Problem Saving Place';
      }
      return response;
    }
  }



}