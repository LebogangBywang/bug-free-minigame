package ServerCommands;


import SServer.World.Map;
import org.json.simple.JSONObject;

import java.sql.SQLException;

public class SaveCommand extends Command {
    String names;
    Map map;
    Boolean bool;

    public SaveCommand(String arg, Map map, boolean b) {
        super("save", arg);
        this.names = arg;
        this.map = map;
        bool = b;
    }

    @Override
    public boolean execute() {
        JSONObject objects = map.getObjects();
        try {
            if (!worldExists()) {
                map.getDBConnect().getDAta(objects, this.names, bool);
            } else {
                    System.out.println("world not saved");
                    return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean worldExists() throws SQLException {
        int isValid = map.getDBConnect().isValid(this.names);
        if (isValid != -1) {
            return true;
        }
        return false;
    }

}
