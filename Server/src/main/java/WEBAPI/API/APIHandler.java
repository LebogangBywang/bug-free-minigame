package WEBAPI.API;

import DataBase.DbConnect;
import SServer.ClientCommands.Command;
import SServer.ClientCommands.Error;
import ServerCommands.SaveCommand;
import WEBAPI.worldLayer.ClientList;
import WEBAPI.worldLayer.Save;
import ServerCommands.ServerArguments;
import SServer.World.Map;
import WEBAPI.worldLayer.APIWorld;
import io.javalin.http.Context;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.SQLException;

public class APIHandler {
    private APIWorld world;
    private DbConnect dbConnect;
    private Command command;

    public APIHandler(ServerArguments serverArguments, Map map) {
        this.world = new APIWorld(serverArguments, map);
        this.dbConnect = new DbConnect();
    }

    public void getDBData(Context context, Map map) {
        String request = context.path().split("/")[3];
        this.dbConnect.restoreWorld(request);
        JSONObject response = this.dbConnect.getDAta();
        context.json(response.toJSONString());
    }

    public void getWorldData(Context context) {
        JSONObject objects = this.world.getObjects();
        context.json(objects.toJSONString());
    }


    public void postDAta(Context context, Map map) {
        String returnOut = "";
        String body = context.body();

        try {
            command = Command.create(body, map);
            command.execute();
            returnOut = command.createResponse();
            context.result(returnOut);
        } catch (Error error) {
            returnOut = error.buildResponse();
            context.status(400);
            context.json(returnOut);
        }
    }


    public void saveData(Context context, Map map) {
        String name;
        name = context.path().split("/")[3];
        SaveCommand save = new SaveCommand(name, map,false);

        if (!save.execute()) {
            JSONObject error = new JSONObject();
            error.put("error", "Sorry the world aready exists");
            context.result(error.toJSONString());
            context.status(400);
        }

    }


    public void getRobotList(Context context, Map map) {
        JSONArray clients = new JSONArray();
        try {

            for (int i = 0; i < map.getClientsNames().size(); i++) {
                String names = map.getClientsNames().get(i);
                ClientList clientList = new ClientList(map.getClients().get(names));
                clients.add(clientList.getObject());
            }
            context.json(clients);
        } catch (NullPointerException e) {
            context.status(400);
        }
    }


    public void purgeRobot(Context context, Map map) {
        String name = context.path().split("/")[3];

        boolean robotExists = false;
        for (String client : map.getClientsNames()) if (client.equalsIgnoreCase(name)) robotExists = true;
        if (robotExists == true) {
            int iter = map.getClientsNames().indexOf(name);
            map.getClientsNames().remove(iter);
            map.getClients().remove(name);
            context.status(200);
        } else context.status(400);
    }


    public void setObstacles(Context context, Map map) {
        String request = context.body();
        JSONParser parser = new JSONParser();
        try {
            JSONArray obstacles = (JSONArray) parser.parse(request);
            for (Object object :
                    obstacles) {
                map.addObstacles((JSONObject) object);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            context.status(400);
        }
    }


    public void deleteObstacles(Context context, Map map) {
        String request = context.body();
        JSONParser parser = new JSONParser();
        try {
            JSONArray obstacles = (JSONArray) parser.parse(request);
            for (Object object :
                    obstacles) {
                map.removeObstacles((JSONObject) object);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            context.status(400);
        }
    }

    public void override(Context context, Map map) {
        String name;
        name = context.path().split("/")[3];
        SaveCommand save = new SaveCommand(name, map,true);

        if (!save.execute()) {
            JSONObject error = new JSONObject();
            error.put("error", "sorry could not override");
            context.result(error.toJSONString());
            context.status(400);
        }
    }

    public void getWorlds(Context context, Map map) {
        JSONArray worlds = new JSONArray();
        worlds = map.getDBConnect().getWorlds();
        context.result(worlds.toJSONString());
    }
}