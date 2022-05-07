package server;

import java.io.PrintStream;

import client.Player;

public class Game {
    private int total;
    private int match;

    private int evens;

    private Player player1;
    private Player player2;

    private String playerA;
    private String playerB;

    private PrintStream output1; // para enviar quem ganha o jogo ?????
    private PrintStream output2; // para enviar quem ganha o jogo ?????

    public Game(PrintStream outputA, Player player1, PrintStream outputB, Player player2) {
        this.output1 = outputA;
        this.output2 = outputB;
        this.player1 = player1;
        this.player2 = player2;
        match = 0;
        evens = 0;
    }
    
    public void play(int moveA, int moveB) {
        match += 1;
        sendMessageToPlayers( "\n ----------------"
                        + "\n==== INICIO DA JOGADA " + match 
                        + "/ " + total
                        + "\n ----------------"
        );
        sendMatchMoves(moveA, moveB);
        
        final int rock = 1;
        final int paper = 2;
        final int sissors = 3;

        switch (moveA) {
            case rock: // pedra
                if (moveB == paper) {
                    winPlayer2();
                } else if (moveB == sissors) {
                    winPlayer1();
                } else {
                    makeEven();
                }
                break;
            case paper: // papel
                if (moveB == sissors) {
                    winPlayer2();
                } else if (moveB == rock) {
                    winPlayer1();
                } else {
                    makeEven();
                }
                break;
            case sissors:  // tesoura
                if (moveB == rock) {
                    winPlayer2();
                } else if (moveB == paper) {
                    winPlayer1();
                } else {
                    makeEven();
                }
                break;
            default:
            makeEven();
        }
        sendMessageToPlayers( "" + this );
    }
    private void winPlayer1() {
        score1 +=1;
        sendMessageToPlayers(playerA + "ganhou essa partida!");
    }
    private void winPlayer2() {
        score2 +=1;
        sendMessageToPlayers(playerB + "ganhou essa partida!");
    }
    private void makeEven() {
        evens += 1;
        sendMessageToPlayers("EMPATE!!!");
    }
    public void possibleMoves(){
        sendMessageToPlayers(
        "Digite o número de sua Escolha:"
        + "\n1 > pedra "
        + "\n1 > papel "
        + "\n1 > tesoura "
        );
    }
    private int getScore1() {
        return this.score1;
    }
    private int getScore2() {
        return this.score2;
    }
    private void sendMessageToPlayers( String msg ) {
        output1.println( msg );
        if (output2 != null) {
            output2.println( msg );
        } 
    }
    public Boolean isGameOver() {
        if (match < 5) { 
            return false;
        } else {
            String winer;
            if (score1 < score2) {
                winer = playerA;
            } else if (score1 > score2){
                winer = playerB;
            } else {
                winer = "ninguém";
            }
            sendMessageToPlayers( ">>>>>>>>>>>>>>" + winer + " GANHOU!!!" + ">>>>>>>>>>>>>>"
                + "\n:::::::::: GAME OVER ::::::::::");
            return true;
        }
    }
    private void sendMatchMoves(int moveA, int moveB) {
        sendMessageToPlayers(
            playerA + " jogada > " + turnMoveToString(moveA)
            + "\n" + playerB + " jogada > " + turnMoveToString(moveB)
        );
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
        return "------ jogada " + match + "/" + total + " -------"
        + "\n" + playerA + " ganhou " + getScore1()
        + "\n" + playerB + " ganhou " + getScore2()
        + "\n" + "totalizando" + evens + "empates"
        + "\n------------------------------------";
    }
}
