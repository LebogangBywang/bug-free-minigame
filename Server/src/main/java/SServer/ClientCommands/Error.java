package SServer.ClientCommands;

import org.json.simple.JSONObject;

public class Error extends Exception{

    public Error(String not_a_command) {
        super(not_a_command);
    }

    /**
     * Builds the error response
     * @return jsonString
     */
    public String buildResponse() {
        JSONObject response = new JSONObject();
        JSONObject data = new JSONObject();

        response.put("result","ERROR");
        data.put("message",this.getMessage());

        response.put("data",data);
        return response.toJSONString();
    }
}
