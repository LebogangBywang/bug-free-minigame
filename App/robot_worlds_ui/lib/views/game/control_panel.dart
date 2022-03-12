import 'package:flutter/material.dart';

import 'direction.dart';

class ControlPanel extends StatelessWidget {

  // const ControlPanel({Key? key, required this.width, required this.height}) : super(key: key);
  // final double width;
  // final double height;
  @override
  Widget build(BuildContext context) {
    MediaQueryData queryData;
    queryData = MediaQuery.of(context);
    final double width = queryData.size.width.toDouble();
    final double height = queryData.size.height.toDouble() * 0.25;
    return Container(
      width: width,
      height: height,
      color: Colors.blueGrey,
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceAround,
            children: [
              FloatingActionButton(
                heroTag: null,
                onPressed: () {
                  // Add your onPressed code here!
                },
                child: const RotatedBox(
                  quarterTurns: 3,
                  child: Icon(Icons.navigation),
                ),
                backgroundColor: Colors.green,
              ),
              const SizedBox(
                width: 50.0,
              ),
              FloatingActionButton(
                heroTag: null,
                onPressed: () {
                  // Add your onPressed code here!
                },
                child: const RotatedBox(
                  quarterTurns: 1,
                  child: Icon(Icons.navigation),
                ),
                backgroundColor: Colors.green,
              ),
            ],
          ),
          Column(
              children: [
                const SizedBox(
                  height: 25.0,
                ),
                FloatingActionButton(
                  heroTag: null,
                  onPressed: () {
                    // Add your onPressed code here!
                  },
                  child: const RotatedBox(
                    quarterTurns: 4,
                    child: Icon(Icons.navigation),
                  ),
                  backgroundColor: Colors.green,
                ),
                const SizedBox(
                  height: 50.0,
                ),
                FloatingActionButton(
                  heroTag: null,
                  onPressed: () {
                    // Add your onPressed code here!
                  },
                  child: const RotatedBox(
                    quarterTurns: 2,
                    child: Icon(Icons.navigation),
                  ),
                  backgroundColor: Colors.green,
                ),
              ]
          )
        ],
      )
    );}
}
