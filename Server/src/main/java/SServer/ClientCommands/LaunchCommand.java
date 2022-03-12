package SServer.ClientCommands;

import SServer.World.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class LaunchCommand extends Command{
    String name;
    ArrayList<String> arguments;
    Map map;
    Robot robot;

    public LaunchCommand(Convertor request, Map map) {
        this.name = request.getName();
        this.arguments = request.getArguments();
        this.map = map;
    }

    @Override
    public boolean execute() throws Error {
        int maxShield = Integer.parseInt(this.arguments.get(1));
        int maxShots = Integer.parseInt(this.arguments.get(2));

        int shields = Math.min(maxShield, map.getInitialize().getShields());
        int shots = Math.min(maxShots, map.getInitialize().getShots());

        for (String cl: map.getClientsNames()) { // REMOVE CLIENTS FROM SERVERMAIN
            if (cl.equalsIgnoreCase(this.name)) {
                throw new Error("Too many of you in this world");
            }

        }
        robot = new Robot(this.name,shields,shots,map);
        map.getClients().put(robot.getName(), robot);
        map.getClientsNames().add(robot.getName());
        return true;
    }

    @Override
    public String createResponse() {
        JSONObject jsonResponse = new JSONObject();
        JSONObject data = new JSONObject();
        JSONObject state = new JSONObject();
        JSONArray position = new JSONArray();

        position.add(robot.getPosition().getX());
        position.add(robot.getPosition().getY());

        data.put("position",position);
        data.put("reload",robot.getReloadTime());
        data.put("repair",robot.getRepairTime());
        data.put("mine",robot.getMineTime());
        data.put("visibility",robot.getVisibility());

        state.put("position",position);
        state.put("shots",robot.getShots());
        state.put("status",robot.getStatus());
        state.put("direction",robot.getDirection().toString());
        state.put("shields",robot.getShields());

        jsonResponse.put("result","OK");
        jsonResponse.put("data",data);
        jsonResponse.put("state",state);

        return jsonResponse.toJSONString();
    }
}
