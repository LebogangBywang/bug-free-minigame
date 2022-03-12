import 'package:flutter/material.dart';

final Color darkBlue = Color.fromARGB(255, 18, 32, 47);


class Obstacle extends StatelessWidget {

  final double topX, topY;

  const Obstacle({Key? key,required this.topX, required this.topY}) : super(key: key);


  @override
  Widget build(BuildContext context) {
    return  Transform(
              alignment: FractionalOffset.topLeft, // set transform origin
              transform: Matrix4.rotationX(3.137), // rotate -10 deg
              child: Container(
                // pass double.infinity to prevent shrinking of the painter area to 0.
                color: Colors.black,
                child: CustomPaint(painter: FaceOutlinePainter(topX, topY),),
              ),

    );
  }
}

class FaceOutlinePainter extends CustomPainter {
  final double topX, topY;
  FaceOutlinePainter(this.topX, this.topY);

  @override
  void paint(Canvas canvas, Size size) {
    // Define a paint object
    final paint = Paint()
      ..style = PaintingStyle.stroke
      ..strokeWidth = 1.0
      ..color = Colors.red;

    var a = Offset(topX,topY);
    var b = Offset(topX + 5, topY - 5);
    final rect = Rect.fromPoints(a, b);

    canvas.drawRect(rect, paint);
  }

  @override
  bool shouldRepaint(FaceOutlinePainter oldDelegate) => false;
}
