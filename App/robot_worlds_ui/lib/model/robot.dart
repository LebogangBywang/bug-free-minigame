class Robot {

  String? direction;

  List position;

  Robot({required this.position, required this.direction});

  factory Robot.fromJson(json) => Robot(position: json["state"]["position"],direction:json["state"]["direction"]);


  get Direction => direction;
  get Position => position;

}
