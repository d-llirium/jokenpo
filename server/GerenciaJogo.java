package server;

import java.net.Socket;
import java.util.Scanner;

/**
 * jogo
 */
public class GerenciaJogo extends Thread {
    private Socket clienteA = null;
    private Scanner inputA = null;

    public GerenciaJogo(Socket clienteA) {
        this.clienteA = clienteA;
    }
    @Override
    public void run() { // qnd der START
        // fase da comunicação tirada do SERVER = troca de dados
        try {
            inputA = new Scanner(clienteA.getInputStream()); // ler a mensagem do cliente A
            String msgA;
            // String msgB;
            do {
                msgA = inputA.nextLine(); // recebe a mensagem do cliente
                System.out.println("A > " + msgA);
            } while(!msgA.equalsIgnoreCase("exit"));

            System.out.println("GJ > Encerrada a conexão com " + clienteA.getInetAddress().getHostAddress());

            inputA.close();
            clienteA.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    
}