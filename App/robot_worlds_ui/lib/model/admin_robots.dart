import 'dart:convert';

class AdminRobots {
  List<Player> _listOfRobots = [];
  AdminRobots(this._listOfRobots);


  factory AdminRobots.fromJson(String jsonString) {
    var fromJson = jsonDecode(jsonString);
    List<Player> listOfRobots = [];
    for (var i in fromJson) {
      var position = i['position'];
      var coordinates = position.split(',');
      listOfRobots.add(Player(i['name'], int.parse(coordinates[0]),
          int.parse(coordinates[1]), i['shields'], i['shots']));
    }
    return AdminRobots(listOfRobots);
  }
}

class Player {
  final String _name;
  get name => _name;

  int? _x;
  get x => _x;

  int? _y;
  get y => _y;

  int? _shields;
  get shield => _shields;

  int? _shots;
  get shots => _shots;

  Player(this._name, this._x, this._y, this._shields, this._shots);

}
