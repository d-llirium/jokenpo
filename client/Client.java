 package client;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import server.Validate;

public class Client {
    public static void main(String[] args) {
        final String IP = "127.0.0.1"; 
        final int PORT = 12345; 
        Socket socket; 
        PrintStream output = null; 
        Scanner keyboard = null; 
        String clientName; 
        Validate val = new Validate();

        // PEDE CNX
        try {
            socket = new Socket(IP, PORT);

            try {
                output = new PrintStream(socket.getOutputStream()); 
                keyboard = new Scanner(System.in);
                
                System.out.println(
                "C > Digite o seu nome: "
                );
                clientName = keyboard.nextLine(); 
                output.println(clientName); 
                do {    
                    System.out.println(
                        "C > Digite \n1 para jogar contra a máquina"  
                        + "\n2 para jogar com outro jogador: "
                    );
                    val.setReceivedString( keyboard );
                    
                } while(!val.strToInt(0, 3));
                output.println(val.num); 
                if ( val.num == 2) {
                    System.out.println(
                        "S > Aguardando um próximo jogador ... "
                    );
                }
            } catch (Exception e) {
                System.out.println("C > xxxxxxx MSG DE ERRO = " + e.getMessage() + " xxxxxxx");
            }
        } catch (Exception e) {
            System.out.println("C > xxxxxxx MSG DE ERRO = " + e.getMessage() + " xxxxxxx");
            return;
        }

        // TROCA DE DADOS
        try {
            Listening listening = new Listening(socket);
            listening.start();

            String msg; 
            do {
                msg = keyboard.nextLine(); 
                output.println(msg); 
            } while (!listening.getInput().contains("GAME OVER")); 

            output.println("STOP");
            System.out.println("cliente enceraaaaaaaaaa");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // ENCERRA CNX
        try {
            keyboard.close();
            output.close();
            socket.close();
            System.out.println("C > Acabou a conexão do CLIENTE");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
