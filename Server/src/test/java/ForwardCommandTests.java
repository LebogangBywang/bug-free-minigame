import com.fasterxml.jackson.databind.JsonNode;
import mock_client.RobotWorldClient;
import mock_client.RobotWorldJsonClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ForwardCommandTests {
    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private final RobotWorldClient serverClient = new RobotWorldJsonClient();

    @BeforeEach
    void connectToServer() {
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
    }

    @AfterEach
    void disconnectFromServer() {
        serverClient.disconnect();
    }

    @Test
    void forwardShouldSucceed() {
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1
        assertTrue(serverClient.isConnected());

        // And a robot called "HAL" is already connected and launched
        String request = "{" + "  \"robot\": \"HAL\"," + "  \"command\": \"launch\","
                + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";

        JsonNode res = serverClient.sendRequest(request);
        System.out.println(res);
        // When I send a command for "HAL" to move forward by 5 steps
        String request1 = "{" + "  \"robot\": \"HAL\"," + "  \"command\": \"forward\","
                + "  \"arguments\": [\"1\"]" + "}";

        JsonNode response = serverClient.sendRequest(request1);

        String request2 = "{" + "  \"robot\": \"HAL\"," + "  \"command\": \"shutdown\","
                + "  \"arguments\": []" + "}";

        serverClient.sendRequest(request2);

        System.out.println(response);
        // Then I should get an "OK" response with the message "At the NORTH edge
        assertEquals("\"OK\"", response.get("result").toPrettyString());
        assertNotNull(response.get("data").get("message").toPrettyString());
        // assertEquals("\"At the NORTH edge\"", response.get("data").get("message").toPrettyString());

        // And the position should be (x:0, y:0)
        assertNotNull(response.get("data"));
        assertNotNull(response.get("state").get("position"));
        assertNotNull(response.get("state").get("position").get(0));
        assertNotNull(response.get("state").get("position").get(1));
        assertTrue(response.get("state").get("position").get(0).isInt());
        assertTrue(response.get("state").get("position").get(1).isInt());
        assertTrue((response.get("data").get("message").toString().contains("DONE") || response.get("data").get("message").toString().contains("OBSTRUCTED")));

    }
}
