import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:robot_worlds_ui/views/admin_portal/admin_ui.dart';
import 'package:robot_worlds_ui/views/client_portal/launch.dart';
import 'package:robot_worlds_ui/views/client_portal/robot_type.dart';

import 'model/model.dart';


void main() {
  runApp(MultiProvider(
    providers: [
      ChangeNotifierProvider(create: (context) => Model() )
    ],
    child: MaterialApp(
      home:App( ),
    ),
  )
  );
}

class App extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: MyhomePage(),
    );
  }
}


class MyhomePage extends StatefulWidget {
  const MyhomePage({Key? key}) : super(key: key);

  @override
  _MyhomePageState createState() => _MyhomePageState();
}

class _MyhomePageState extends State<MyhomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.blue,
        title: const Text('Robot Worlds',style:TextStyle(color: Colors.black)),
        centerTitle: true,
      ),
      backgroundColor: Colors.black,
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          SizedBox(
            height: 75,
            child: ElevatedButton(
              style: ButtonStyle(
                backgroundColor:
                MaterialStateProperty.all<Color>(Colors.lightBlue),
                elevation: MaterialStateProperty.all(8),
                shape: MaterialStateProperty.all(
                  RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(80)),
                ),
              ),
              child: const Text('Admin'),
              onPressed: () => Navigator.push(context,
                  MaterialPageRoute(builder: (context) => Admin())),
            ),
          ),
          const SizedBox(height: 25,),
          SizedBox(
            height: 75,
            child: ElevatedButton(
              style: ButtonStyle(
                backgroundColor:
                MaterialStateProperty.all<Color>(Colors.lightBlue),
                elevation: MaterialStateProperty.all(8),
                shape: MaterialStateProperty.all(
                  RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(80)),
                ),
              ),
              onPressed: () => Navigator.push(context,
                  MaterialPageRoute(builder: (context) => Robot_type())),
              child: const Text('Player'),
            ),
          ),
        ],
      ),
    );
  }
}


