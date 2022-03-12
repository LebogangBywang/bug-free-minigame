package WEBAPI.API;

import SServer.Network.CommandListener;
import ServerCommands.ServerArguments;
import SServer.World.Map;
import io.javalin.Javalin;


public class APIServer {
    private static ServerArguments serverArguments;
    Javalin server;

    public APIServer(ServerArguments args, Map map){

        APIHandler apiHandler = new APIHandler(args,map);
        this.server = Javalin.create();

        this.server.get("/world", apiHandler::getWorldData);
        this.server.post("/robot/{name}", context -> apiHandler.postDAta(context,map));

        //Admin end-Points
        //demo

        this.server.get("/admin/robots", context -> apiHandler.getRobotList(context,map)); // return list of robots
        this.server.delete("admin/robot/{name}", context -> apiHandler.purgeRobot(context,map)); // kill the robot
        this.server.post("/admin/obstacles/", context -> apiHandler.setObstacles(context,map)); // populate the world with the objects
        this.server.delete("/admin/obstacles", context -> apiHandler.deleteObstacles(context,map)); // delete the listed obstacles
        this.server.post("/admin/save/{world-name}", context -> apiHandler.saveData(context,map)); // save the world map to the database
        this.server.post("/admin/save/override/{world-name}", context -> apiHandler.override(context,map)); // overwrite the existing world
        this.server.get("/admin/load/{world-name}", context -> apiHandler.getDBData(context,map)); //  load the world map and set that as current world state
        this.server.get("/admin/worlds",context -> apiHandler.getWorlds(context,map));

    }

    public static void main(String[] args) {
        serverArguments = new ServerArguments(args);
        Map map = new Map(serverArguments);
        APIServer apiServer = new APIServer(serverArguments, map);
        apiServer.start(serverArguments.getPort());

        CommandListener cmdListener = new CommandListener(map);
        Thread cmd = new Thread(cmdListener);

        try {
            cmd.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void start(int port) {
        this.server.start(port);
    }

    public void stop() {
        this.server.stop();
    }

}


/*
    POST BODIES:

        TO LAUNCH A ROBOT
        "{\"robot\": \"hal\", \"command\": \"launch\", \"arguments\": [\"shooter\", \"5\", \"5\"]}"


        TO SAVE A WORLD
        "{\"mines\":null,\"obstacles\":[{\"Obstacle3bd\":{\"size\":\"1,1\",\"position\":\"-1,-1\"}},{\"@617c74e5\":{\"size\":\"1,1\",\"position\":\"-2,-2\"}}],\"pits\":[],\"worldSize\":{\"length\":10,\"width\":10}}"


        TO SET AN OBSTACLE
        "[{\"size\":\"x,y\",\"position\":\"x,y\",\"type\":\"type in singular form\"}]"

        TO DELETE AN OBSTACLE
        "[{\"position\":\"x,y\",\"\type":\"type in singular form\"}]"

*/