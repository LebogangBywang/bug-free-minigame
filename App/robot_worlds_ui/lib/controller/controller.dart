import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:provider/provider.dart';
import 'package:robot_worlds_ui/model/model.dart';
import 'package:robot_worlds_ui/model/robot.dart';
import 'package:robot_worlds_ui/services/robot_service.dart';

import '../services/robot_service.dart';

class Controller {

  Future<Map<String, dynamic>> getRobotData(name,robot) async {
    final body = getRobot(name,robot);
    return body;
  }

  Future<Map<String, dynamic>> getWorldData() async {
    final body = getWorld();
    return body;
  }

  void setRobotData(BuildContext context, data) async {
    Map< String , dynamic> robot = await data;
    Provider.of<Model>(context, listen: false).setRobot(robot);
  }

  void setWordData(BuildContext context, data) async {
    Map< String , dynamic> worldData = await data;
    Provider.of<Model>(context, listen: false).setWord(worldData);
  }

}