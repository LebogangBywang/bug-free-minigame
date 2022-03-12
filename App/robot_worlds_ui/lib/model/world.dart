import 'package:robot_worlds_ui/model/world_objects.dart';

class Load {
  final String _name;
  get name => _name;

  final Map<String, WorldObjects> _worldObjects;
  get worldObjects => _worldObjects;

  Load(this._name, this._worldObjects);
}
