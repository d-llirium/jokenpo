package server;

import java.net.Socket;
import java.util.Scanner;

public class Listening {
    private int msg;
    private Scanner input; 

    public Listening (Socket socket) {
        try {
            input = new Scanner(socket.getInputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    protected int receiveMessage() {
        msg = 0;
        do {
            msg = input.nextInt();
            System.out.println(msg);
        } while(msg != 0);
        return msg;
    }
    
    protected void stopListening() {
        input.close();
    }
            

}
