package server;

import client.Player;

public class Game {
    private int total;
    private int match;

    private Player player1;
    private Player player2;


    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        match = 0;
        total = 5;
    }

    private void sendMessageToPlayers( String msg ) {
        player1.getGameToPlayer().println( msg );
        if (player2.getGameType() == 2) {
            player2.getGameToPlayer().println( msg );
        } 
    }
    private void sendMatchMoves(int move_player1, int move_player2) {
        sendMessageToPlayers(
            player1.getName() + " jogada > " + turnMoveToString(move_player1)
            + "\n" + player2.getName() + " jogada > " + turnMoveToString(move_player2)
        );
    }

    public void play(int move_player1, int move_player2) {
        match += 1;
        sendMessageToPlayers( 
            "\n --------------------------------"
            + "\n INICIO DA PARTIDA " + match + "/ " + total
            + "\n --------------------------------"
        );
        sendMatchMoves(move_player1, move_player2);
        
        int outcome = move_player1 - move_player2;

        if (outcome == -2 || outcome == 1) {
            whoWins(player1, player2);
        } else if (outcome == -1 || outcome == 2) {
            whoWins(player2, player1);
        } else {
            makeEven();
        }
        sendMessageToPlayers( "" + this );
    }

    private void whoWins(Player winer, Player loser) {
        winer.setWins(1);
        loser.setLose(1);
        winer.getGameToPlayer().println(
            "!!!!!!!!!!!!!!!! " + winer.getName() + " !!!!!!!!!!!!!!!!"
            + "\n       GANHOU A PARTIDA MAS NÃO GANHOU O JOGO"
        );
    }
    private void makeEven() {
        player1.setEvens(1);
        player2.setEvens(1);
        sendMessageToPlayers(
            "EMPATE!!!"
        );
    }
    public void isGameOver() {
        if (match == total) { 
            String winer;
            if (player1.getWins() < player2.getWins()) {
                winer = player2.getName();
            } else if (player1.getWins() > player2.getWins()){
                winer = player1.getName();
            } else {
                winer = "NINGUÉM";
            }
            sendMessageToPlayers( 
                ">>>>>>>>>>>>>>" + winer + " GANHOU!!!" + ">>>>>>>>>>>>>>"
                + "\n:::::::::: GAME OVER ::::::::::"
            );
        } else {
            System.out.println("ainda tá rolando o jogo");
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
    public String toString() {
        return "------ jogada " + match + " / " + total + " -------"
        + "\n" + player1
        + "\n" + player2
        + "\n------------------------------------";
    } 
}
