package server;

import java.io.PrintStream;

public class Game {
    private int total;
    private int match;
    private int evens;
    private int score1;
    private int score2;
    private String player1;
    private String player2;
    private PrintStream output1; // para enviar quem ganha o jogo ?????
    private PrintStream output2; // para enviar quem ganha o jogo ?????

    public Game(PrintStream outputA, String playerName1, PrintStream outputB, String playerName2) {
        this.output1 = outputA;
        this.output2 = outputB;
        this.player1 = playerName1;
        this.player2 = playerName2;
        match = 0;
        score1 = 0;
        score2 = 0;
        evens = 0;
    }

    public void play(int moveA, int moveB) {
        match += 1;
        if (match > total) {
            // chama o método para acabar o JOGO
        }
        outputToPlayers( "\n ----------------"
                        + "\n==== INICIO DA JOGADA " + match 
                        + "/ " + total
                        + "\n ----------------"
        );      
        final int rock = 1;
        final int paper = 2;
        final int sissors = 3;

        switch (moveA) {
            case rock: // pedra
                if (moveB == paper) {
                    score2 += 1;
                } else if (moveB == sissors) {
                    score1 +=1;
                } else {
                    evens += 1;
                }
                break;
            case paper: // papel
                if (moveB == sissors) {
                    score2 += 1;
                } else if (moveB == rock) {
                    score1 +=1;
                } else {
                    evens += 1;
                }
                break;
            case sissors:  // tesoura
                if (moveB == rock) {
                    score2 += 1;
                } else if (moveB == paper) {
                    score1 +=1;
                } else {
                    evens += 1;
                }
                break;
            default:
            evens += 1;
        }
    }
    public void possibleMoves(){
        outputToPlayers(
        "Digite o número de sua Escolha:"
        + "\n1 > pedra "
        + "\n1 > papel "
        + "\n1 > tesoura "
        );
    }
    public int getScore1() {
        return this.score1;
    }
    public int getScore2() {
        return this.score2;
    }
    public void outputToPlayers( String msg ) {
        output1.println( msg );
        output2.println( msg );
    }
    public String toString() {
        return "------ SCOREBOARD -------"
        + "\n" + player1 + " ganhou " + getScore1()
        + "\n" + player2 + " ganhou " + getScore2()
        + "\n" + "totalizando" + evens + "empates"
        + "\n------------------------------------";
    }
    // cria um m´´todo para acabar o jogo
}
