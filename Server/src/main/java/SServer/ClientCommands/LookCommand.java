package SServer.ClientCommands;

import SServer.World.Mine;
import SServer.World.Obstacle;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public class LookCommand extends Command {

    Robot robot;
    ArrayList<Map<String, String>> spottedObstacles = new ArrayList<>();
    Map<String, String> currentObst;
    ArrayList<Object> obstacles;
    int visibility;
    int steps = 0;
    Position position;
    boolean checkNorth = true;
    boolean checkSouth = true;
    boolean checkWest = true;
    boolean checkEast = true;
    boolean shouldContinue = true;
    SServer.World.Map map;

    public LookCommand(Convertor creator, SServer.World.Map map) {
        super();
        this.map = map;
        this.robot = map.getClients().get(creator.getName());
    }

    @Override
    public boolean execute() throws Error {

        obstacles = map.getObstacles(); // REMOVE CLIENTS FROM SERVERMAIN
        visibility = robot.getVisibility();
        position = robot.getPosition();

        for (steps = 1; steps <= visibility; steps++) {
            for (Object object : obstacles) {
                if (checkNorth) spottedObstacles.add(checkNorth(object));
                if (checkSouth) spottedObstacles.add(checkSouth(object));
                if (checkWest) spottedObstacles.add(checkWest(object));
                if (checkEast) spottedObstacles.add(checkEast(object));
                
            }


            for (Object client: map.getClients().values()){
                System.out.println(checkNorth);
                if (checkNorth) spottedObstacles.add(checkNorth(client));
                if (checkSouth) spottedObstacles.add(checkSouth(client));
                if (checkWest) spottedObstacles.add(checkWest(client));
                if (checkEast) spottedObstacles.add(checkEast(client));
                if(!shouldContinue) {
                    break;
                }

            }
        }
        spottedObstacles.removeAll(Collections.singleton(null));
        return true;
    }

    public Map<String, String> checkNorth(Object object) {
        System.out.println(object.getClass().getName());
        switch (object.getClass().getName()) {
            case "SServer.World.Obstacle":
                Obstacle obstacle = (Obstacle) object;

                if (obstacle.blocksPosition(new Position(position.getX(), position.getY() + steps))) {
                    currentObst = new HashMap<>();
                    currentObst.put("direction", "NORTH");
                    currentObst.put("distance", String.valueOf(steps));
                    currentObst.put("type", "OBSTACLE");
                    return currentObst;
                }
                break;
            case "SServer.ClientCommands.Robot":
                Robot enemyRobot = (Robot)  object;
                System.out.println("look robot");
                System.out.println(position.getX());
                System.out.println(position.getY());
                if (enemyRobot.blocksPosition(new Position(position.getX(), position.getY() + steps))) {
                    currentObst = new HashMap<>();
                    currentObst.put("direction", "NORTH");
                    currentObst.put("distance", String.valueOf(steps));
                    currentObst.put("type", "ROBOT");
                    shouldContinue = false;
                    return currentObst;
                }
                break;
            case "SServer.World.Pits":
                Obstacle pit = (Obstacle) object;
                if (pit.blocksPosition(new Position(position.getX(), position.getY() + steps))) {
                    currentObst = new HashMap<>();
                    currentObst.put("direction", "NORTH");
                    currentObst.put("distance", String.valueOf(steps));
                    currentObst.put("type", "PIT");
                    return currentObst;
                }
                break;
            case "SServer.World.Mine":
                if (steps < visibility / 4) {
                    Mine mine = (Mine) object;
                    if (mine.blocksPosition(new Position(position.getX(), position.getY() + steps))) {
                        currentObst = new HashMap<>();
                        currentObst.put("direction", "NORTH");
                        currentObst.put("distance", String.valueOf(steps));
                        currentObst.put("type", "MINE");
                        return currentObst;
                    }
                }
                break;
        }
        return null;
    }

    public Map<String, String> checkSouth(Object object) {
        switch (object.getClass().getName()) {
            case "SServer.World.Obstacle":
                Obstacle obstacle = (Obstacle) object;
                if (obstacle.blocksPosition(new Position(position.getX(), position.getY() - steps))) {
                    currentObst = new HashMap<>();
                    currentObst.put("direction", "SOUTH");
                    currentObst.put("distance", String.valueOf(steps));
                    currentObst.put("type", "OBSTACLE");
                    return currentObst;

                }
                break;
            case "SServer.ClientCommands.Robot":
                Robot enemyRobot = (Robot)  object;
                if (enemyRobot.blocksPosition(new Position(position.getX(), position.getY() - steps))) {
                    currentObst = new HashMap<>();
                    currentObst.put("direction", "SOUTH");
                    currentObst.put("distance", String.valueOf(steps));
                    currentObst.put("type", "ROBOT");
                    shouldContinue = false;
                    return currentObst;
                }
                break;
            case "SServer.World.Pits":
                Obstacle pit = (Obstacle) object;
                if (pit.blocksPosition(new Position(position.getX(), position.getY() - steps))) {
                    currentObst = new HashMap<>();
                    currentObst.put("direction", "SOUTH");
                    currentObst.put("distance", String.valueOf(steps));
                    currentObst.put("type", "PIT");
                    return currentObst;
                }
                break;
            case "SServer.World.Mine":
                if (steps < visibility / 4) {
                    Mine mine = (Mine) object;
                    if (mine.blocksPosition(new Position(position.getX(), position.getY() - steps))) {
                        currentObst = new HashMap<>();
                        currentObst.put("direction", "SOUTH");
                        currentObst.put("distance", String.valueOf(steps));
                        currentObst.put("type", "MINE");
                        return currentObst;
                    }
                }
                break;
        }
        return null;
    }

    public Map<String, String> checkEast(Object object) {
        switch (object.getClass().getName()) {
            case "SServer.World.Obstacle":
                Obstacle obstacle = (Obstacle) object;
                if (obstacle.blocksPosition(new Position(position.getX() + steps, position.getY()))) {
                    System.out.println("working");
                    currentObst = new HashMap<>();
                    currentObst.put("direction", "EAST");
                    currentObst.put("distance", String.valueOf(steps));
                    currentObst.put("type", "OBSTACLE");
                    return currentObst;
                }
                break;
            case "SServer.ClientCommands.Robot":
                Robot enemyRobot = (Robot)  object;
                if (enemyRobot.blocksPosition(new Position(position.getX() + steps, position.getY()))) {
                    currentObst = new HashMap<>();
                    currentObst.put("direction", "EAST");
                    currentObst.put("distance", String.valueOf(steps));
                    currentObst.put("type", "ROBOT");
                    shouldContinue = false;
                    return currentObst;
                }
                break;
            case "SServer.World.Pits":
                Obstacle pit = (Obstacle) object;
                if (pit.blocksPosition(new Position(position.getX() + steps, position.getY()))) {
                    currentObst = new HashMap<>();
                    currentObst.put("direction", "EAST");
                    currentObst.put("distance", String.valueOf(steps));
                    currentObst.put("type", "PIT");
                    return currentObst;
                }
                break;
            case "SServer.World.Mine":
                if (steps < visibility / 4) {
                    Mine mine = (Mine) object;
                    if (mine.blocksPosition(new Position(position.getX() + steps, position.getY()))) {
                        currentObst = new HashMap<>();
                        currentObst.put("direction", "EAST");
                        currentObst.put("distance", String.valueOf(steps));
                        currentObst.put("type", "MINE");
                        return currentObst;
                    }
                }
                break;
        }
        return null;
    }

    public Map<String, String> checkWest(Object object) {
        switch (object.getClass().getName()) {
            case "SServer.World.Obstacle":
                Obstacle obstacle = (Obstacle) object;
                if (obstacle.blocksPosition(new Position(position.getX() - steps, position.getY()))) {
                    currentObst = new HashMap<>();
                    currentObst.put("direction", "WEST");
                    currentObst.put("distance", String.valueOf(steps));
                    currentObst.put("type", "OBSTACLE");
                    return currentObst;
                }
                break;
            case "SServer.ClientCommands.Robot":
                Robot enemyRobot = (Robot)  object;
                if (enemyRobot.blocksPosition(new Position(position.getX() - steps, position.getY()))) {
                    currentObst = new HashMap<>();
                    currentObst.put("direction", "WEST");
                    currentObst.put("distance", String.valueOf(steps));
                    currentObst.put("type", "ROBOT");
                    shouldContinue = false;
                    return currentObst;
                }
                break;
            case "SServer.World.Pits":
                Obstacle pit = (Obstacle) object;
                if (pit.blocksPosition(new Position(position.getX() - steps, position.getY()))) {
                    currentObst = new HashMap<>();
                    currentObst.put("direction", "WEST");
                    currentObst.put("distance", String.valueOf(steps));
                    currentObst.put("type", "PIT");
                    return currentObst;
                }
                break;
            case "SServer.World.Mine":
                if (steps < visibility / 4) {
                    Mine mine = (Mine) object;
                    if (mine.blocksPosition(new Position(position.getX() - steps, position.getY()))) {
                        currentObst = new HashMap<>();
                        currentObst.put("direction", "WEST");
                        currentObst.put("distance", String.valueOf(steps));
                        currentObst.put("type", "MINE");
                        return currentObst;
                    }
                }
                break;
        }
        return null;
    }

    @Override
    public String createResponse() {

        JSONObject response = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray objects = new JSONArray();
        for (Map map : spottedObstacles) {
            objects.add(new JSONObject(map));
        }
        data.put("objects", objects);
        response.put("data", data);
        JSONObject state = new JSONObject();
        JSONArray position = new JSONArray();
        position.add(robot.getPosition().getX());
        position.add(robot.getPosition().getY());
        state.put("position", position);
        state.put("direction", robot.getDirection().toString());
        state.put("shields", robot.getShields());
        state.put("shots", robot.getShots());
        state.put("status", "NORMAL");
        response.put("state", state);
        response.put("result", "OK");

        return response.toJSONString();
    }
}
