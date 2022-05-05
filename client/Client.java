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

        // criação do socket a pedido de conexão
        try {
            socket = new Socket(IP, PORT);
        } catch (Exception e) {
            System.out.println("Não foi possível conectar ao servidor");
            System.out.println("MSG DE ERRO = " + e.getMessage());
            return;
        }

        // fase de comunicação , troca de dados
        try {
            output = new PrintStream(socket.getOutputStream()); // para escrever para o servidor

            // ** INPUT PARA LER MSG DO SERVIDOR **
            // input = new Scanner(socket.getInputStream()); // para ler a mensagem do servidor
 
            teclado = new Scanner(System.in); //permite que seja escrita meg no teclado para o servidor 
            String msg; // recebe a msg do teclado 
            System.out.println("Digite o seu nome: ");
            msg = teclado.nextLine(); 
            output.println(msg); 
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
