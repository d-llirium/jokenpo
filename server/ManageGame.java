package server;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ManageGame extends Thread {
    private Socket clienteA = null;
    private Scanner inputA = null;
    private String nameA;
    private Socket clienteB = null;
    private Scanner inputB = null;
    String nameB;
    // private PrintStream output = null; // para enviar quem ganha o jogo ?????

    // SOBRECARGA DE MÉTODO PARA RECEBER TIPO DE JOGO
    public ManageGame(Socket clienteA, String nameA) {
        this.clienteA = clienteA;
        this.nameA = nameA;
        System.out.println("MG > Se prepare paga jogar contra a máquina.");
    }

    public ManageGame(Socket clienteA, String nameA, Socket clienteB, String nameB) {
        this.clienteA = clienteA;
        this.nameA = "A ." + nameA;
        this.clienteB = clienteB;
        this.nameB = "B ." + nameB;
        System.out.println("MG > " + nameA + " vs " + nameB + "...   fight! ");
    }
    @Override
    public void run() { // qnd der START
        // fase da comunicação tirada do SERVER = troca de dados
        try {
            inputA = new Scanner(clienteA.getInputStream()); // ler a mensagem do cliente A
            inputB = new Scanner(clienteB.getInputStream()); // ler a mensagem do cliente B
            String msgA;
            String msgB;
            // trabalhar nessa troca de mensagens
            do {
                msgA = inputA.nextLine(); // recebe a mensagem do cliente
                System.out.println( "MG > " + nameA + " > " + msgA);
                msgB = inputB.nextLine(); // recebe a mensagem do cliente
                System.out.println( "MG > " + nameB + " > " + msgB);
            } while((!msgA.equalsIgnoreCase("exit")) || (!msgB.equalsIgnoreCase("exit")));

            System.out.println("MG > Encerrada a conexão com " + clienteA.getInetAddress().getHostAddress());

            inputA.close();
            clienteA.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    
}