//import 'dart:async';
//
//import 'package:cluj_eats_flutter/domain/place.dart';
//import 'package:cluj_eats_flutter/network/ApiResponse.dart';
//
//import 'PlaceRepository.dart';
//
//class PlaceBloc {
//  PlaceRepository _placeRepository;
//
//  StreamController _placeListController;
//
//  StreamSink<ApiResponse<List<Place>>> get placeListSink =>
//      _placeListController.sink;
//
//  Stream<ApiResponse<List<Place>>> get placeListStream =>
//      _placeListController.stream;
//
//  PlaceBloc() {
//    _placeListController = StreamController<ApiResponse<List<Place>>>();
//    _placeRepository = PlaceRepository();
//    fetchPlacesList();
//  }
//
//  fetchPlacesList() async {
//    placeListSink.add(ApiResponse.loading('Fetching Places'));
//    try {
//      List<Place> places = await _placeRepository.getPlaceList();
//      placeListSink.add(ApiResponse.completed(places));
//    } catch (e) {
//      placeListSink.add(ApiResponse.error(e.toString()));
//      print(e);
//    }
//  }
//
//  dispose() {
//    _placeListController?.close();
//  }
//}