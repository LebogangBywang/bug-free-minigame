import 'package:flutter/material.dart';

import 'control_panel.dart';
import 'direction.dart';
import 'game.dart';
import 'mine.dart';

final Color darkBlue = Color.fromARGB(255, 18, 32, 47);



class Main extends StatelessWidget {
  Main(this.robot, this.world);

  Future<Map<String, dynamic>> robot;
  Future<Map<String, dynamic>> world;
  @override
  Widget build(BuildContext context) {
    return  Column(
        children: [Game(this.robot, this.world),  ControlPanel(),],
    );
  }
}

