package SServer.ClientCommands;


import SServer.World.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;


/**
 * Enables the robot to move forward with the specified steps
 */

public class ForwardCommand extends Command{
    private ArrayList<String> arguments;
    private String robotName;
    public Robot robot;

    public ForwardCommand(Convertor creator, Map map) {
        super();
        this.arguments = creator.getArguments();
        this.robotName = creator.getName();
        this.robot = map.getClients().get(creator.getName());
    }

    @Override
    public boolean execute() throws Error {
        robot.setStatus("NORMAL");
        if(this.arguments.size() != 1) throw new Error("Could not parse arguments");
        else if(!this.arguments.get(0).matches("\\d+")) throw new Error("Could not parse arguments");

        int nrSteps = Integer.parseInt(this.arguments.get(0));
        if (robot.updatePosition(nrSteps)) {
            if (robot.getResponse().equals(Response.FELL)) {
                robot.setStatus("DEAD");
//                doKill(clientThreadHandler,robot);
                return false;
            } else if (robot.getResponse().equals(Response.MINE)) {
                if (robot.getShields() - 3 < 0) {
                    robot.setStatus("DEAD");
//                    doKill(clientThreadHandler,robot);
                    return false;
                } else {
                    robot.setShields(robot.getShields() - 3);
                    robot.setStatus("NORMAL");
                }
            }
            robot.setResponse(Response.DONE);
        }
        return true;
    }

//    private void doKill(ClientThreadHandler clientThreadHandler, Robot robot) {
//        this.createResponse();
//        new PurgeClient(robot.getName()).execute();
//    }

    @Override
    public String createResponse() {
        JSONObject jsonResponse = new JSONObject();
        JSONObject data = new JSONObject();
        JSONObject state = new JSONObject();
        JSONArray position = new JSONArray();

        position.add(robot.getPosition().getX());
        position.add(robot.getPosition().getY());

        data.put("message",robot.getResponse().toString());

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
