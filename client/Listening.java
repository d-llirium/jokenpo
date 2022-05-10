package client;

import java.net.Socket;
import java.util.Scanner;

public class Listening extends Thread {
    private String msg;
    private Socket socket;

    public Listening (Socket socket) {
        this.socket = socket;
    }
    // para resgatar a mensagem que recebida do client
    protected String getInput() {
        return this.msg;
    }

    @Override
    public void run() {
        try {
            Scanner input = new Scanner(socket.getInputStream());
            // fica escutando o que o Server fala
            do {
                msg = input.nextLine();
                System.out.println(msg);
            } while(!msg.contains("GAME OVER"));

            // 1o ponto a ser fechado
            input.close(); 
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
