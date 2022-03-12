import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:robot_worlds_ui/controller/controller.dart';
import 'package:robot_worlds_ui/model/model.dart';
import 'package:robot_worlds_ui/model/robot.dart';
import 'package:robot_worlds_ui/services/robot_service.dart';
import 'package:robot_worlds_ui/views/game/robot.dart';


import 'control_panel.dart';
import 'direction.dart';
import 'mine.dart';
import 'obstacle.dart';

final Color darkBlue = Color.fromARGB(255, 18, 32, 47);


class Game extends StatelessWidget {
  Game(this.robot, this.world);

  Future<Map<String, dynamic>> robot;
  Future<Map<String, dynamic>> world;
  // Future<Map< String , dynamic>> wordData = Controller().getWorld();
  List<Obstacle> getObstacles (List<dynamic> worldData) {
    final obstacles = <Obstacle>[];
    print(worldData);
    List obs = worldData[0]['obstacles'];
    obs.forEach((element) {

      Map ele = element;

      for(var f in ele.values) {
        List topCo = f['position'].toString().split(',');
        double x = double.parse(topCo[0]);
        double y = double.parse(topCo[1]);
        obstacles.add(
          Obstacle(topX: x, topY:  y),
        );
      }
    });
    // obstacles.add(
    //   Obstacle(topX: 5, topY: 5),
    // );
    // obstacles.add(
    //   Obstacle(topX: 20, topY: -20),
    // );
    // obstacles.add(
    //   Obstacle(topX: -70, topY: 50),
    // );
    return obstacles;
  }

  List<Mine> getMines () {
    final Mines = <Mine>[];

    Mines.add(
      Mine(topX: 50, topY: 50),
    );
    Mines.add(
      Mine(topX: 90, topY: -20),
    );
    Mines.add(
      Mine(topX: -70, topY: 100),
    );
    return Mines;
  }

  RobotWidget getRobot (Robot robot) {
    print('robot');
    print(robot.Direction);
    print(robot.Position);

    double x = double.parse(robot.Position[0].toString());
    double y = double.parse(robot.Position[1].toString());


    return RobotWidget(topX: x, topY: y, direction: robot.Direction);
  }

  @override
  Widget build(BuildContext context) {
    Controller().setWordData(context, world);
    Controller().setRobotData(context, robot);
    MediaQueryData queryData;
    queryData = MediaQuery.of(context);
    final double width = queryData.size.width.toDouble();
    final double height =queryData.size.height.toDouble() * 0.75;
    return  FutureBuilder<Map< String , dynamic>>(
        future: world,
        builder: (context, snapshot)
        {
          if (snapshot.hasData) {
            return Consumer<Model>(
                builder: (context, model, child) =>
                    Container(
                        width: width,
                        height: height,
                        color: Colors.white,
                        padding: EdgeInsets.symmetric(
                            horizontal: width / 2, vertical: height / 2),
                        // Inner yellow container
                        child: Stack(
                          children: [
                            Stack(
                              children: getMines(),
                            ),
                            Stack(
                              children: getObstacles(model.getWorldData()),
                            ),
                            getRobot(model.robot)
                          ],
                        )
                    ));
          } else if (snapshot.hasError) {
            return Text('${snapshot.hasError}');
          }
          return const Center(
              child: CircularProgressIndicator()
          );
        }
    );
  }
}

