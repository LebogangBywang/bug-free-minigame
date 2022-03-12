import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:robot_worlds_ui/controller/controller.dart';
import 'package:robot_worlds_ui/views/admin_portal/bottom-nav/bottom_nav_ui.dart';
import 'package:robot_worlds_ui/views/client_portal/robot_type.dart';
import 'package:robot_worlds_ui/views/game/main.dart';

import '../../controller/controller.dart';
import '../../main.dart';

class Launch extends StatefulWidget {
  String robot;
  Launch({Key? key, required this.robot}) : super(key: key);

  @override
  _LaunchState createState() => _LaunchState();
}

class _LaunchState extends State<Launch> {
  final nameController = TextEditingController();

  String name = "";

  @override
  void initState() {
    super.initState();

    nameController.addListener(() => setState(() {}));
  }

  @override
  void dispose() {
    nameController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        backgroundColor: Colors.black,
        appBar: AppBar(
          leading: IconButton(
            icon: Icon(
              Icons.arrow_back_sharp,
              color: Colors.black,
            ),
            onPressed: () {
              Navigator.push(context,
                  MaterialPageRoute(builder: (context) => Robot_type()));
            },
          ),
          backgroundColor: Colors.blue,
          title: const Text(
            'Robot Worlds',
            style: TextStyle(color: Colors.black),
          ),
          centerTitle: true,
        ),
        // backgroundColor: Colors.black,
        body: launchActions(),
      ),
    );
  }

  Widget launchActions() => Padding(
        padding: const EdgeInsets.fromLTRB(35, 0, 35, 0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            textField(),
            const SizedBox(
              height: 25,
            ),
            SizedBox(
              height: 50,
              width: 50,
              child: ElevatedButton(
                onPressed: () {
                  Navigator.push(context,
                      MaterialPageRoute(builder: (context) =>Main(Controller().getRobotData(name, widget.robot),
                          Controller().getWorldData()) ));
                },
                child: const Text(
                  "launch",
                  style: TextStyle(color: Colors.white),
                ),
                style: ButtonStyle(
                  backgroundColor:
                      MaterialStateProperty.all<Color>(Colors.lightBlue),
                  elevation: MaterialStateProperty.all(8),
                  shape: MaterialStateProperty.all(
                    RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(80)),
                  ),
                ),
              ),
            )
          ],
        ),
      );

  Widget textField() => TextField(
        style: TextStyle(color: Colors.black),
        onChanged: (value) => name = value,
        controller: nameController,
        decoration: const InputDecoration(
          filled: true,
          fillColor: Colors.white,
          label: Text("robot name",style: TextStyle(color: Colors.black),),
          hintText: 'type here',
        ),
      );
}
