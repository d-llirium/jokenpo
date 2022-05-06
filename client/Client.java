 package client;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        final String IP = "127.0.0.1"; //IP que se refere a proópria máquina, como o THIS
        final int PORT = 12345; // precisa ser o mesmo número da porta do servidor
        Socket socket;
        PrintStream output = null;
        // Scanner input = null; // pra receber quem ganha as partidas ???
        Scanner keyboard = null; 
        String clientName;
        int gameType;

        // criação do socket a pedido de conexão
        try {
            socket = new Socket(IP, PORT);

            // PSEUDO FASE DE TROCA DE DADOS SOMENTE PARA PEGAR NOME E TIPO DE JOGO
            try {
                output = new PrintStream(socket.getOutputStream()); // msg enviada
                keyboard = new Scanner(System.in); // permite que seja escrita msg no teclado para o servidor 
    
                System.out.println("C > Digite o seu nome: ");
                clientName = keyboard.nextLine(); 
                output.println(clientName); 

                System.out.println("C > Digite \n0 para jogar contra a máquina \n1 para jogar com outro jogador: ");
                gameType = keyboard.nextInt(); 
                output.println(gameType); 

            } catch ( Exception e ){
                System.out.println("C > xxxxxxx ERRO não peguei o seu nome xxxxxxx");
            }

        } catch (Exception e) {
            System.out.println("C > Não foi possível conectar ao servidor");
            System.out.println("C > xxxxxxx MSG DE ERRO = " + e.getMessage() + " xxxxxxx");
            return;
        }

        // fase de comunicação , troca de dados
        try {
            // ** INPUT PARA LER MSG DO SERVIDOR **
            // input = new Scanner(socket.getInputStream()); // para ler a mensagem do servidor
 
            keyboard = new Scanner(System.in); //permite que seja escrita meg no teclado para o servidor 
            String msg; // recebe a msg do teclado 
// >>>> ADD aqui alguma coisa para dizer que a jogada iniciou
            do { 
                System.out.println("C > Digite...: "); 
                msg = keyboard.nextLine(); 
                output.println(msg); 
            } while (!msg.equalsIgnoreCase("exit")); 
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // fase de encerramento da conexão
        try {
            output.close();
            socket.close();
            keyboard.close();
            System.out.println("C > Acabou a conexão do CLIENTE");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
