import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import mock_client.RobotWorldClient;
import mock_client.RobotWorldJsonClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * As a player
 * I want my robot to get the state in the online robot world
 */
public class StateTests {
    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private final RobotWorldClient serverClient = new RobotWorldJsonClient();

    @BeforeEach
    void connectToServer(){
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
    }

    @AfterEach
    void disconnectFromServer(){
        serverClient.disconnect();
    }
    @Test
    void robotExists() throws JsonMappingException, JsonProcessingException{
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
        assertTrue(serverClient.isConnected());

        // When my robot is launched into the server
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        serverClient.sendRequest(request);

        // When I send a valid look request to the server
        String request2 = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"State\"," +
                "  \"arguments\": []" +
                "}";
        JsonNode response = serverClient.sendRequest(request2);
        System.out.println(response);
        String request1 = "{" + "  \"robot\": \"HAL\"," + "  \"command\": \"shutdown\","
                + "  \"arguments\": []" + "}";

        serverClient.sendRequest(request1);
        // Then I should get a valid response from the server
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());

        // And I should also get the state of the robot
        assertNotNull(response.get("state"));
        assertNotNull(response.get("state").get("position"));
        assertNotNull(response.get("state").get("direction"));
        assertNotNull(response.get("state").get("shields"));
        assertNotNull(response.get("state").get("shots"));
        assertNotNull(response.get("state").get("status"));
    }

    @Test
    void validStateShouldFail(){
        // Given that I am not connected to a running Robot Worlds server
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
        assertTrue(serverClient.isConnected());

        // When I send a valid State request to the server
        String request = "{" +
                "  \"robot\": \"HAL4\"," +
                "  \"command\": \"State\"," +
                "  \"arguments\": []" +
                "}";
        JsonNode response = serverClient.sendRequest(request);


        // Then I should get an error response
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());

        // And the message "Robot does not exist""
        assertNotNull(response.get("data"));
        assertEquals("Robot does not exist", response.get("data").get("message").asText());
    }
}
