package ServerCommands;

import SServer.World.Map;
import org.json.simple.JSONObject;

import java.sql.SQLException;

public class RestoreCommand extends Command{
    String name;
    Map map;


    public RestoreCommand(String name, Map map){
        super("restore", name);
        this.name = name;
        this.map = map;
    }


    @Override
    public boolean execute() {
        try {
            if (worldExists()) {
                map.getDBConnect().restoreWorld(this.name);
                JSONObject Data = map.getDBConnect().getDAta();
                map.restoreWorld(Data);
            }
            else{
                System.out.println("Sorry the selected world does not exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    boolean worldExists() throws SQLException {
        int isValid = map.getDBConnect().isValid(this.name);
        if (isValid != -1) {
            return true;
        }
        return false;
    }
}
