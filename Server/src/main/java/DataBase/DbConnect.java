package DataBase;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.*;
import java.util.ArrayList;

public class DbConnect {
    public static final String DISK_DB_URL = "jdbc:sqlite:Server/src/main/resources/RobotWorld.db";
    JSONObject objects = new JSONObject();


    public void getDAta(JSONObject Data, String name, boolean bool) {
        ArrayList<String> objects = new ArrayList<String>();
        objects.add("obstacles");
        objects.add("mines");
        objects.add("pits");

        try (final Connection connection = DriverManager.getConnection(DISK_DB_URL)) {
            if (connection != null) {
                createTables(connection);
                if (bool == true) {
                    overwrite( connection,name);
                }
                System.out.println("Saving data into dataBase...");
                int worldID = saveWorld((JSONObject) Data.get("worldSize"), connection, name);
                if (worldID > 0) {
                    for (String vals :
                            objects) {
                        try {
                            JSONArray obj = (JSONArray) Data.get(vals);
                            saveObjects(obj, connection, vals, worldID);
                        } catch (NullPointerException e) {
                            continue;
                        }
                    }
                    System.out.println("Data saved");
                } else {
                    System.out.println("An error occurred, could not save data.");
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    public static void createTables(Connection connection) {
        try (final Statement stmt = connection.createStatement()) {
            String table = "CREATE TABLE IF NOT EXISTS worlds ( " +
                    "worldID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT " +
                    ")";
            stmt.executeUpdate(table);

            table = "CREATE TABLE IF NOT EXISTS worldSize ( " +
                    "worldID INTEGER, " +
                    "length INTEGER, " +
                    "width INTEGER" +
                    ")";
            stmt.executeUpdate(table);

            table = "CREATE TABLE IF NOT EXISTS object ( " +
                    "worldID INTEGER , " +
                    "objType TEXT, " +
                    "size TEXT, " +
                    "x INTEGER, " +
                    "y INTEGER" +
                    ")";
            stmt.executeUpdate(table);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    private void saveObjects(JSONArray obstacles, Connection connection, String type, int worldID) {
        int len;
        int width;
        int x;
        int y;
        String object;
        for (int i = 0; i < obstacles.size(); i++) {
            JSONObject obs = (JSONObject) obstacles.get(i);
            String Obstaclekey = obs.keySet().toString().replace("[", "").replace("]", "");
            object = Obstaclekey;
            String objID;

            String[] size =  ((JSONObject) obs.get(Obstaclekey)).get("size").toString().split(",");
            len = Integer.parseInt(size[0]);
            width = Integer.parseInt(size[1]);
            String sizeString = "(" + len + "," + width + ")";

            String[] positions = ((JSONObject) obs.get(Obstaclekey)).get("position").toString().split(",");
            x = Integer.parseInt(positions[0]);
            y = Integer.parseInt(positions[1]);

            try (final Statement stmt = connection.createStatement()) {
                String insert = "INSERT INTO object(worldID, objType, size, x, y) "
                        + "VALUES ( " + worldID + ", "
                        + "\'" + type + "\',\'"
                        + sizeString + "\', "
                        + x + ", "
                        + y + ")";
                stmt.executeUpdate(insert);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private int saveWorld(JSONObject worldSize, final Connection connection, String name) throws SQLException {
        int length = Integer.parseInt(worldSize.get("length").toString());
        int width = Integer.parseInt(worldSize.get("width").toString());
        int worldID = 0;

        try (final Statement stmt = connection.createStatement()) {
            String insert = "INSERT INTO worlds( name ) "
                    + "VALUES( \'" + name + "\' )";
            stmt.executeUpdate(insert);

            insert = "SELECT worldID " +
                    "FROM worlds " +
                    "WHERE name = \'" + name + "\'";
            ResultSet result = stmt.executeQuery(insert);

            if (result.next()) {
                worldID = result.getInt("worldID");

                insert = "INSERT INTO worldSize(worldID, length,width) "
                        + "VALUES( " + worldID + ", "
                        + length + ", "
                        + width
                        + ")";
                stmt.executeUpdate(insert);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return worldID;
    }


    public void restoreWorld(String name) {

        try (final Connection connection = DriverManager.getConnection(DISK_DB_URL)) {
            if (connection != null) {
                System.out.println("Restoring world...");
                pullData(connection, name);

                System.out.println("World fully restored");
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    private void pullData(final Connection connection, String name) throws SQLException {

        int length;
        int width;

        try (final Statement stmt = connection.createStatement()) {

            int worldID = isValid(name);

            String query = "SELECT DISTINCT worlds.worldID, length, width "
                    + "FROM worlds \n" +
                    "INNER JOIN worldSize " +
                    "ON worlds.worldID = worldSize.worldID " +
                    "WHERE worlds.worldID = " + worldID + ";";
            ResultSet result = stmt.executeQuery(query);

            if (result.next()) {
                JSONObject object = new JSONObject();
                length = Integer.parseInt(result.getString("length"));
                width = Integer.parseInt(result.getString("width"));
                object.put("length", length);
                object.put("width", width);
                objects.put("worldSize", object);
            }

            //query all objects with type mine

            query = "SELECT DISTINCT worlds.worldID, size, x, y, objType \n" +
                    "from worlds \n" +
                    "INNER JOIN object \n" +
                    "ON  worlds.worldID = object.worldID \n" +
                    "WHERE objType = \"mines\" " +
                    "and worlds.worldID = " + worldID + ";";
            result = stmt.executeQuery(query);

            JSONArray objs = new JSONArray();
            while (result.next()) {
                JSONObject object = new JSONObject();

                String size = result.getString("size").replace("(", "").replace(")", "");
                object.put("size", size);

                length = result.getInt("x");
                width = result.getInt("y");
                object.put("x", length);
                object.put("y", width);

                objs.add(object);
            }
            objects.put("mines", objs);

            //query all objects with type obstacles

            query = "SELECT DISTINCT size, x, y, objType \n" +
                    "from worlds \n" +
                    "INNER JOIN object \n" +
                    "ON  worlds.worldID = object.worldID \n" +
                    "WHERE worlds.worldID = " + worldID +
                    " and  objType = \"obstacles\" " +
                    "and worlds.worldID = " + worldID + ";";
            result = stmt.executeQuery(query);

            objs = new JSONArray();
            while (result.next()) {
                JSONObject object = new JSONObject();

                String size = result.getString("size").replace("(", "").replace(")", "");
                object.put("size", size);

                length = result.getInt("x");
                width = result.getInt("y");
                object.put("x", length);
                object.put("y", width);

                objs.add(object);
            }
            objects.put("obstacles", objs);


            //query all objects with type pit
            query = "SELECT DISTINCT size, x, y, objType \n" +
                    "from worlds \n" +
                    "INNER JOIN object \n" +
                    "ON  worlds.worldID = object.worldID \n" +
                    "WHERE objType = \"pits\" " +
                    "and worlds.worldID = " + worldID + ";";
            result = stmt.executeQuery(query);

            objs = new JSONArray();
            while (result.next()) {
                JSONObject object = new JSONObject();

                String size = result.getString("size").replace("(", "").replace(")", "");
                object.put("size", size);

                length = result.getInt("x");
                width = result.getInt("y");
                object.put("x", length);
                object.put("y", width);

                objs.add(object);
            }
            objects.put("pits", objs);

        }
    }


    public int isValid(String name) throws SQLException {
        int id;

        try (final Connection connection = DriverManager.getConnection(DISK_DB_URL)) {
            if (connection != null) {
                try (final Statement stmt = connection.createStatement()) {
                    String query = "SELECT worldID,name " +
                            "FROM  worlds " +
                            "WHERE name = \"" + name + "\"";
                    ResultSet result = stmt.executeQuery(query);


                    if (result.next()) {
                        id = result.getInt("worldID");
                        return id;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return -1;
    }


    public JSONObject getDAta() {
        return this.objects;
    }

    public void overwrite(Connection connection,String name) {
        int id = 0;
            if (connection != null) {
                try (final Statement stmt = connection.createStatement()) {

                    String query = "SELECT worldID,name " +
                            "FROM  worlds " +
                            "WHERE name = \"" + name + "\"";
                    ResultSet result = stmt.executeQuery(query);

                    if (result.next()) {
                        id = result.getInt("worldID");
                    }

                    String delete = "DELETE FROM worlds \n" +
                            "WHERE worlds.name = \"" + name + "\";";
                    int rs = stmt.executeUpdate(delete);

                    delete = "DELETE FROM worldSize \n" +
                            "WHERE worldID = " + id + ";";
                    rs = stmt.executeUpdate(delete);

                    delete = "DELETE FROM object \n" +
                            "WHERE worldID = " + id + ";";
                    rs = stmt.executeUpdate(delete);


                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    }

    public JSONArray getWorlds(){
        JSONArray worlds = new JSONArray();

        try (final Connection connection = DriverManager.getConnection(DISK_DB_URL)) {
            if (connection != null) {
                try (final Statement stmt = connection.createStatement()) {
                    String query = "SELECT name " +
                            "FROM  worlds ";
                    ResultSet result = stmt.executeQuery(query);


                    while (result.next()) {
                        String world = result.getString("name");
                        worlds.add(world);
                    }
                    return worlds;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return worlds;
    }
}
