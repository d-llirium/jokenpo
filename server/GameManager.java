package server;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

import client.Player;

public class GameManager extends Thread {
    private Player player1;
    private Player player2;
    private Game game; 

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

    private int validateMatch( Player player ) {
        int i = 0;
        do {
            System.out.println("ON VALIDA por " + i );
            player.getGameToPlayer().println(game.possibleMoves());;
            int move = player.makeMove(player.getGameFromPlayer().nextInt());
            if ( move > 0 && move < 4 ) {
                return move;
            } else {
                player.getGameToPlayer().println("xxxxxxx escolha inálida, tente novamente.");
                i += 1;
            }
            if ( i == 5 ) {
                player.getGameToPlayer().println("!!! GAME OVER > DESQUALIFICADO");
            }
        } while ( i < 5 );
        return 0;
    }

    @Override
    public void run() { // .START
        // TROCA DE DADOS
        try {
            Scanner receive_from_player1 = new Scanner(player1.getPlug().getInputStream());
            player1.setGameFromPlayer(receive_from_player1);
            
            PrintStream send_to_player1 = new PrintStream(player1.getPlug().getOutputStream());
            player1.setGameToPlayer(send_to_player1);

            Scanner receive_from_player2 = null;
            PrintStream send_to_player2 = null;        

            if (player2.getPlug() != null) { // = MUDA ESSA VALIDAÇÃO
                receive_from_player2 = new Scanner(player2.getPlug().getInputStream());
                player2.setGameFromPlayer(receive_from_player2);

                send_to_player2 = new PrintStream(player2.getPlug().getOutputStream());
                player2.setGameToPlayer(send_to_player2);
            } 
            System.out.println(
                "GM > " + player1.getName() + " vs " + player2.getName() + " ...   FIGHT! "
            );
            game = new Game(send_to_player1, player1, send_to_player2, player2);

            do {
                // ---------- tratar entradas
                int move_player1 = validateMatch(player1);

                int move_player2;

                if (receive_from_player2 != null) { // = MUDA ESSA VALIDAÇÃO
                    move_player2 = validateMatch(player2);
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