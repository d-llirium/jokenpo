package client;

import java.net.Socket;

public class Player {
    private String name;
    private int gameType;

    private Socket connectionPlug;

    private int wins;
    private int evens;
    private int lose;


    public Player(String name, int gameType, Socket socket){
        this.name = name;
        this.gameType = gameType;
        this.connectionPlug = socket;
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

    public Socket getConnectionPlug() {
        return this.connectionPlug;
    }

    public void setWins(int times) {
        this.wins += times;
    }
    public int getWins() {
        return this.wins;
    }
    public void setEvens(int times) {
        this.evens += times;
    }
    public int getEvens() {
        return this.evens;
    }
    public void setLose(int times) {
        this.lose += times;
    }
    public int getLose() {
        return this.lose;
    }
    @Override
    public String toString() {
        return ">>>>>>>>>" + getName() + "<<<<<<<<<"
            + "\nvitórias: " + getWins() 
            + "\nderrotas: " + getLose()
            + "\nempates: " + getEvens()
            + "\n------------------------------------"
        ;
    }

}
