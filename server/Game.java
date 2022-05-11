package server;

import client.Player;

public class Game {
    private int total;
    private int match;

    private Player player1;
    private Speaking speaking_player1;

    private Player player2;
    private Speaking speaking_player2;


    public Game(Player player1, Speaking speaking_player1, Player player2, Speaking speaking_player2) {
        this.player1 = player1;
        this.speaking_player1 = speaking_player1;
        this.player2 = player2;
        this.speaking_player2 = speaking_player2;
        this.match = 0;
        this.total = 5;
    }

    private void sendMessageToPlayers( String msg ) {
        this.speaking_player1.sendMessage( msg );

        if (this.player2.getGameType() == 2) {
            this.speaking_player2.sendMessage( msg );
        } 
    }

    private void sendMatchMoves(int move_player1, int move_player2) {
        sendMessageToPlayers(
            this.player1.getName() + " jogada > " + turnMoveToString(move_player1)
            + "\n" + this.player2.getName() + " jogada > " + turnMoveToString(move_player2)
        );
    }

    public void play(int move_player1, int move_player2) {
        this.match += 1;
        sendMessageToPlayers( 
            "\n --------------------------------"
            + "\n INICIO DA PARTIDA " + this.match + "/ " + this.total
            + "\n --------------------------------"
        );
        sendMatchMoves(move_player1, move_player2);
        
        int outcome = move_player1 - move_player2;

        if (outcome == -2 || outcome == 1) {
            whoWins(this.player1, this.player2);
        } else if (outcome == -1 || outcome == 2) {
            whoWins(this.player2, this.player1);
        } else {
            makeEven();
        }
        sendMessageToPlayers( "" + this );
    }

    private void whoWins(Player winer, Player loser) {
        winer.setWins(1);
        loser.setLose(1);
        sendMessageToPlayers( "!!!!!!!!!!!!!!!! " + winer.getName() + " !!!!!!!!!!!!!!!!"
                                                + "\n       GANHOU A PARTIDA MAS NÃO GANHOU O JOGO" );
    }
    private void makeEven() {
        player1.setEvens(1);
        player2.setEvens(1);
        sendMessageToPlayers(
            "EMPATE!!!"
        );
    }
    public Boolean isGameOver() {
        if (this.match == this.total) {
            String winer;
            if (this.player1.getWins() < this.player2.getWins()) {
                winer = this.player2.getName();

            } else if (this.player1.getWins() > this.player2.getWins()){
                winer = this.player1.getName();

            } else {
                winer = "NINGUÉM";
            }
            sendMessageToPlayers( ">>>>>>>>>>>>>>" + winer + " GANHOU!!!" + ">>>>>>>>>>>>>>"
                                + "\n:::::::::: GAME OVER ::::::::::");
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
            }
            return true;
        } else {
            System.out.println("ainda tá rolando o jogo");
            return false;
        }
    }
    private String turnMoveToString( int move_int ) {
        switch ( move_int ) 
        {
            case 1:
                return "PEDRA";
            case 2:
                return "PAPEL";
            case 3:
                return "TESOURA";     
            default: 
                return "INVÁLIDA";
        }
    }
    public void endGame() {
        this.match = this.total;
        isGameOver();
    }
    
    @Override
    public String toString() {
        return "------ jogada " + this.match + " / " + this.total + " -------"
        + "\n" + this.player1
        + "\n" + this.player2
        + "\n------------------------------------";
    } 
}
