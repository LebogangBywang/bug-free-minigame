package WEBAPI.worldLayer;

import SServer.ClientCommands.Robot;
import SServer.World.Direction;
import org.json.simple.JSONObject;

public class ClientList {
    private final Robot robot;
    JSONObject object = new JSONObject();

    public ClientList(Robot robot){
        this.robot = robot;
        makeList();
    }

    private void makeList(){
        object.put("name",robot.getName());
        object.put("position",robot.getPosition().getX() + "," + robot.getPosition().getY());
        object.put("direction",  robot.getDirection());
        object.put("shields", robot.getShields());
        object.put("shots", robot.getShots());
        object.put("state", robot.getStatus());
    }

    public JSONObject getObject() {
        return object;
    }
}
