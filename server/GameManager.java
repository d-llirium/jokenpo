package server;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

import client.Player;

public class GameManager extends Thread {
    private Player player1;
    private Player player2;
    private Scanner msg_from_player1;
    private Scanner msg_from_player2;
    private PrintStream msg_to_player1;
    private PrintStream msg_to_player2; 

    // SOBRECARGA DE MÉTODO PARA RECEBER TIPO DE JOGO
    // jogador vs CPU
    public GameManager(Socket player_plug, String player_name) {
        player1 = new Player("player ." + player_name, 0, player_plug);
        player2 = new Player("MÁQUINA", 0, null);
        }
    // jogador vs jogador
    public GameManager(Socket player1_plug, String player1_name, Socket player2_plug, String player2_name) {
        player1 = new Player("player 1. " + player1_name, 1, player1_plug);
        player1 = new Player("player 2. " + player2_name, 1, player2_plug);
    }

    @Override
    public void run() { // qnd der START
        // fase da comunicação tirada do SERVER = troca de dados
        try {
            System.out.println("MG > " + player1.getName() + " vs " + player2.getName() + " ...   FIGHT! ");
            msg_from_player1 = new Scanner(player1.getPlug().getInputStream()); 
            msg_to_player1 = new PrintStream(player1.getPlug().getOutputStream());
            if (player2.getPlug() != null) {
                msg_from_player2 = new Scanner(player2.getPlug().getInputStream());
                msg_to_player2 = new PrintStream(player2.getPlug().getOutputStream());
            } else {
                msg_from_player2 = null;
                msg_to_player2 = null;
            }
            
            Game game = new Game(msg_to_player1, player1, msg_to_player2, player2);
            // trabalhar nessa troca de mensagens
            do {
                game.possibleMoves();

                int move_player1 = player1.makeMove(msg_from_player1.nextInt());
                int move_player2 = int();

                if (msg_from_player2 != null) {
                    move_player2 = player2.makeMove(msg_from_player2.nextInt());
                } else {
                    Random move_server = new Random();
                    move_player2 = player2.makeMove(move_server.nextInt(4));
                    System.out.println(">>>>> MG > SERVER MOVE " + move_player2);
                }
                
                game.play(move_player1, move_plyer2);
                
            } while(!game.isGameOver()); // colocar o fim do jogo aqui

            System.out.println("MG > Encerrada a conexão com " + clientA.getInetAddress().getHostAddress());
            msg_from_player2.close();
            msg_from_player1.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    
}