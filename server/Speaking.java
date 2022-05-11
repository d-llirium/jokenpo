package server;

import java.io.PrintStream;
import java.net.Socket;

public class Speaking {

    private PrintStream output;

    public Speaking (Socket socket) {
        try {
            this.output = new PrintStream(socket.getOutputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    protected void sendMessage( String msg) {
        this.output.println(msg);
    }  
    protected void stopSpeaking(){
        this.output.close();
    }
}
