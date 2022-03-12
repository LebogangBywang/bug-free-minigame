package SServer.ClientCommands;

import SServer.World.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class StateCommand extends Command {
    private Robot robot;
    public StateCommand(Convertor creator, Map map) {
        super();
        this.robot = map.getClients().get(creator.getName());
    }

    @Override
    public boolean execute() throws Error {
        return true;
    }

    @Override
    public String createResponse() {

        JSONObject response = new JSONObject();
        JSONObject state = new JSONObject();
        JSONArray position = new JSONArray();

        position.add(robot.getPosition().getX());
        position.add(robot.getPosition().getY());
        state.put("position", position);
        state.put("shields", robot.getShields());
        state.put("shots", robot.getShots());
        state.put("direction", robot.getDirection().toString());
        state.put("status", robot.getStatus());
        response.put("result", "OK");
        response.put("state", state);
        return response.toJSONString();
    }
}
