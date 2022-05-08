package client;

import java.net.Socket;

public class Player {
    private String name;
    private int gameType;
    private Socket plug;
    private int wins;
    private int evens;
    private int lose;

    public Player(String name, int gameType, Socket socket){
        this.name = name;
        this.gameType = gameType;
        this.plug = socket;
        this.wins = 0;
        this.evens = 0;
        this.lose = 0;
    }   

    public String getName() {
        return this.name;
    }
    public int getGameType() {
        return this.gameType;
    }
    public Socket getPlug() {
        return plug;
    }
    public int makeMove( int move ){
        return move;
    }
    public void setWins(int times) {
        wins += times;
    }
    public int getWins() {
        return wins;
    }
    public void setEvens(int times) {
        evens += times;
    }
    public int getEvens() {
        return evens;
    }
    public void setLose(int times) {
        lose += times;
    }
    public int getLose() {
        return lose;
    }
    public String toString() {
        return ">>>>>>>>>" + name + "<<<<<<<<<"
            + "\nvitórias: " + getWins() 
            + "\nderrotas: " + getLose()
            + "\nempates: " + getEvens()
            + "\n------------------------------------"
        ;
    }

}
