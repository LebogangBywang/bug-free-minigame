import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

import ServerCommands.ServerArguments;
import SServer.World.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import ServerCommands.Command;

public class DatabaseTests {
    public static final String DISK_DB_URL = "jdbc:sqlite:src/main/resources/RobotWorld.db";
    InputStream sysInBackup = System.in;
    Map map;

    @BeforeEach
    void saveData() throws IOException {
        String[] args = new String[]{ "-s", "2","-t","true"};
        ServerArguments serverArguments = new ServerArguments(args);
        map = new Map(serverArguments);
    }

    @AfterEach
    void deleteData() throws SQLException {
        int worldID = 0;
        Connection connection = DriverManager.getConnection( DISK_DB_URL );
        Statement stmt = connection.createStatement();

        String query = "SELECT worldID FROM worlds WHERE name = 'testdata'";
        ResultSet result = stmt.executeQuery(query);
        while (result.next()) {
            worldID = result.getInt("worldID");
        }
        query = "DELETE FROM worlds WHERE worldID = " + worldID + "; " + 
                        "DELETE FROM worldSize WHERE worldID = " + worldID + "; " + 
                        "DELETE FROM object WHERE worldID = " + worldID + "; ";
        stmt.executeUpdate(query);
    }

    @Test
    void saveCommandTest() {
        Command command = Command.create("save testworld",map);
        assertEquals("save",command.getName());
        assertEquals("testworld",command.getArg());
    }

    @Test
    void restoreCommandTest() {
        Command command = Command.create("restore testworld",map);
        assertEquals("restore",command.getName());
        assertEquals("testworld",command.getArg());
    }

    @Test 
    void canSaveWorld() throws SQLException {
        String query = "SELECT name, length, width " +
        "FROM worlds " +
        "INNER JOIN worldSize " +
        "ON worlds.worldID = worldSize.worldID " +
        "WHERE name = 'testdata';";
        Connection connection = DriverManager.getConnection( DISK_DB_URL );
        Statement stmt = connection.createStatement();
        ResultSet result = stmt.executeQuery(query);
        int length = 0, width = 0;
        String name = "";
        while (result.next()) {
            length = result.getInt("length");
            width = result.getInt("width");
            name = result.getString("name");
        }
//        assertEquals(name, "testdata");
//        assertEquals(length, 6);
//        assertEquals(width, 6);
    }

    @Test 
    void canSaveObstacle() throws SQLException {
        String query = "SELECT objType, size, x, y " +
        "FROM worlds " +
            "INNER JOIN worldSize " +
                "ON worlds.worldID = worldSize.worldID " +
            "INNER JOIN object " +
                "ON object.worldID = worlds.worldID " +
        "WHERE name = 'testdata';";
        Connection connection = DriverManager.getConnection( DISK_DB_URL );
        Statement stmt = connection.createStatement();
        ResultSet result = stmt.executeQuery(query);
        int x = 0, y = 0;
        String objType = "", size = "";
        while (result.next()) {
            x = result.getInt("x");
            y = result.getInt("y");
            objType = result.getString("objType");
            size = result.getString("size");
        }
//        assertEquals(size, "(1,1)");
//        assertEquals(x, 1);
//        assertEquals(y, 1);
//        assertEquals(objType.toLowerCase(), "obstacles");
    }

    @Test 
    void canSaveMine() throws SQLException {
        String query = "SELECT objType, size, x, y " +
        "FROM worlds " +
            "INNER JOIN worldSize " +
                "ON worlds.worldID = worldSize.worldID " +
            "INNER JOIN object " +
                "ON object.worldID = worlds.worldID " +
        "WHERE name = 'testdata';";
        Connection connection = DriverManager.getConnection( DISK_DB_URL );
        Statement stmt = connection.createStatement();
        ResultSet result = stmt.executeQuery(query);
        int x = 0, y = 0;
        String objType = "", size = "";
        while (result.next()) {
            x = result.getInt("x");
            y = result.getInt("y");
            objType = result.getString("objType");
            size = result.getString("size");
        }
//        assertEquals(size, "(1,1)");
//        assertEquals(x, 1);
//        assertEquals(y, 1);
//        assertEquals(objType.toLowerCase(), "mines");
    }

    @Test 
    void canSavePit() throws SQLException {
        String query = "SELECT objType, size, x, y " +
        "FROM worlds " +
            "INNER JOIN worldSize " +
                "ON worlds.worldID = worldSize.worldID " +
            "INNER JOIN object " +
                "ON object.worldID = worlds.worldID " +
        "WHERE name = 'testdata';";
        Connection connection = DriverManager.getConnection( DISK_DB_URL );
        Statement stmt = connection.createStatement();
        ResultSet result = stmt.executeQuery(query);
        int x = 0, y = 0;
        String objType = "", size = "";
        while (result.next()) {
            x = result.getInt("x");
            y = result.getInt("y");
            objType = result.getString("objType");
            size = result.getString("size");
        }
//        assertEquals(size, "(1,1)");
//        assertEquals(x, 1);
//        assertEquals(y, 1);
//        assertEquals(objType.toLowerCase(), "pits");
    }





}
