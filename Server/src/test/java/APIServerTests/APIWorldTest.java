package APIServerTests;

import ServerCommands.ServerArguments;
import SServer.World.Map;
import WEBAPI.API.APIServer;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class APIWorldTest {
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
        @DisplayName("GET /world")
        public void EndPoint() throws UnirestException {
            HttpResponse<JsonNode> response = Unirest.get("http://localhost:"+port+"/world" +
                    "").asJson();
            assertEquals(200, response.getStatus());
        }

        @Test
        @DisplayName("GET /world")
        public void getCurrentWorld() throws UnirestException {
            HttpResponse<JsonNode> response = Unirest.get("http://localhost:"+port+"/world" + "").asJson();
            assertEquals(200, response.getStatus());

            JsonNode res = response.getBody();
            assertEquals(res.toString(),"{\"mines\":[],\"obstacles\":[],\"pits\":[],\"worldSize\":{\"length\":2,\"width\":2}}");
        }
    }
