import com.fasterxml.jackson.databind.JsonNode;
import mock_client.RobotWorldClient;
import mock_client.RobotWorldJsonClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;  

import static org.junit.jupiter.api.Assertions.*;

/**
 * As a player I want launch a robot into world
 **/

public class LaunchTests {
        private final static int PORT_1x1 = 5000;
        private final static int PORT_2x2 = 5050;
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
        void disconnectFromServer() {
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
        void SuccessfulLaunch() {
                // Given that I am connected to a running Robot Worlds server
                // And the world is of size 1x1
                // // When I send a valid launch request to the server
                // // Then I should get a valid response from the server
                List<JsonNode> respNodes = createRobots(1, 5000);
                assertTrue(serverClient.isConnected());

                //Shutdown robots
                shutdownRobots(1);

                assertEquals("\"OK\"", respNodes.get(0).get("result").toPrettyString());

        }

        @Test
        void SecondRobotLaunchShouldSucceed() {
                // Given that I am connected to a running Robot Worlds server
                // And the world is of size 1x1
                List<JsonNode> respNodes = createRobots(2, 5000);
                assertTrue(serverClient.isConnected());
                assertTrue(serverClient1.isConnected());

                // When I send a valid launch request to the server
                // Then I should get a valid response from the server

                assertEquals("\"OK\"", respNodes.get(1).get("result").toPrettyString());
                //Shutdown robots
                shutdownRobots(2);
        }

        @Test
        void IncorrectLaunchCommand() {
                // Given that I am connected to a running Robot Worlds server
                // And the world is of size 1x1
                connectToServer("localhost", 5000);
                assertTrue(serverClient.isConnected());

                // When I send a invalid launch request to the server
                // Then I should get a error response from the server
                String request = "{" + "  \"robot\": \"HAL\"," + "  \"command\": \"laanch\","
                                + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";

                JsonNode response = serverClient.sendRequest(request);

                assertEquals("\"ERROR\"", response.get("result").toPrettyString());

        }

        @Test
        void nameAlreadyExists() {
                // Given that I am connected to a running Robot Worlds server
            List<JsonNode> respNodes = createRobots(1, 5000);

            assertTrue(serverClient.isConnected());

                // When I Launch a robot with the same name the second time
                // Then I should get an "ERROR" response with the message "Username already
                // taken".
                String request = "{" + "  \"robot\": \"Sam\"," + "  \"command\": \"launch\","
                                + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
                serverClient.sendRequest(request);
                assertTrue(serverClient.isConnected());

                JsonNode response = serverClient1.sendRequest(request);

                String request1 = "{" + "  \"robot\": \"HAL\"," + "  \"command\": \"shutdown\"," + "  \"arguments\": []"
                                + "}";
                serverClient.sendRequest(request1);
                serverClient1.sendRequest(request1);

                assertEquals("\"ERROR\"", response.get("result").toPrettyString());
                assertEquals("\"Too many of you in this world\"", response.get("data").get("message").toPrettyString());
        }

        @Test
        void worldWithoutObstacle() {
            // given a world of size 2x2
            // and I have successfully launched 4 robots into the world


            List<JsonNode> respNodes = createRobots(9, 5000);
            assertTrue(serverClient.isConnected());

            shutdownRobots(9);
            // Then I should get an error response back with the message
            // "No more space in this world""
            assertEquals(respNodes.get(0).get("result").toPrettyString(), "\"OK\"");
            assertEquals(respNodes.get(1).get("result").toPrettyString(), "\"OK\"");
            assertEquals(respNodes.get(2).get("result").toPrettyString(), "\"OK\"");
            assertEquals(respNodes.get(3).get("result").toPrettyString(), "\"OK\"");
            assertEquals(respNodes.get(4).get("result").toPrettyString(), "\"OK\"");
            assertEquals(respNodes.get(5).get("result").toPrettyString(), "\"OK\"");
            assertEquals(respNodes.get(6).get("result").toPrettyString(), "\"OK\"");
            assertEquals(respNodes.get(7).get("result").toPrettyString(), "\"OK\"");
            assertEquals(respNodes.get(8).get("result").toPrettyString(), "\"OK\"");
        }

        @Test
        void worldWithObstacleFull() {
            // given a world of size 2x2
            // and the world has an obstacle at coordinate [1,1]

            List<JsonNode> respNodes = createRobots(9, 5050);
            assertTrue(serverClient.isConnected());

            shutdownRobots(9);
            // Then I should get an error response back with the message
            // "No more space in this world""
            assertEquals(respNodes.get(0).get("result").toPrettyString(), "\"OK\"");
            assertEquals(respNodes.get(1).get("result").toPrettyString(), "\"OK\"");
            assertEquals(respNodes.get(2).get("result").toPrettyString(), "\"OK\"");
            assertEquals(respNodes.get(3).get("result").toPrettyString(), "\"OK\"");
            assertEquals(respNodes.get(4).get("result").toPrettyString(), "\"OK\"");
            assertEquals(respNodes.get(5).get("result").toPrettyString(), "\"OK\"");
            assertEquals(respNodes.get(6).get("result").toPrettyString(), "\"OK\"");
            assertEquals(respNodes.get(7).get("result").toPrettyString(), "\"OK\"");

                // Then I should get an error response back with the message
                // "No more space in this world""
            assertEquals(respNodes.get(8).get("data").get("message").toPrettyString(), "\"No more space in this world\"");
        }

        @Test
        void worldWithAnObstacle() {
                // Given a world of size 2x2
                // and the world has an obstacle at coordinate [1,1]
                // When I launch 8 robots into the world
                // Then each robot cannot be in position [1,1].

                int numberOfRobots = 8;
                List<JsonNode> responses = createRobots(8, 5050);
                for (JsonNode jsonNode : responses) {
                        System.out.println(jsonNode.asText());
                }
                //Shutdown all robots
                shutdownRobots(numberOfRobots);
                assertTrue(serverClient.isConnected());
                // Robot not in position [1,1] where an obstacle is placed
                assertNotEquals("[-1,0]", responses.get(0).get("state").get("position").toString());

                assertTrue(serverClient1.isConnected());
                // Robot not in position [1,1] where an obstacle is placed
                assertNotEquals("[-1,0]", responses.get(1).get("state").get("position").toString());

                assertTrue(serverClient2.isConnected());
                // Robot not in position [1,1] where an obstacle is placed
                assertNotEquals("[-1,0]", responses.get(2).get("state").get("position").toString());

                assertTrue(serverClient3.isConnected());
                // Robot not in position [1,1] where an obstacle is placed
                assertNotEquals("[-1,0]", responses.get(3).get("state").get("position").toString());

                assertTrue(serverClient4.isConnected());
                // Robot not in position [1,1] where an obstacle is placed
                assertNotEquals("[-1,0]", responses.get(4).get("state").get("position").toString());

                assertTrue(serverClient5.isConnected());
                // Robot not in position [1,1] where an obstacle is placed
                assertNotEquals("[-1,0]", responses.get(5).get("state").get("position").toString());

                assertTrue(serverClient6.isConnected());
                // Robot not in position [1,1] where an obstacle is placed
                assertNotEquals("[-1,0]", responses.get(6).get("state").get("position").toString());

                assertTrue(serverClient7.isConnected());

                // Robot not in position [1,1] where an obstacle is placed
                assertNotEquals("[-1,0]", responses.get(7).get("state").get("position").toString());

        }
}
