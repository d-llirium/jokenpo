package server;

import java.net.Socket;
import java.util.Random;

import client.Player;
// ** TRABALHA COMO JUIZ DO JOGO
public class GameManager extends Thread {

    private Game game; 

    private Player player1;
    private Player player2;

    // ** SOBRECARGA DE MÉTODO PARA RECEBER TIPO DE JOGO
    // jogador vs CPU
    public GameManager(Socket player_connection_plug, String player_name) {

        Listening listening_player = new Listening(player_connection_plug);
        Speaking speaking_player = new Speaking(player_connection_plug); //objeto para falar

        player1 = new Player("player ." + player_name, 1, player_connection_plug, listening_player, speaking_player);

        player2 = new Player("MÁQUINA", 1, null, null, null);
    }

    // jogador vs jogador 
    public GameManager(Socket player1_connection_plug, String player1_name, Socket player2_connection_plug, String player2_name) {

        Listening listening_player1 = new Listening(player1_connection_plug);
        Speaking speaking_player1 = new Speaking(player1_connection_plug);  //objeto para falar

        player1 = new Player("player 1. " + player1_name, 2, player1_connection_plug, listening_player1, speaking_player1);

        Listening listening_player2 = new Listening(player2_connection_plug);
        Speaking speaking_player2 = new Speaking(player2_connection_plug);  //objeto para falar

        player2 = new Player("player 2. " + player2_name, 2, player2_connection_plug, listening_player2, speaking_player2);
    }
    private String possibleMoves() {
        return "Digite o número de sua Escolha:"
            + "\n1 > pedra "
            + "\n2 > papel "
            + "\n3 > tesoura "; 
    }
    private int match( Player player ) {
        int i = 0;
        do {
            // -- envia as opções 
            player.getSpeakingToPlayer().sendMessage(possibleMoves());
            int num = player.getListeningToPlayer().receiveMessage();
            if (num > 0 || num < 4) {
                return num;
            } else {
                player.getSpeakingToPlayer().sendMessage("xxxxxxx escolha inálida, tente novamente.");
                i += 1;
            }            
            if ( i == 5 ) {
                player.getSpeakingToPlayer().sendMessage("!!! GAME OVER > DESQUALIFICADO");
                game.endGame();
            }
        } while ( i < 5 );
        return 1;
    }
    @Override
    public void run() { // .START
        // TROCA DE DADOS
        try {
            System.out.println("GM > " 
            + "\n:::::: " + player1.getName() + " VS " + player2.getName() + " ...   FIGHT! ::::::");
            // ** O JUIZ CRIA O JOGO COM OS DOIS PLAYERS
            game = new Game(player1, player2);

            do {
                // jogada do player 1
                int move_player1 = match(player1);
                int move_player2;

                // jogada do player 2
                if (player1.getGameType() == 2) { 
                    move_player2 = match(player2);
                } else {
                    // caso seja contra uma máquina essa é a jogada da máquina
                    Random move_server = new Random();
                    move_player2 = move_server.nextInt(3)+1; 
                    System.out.println( ">>>>> MG > SERVER MOVE " + move_player2);
                }
                // processando a jogada 
                game.play(move_player1, move_player2);
        
            } while(!game.isGameOver()); 
            
            // 3o e último ponto a ser fechado
            player1.getListeningToPlayer().stopListening();
            player1.getSpeakingToPlayer().stopSpeaking();
            player1.getConnectionPlug().close();
            System.out.println(
                "MG > Encerrada a conexão com " + player1.getConnectionPlug().getInetAddress().getHostAddress()
            );

            if (player1.getGameType() == 2) {
                player2.getListeningToPlayer().stopListening();
                player2.getSpeakingToPlayer().stopSpeaking();;
                player2.getConnectionPlug().close();
                System.out.println("MG > Encerrada a conexão com " + player2.getConnectionPlug().getInetAddress().getHostAddress());
            }
    
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}