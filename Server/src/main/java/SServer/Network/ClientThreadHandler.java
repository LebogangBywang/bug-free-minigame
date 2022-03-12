package SServer.Network;

import SServer.ClientCommands.Command;
import SServer.ClientCommands.Error;
import SServer.World.Map;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ConcurrentModificationException;

public class ClientThreadHandler extends Thread{
    final PrintStream output;
    final DataInputStream input;
    private final Socket socket;
    public String name;
    Command command;
    public Map map;


    /**
     * The client thread constructor
     * @param s the socket we are connected to
     * @param input the input we get from the server
     * @param output the output we send to the server
     * @param map
     * @param clientName the clients default name before a name is assigned
     */
    public ClientThreadHandler(Socket s, DataInputStream input, PrintStream output, Map map, String clientName) {
        this.output = output;
        this.socket = s;
        this.input = input;
        this.name = clientName;
        this.map = map;
    }

    /**
     * The thread for handling each client
     */
    @Override
    public void run() {
        String returnOut = "";
        boolean shouldContinue = true;
        while (shouldContinue) {
            try {
                String userInput = input.readLine();
                try {
                    if (userInput == null){
                        continue;
                    }
                    command = Command.create(userInput, map);
                    shouldContinue = command.execute();
                    if (!shouldContinue) break;
                    returnOut = command.createResponse();
                } catch (Error error) {
                    returnOut = error.buildResponse();
                }

                output.println(returnOut);

                if (returnOut.contains("quit")) {
                    this.socket.close();
                }

            } catch (IOException e) {
                try {
                    this.output.close();
                    this.input.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        try {
            this.socket.close();
            this.input.close();
            this.output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the clients input and output streams and the socket
     * @throws IOException
     */
    public void closeClient() throws IOException {
        try {
//            map.getClients().remove(this.getRobotName());
        } catch (ConcurrentModificationException ignored) {}
        this.input.close();
        this.output.close();
        this.socket.close();
    }


//    public boolean blocksPosition(Position position) {
//        int topLeftX = this.robot.getPosition().getX();
//        //int bottomRightX = this.bottomRight.getX();
//        int topLeftY = this.robot.getPosition().getY();
//        //int bottomRightY = this.bottomRight.getY();
//        System.out.println(topLeftY);
//        System.out.println(topLeftX);
//        boolean xOnObstacle = topLeftX == position.getX();
//        boolean yOnObstacle = topLeftY == position.getY();
//        return xOnObstacle && yOnObstacle;
//    }
//
//    public boolean blocksPath(Position currentPos, Position newPosition, String name) {
//        int curPosX = currentPos.getX();
//        int curPosY = currentPos.getY();
//        int newPosX = newPosition.getX();
//        int newPosY = newPosition.getY();
//
//        if (curPosX == newPosX) {
//            if (curPosY < newPosY) {
//                for (int i = curPosY; i <= newPosY; i++) {
//                    if(notAvailable(new Position(newPosX, i),name)) return true;
//                }
//            }else if (curPosY > newPosY) {
//                for (int i = newPosY; i < curPosY; i++) {
//                    if (notAvailable(new Position(newPosX,i),name)) return true;
//                }
//            }
//        } else if (curPosY == newPosY) {
//            if (curPosX < newPosX) {
//                for (int i = curPosX; i <= newPosX; i++) {
//                    if (notAvailable(new Position(i,newPosY),name)) return true;
//                }
//            } else if (curPosX > newPosX) {
//                for (int i = newPosX; i <= curPosX ; i++) {
//                    if (notAvailable(new Position(i,newPosY),name)) return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    public boolean notAvailable(Position position, String name) {
//        for (ClientThreadHandler cl : ServerMain.clients.values()) {
//            if (cl.robot != null && !cl.getRobot().getName().equals(name) &&
//                    cl.robot.getPosition().equals(position)) {
//                enemyClient = cl;
//                return true;
//            }
//        }
//        return false;
//    }

    /**
     * Sends a state to the client
     * @param state the state object in string format
     */
    public void sendState(String state) {

        output.println(state);
    }

//    public ClientThreadHandler getEnemyClient() { return this.enemyClient; }
}