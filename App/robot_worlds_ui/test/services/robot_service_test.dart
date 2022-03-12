import 'dart:async';
import 'dart:convert';

import 'package:flutter_test/flutter_test.dart';
import 'package:robot_worlds_ui/services/robot_service.dart';

void main() {
  group('Testing App Service', () {
    String robot = "[\"shooter\", \"5\", \"5\"]";

    test('The robot should launch', () async {
      await getRobot("jeff", robot).then((value) async {
        print(value);

        expect(value['result'].toString().contains("OK"), true);
      });
    });

    test('get world data', () async {
      await getWorld().then((value) async {
        print(value);

        expect(value['mines'].toString().contains("[]"), true);
        expect(value['obstacles'].toString().contains("[]"), true);
        expect(value['pits'].toString().contains("[]"), true);
        expect(
            value['worldSize'].toString().contains("{length: 10, width: 10}"),
            true);
      });
    });

    test('The launched robot should move forward', () async {
      await getRobot("mike", robot).then((value) async {
        print(value);
        var res = await moveForward("mike");
        print(res);

        print(res['data']["message"]);
        expect(res['result'].toString().contains("OK"), true);
        expect(
            res['data']["message"].toString().contains("DONE") ||
                res['data']["message"].toString().contains("OBSTRUCTED"),
            true);
      });
    });

    test('The launched robot should move back', () async {
      await getRobot("hall", robot).then((value) async {
        print(value);
        var res = await moveBack("hall");
        print(res);

        print(res['data']["message"]);
        expect(res['result'].toString().contains("OK"), true);
        expect(
            res['data']["message"].toString().contains("DONE") ||
                res['data']["message"].toString().contains("OBSTRUCTED"),
            true);
      });
    });

    test('The launched robot should turn right', () async {
      await getRobot("lebo", robot).then((value) async {
        print(value);
        var res = await turnRight("lebo");
        print(res);

        print(res['data']["message"]);
        expect(res['result'].toString().contains("OK"), true);
        expect(res['data']["message"].toString().contains("Done"), true);
        expect(
            res['state']['direction']
                .toString()
                .contains(value['state']['direction'].toString()),
            false);
      });
    });

    test('The launched robot should turn left', () async {
      await getRobot("sam", robot).then((value) async {
        print(value);
        var res = await turnLeft("sam");
        print(res);

        print(res['data']["message"]);
        expect(res['result'].toString().contains("OK"), true);
        expect(res['data']["message"].toString().contains("Done"), true);
        expect(
            res['state']['direction']
                .toString()
                .contains(value['state']['direction'].toString()),
            false);
      });
    });
  });
}
