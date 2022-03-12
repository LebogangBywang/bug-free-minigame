import 'dart:convert';

import 'package:http/http.dart' as http;

Future<Map< String , dynamic>> getRobot(String name, String robot) async {
  String body = "{\"robot\": \"$name\", \"command\": \"launch\", \"arguments\": $robot}";
  final res = await http.post(Uri.parse("http://192.168.8.115:5000/robot/$name"),body: body);
  if (res.statusCode == 200) {
    return jsonDecode(res.body);
  } else {
    throw Exception('Could not return robot data');
  }
}

Future<Map< String , dynamic>> getWorld() async {
  final response =
  await http.get(Uri.parse("http://192.168.8.115:5000/world/"));
  print(response.statusCode);
  if (response.statusCode == 200) {
    return json.decode(response.body);
  } else {
    throw Exception("Could not return world data");
  }
}

Future<Map< String , dynamic>> moveForward(String name) async {
  String body = "{\"robot\": \"$name\", \"command\": \"forward\", \"arguments\": [\"1\"]}";
  final res = await http.post(Uri.parse("http://192.168.8.115:5000/robot/$name"),body: body);
  print(res.body);
  if (res.statusCode == 200) {
    return jsonDecode(res.body);
  } else {
    throw Exception('Could not return robot data');
  }
}

Future<Map< String , dynamic>> moveBack(String name) async {
  String body = "{\"robot\": \"$name\", \"command\": \"back\", \"arguments\": [\"1\"]}";
  final res = await http.post(Uri.parse("http://192.168.8.115:5000/robot/$name"),body: body);
  print(res.body);
  if (res.statusCode == 200) {
    return jsonDecode(res.body);
  } else {
    throw Exception('Could not return robot data');
  }
}

Future<Map< String , dynamic>> turnRight(String name) async {
  String body = "{\"robot\": \"$name\", \"command\": \"turn\", \"arguments\": [\"right\"]}";
  final res = await http.post(Uri.parse("http://192.168.8.115:5000/robot/$name"),body: body);
  if (res.statusCode == 200) {
    return jsonDecode(res.body);
  } else {
    throw Exception('Could not return robot data');
  }
}

Future<Map< String , dynamic>> turnLeft(String name) async {
  String body = "{\"robot\": \"$name\", \"command\": \"turn\", \"arguments\": [\"left\"]}";
  final res = await http.post(Uri.parse("http://192.168.8.115:5000/robot/$name"),body: body);
  if (res.statusCode == 200) {
    return jsonDecode(res.body);
  } else {
    throw Exception('Could not return robot data');
  }
}