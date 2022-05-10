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
        Listening listening = null;

        // PEDE CNX
        try {
            socket = new Socket(IP, PORT);
            try {
                output = new PrintStream(socket.getOutputStream()); 
                keyboard = new Scanner(System.in);
                
                System.out.println("C > Digite o seu nome: ");
                clientName = keyboard.nextLine(); 
                output.println(clientName); 

                int gameType;
                do {    
                    System.out.println("C > Digite \n1 para jogar contra a máquina"  
                        + "\n2 para jogar com outro jogador: " );
                    gameType = keyboard.nextInt();
                } while(!(gameType == 1 || gameType == 2));

                output.println(gameType); 
                if ( gameType == 2) {
                    System.out.println("S > Aguardando um próximo jogador ... ");
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
            // CLIENT ESCUTA
            listening = new Listening(socket);
            listening.start();

            // CLIENT FALA
            String msg; 
            do {
                msg = keyboard.nextLine(); 
                output.println(msg); 
            } while (!listening.getInput().contains("GAME OVER")); 
            // 2o ponto a ser fechado

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // ENCERRA CNX
        try { 
            output.println(111111);
            keyboard.close();
            output.close();
            socket.close();
            System.out.println("C > Acabou a conexão do CLIENTE");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
