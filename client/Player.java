package client;

import java.net.Socket;

import server.Listening;
import server.Speaking;

public class Player {
    private String name;
    private int gameType;

    private Socket connectionPlug;
    private Listening listening_player;
    private Speaking speaking_player;

    private int wins;
    private int evens;
    private int lose;


    public Player(String name, int gameType, Socket socket, Listening listening_player, Speaking speaking_player){
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
        return connectionPlug;
    }
    public Listening getListeningToPlayer() {
        return listening_player;
    }
    public Speaking getSpeakingToPlayer() {
        return speaking_player;
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
    @Override
    public String toString() {
        return ">>>>>>>>>" + name + "<<<<<<<<<"
            + "\nvitórias: " + getWins() 
            + "\nderrotas: " + getLose()
            + "\nempates: " + getEvens()
            + "\n------------------------------------"
        ;
    }

}
