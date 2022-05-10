package server;

import java.io.PrintStream;
import java.net.Socket;

public class Speaking {

    private PrintStream output;

    public Speaking (Socket socket) {
        try {
            output = new PrintStream(socket.getOutputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    protected void sendMessage( String msg) {
        output.println(msg);
    }  
    protected void stopSpeaking(){
        output.close();
    }
}
