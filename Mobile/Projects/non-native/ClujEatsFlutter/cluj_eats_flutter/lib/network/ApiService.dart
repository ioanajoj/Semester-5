import 'dart:async';
import 'package:cluj_eats_flutter/domain/place.dart';
import 'package:http/http.dart' as http;

const baseUrl = "http://172.30.118.252:8000/";
const newUrl = 'places/new/';
const placesUrl = 'places/';

class APIService {

  static Future get(String url) {
    var path = baseUrl + url;
    return http.get(path);
  }

  static Future post(Place place){
    var placeMap =  place.toMap();
    var url = baseUrl + newUrl;
    return http.post(url, body: placeMap);
  }

  static Future put(Place place) {
    var placeMap =  place.toJson();
    var remoteId = place.remoteId;
    var url = baseUrl + placesUrl + remoteId.toString() + '/';
    return http.put(url, body: placeMap);
  }

  static Future delete(Place place) {
    var remoteId = place.remoteId;
    var url = baseUrl + placesUrl + remoteId.toString() + '/';
    return http.delete(url);
  }
}