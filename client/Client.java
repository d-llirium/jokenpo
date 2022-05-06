 package client;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

// aplicação Cliente
public class Client {
    public static void main(String[] args) {
        final String IP = "127.0.0.1"; //IP que se refere a proópria máquina, como o THIS
        final int PORT = 12345; // precisa ser o mesmo número da porta do servidor
        Socket socket;
        PrintStream output = null;
        // Scanner input = null;
        Scanner teclado = null; 
        String clientName;
        int gameType;

        // criação do socket a pedido de conexão
        try {
            socket = new Socket(IP, PORT);

            try {
                // fase de comunicação , troca de dados
                output = new PrintStream(socket.getOutputStream()); // para escrever para o servidor
                teclado = new Scanner(System.in); //permite que seja escrita msg no teclado para o servidor 
    
                System.out.println("C > Digite o seu nome: ");
                clientName = teclado.nextLine(); 
                output.println(clientName); 

                System.out.println("C > Digite \n0 para jogar contra a máquina \n1 para jogar com outro jogador: ");
                gameType = teclado.nextInt(); 
                output.println(gameType); 

            } catch ( Exception e ){
                System.out.println("S > xxxxxxx ERRO não peguei o seu nome xxxxxxx");
            }

        } catch (Exception e) {
            System.out.println("S > Não foi possível conectar ao servidor");
            System.out.println("S > xxxxxxx MSG DE ERRO = " + e.getMessage() + " xxxxxxx");
            return;
        }

        // fase de comunicação , troca de dados
        try {
            // output = new PrintStream(socket.getOutputStream()); // para escrever para o servidor

            // ** INPUT PARA LER MSG DO SERVIDOR **
            // input = new Scanner(socket.getInputStream()); // para ler a mensagem do servidor
 
            teclado = new Scanner(System.in); //permite que seja escrita meg no teclado para o servidor 
            String msg; // recebe a msg do teclado 

            do { 
                System.out.println("Digite...: "); 
                msg = teclado.nextLine(); 
                output.println(msg); 
            } while (!msg.equalsIgnoreCase("exit")); 
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // fase de encerramento da conexão
        try {
            // input.close();
            output.close();
            socket.close();
            teclado.close();
            System.out.println("Acabou a conexão do CLIENTE");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
