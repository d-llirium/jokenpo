package server;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

import client.Player;

public class GameManager extends Thread {
    private Player player1;
    private Player player2;

    private Scanner receive_from_player1;
    private Scanner receive_from_player2;
    private PrintStream send_to_player1 = null;
    private PrintStream send_to_player2 = null; 

    // SOBRECARGA DE MÉTODO PARA RECEBER TIPO DE JOGO
    // jogador vs CPU
    public GameManager(Socket player_plug, String player_name) {
        player1 = new Player("player ." + player_name, 0, player_plug);
        player2 = new Player("MÁQUINA", 0, null);
    }
    // jogador vs jogador
    public GameManager(Socket player1_plug, String player1_name, Socket player2_plug, String player2_name) {
        player1 = new Player("player 1. " + player1_name, 1, player1_plug);
        player2 = new Player("player 2. " + player2_name, 1, player2_plug);
    }

    @Override
    public void run() { // .START
        // TROCA DE DADOS
        try {
            receive_from_player1 = new Scanner(player1.getPlug().getInputStream()); 
            send_to_player1 = new PrintStream(player1.getPlug().getOutputStream());

            if (player2.getPlug() != null) {
                receive_from_player2 = new Scanner(player2.getPlug().getInputStream());
                send_to_player2 = new PrintStream(player2.getPlug().getOutputStream());
            } else {
                receive_from_player2 = null;
                send_to_player2 = null;
            }
            System.out.println(
                "MG > " + player1.getName() + " vs " + player2.getName() + " ...   FIGHT! "
            );
            Game game = new Game(send_to_player1, player1, send_to_player2, player2);

            do {
                game.possibleMoves();

                // ---------- tratar entradas
                int move_player1 = player1.makeMove(receive_from_player1.nextInt());
                int move_player2;

                if (receive_from_player2 != null) {
                    move_player2 = player2.makeMove(receive_from_player2.nextInt());
                } else {
                    Random move_server = new Random();
                    move_player2 = player2.makeMove(move_server.nextInt(3)+1); 
                    System.out.println(
                        ">>>>> MG > SERVER MOVE " + move_player2
                    );
                }
                game.play(move_player1, move_player2);

            } while(!game.isGameOver()); 

            receive_from_player1.close();
             // send_to_player1.close();
            // player1.getPlug().close();
            if (receive_from_player2 != null) {
                receive_from_player2.close();
                // send_to_player2.close();
                // player2.getPlug().close();
            }
            System.out.println(
                "MG > Encerrada a conexão com " + player1.getPlug().getInetAddress().getHostAddress()
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}