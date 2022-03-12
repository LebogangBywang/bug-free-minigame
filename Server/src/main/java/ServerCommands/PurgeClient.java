package ServerCommands;

public class PurgeClient extends Command{
    String client;

    /**
     * The constructor that sets the client
     * @param arg the client name
     */
    public PurgeClient(String arg) {
        super("purge",arg);
        this.client = arg;
    }

    /**
     * The purge execute that closes a specific client
     * @return true to show our server thread still runs
     */
    @Override
    public boolean execute() {
//        Hashtable<String, ClientThreadHandler> clients = ServerMain.clients;
//        if(!clients.isEmpty()) {
//            for (ClientThreadHandler iter: clients.values()) {
//                ClientThreadHandler cl = iter;
//                if (cl.name.equalsIgnoreCase(client)) {
//                    try{
//                        cl.closeClient();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (ConcurrentModificationException ignored) {}
//                    System.out.println("The client: " + client + " has been purged.");
//                    break;
//                }
//            }
//        }
        return true;
    }
}
