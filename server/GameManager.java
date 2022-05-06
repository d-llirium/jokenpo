package server;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class GameManager extends Thread {
    private Socket clientA = null;
    private Scanner inputA = null;
    private String nameA;
    private Socket clientB = null;
    private Scanner inputB = null;
    private String nameB;
    private PrintStream outputA = null; // para enviar quem ganha o jogo ?????
    private PrintStream outputB = null; // para enviar quem ganha o jogo ?????


    // SOBRECARGA DE MÉTODO PARA RECEBER TIPO DE JOGO
    // jogador vs CPU
    public GameManager(Socket clientA, String nameA) {
        this.clientA = clientA;
        this.nameA = nameA;
        System.out.println("MG > Se prepare paga jogar contra a máquina.");
    }
    // jogador vs jogador
    public GameManager(Socket clientA, String nameA, Socket clientB, String nameB) {
        this.clientA = clientA;
        this.nameA = "A ." + nameA;
        this.clientB = clientB;
        this.nameB = "B ." + nameB;
        System.out.println("MG > " + nameA + " vs " + nameB + "...   fight! ");
    }

    @Override
    public void run() { // qnd der START
        // fase da comunicação tirada do SERVER = troca de dados
        try {
            inputA = new Scanner(clientA.getInputStream()); 
            outputA = new PrintStream(clientA.getOutputStream());

            inputB = new Scanner(clientB.getInputStream());
            outputB = new PrintStream(clientB.getOutputStream());
            
            int moveA;
            int moveB;
            Game game = new Game(outputA, nameA, outputB, nameB);
            // trabalhar nessa troca de mensagens
            do {
                game.possibleMoves();

                moveA = inputA.nextInt(); 
                moveB = inputB.nextInt();

                game.play(moveA, moveB);

                
                

                
                // System.out.println( "MG > " + nameA + " > " + msgA);
                // msgB = inputB.nextLine(); // recebe a mensagem do cliente
                // System.out.println( "MG > " + nameB + " > " + msgB);
            } while((!msgA.equalsIgnoreCase("exit")) || (!msgB.equalsIgnoreCase("exit"))); // colocar o fim do jogo aqui

            System.out.println("MG > Encerrada a conexão com " + clientA.getInetAddress().getHostAddress());

            inputA.close();
            clientA.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    
}