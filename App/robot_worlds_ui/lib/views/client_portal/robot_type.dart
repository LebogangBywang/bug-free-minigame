
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

import '../../main.dart';
import 'launch.dart';

class Robot_type extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: options(context),
    );
  }

  Widget options(BuildContext context) => Scaffold(
    backgroundColor: Colors.black,
    appBar: AppBar(
      leading: IconButton(
        onPressed: () {
          Navigator.push(
              context,
              MaterialPageRoute(
                builder: (context) => MyhomePage(),
              ));
        },
        icon: const Icon(
          Icons.arrow_back_sharp,
          color: Colors.black,
        ),
      ),
      backgroundColor: Colors.blue,
      title: const Text('Robot Worlds',style: TextStyle(color: Colors.black),),
      centerTitle: true,
    ),
    body: Column(
      mainAxisAlignment: MainAxisAlignment.center,
      crossAxisAlignment: CrossAxisAlignment.stretch,
      children: [
        SizedBox(
          height: 50,
          child: ElevatedButton(
            onPressed: () {
              String robot = "[\"shooter\", \"5\", \"5\"]";
              Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => Launch(robot: robot)));
            },
            child: const Text(
              "Sniper",
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
        ),
        SizedBox(
          height: 25,
        ),
        SizedBox(
          height: 50,
          child: ElevatedButton(
            onPressed: () {
              String robot = "[\"shooter\", \"5\", \"5\"]";
              Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => Launch(robot: robot)));
            },
            child: const Text(
              "Sprinter",
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
        ),
        SizedBox(
          height: 25,
        ),
        SizedBox(
          height: 50,
          child: ElevatedButton(
            onPressed: () {
              String robot = "[\"shooter\", \"5\", \"5\"]";
              Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => Launch(robot: robot)));
            },
            child: const Text(
              "killer",
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
}
