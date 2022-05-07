package client;

import java.net.Socket;

public class Player {
    private String name;
    private int gameType;
    private int score;
    private Socket plug;
    private int move;

    public Player(String name, int gameType, Socket socket){
        this.name = name;
        this.gameType = gameType;
        this.score = 0;
        this.plug = socket;
    }

    public String getName() {
        return this.name;
    }
    public int getGameType() {
        return this.gameType;
    }
    public int getScocore() {
        return this.score;
    }
    public Socket getPlug() {
        return plug;
    }
    public int makeMove( int move ){
        return move;
    }

}
