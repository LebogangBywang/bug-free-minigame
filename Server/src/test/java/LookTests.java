import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import mock_client.RobotWorldClient;
import mock_client.RobotWorldJsonClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * As a player
 * I want my robot to look in the online robot world
 * So that it is aware of its surrounding
 */
public class LookTests {
    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private final RobotWorldClient serverClient = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient1 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient2 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient3 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient4 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient5 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient6 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient7 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient8 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient9 = new RobotWorldJsonClient();

    void connectToServer(String DEFAULT_IP, int DEFAULT_PORT) {
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClient1.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClient2.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClient3.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClient4.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClient5.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClient6.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClient7.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClient8.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClient9.connect(DEFAULT_IP, DEFAULT_PORT);
    }

    @AfterEach
    void disconnectFromServer(){
        serverClient.disconnect();
        serverClient1.disconnect();
        serverClient2.disconnect();
        serverClient3.disconnect();
        serverClient4.disconnect();
        serverClient5.disconnect();
        serverClient6.disconnect();
        serverClient7.disconnect();
        serverClient8.disconnect();
        serverClient9.disconnect();
    }


    List<JsonNode> createRobots(int numberOfRobots, int DEFAULT_PORT) {
        String[] names = {"Sam", "Thato", "Sifiso", "Lebo", "Nyari",
                "Hal", "Toy", "Ruan", "WTC", "Messi"};
        RobotWorldClient[] clients = {
                serverClient, serverClient1, serverClient2,
                serverClient3, serverClient4, serverClient5,
                serverClient6, serverClient7, serverClient8,
                serverClient9,
        };
        connectToServer("localhost", DEFAULT_PORT);
        List<JsonNode> respNodes = new ArrayList<>();
        for (int i = 0; i <  numberOfRobots; i++) {

            String request = "{" +
                    "  \"robot\":" + "\"" + names[i] + "\"," +
                    "  \"command\": \"launch\"," +
                    "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                    "}";
            System.out.println(request);
            JsonNode response = clients[i].sendRequest(request);
            System.out.println(response);
            respNodes.add(response);
        }
        return respNodes;
    }

    List<JsonNode> shutdownRobots(int numberOfRobots) {
        String[] names = {"Sam", "Thato", "Sifiso", "Lebo", "Nyari",
                "Hal", "Toy", "Ruan", "WTC", "Messi"};
        RobotWorldClient[] clients = {
                serverClient, serverClient1, serverClient2,
                serverClient3, serverClient4, serverClient5,
                serverClient6, serverClient7, serverClient8,
                serverClient9,
        };
        List<JsonNode> respNodes = new ArrayList<>();
        for (int i = 0; i <  numberOfRobots; i++) {
            String request = "{" +
                    "  \"robot\":" + "\"" + names[i] + "\"," +
                    "  \"command\": \"shutdown\"," +
                    "  \"arguments\": []" +
                    "}";
            System.out.println(request);
            JsonNode response = clients[i].sendRequest(request);
            System.out.println(response);
            respNodes.add(response);
        }
        return respNodes;
    }


    @Test
    void validLookShouldSucceed(){
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
        List<JsonNode> responses = createRobots(1, 5000);
        assertTrue(serverClient.isConnected());

        // When my robot is launched into the server


        // When I send a valid look request to the server
        String request2 = "{" +
                "  \"robot\": \"Sam\"," +
                "  \"command\": \"Look\"," +
                "  \"arguments\": []" +
                "}";
        JsonNode response = serverClient.sendRequest(request2);

        System.out.println(response);

        shutdownRobots(1);

        // Then I should get a valid response from the server
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());

        // And I should also get the state of the robot
        assertNotNull(response.get("state"));
        assertNotNull(response.get("state").get("position"));

        //And the objects should be an array
        assertTrue(response.get("data").get("objects").isArray());

    }

    @Test
    void seeObstacle(){
//      Given a world of size 2x2
//      and the world has an obstacle at coordinate [1,1]
        createRobots(1, 5050);
        assertTrue(serverClient.isConnected());

//      and I have successfully launched a robot into the world


//      When I ask the robot to look
        String request = "{" +
                "  \"robot\": \"Sam\"," +
                "  \"command\": \"Look\"," +
                "  \"arguments\": []" +
                "}";

        JsonNode response = serverClient.sendRequest(request);
        System.out.println(response);

        shutdownRobots(1);
//      Then I should get an response back with

//      an object of type OBSTACLE
        assertEquals("\"OBSTACLE\"",response.get("data").get("objects").get(0).get("type").toPrettyString());

//      at a distance of 1 step.
        assertEquals("\"1\"",response.get("data").get("objects").get(0).get("distance").toPrettyString());

    }

    @Test
    void seeRobot(){

//      Given a world of size 2x2
//      and all nine clients are connected

        createRobots(2, 5000);

        assertTrue(serverClient.isConnected());
        assertTrue(serverClient2.isConnected());

//      and I have successfully launched nine robots into the world

//      When I ask one of the robots to look
        String request2 = "{" +
                "  \"robot\": \"Sam\"," +
                "  \"command\": \"Look\"," +
                "  \"arguments\": []" +
                "}";
        JsonNode response = serverClient.sendRequest(request2);
        System.out.println(response);
        /*Shutdown all robots*/
        shutdownRobots(2);

//      Then I should get an response back with

//      an object of type ROBOT
        assertEquals("\"ROBOT\"",response.get("data").get("objects").get(0).get("type").toPrettyString());

//      at a distance of 1 step.
        assertEquals("\"1\"",response.get("data").get("objects").get(0).get("distance").toPrettyString());
    }

    @Test
    public void seeObstacleAndRobot() {

//      Given a world of size 2x2
//      and all nine clients are connected

        createRobots(2, 5050);

        assertTrue(serverClient.isConnected());
        assertTrue(serverClient2.isConnected());

//      and I have successfully launched nine robots into the world

//      When I ask one of the robots to look
        String request2 = "{" +
                "  \"robot\": \"Sam\"," +
                "  \"command\": \"Look\"," +
                "  \"arguments\": []" +
                "}";
        JsonNode response = serverClient.sendRequest(request2);
        System.out.println(response);
        /*Shutdown all robots*/
        shutdownRobots(2);

//      Then I should get an response back with

//      an object of type OBSTACLE
        assertEquals("\"OBSTACLE\"",response.get("data").get("objects").get(0).get("type").toPrettyString());

//      at a distance of 1 step.
        assertEquals("\"1\"",response.get("data").get("objects").get(0).get("distance").toPrettyString());

//      an object of type ROBOT
        assertEquals("\"ROBOT\"",response.get("data").get("objects").get(1).get("type").toPrettyString());

//      at a distance of 1 step.
        assertEquals("\"2\"",response.get("data").get("objects").get(1).get("distance").toPrettyString());
    }
    }

