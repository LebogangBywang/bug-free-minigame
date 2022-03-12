package SServer.ClientCommands;

import ServerCommands.PurgeClient;
import SServer.World.Map;
import SServer.World.Mine;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.concurrent.TimeUnit;


public class MineCommand extends Command {
    JSONObject object = new JSONObject();
    JSONArray minesLIst = new JSONArray();
    private Robot robot;

    public MineCommand(Convertor creator, Map map) {
        super();
        this.robot = map.getClients().get(creator.getName());
    }

    @Override
    public boolean execute() throws Error {
        robot.setStatus("SETMINE");
        JSONObject mines = new JSONObject();

        int storedShields = robot.getShields();
        //Robot's shields must be 0 when setting a mine
        robot.setShields(0);
        Position position = robot.getPosition();
//        clientThreadHandler.sendState(this.createResponse(robot));
        Mine mine = new Mine(position.getX(), position.getY());

        mines.put("position",""+position.getX()+","+position.getY());
        mines.put("size",""+1+","+1);
        this.object.put(mine.toString(),minesLIst);

        this.minesLIst.add(object);
        robot.setMineData(minesLIst);

        if (!robot.updatePosition(1)) {
            new PurgeClient(robot.getName()).execute();
        } else {
            robot.addObstacle(mine);
        }

        try {
            TimeUnit.SECONDS.sleep(robot.getMineTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.setShields(storedShields);
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
