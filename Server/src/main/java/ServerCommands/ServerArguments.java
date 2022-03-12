package ServerCommands;

import java.util.ArrayList;
import java.util.List;

public class ServerArguments {

    private final String[] args;
    private int port;
    private int[] size;
    private final List<int[]> obs = new ArrayList<>();
    private final List<int[]> pits = new ArrayList<>();
    private int[] position;
    private boolean testing =false;

    public ServerArguments(String[] args){
        this.args = args;
        handleArg();
    }

    public void handleArg(){
        String port = "5000";
        String size = "1,1";
        String obs = "";
        String pits = "";
        for (int i=0; i< args.length;i++){

            if (this.args[i].equalsIgnoreCase("-t")){
                try {
                    if (this.args[i + 1].equalsIgnoreCase("true")){
                        testing = true;
                    }
                }catch (ArrayIndexOutOfBoundsException e){
                    testing = false;
                }
            }

            if (this.args[i].equals("-p")){
                try {
                    port = this.args[i + 1];
                }catch(ArrayIndexOutOfBoundsException e){
                    port = "5000";
                }
            }

            if(this.args[i].equals("-s")){
                try {
                    size = (this.args[i+1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    size = "1x1";
                }
            }

            if (this.args[i].equalsIgnoreCase("-o")){
                try {
                    obs = (this.args[i+1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    obs= "";
                }
            }

            if (this.args[i].equalsIgnoreCase("-b")){
                try {
                    pits = (this.args[i+1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    pits= "";
                }
            }
        }
        setPort(port);
        setSize(size);
        setObs(obs);
        setPits(pits);
    }

    public boolean getTesting() {
        return this.testing;
    }

    private void setSize(String val) {
        String[] value = val.split("x");

        if (value.length == 1){
            value = new String[]{value[0],value[0]};
        }

        int[] worldSize = {1,1};
        try {
            worldSize[0] = Integer.parseInt(value[0]);
            worldSize[1] = Integer.parseInt(value[1]);
        } catch (NumberFormatException e) {
            System.out.println("setting default world size 1x1");
            this.size = new int[]{1,1};
        }

        if (worldSize[0] < 0 || worldSize[0] > 9999 || worldSize[1] < 0 || worldSize[1] > 9999 ){
            System.out.println("world size out of range!");
            System.out.println("setting default world size 1x1");
            this.size = new int[] {1,1};
        }
        this.size = worldSize;
    }

    /**
     * Checks if the port entered by the user is a digit within a specific range
     * @return port entered by the user
     */
    private void setPort(String val) {
        int serverPort = 5000;
//
        try {
            serverPort = Integer.parseInt(val);
        } catch (NumberFormatException e) {
            this.port = 5000;
        }
        //Check the specific range of the port
        //2000 and above just to be safe
        if (serverPort < 0 || serverPort > 9999) {
            System.out.println("Port out of range!");
            System.out.println("Setting default port: 5000");
            this.port = 5000;
        }
        this.port = serverPort;
    }

    private int setObs(String val){
//        (1,1);(2,2);(3,3)
        String[] Obstacles = val.split(";");
        List<int[]> obsList = null;

        if (Obstacles.length == 0){
//            this.obs = {};
            return 0;
        }

        for (int i=0; i < Obstacles.length; i++){
            int[] Obs;
            try {
                Obstacles[i] = Obstacles[i].replace("(","").replace(")","");
                String[] obstacles = Obstacles[i].split(",");
                Obs = new int[]{Integer.parseInt(obstacles[0]),Integer.parseInt(obstacles[1])};
                this.obs.add(Obs);

            } catch (NumberFormatException e) {
                System.out.println("incorrect world obstacle format - " + Obstacles[i]);
                return 0;
                }
        }
        return 1;

    }

    private int setPits(String val){
//        (1,1);(2,2);(3,3)
        String[] Pits = val.split(";");

        if (Pits.length == 0)
            return 0;

        for (int i=0; i < Pits.length; i++){
            int[] b_pits;
            try {
                Pits[i] = Pits[i].replace("(","").replace(")","");
                String[] pits = Pits[i].split(",");
                b_pits = new int[]{Integer.parseInt(pits[0]),Integer.parseInt(pits[1])};
                this.pits.add(b_pits);

            } catch (NumberFormatException e) {
                System.out.println("incorrect world pit format - " + Pits[i]);
                return 0;
                }
        }
        return 1;

    }

    public List<int[]> getPits(){
        return this.pits;
    }

    public int[] getSize(){
        return this.size;
    }

    public int getPort(){
        return this.port;
    }

    public List<int[]> getObs(){
        return this.obs;
    }



}
