import 'package:sqflite/sqflite.dart';
import 'dart:async';
import 'dart:io';
import 'package:path_provider/path_provider.dart';
import 'package:cluj_eats_flutter/domain/place.dart';

class DatabaseHelper {

  static DatabaseHelper _databaseHelper;    // Singleton DatabaseHelper
  static Database _database;                // Singleton Database

  String placeTable = 'place_table';
  String colId = 'localId';
  String colRemoteId = 'remoteId';
  String colName = 'name';
  String colAddress = 'address';
  String colPhoto = 'photo';

  DatabaseHelper._createInstance(); // Named constructor to create instance of DatabaseHelper

  factory DatabaseHelper() {

    if (_databaseHelper == null) {
      _databaseHelper = DatabaseHelper._createInstance(); // This is executed only once, singleton object
    }
    return _databaseHelper;
  }

  Future<Database> get database async {

    if (_database == null) {
      _database = await initializeDatabase();
    }
    return _database;
  }

  Future<Database> initializeDatabase() async {
    // Get the directory path for both Android and iOS to store database.
    Directory directory = await getApplicationDocumentsDirectory();
    String path = directory.path + 'places.db';

    // Open/create the database at a given path
    var placesDatabase = await openDatabase(path, version: 1, onCreate: _createDb);
    return placesDatabase;
  }

  void _createDb(Database db, int newVersion) async {

    await db.execute('CREATE TABLE $placeTable('
        '$colId INTEGER PRIMARY KEY AUTOINCREMENT, '
        '$colRemoteId INTEGER, '
        '$colName TEXT, '
        '$colAddress TEXT, '
        '$colPhoto TEXT)');
  }

  // Fetch Operation: Get all place objects from database
  Future<List<Map<String, dynamic>>> getPlaceMapList() async {
    Database db = await this.database;

//		var result = await db.rawQuery('SELECT * FROM $todoTable order by $colTitle ASC');
    var result = await db.query(placeTable, orderBy: '$colName ASC');
    return result;
  }

  // Insert Operation: Insert a place object to database
  Future<int> insertPlace(Place place) async {
    Database db = await this.database;
    place.localId = await db.insert(placeTable, place.toMap());
    return place.localId;
  }

  // Update Operation: Update a place object and save it to database
  Future<int> updatePlace(Place place) async {
    var db = await this.database;
    var result = await db.update(placeTable, place.toMap(), where: '$colId = ?', whereArgs: [place.localId]);
    return result;
  }


  // Delete Operation: Delete a place object from database
  Future<int> deletePlace(int id) async {
    var db = await this.database;
    int result = await db.rawDelete('DELETE FROM $placeTable WHERE $colId = $id');
    print('Delete result: ' + result.toString());
    return result;
  }

  // Get number of place objects in database
  Future<int> getCount() async {
    Database db = await this.database;
    List<Map<String, dynamic>> x = await db.rawQuery('SELECT COUNT (*) from $placeTable');
    int result = Sqflite.firstIntValue(x);
    return result;
  }

  // Get the 'Map List' [ List<Map> ] and convert it to 'place List' [ List<Place> ]
  Future<List<Place>> getPlaceList() async {

    var placeMapList = await getPlaceMapList(); // Get 'Map List' from database
    int count = placeMapList.length;         // Count the number of map entries in db table

    List<Place> placeList = List<Place>();
    // For loop to create a 'place List' from a 'Map List'
    for (int i = 0; i < count; i++) {
      placeList.add(Place.fromDB(placeMapList[i]));
    }

    return placeList;
  }

  void clear() async {
    Database db = await this.database;
    await db.rawQuery('DELETE FROM $placeTable');
  }

}