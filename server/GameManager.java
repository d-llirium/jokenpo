package server;

import java.net.Socket;
import java.util.Random;

import client.Player;
// ** TRABALHA COMO JUIZ DO JOGO
public class GameManager extends Thread {

    private Game game; 

    private Player player1;
    private Player player2;

    private Listening listening_player1;
    private Speaking speaking_player1;

    private Listening listening_player2;
    private Speaking speaking_player2;

    // ** SOBRECARGA DE MÉTODO PARA RECEBER TIPO DE JOGO
    // jogador vs CPU
    public GameManager(Socket player_connection_plug, String player_name) {

        this.listening_player1 = new Listening(player_connection_plug); //objeto para escutar o jogador
        this.speaking_player1 = new Speaking(player_connection_plug); //objeto para falar o jogador

        this.player1 = new Player("player ." + player_name, 1, player_connection_plug);

        this.listening_player2 = null;
        this.speaking_player2 = null;
        this.player2 = new Player("MÁQUINA", 1, null);
    }

    // jogador vs jogador 
    public GameManager(Socket player1_connection_plug, String player1_name, Socket player2_connection_plug, String player2_name) {

        this.listening_player1 = new Listening(player1_connection_plug); //objeto para escutar o jogador 1
        this.speaking_player1 = new Speaking(player1_connection_plug);  //objeto para falar o jogador 1

        this.player1 = new Player("player 1. " + player1_name, 2, player1_connection_plug);

        this.listening_player2 = new Listening(player2_connection_plug);//objeto para escutar o jogador 2
        this.speaking_player2 = new Speaking(player2_connection_plug);  //objeto para falar o jogador 2

        this.player2 = new Player("player 2. " + player2_name, 2, player2_connection_plug);
    }
    private String possibleMoves() {
        return "Digite o número de sua Escolha:"
            + "\n1 > pedra "
            + "\n2 > papel "
            + "\n3 > tesoura "; 
    }
    private int match( Listening listening_player, Speaking speaking_player ) {
        int i = 0;
        do {
            // -- envia as opções 
            speaking_player.sendMessage(possibleMoves());
            int num = listening_player.receiveMessage();
            if (num > 0 && num < 4) {
                return num;
            } else {
                speaking_player.sendMessage("xxxxxxx escolha inálida, tente novamente.");
                i += 1;
            }            
            if ( i == 5 ) {
                speaking_player.sendMessage("!!! GAME OVER > DESQUALIFICADO");
                this.game.endGame();
            }
        } while ( i < 5 );
        System.out.println("GM > validação falhouuuuuuu");
        return 1;
    }
    @Override
    public void run() { // .START
        // TROCA DE DADOS
        try {
            System.out.println("GM > " 
            + "\n:::::: " + this.player1.getName() + " VS " + this.player2.getName() + " ...   FIGHT! ::::::");
            // ** O JUIZ CRIA O JOGO COM OS DOIS PLAYERS
            this.game = new Game(this.player1, this.speaking_player1, this.player2, this.speaking_player2);

            do {
                // jogada do player 1
                int move_player1 = match(this.listening_player1, this.speaking_player1);
                int move_player2;

                // jogada do player 2
                if (this.player1.getGameType() == 2) { 
                    move_player2 = match(this.listening_player2, this.speaking_player2);
                } else {
                    // caso seja contra uma máquina essa é a jogada da máquina
                    Random move_server = new Random();
                    move_player2 = move_server.nextInt(3)+1; 
                    System.out.println( ">>>>> MG > SERVER MOVE " + move_player2);
                }
                // processando a jogada 
                this.game.play(move_player1, move_player2);
                this.game.isGameOver();
        
            } while(!this.game.isGameOver()); 
            
            // 3o e último ponto a ser fechado
            this.listening_player1.stopListening();
            this.speaking_player1.stopSpeaking();
            this.player1.getConnectionPlug().close();
            System.out.println(
                "MG > Encerrada a conexão com " + this.player1.getConnectionPlug().getInetAddress().getHostAddress()
            );

            if (this.player1.getGameType() == 2) {
                this.listening_player2.stopListening();
                this.speaking_player2.stopSpeaking();;
                this.player2.getConnectionPlug().close();
                System.out.println("MG > Encerrada a conexão com " + this.player2.getConnectionPlug().getInetAddress().getHostAddress());
            }
    
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}