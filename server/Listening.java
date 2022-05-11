package server;

import java.net.Socket;
import java.util.Scanner;

public class Listening {
    private String msg;
    private Scanner input; 
    private Validate val = new Validate();

    public Listening (Socket socket) {
        try {
            this.input = new Scanner(socket.getInputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    protected int receiveMessage() {
        int num;

        this.msg = "";
            this.msg = input.nextLine();
            System.out.println(msg);
            num = val.stringToInt(msg);
            
        return num;
    }
    
    protected void stopListening() {
        this.input.close();
    }
            

}
