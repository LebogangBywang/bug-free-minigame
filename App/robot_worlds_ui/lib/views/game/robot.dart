import 'package:flutter/material.dart';
import 'package:robot_worlds_ui/model/robot.dart';

final Color darkBlue = Color.fromARGB(255, 18, 32, 47);


class RobotWidget extends StatelessWidget {
  final double topX, topY;
  final String direction;

  const RobotWidget({Key? key, required this.topX,  required this.topY, required this.direction}) : super(key: key);


  @override
  Widget build(BuildContext context) {
    return  Transform(
      alignment: FractionalOffset.topLeft, // set transform origin
      transform: Matrix4.rotationX(3.137), // rotate -10 deg
      child: Container(
        // pass double.infinity to prevent shrinking of the painter area to 0.
        color: Colors.black,
        child: CustomPaint(painter: FaceOutlinePainter(topX, topY, direction),),
      ),

    );
  }
}

class FaceOutlinePainter extends CustomPainter {

  final double topX, topY;
  final String direction;
  FaceOutlinePainter(this.topX, this.topY, this.direction);
  @override
  void paint(Canvas canvas, Size size) {
    // Define a paint object
    final paint = Paint()
      ..style = PaintingStyle.stroke
      ..strokeWidth = 10.0
      ..color = Colors.indigo;

    final path1 = Path();
    // path1.moveTo(-20, 20);
    // path1.lineTo(-22, 18);
    // // path1.lineTo(-15, 15);
    // // path1.close();
    if (direction == 'NORTH') {
      path1.addPolygon([
        Offset(topX - 2, topY - 2), //bottom left
        Offset(topX, topY),
        Offset(topX + 2, topY - 2) //bottom right

      ], false);
    } else if (direction == 'SOUTH') {
      path1.addPolygon([
        Offset(topX - 2, topY + 2), //bottom right
        Offset(topX, topY),
        Offset(topX + 2, topY + 2) //bottom left

      ], false);
    } else if (direction == 'EAST') {
      path1.addPolygon([
        Offset(topX - 2, topY + 2), //bottom rifgt
        Offset(topX, topY),
        Offset(topX - 2, topY - 2) //bottom left

      ], false);
    } else if (direction == 'WEST') {
      path1.addPolygon([
        Offset(topX + 2, topY + 2), //bottom rifht
        Offset(topX, topY),
        Offset(topX + 2, topY - 2) //bottom left

      ], false);
    }


    canvas.drawPath(path1, paint);
  }

  @override
  bool shouldRepaint(FaceOutlinePainter oldDelegate) => false;
}
