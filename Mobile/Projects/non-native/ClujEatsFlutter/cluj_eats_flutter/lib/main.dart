import 'package:cluj_eats_flutter/screens/place_list.dart';
import 'package:flutter/material.dart';

void main() => runApp(PlaceApp());

class PlaceApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'ClujEatsFlutter',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        // Define the default brightness and colors.
        brightness: Brightness.dark,
        primaryColor: Colors.green,
        primaryColorDark: Colors.blueGrey,
        accentColor: Colors.amber[400],

        // Define the default font family.
        fontFamily: 'Architect',

        // Define the default TextTheme. Use this to specify the default
        // text styling for headlines, titles, bodies of text, and more.
        textTheme: TextTheme(
          headline: TextStyle(fontSize: 64.0, fontWeight: FontWeight.bold),
          title: TextStyle(fontSize: 18.0, fontStyle: FontStyle.italic),
          body1: TextStyle(fontSize: 10.0, fontFamily: 'Hind'),
        ),
      ),
      home: PlaceList(),
    );
  }
}
