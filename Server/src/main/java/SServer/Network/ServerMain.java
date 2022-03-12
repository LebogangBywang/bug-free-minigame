package SServer.Network;

import DataBase.DbConnect;
import ServerCommands.ServerArguments;
import SServer.World.Map;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintStream;

public class ServerMain {
    private static ServerSocket serverSocket;
    private static ServerArguments serverArguments;
    private static DbConnect dbConnect;
    private static Map map;

    /**
     * The main method of the server side, runs a continues to loop to allow more clients to join
     * @param args the command-line arguments
     * @throws IOException if the input or output is interrupted
     */
    public static void main(String[] args) throws IOException {
        serverArguments = new ServerArguments(args);
        map = new Map(serverArguments);
        serverSocket = new ServerSocket(map.getInitialize().getServerPort());
        System.out.println("Robot Worlds Server is running.");

        CommandListener cmdListener = new CommandListener(map);

        Thread cmd = new Thread(cmdListener);

        try {
            cmd.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        waitForConnection();
    }

    public static void waitForConnection() throws IOException{

        int clientNum = 0;
        while (true) {
            Socket socket = null;
            try {
                System.out.println("Waiting for client to connect...");

                socket = serverSocket.accept();

                while (!socket.isConnected()) {
                    continue;
                }

                System.out.println("Client has joined the server: " + socket);

                DataInputStream input = new DataInputStream(socket.getInputStream());
                PrintStream output = new PrintStream(socket.getOutputStream());

                ClientThreadHandler client = new ClientThreadHandler(socket,input,output,map,"client#"+clientNum);
                clientNum++;

                Thread thread = new Thread(client);
//                clients.put(client.getRobotName(),client);
                thread.start();

            } catch (IOException e) {
                socket.close();
                e.printStackTrace();
            }
        }
    }

    /**
     * Initializes whatever constant parameters we need
     * @param
     */


//    /**
//     * Checks if the position we want to spawn on is taken
//     * @param position the position we want to spawn on
//     * @param name
//     * @return true if the position is not available else false
//     */
//    public static boolean notAvailable(Position position, String name) {
//        for (Robot cl: map.getClients().values())
//            if (!cl.getName().equals(name) &&
//                    cl.getPosition().equals(position)) return true;
//        return false;
//    }

//    public static void restoreWorld(int length, int width){
//        WORLD_WIDTH = width;
//        WORLD_LENGTH = length;
//    }

    public static DbConnect getDbConnect(){
        return dbConnect;
    }

    public static Map getMap(){
        return map;
    }

    /*public static void Map() {
        JSONObject objects = ServerMain.getMap().getObjects();
        ServerMain.getDbConnect().getDAta(objects, names);
    }*/
}
