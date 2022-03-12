package APIServerTests;

import ServerCommands.ServerArguments;
import SServer.World.Map;
import WEBAPI.API.APIServer;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class APILaunchTest {
    private static APIServer server;
    private static int port;

    @BeforeAll
    public static void startServer() {
        String[] args = new String[]{"-s", "2","-t","true","-p","1804"};
        ServerArguments serverArguments = new ServerArguments(args);
        Map map = new Map(serverArguments);
        server = new APIServer(serverArguments, map);
        port = serverArguments.getPort();
        server.start(serverArguments.getPort());
    }

    @AfterAll
    public static void stopServer() throws InterruptedException {
        server.stop();
        Thread.sleep(2000);
    }

    @Test
    @DisplayName("POST /robot/{name}")
    public void SuccessfulLaunch() throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post("http://localhost:" + port + "/robot/hal")
                .header("Content-Type", "application/json")
                .body("{\"robot\": \"hal\", \"command\": \"launch\", \"arguments\": [\"shooter\", \"5\", \"5\"]}")
                .asJson();
        assertEquals(200, response.getStatus());
        assertEquals("OK", response.getBody().getObject().get("result").toString());

        response = Unirest.post("http://localhost:" + port + "/robot/hal")
                .header("Content-Type", "application/json")
                .body("{\"robot\": \"hal\", \"command\": \"shutdown\", \"arguments\": []}")
                .asJson();
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("POST /robot/{name}")
    void SecondRobotLaunchShouldSucceed() {
            HttpResponse<JsonNode> response = Unirest.post("http://localhost:" + port + "/robot/hal")
                    .header("Content-Type", "application/json")
                    .body("{\"robot\": \"hal\", \"command\": \"launch\", \"arguments\": [\"shooter\", \"5\", \"5\"]}")
                    .asJson();
            assertEquals(200, response.getStatus());
            assertEquals("OK", response.getBody().getObject().get("result").toString());


        response = Unirest.post("http://localhost:" + port + "/robot/hal")
                .header("Content-Type", "application/json")
                .body("{\"robot\": \"sifiso\", \"command\": \"launch\", \"arguments\": [\"shooter\", \"5\", \"5\"]}")
                .asJson();
        assertEquals(200, response.getStatus());
        assertEquals("OK", response.getBody().getObject().get("result").toString());


        response = Unirest.post("http://localhost:" + port + "/robot/hal")
                .header("Content-Type", "application/json")
                .body("{\"robot\": \"hal\", \"command\": \"shutdown\", \"arguments\": []}")
                .asJson();
        assertEquals(200, response.getStatus());

        response = Unirest.post("http://localhost:" + port + "/robot/hal")
                .header("Content-Type", "application/json")
                .body("{\"robot\": \"sifiso\", \"command\": \"shutdown\", \"arguments\": []}")
                .asJson();
        assertEquals(200, response.getStatus());
        }


    @Test
    @DisplayName("POST /robot/{name}")
    void ShouldNotLaunchSameRobot() {
        HttpResponse<JsonNode> response = Unirest.post("http://localhost:" + port + "/robot/hal")
                .header("Content-Type", "application/json")
                .body("{\"robot\": \"hal\", \"command\": \"launch\", \"arguments\": [\"shooter\", \"5\", \"5\"]}")
                .asJson();
        assertEquals(200, response.getStatus());
        assertEquals("OK", response.getBody().getObject().get("result").toString());


        response = Unirest.post("http://localhost:" + port + "/robot/hal")
                .header("Content-Type", "application/json")
                .body("{\"robot\": \"hal\", \"command\": \"launch\", \"arguments\": [\"shooter\", \"5\", \"5\"]}")
                .asJson();
        assertEquals(400, response.getStatus());
        assertEquals("ERROR", response.getBody().getObject().get("result").toString());

        response = Unirest.post("http://localhost:" + port + "/robot/hal")
                .header("Content-Type", "application/json")
                .body("{\"robot\": \"hal\", \"command\": \"shutdown\", \"arguments\": []}")
                .asJson();
        assertEquals(200, response.getStatus());
    }

    }
