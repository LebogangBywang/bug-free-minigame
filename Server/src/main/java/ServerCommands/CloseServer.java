package ServerCommands;

public class CloseServer extends Command{

    public CloseServer() {
        super("quit");
    }

    /**
     * The quit execute that closes all the clients
     * @return false to show we need to stop the server
     */
    @Override
    public boolean execute() {
//        Hashtable<String, ClientThreadHandler> clients = ServerMain.clients;
//        if(!clients.isEmpty()) {
//            for (ClientThreadHandler iter: clients.values()) {
//                ClientThreadHandler client = iter;
//                if (client.isAlive()) {
//                    try {
//                        client.closeClient();
//                    } catch (IOException e) {
//                        continue;
//                    }
//                }
//            }
//        }
        return false;
    }
}
