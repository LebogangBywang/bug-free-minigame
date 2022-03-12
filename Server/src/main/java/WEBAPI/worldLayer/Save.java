package WEBAPI.worldLayer;

import SServer.World.Map;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class Save {
    String name;
    Map map;
    JSONObject world;
    Boolean bool;

    public Save(String name, Map map, JSONObject world) {
        this.name = name;
        this.map = map;
        this.world = world;
    }


    public boolean save() {
        try {
            if (!worldExists()) {
                map.getDBConnect().getDAta(world, this.name, false);
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    boolean worldExists() throws SQLException {
        int isValid = map.getDBConnect().isValid(this.name);
        if (isValid != -1) {
            return true;
        }
        return false;
    }

}

