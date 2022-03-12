import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:robot_worlds_ui/model/robot.dart';
import 'package:robot_worlds_ui/model/world.dart';

class Model extends ChangeNotifier {

   final List<Map< String , dynamic>> word =  [];

   late final Robot robot;

  void setWord(wordData){
    word.add(wordData);
  }
  void notify() {
    print('Notified');
    notifyListeners();
  }
  void setRobot(robotData) {
    robot =  Robot(position: robotData["state"]["position"],direction:robotData["state"]["direction"]);;
  }

  List<dynamic> getWorldData() {
    return word;
  }


}