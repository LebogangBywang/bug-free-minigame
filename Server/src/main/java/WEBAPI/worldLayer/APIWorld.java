package WEBAPI.worldLayer;

import DataBase.DbConnect;
import SServer.Network.ServerMain;
import ServerCommands.ServerArguments;
import SServer.World.Map;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class APIWorld {
    static InputStream inputStream;
    private static int SERVER_PORT;
    private static int MAX_SHOTS;
    private static int MAX_SHIELD_STRENGTH;
    private static int RELOAD_TIME;
    private static int REPAIR_TIME;
    private static int SET_MINE_TIME;
    private static int VISIBILITY;
    private static int WORLD_WIDTH;
    private static int WORLD_LENGTH;
    private Map map;
    private static DbConnect dbConnect;


    public APIWorld(ServerArguments serverArguments, Map map){
        initialize("config.properties",serverArguments);
        this.map = map;

        dbConnect = new DbConnect();
        System.out.println("Robot Worlds Server is running.");


    }

    public static void initialize(String s, ServerArguments serverArguments) {
        try {
            inputStream = ServerMain.class.getClassLoader().getResourceAsStream(s);
            Properties properties = new Properties();

            // Loads a properties file
            properties.load(inputStream);

            MAX_SHIELD_STRENGTH = Integer.parseInt(properties.getProperty("config.shields"));
            RELOAD_TIME = Integer.parseInt(properties.getProperty("config.reload"));
            REPAIR_TIME = Integer.parseInt(properties.getProperty("config.repair"));
            MAX_SHOTS = Integer.parseInt(properties.getProperty("config.shots"));
            SET_MINE_TIME = Integer.parseInt(properties.getProperty("config.mine"));
            VISIBILITY = Integer.parseInt(properties.getProperty("config.visibility"));

            WORLD_WIDTH = serverArguments.getSize()[0];
            WORLD_LENGTH = serverArguments.getSize()[1];
            SERVER_PORT = serverArguments.getPort();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getObjects(){
        return map.getObjects();
    }


}
