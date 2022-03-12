package SServer.ClientCommands;


import SServer.World.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.ArrayList;

public class FireCommand extends Command {
    private ArrayList<String> arguments;
    private String outcome = "Miss";
    private int shotDistance;
    private Robot enemyRobot;
    private Robot robot;
    private String enemyStatus = "NORMAL";
//    private ClientThreadHandler client;

    public FireCommand(ArrayList<String> arguments) {
        this.arguments = arguments;
    }

    public FireCommand(Convertor creator, Map map) {
        super();
        this.arguments = creator.getArguments();
        this.robot = map.getClients().get(creator.getName());
    }

    @Override
    public boolean execute() throws Error {
        robot.setStatus("NORMAL");
        //Check if the robot has any shots to shoot
        if (robot.getShots() < 1) { return true; }

        shotDistance = robot.getDistance();
        robot.setShots(robot.getShots() - 1);
        Position position = robot.getPosition();
        int newX = position.getX();
        int newY = position.getY();

        switch (robot.getDirection()) {
            case NORTH:
                newY += shotDistance;
                break;
            case SOUTH:
                //Distance will be subtracted from the y-value
                shotDistance *= -1;
                newY += shotDistance;
                break;
            case EAST:
                newX += shotDistance;
                break;
            case WEST:
                //Distance will be subtracted from the x-value
                shotDistance *= -1;
                newX += shotDistance;
        }
//        if (clientThreadHandler.blocksPath(new Position(position.getX(), position.getY()),
//            new Position(newX, newY), robot.getName())) {
//            outcome = "Hit";
//            client = clientThreadHandler.getEnemyClient();
//            enemyRobot = client.getRobot();
//            if (enemyRobot.getShields() == 0) { enemyStatus = "DEAD"; }
//            else { enemyRobot.setShields(enemyRobot.getShields() - 1); }
//            //Get the distance between the 2 robots
//            shotDistance = calculateDistance(position, enemyRobot.getPosition());
//        }
        return true;
    }

    /**
     * Uses the Distance Formula to calculate the distance between the 2 robots
     * Distance^2 = (x1 - x2)^2 + (y1 - y2)^2
     * @param robotPosition The position of the client's robot
     * @param enemyPosition The position of the enemy's robot
     * @return The distance
     */
    private int calculateDistance(Position robotPosition, Position enemyPosition) {

        return (int) Math.sqrt(Math.pow((robotPosition.getX() - enemyPosition.getX()), 2)
                + Math.pow((robotPosition.getY() - enemyPosition.getY()), 2));
    }

    @Override
    public String createResponse() {

        JSONObject response = new JSONObject();
        response.put("result", "OK");

        JSONObject data = new JSONObject();
        data.put("message", outcome);
        if (outcome.equals("Hit")) {
            data.put("distance", shotDistance);
            data.put("robot", enemyRobot.getName());

            JSONObject enemyState = new JSONObject();
            JSONArray enemyPosition = new JSONArray();
            enemyPosition.add(enemyRobot.getPosition().getX());
            enemyPosition.add(enemyRobot.getPosition().getY());
            enemyState.put("position", enemyPosition);
            enemyState.put("direction", enemyRobot.getDirection().toString());
            enemyState.put("shields", enemyRobot.getShields());
            enemyState.put("shots", enemyRobot.getShots());
            enemyState.put("status", enemyStatus);
            data.put("state", enemyState);
            //Send the state to the enemy client so they know they've been shot
//            JSONObject stateToSend = new JSONObject();
//            stateToSend.put("state", enemyState);
//            client.sendState(stateToSend.toJSONString());
//            if (enemyStatus.equalsIgnoreCase("dead"))
//                new PurgeClient(client.getRobot().getName()).execute();
        }
        response.put("data", data);

        JSONObject state = new JSONObject();
        JSONArray positionArray = new JSONArray();
        positionArray.add(robot.getPosition().getX());
        positionArray.add(robot.getPosition().getY());
        state.put("position", positionArray);
        state.put("direction", robot.getDirection().toString());
        state.put("shields", robot.getShields());
        state.put("shots", robot.getShots());
        state.put("status", "NORMAL");
        response.put("state", state);

        return response.toJSONString();
    }
}
