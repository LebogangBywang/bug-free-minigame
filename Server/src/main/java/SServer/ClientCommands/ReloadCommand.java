package SServer.ClientCommands;

import SServer.World.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.concurrent.TimeUnit;

public class ReloadCommand extends Command{
    Robot robot;
    public ReloadCommand(Convertor creator, Map map) {
        super();
        this.robot = map.getClients().get(creator.getName());
    }

    @Override
    public boolean execute() throws Error {
        robot.setStatus("RELOAD");
//        clientThreadHandler.sendState(this.createResponse(robot));

        int shots = robot.getMAX_SHOTS();
        try {
            TimeUnit.SECONDS.sleep(robot.getReloadTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        robot.setShots(shots);
        robot.setStatus("NORMAL");

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
        JSONArray positionArray = new JSONArray();
        positionArray.add(robot.getPosition().getX());
        positionArray.add(robot.getPosition().getY());
        state.put("position", positionArray);
        state.put("direction", robot.getDirection().toString());
        state.put("shields", robot.getShields());
        state.put("shots", robot.getShots());
        state.put("status", robot.getStatus());
        response.put("state", state);

        return response.toJSONString();
    }
}
