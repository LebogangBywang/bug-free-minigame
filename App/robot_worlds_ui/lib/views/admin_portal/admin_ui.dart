import 'package:flutter/material.dart';
import 'bottom-nav/bottom_nav_ui.dart';
import 'robot-list/robot_list_ui.dart';

class Admin extends StatefulWidget {
  const Admin({ Key? key }) : super(key: key);

  @override
  _AdminState createState() => _AdminState();
}

class _AdminState extends State<Admin> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Adminstrator Page')
      ),
      body: const RobotList(),
      bottomNavigationBar: const BottomNav(),
    );
  }
}