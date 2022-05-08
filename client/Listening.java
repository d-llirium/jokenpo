package client;

import java.net.Socket;
import java.util.Scanner;

public class Listening extends Thread {
    private String msg;
    private Socket socket;
    private Scanner input;

    public Listening (Socket socket) {
        this.socket = socket;
    }
    protected String getInput() {
        return this.msg;
    }

    @Override
    public void run() {
        try {
            input = new Scanner(socket.getInputStream());
            do {
                msg = input.nextLine();
                System.out.println(msg);
            } while(!msg.contains("GAME OVER"));
            input.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
