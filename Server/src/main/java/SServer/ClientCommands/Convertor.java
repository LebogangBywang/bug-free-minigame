package SServer.ClientCommands;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class Convertor {
    String jsonString;
    String name;
    String command;
    ArrayList<String> args = new ArrayList<>();

    public Convertor(String userInput) {
        this.jsonString = userInput;
    }

    public Convertor() {}

    /**
     * Converts the json string to a json object
     * Gets the command a client wishes to use
     * Gets the arguments the client wants to use with their command
     * @throws ParseException
     */
    public void convertData() throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
        name = (String) jsonObject.get("robot");
        command = (String) jsonObject.get("command");
        JSONArray jsonArray = (JSONArray) jsonObject.get("arguments");
        if (jsonArray != null) {
            for (Object o : jsonArray) {
                args.add(o.toString());
            }
        }
    }

    /**
     * @return String command
     */
    public String getCommand() {
        return this.command;
    }

    /**
     * @return arraylist of the arguments
     */
    public ArrayList<String> getArguments() {
        return this.args;
    }

    /**
     * The default response that gets sent to the client
     * @return
     */
//    public String createResponse(Robot robot) {
//        JSONObject jsonResponse = new JSONObject();
//        JSONObject data = new JSONObject();
//        JSONObject state = new JSONObject();
//        JSONArray position = new JSONArray();
//
//        position.add(robot.getPosition().getX());
//        position.add(robot.getPosition().getY());
//
//        data.put("message","Done");
//
//        state.put("position",position);
//        state.put("shots",robot.getShots());
//        state.put("status",robot.getStatus());
//        state.put("direction",robot.getDirection().toString());
//        state.put("shields",robot.getShields());
//
//        jsonResponse.put("result","OK");
//        jsonResponse.put("data",data);
//        jsonResponse.put("state",state);
//
//        return jsonResponse.toJSONString();
//    }

    public String getName() {
        return this.name;
    }
}