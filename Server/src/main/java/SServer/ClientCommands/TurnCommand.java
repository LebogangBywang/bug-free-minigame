package SServer.ClientCommands;

import SServer.World.Direction;
import SServer.World.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class TurnCommand extends Command {

    private ArrayList<String> arguments;
    private String robotName;
    public Robot robot;

    public TurnCommand(Convertor creator, Map map) {
        super();
        this.arguments = creator.getArguments();
        this.robotName = creator.getName();
        this.robot = map.getClients().get(creator.getName());
    }

    @Override
    public boolean execute() throws Error {
        robot.setStatus("NORMAL");
        if(this.arguments.size() != 1) throw new Error("Could not parse arguments");
        if(!this.arguments.get(0).equalsIgnoreCase("right") &&
                !this.arguments.get(0).equalsIgnoreCase("left"))
            throw new Error("Could not parse arguments");

        int index;
        switch (arguments.get(0).toLowerCase(Locale.ROOT)) {
            case "right":
                index = robot.getDirection().ordinal() + 1;
                //Reset the direction back to North
                if (index == 4) index = 0;
                robot.setDirection(Direction.values()[index]);
                break;
            case "left":
                index = robot.getDirection().ordinal() - 1;
                //Reset the direction back to West
                if (index == -1) index = 3;
                robot.setDirection(Direction.values()[index]);
                break;
        }
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
        state.put("status", robot.getStatus());
        state.put("direction", robot.getDirection().toString());
        state.put("shields", robot.getShields());

        response.put("state", state);

        return response.toJSONString();
    }
}
