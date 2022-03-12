package SServer.World;

import ServerCommands.ServerArguments;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Initialize {

    private static int MAX_SHOTS;
    private static int MAX_SHIELD_STRENGTH;
    private static int RELOAD_TIME;
    private static int REPAIR_TIME;
    private static int SET_MINE_TIME;
    private static int VISIBILITY;
    private static int WORLD_WIDTH;
    private static int WORLD_LENGTH;
    static InputStream inputStream;
    private static int SERVER_PORT;

    public Initialize(String s, ServerArguments serverArguments) {
        try {
            inputStream = Initialize.class.getClassLoader().getResourceAsStream(s);
            Properties properties = new Properties();

            // Loads a properties file
            properties.load(inputStream);

            // Gets the server config and world configs
//            SERVER_PORT = Integer.parseInt(properties.getProperty("config.port"));
            MAX_SHIELD_STRENGTH = Integer.parseInt(properties.getProperty("config.shields"));
            RELOAD_TIME = Integer.parseInt(properties.getProperty("config.reload"));
            REPAIR_TIME = Integer.parseInt(properties.getProperty("config.repair"));
            MAX_SHOTS = Integer.parseInt(properties.getProperty("config.shots"));
            SET_MINE_TIME = Integer.parseInt(properties.getProperty("config.mine"));
            VISIBILITY = Integer.parseInt(properties.getProperty("config.visibility"));
//            WORLD_WIDTH =Integer.parseInt(properties.getProperty("config.maxWidth"));
//            WORLD_LENGTH = Integer.parseInt(properties.getProperty("config.maxLength"));

            WORLD_WIDTH = serverArguments.getSize()[0];
            WORLD_LENGTH = serverArguments.getSize()[1];
            SERVER_PORT = serverArguments.getPort();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getServerPort(){
        return SERVER_PORT;
    }

    public int getShots() {
        return MAX_SHOTS;
    }

    public int getShields() {
        return MAX_SHIELD_STRENGTH;
    }

    public int getReloadTime() {
        return RELOAD_TIME;
    }

    public int getRepairTime() {
        return REPAIR_TIME;
    }

    public int getSetMineTime() {
        return SET_MINE_TIME;
    }

    public int getVisibility() {
        return VISIBILITY;
    }

    public int getMaxWidth() { return WORLD_WIDTH; }

    public int getMaxHeight() { return WORLD_LENGTH; }
}
