package APIServerTests;

import SServer.World.Map;
import ServerCommands.ServerArguments;
import WEBAPI.API.APIServer;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class APIForwardCommandTests {
    private static APIServer server;
    private static int port;

    @BeforeEach
    void startServer() {
        String[] args = new String[]{"-s", "2","-t","true","-p","1804"};
        ServerArguments serverArguments = new ServerArguments(args);
        Map map = new Map(serverArguments);
        server = new APIServer(serverArguments, map);
        port = serverArguments.getPort();
        server.start(serverArguments.getPort());
    }

    @AfterEach
    void stopServer() throws InterruptedException {
        server.stop();
        Thread.sleep(2000);
    }

    @Test
    @DisplayName("POST /robot/{name}")
    void ForwardShouldSucceed()throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post("http://localhost:" + port + "/robot/hal")
                .header("Content-Type", "application/json")
                .body("{\"robot\": \"hal\", \"command\": \"launch\", \"arguments\": [\"shooter\", \"5\", \"5\"]}")
                .asJson();
        assertEquals(200, response.getStatus());
        assertEquals("OK", response.getBody().getObject().get("result").toString());

        response = Unirest.post("http://localhost:" + port + "/robot/hal")
                .header("Content-Type", "application/json")
                .body("{\"robot\": \"hal\", \"command\": \"forward\", \"arguments\": [\"1\"]}")
                .asJson();
        assertEquals(200, response.getStatus());
        assertEquals("OK", response.getBody().getObject().get("result").toString());
        JSONObject obj = (JSONObject) response.getBody().getObject().get("data");
//        assertEquals("DONE",obj.get("message"));

        response = Unirest.post("http://localhost:" + port + "/robot/hal")
                .header("Content-Type", "application/json")
                .body("{\"robot\": \"hal\", \"command\": \"shutdown\", \"arguments\": []}")
                .asJson();
        assertEquals(200, response.getStatus());
    }

}
