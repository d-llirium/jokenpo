 package client;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        final String IP = "127.0.0.1"; 
        final int PORT = 12345; 
        Socket socket;
        PrintStream output = null;
        Scanner keyboard = null; 
        String clientName;
        int gameType;

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
                    
                System.out.println(
                    "C > Digite \n0 para jogar contra a máquina"  
                    + "\n1 para jogar com outro jogador: "
                    );
                gameType = keyboard.nextInt(); // ----------- tratar entrada
                output.println(gameType); 

            } catch (Exception e) {
                System.out.println("C > xxxxxxx MSG DE ERRO = " + e.getMessage() + " xxxxxxx");
            }
        } catch (Exception e) {
            System.out.println("C > Não foi possível conectar ao servidor");
            System.out.println("C > xxxxxxx MSG DE ERRO = " + e.getMessage() + " xxxxxxx");
            return;
        }

        // TROCA DE DADOS
        try {
            keyboard = new Scanner(System.in); 
            Listening listening = new Listening(socket);
            listening.start();

            String msg; 
            do {
                msg = keyboard.nextLine(); 
                output.println(msg); 
            } while (!listening.getInput().equalsIgnoreCase("GAME OVER")); 
            
        } catch (Exception e) {
            System.out.println("cliente enceraaaaaaaaaa");
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
