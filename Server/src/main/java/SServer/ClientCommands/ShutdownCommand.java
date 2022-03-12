package SServer.ClientCommands;

import SServer.World.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ShutdownCommand extends Command {
    Robot robot;
    Map map;
    String robotName;

    public ShutdownCommand(Convertor creator, Map map) {
        super();
        this.robotName = creator.getName();
        System.out.println(this.robotName);
        this.map = map;
        this.robot = map.getClients().get(creator.getName());
        System.out.println(this.robot.getName());
    }

    @Override
    public boolean execute() throws Error {
        return true;
    }

    @Override
    public String createResponse() {

        JSONObject response = new JSONObject();
        response.put("result", "OK");

        JSONObject data = new JSONObject();
        data.put("message", "Done");
        response.put("data", data);

        JSONObject state = new JSONObject();
        JSONArray position = new JSONArray();
        position.add(robot.getPosition().getX());
        position.add(robot.getPosition().getY());
        state.put("position", position);
        state.put("shots", robot.getShots());
        state.put("status", "quit");
        state.put("direction", robot.getDirection().toString());
        state.put("shields", robot.getShields());

        response.put("state", state);
        System.out.println(map.getClients());
        System.out.println(this.robotName);
        map.getClients().remove(this.robotName);
        map.getClientsNames().remove(this.robotName);
        return response.toJSONString();
    }
}
