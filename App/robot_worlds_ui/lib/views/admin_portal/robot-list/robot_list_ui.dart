import 'package:flutter/material.dart';

class RobotList extends StatefulWidget {
  const RobotList({Key? key}) : super(key: key);

  @override
  _RobotListState createState() => _RobotListState();
}

class _RobotListState extends State<RobotList> {
  
  static const TextStyle optionStyle =
      TextStyle(fontSize: 20, fontWeight: FontWeight.w500);
  @override
  Widget build(BuildContext context) {
    return Column(
      children: const <Widget>[
        ExpansionTile(
          title: Text(
            'Sam',
            style: optionStyle,
          ),
          controlAffinity: ListTileControlAffinity.leading,
          trailing: Icon(
            Icons.close,
            color: Colors.red,
          ),
          children: <Widget>[
            ListTile(title: Text('Name: Sam')),
            ListTile(title: Text("State: Normal")),
            ListTile(title: Text("Shields: 3")),
            ListTile(title: Text("Position: (9,9)")),
            ListTile(title: Text("Direction: North")),
            // {"shields":4,"name":"sifiso","position":"9,9","state":"NORMAL","shots":5,"direction":"NORTH"}
          ],
        ),
      ],
    );
  }
}
