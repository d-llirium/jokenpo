package server;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

import client.Player;

public class GameManager extends Thread {

    private Game game; 
    private Validate val = new Validate();

    private Player player1;
    Scanner receive_from_player1 = null;
    PrintStream send_to_player1 = null;

    private Player player2;
    Scanner receive_from_player2 = null;
    PrintStream send_to_player2 = null;  

    // SOBRECARGA DE MÉTODO PARA RECEBER TIPO DE JOGO
    // jogador vs CPU
    public GameManager(Socket player_plug, String player_name) {
        player1 = new Player("player ." + player_name, 1, player_plug);
        player2 = new Player("MÁQUINA", 1, null);
    }
    // jogador vs jogador
    public GameManager(Socket player1_plug, String player1_name, Socket player2_plug, String player2_name) {
        player1 = new Player("player 1. " + player1_name, 2, player1_plug);
        player2 = new Player("player 2. " + player2_name, 2, player2_plug);
    }

    @Override
    public void run() { // .START
        // TROCA DE DADOS
        try {
            receive_from_player1 = new Scanner(player1.getPlug().getInputStream());
            player1.setGameFromPlayer(receive_from_player1);
            
            send_to_player1 = new PrintStream(player1.getPlug().getOutputStream());
            player1.setGameToPlayer(send_to_player1);      

            if (player1.getGameType() == 2) { 
                receive_from_player2 = new Scanner(player2.getPlug().getInputStream());
                player2.setGameFromPlayer(receive_from_player2);

                send_to_player2 = new PrintStream(player2.getPlug().getOutputStream());
                player2.setGameToPlayer(send_to_player2);
            } 
            System.out.println(
                "GM > " + player1.getName() + " vs " + player2.getName() + " ...   FIGHT! "
            );
            game = new Game(player1, player2);

            do {
                int move_player1 = val.match(player1);
                int move_player2;

                if (player1.getGameType() == 2) { 
                    move_player2 = val.match(player2);
                } else {
                    Random move_server = new Random();
                    move_player2 = move_server.nextInt(3)+1; 
                    System.out.println(
                        ">>>>> MG > SERVER MOVE " + move_player2
                    );
                }
                game.play(move_player1, move_player2);
                game.isGameOver();
        
            } while(!receive_from_player1.nextLine().contains("STOP")); 

            receive_from_player1.close();
            send_to_player1.close();
            player1.getPlug().close();

            if (receive_from_player2 != null) {
                receive_from_player2.close();
                send_to_player2.close();
                player2.getPlug().close();
            }
            
            System.out.println(
                "MG > Encerrada a conexão com " + player1.getPlug().getInetAddress().getHostAddress()
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}