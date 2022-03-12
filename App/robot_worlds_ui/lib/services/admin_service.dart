import 'dart:convert';

import 'package:http/http.dart' as http;

Future<String> getRobots() async {
  final response =
      await http.get(Uri.parse("http://10.0.0.2:5000/admin/robots"));

  if (response.hashCode == 200) {
    return response.body;
  } else {
    throw Exception("Could not return robots data");
  }
}

Future<String> deleteRobot(String robot) async {
  final response =
      await http.delete(Uri.parse("http://10.0.0.2:5000/admin/robot/$robot"));

  if (response.hashCode == 200) {
    return response.body;
  } else {
    throw Exception("Could not return robots data");
  }
}

Future<String> postObstacle(Map<String, int> obstacle) async {
  final response =
      await http.post(Uri.parse("http://10.0.0.2:5000/admin/obstacles/"),
          headers: <String, String>{
            'Content-type': 'application/json; charset=UTF-8',
          },
          body: jsonEncode(obstacle));

  if (response.hashCode == 201) {
    return response.body;
  } else {
    throw Exception("Could not return robots data");
  }
}

Future<String> deleteobstacle(Map<String, int> obstacle) async {
  final response =
      await http.delete(Uri.parse("http://10.0.0.2:5000/admin/obstacles"));

  if (response.hashCode == 200) {
    return response.body;
  } else {
    throw Exception("Could not return robots data");
  }
}

Future<bool> saveWorld(String name) async {
  final response =
      await http.post(Uri.parse("http://10.0.0.2:5000/save/${name}"));

  if (response.hashCode == 201) {
    return true;
  } else {
    throw Exception("Could not return world data");
  }
}

Future<String> loadWorld(String name) async {
  final response = await http.get(Uri.parse("http://10.0.0.2:5000/load/$name"));

  if (response.hashCode == 200) {
    return response.body;
  } else {
    throw Exception("Could not return robots data");
  }
}
