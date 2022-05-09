package server;

import java.util.Scanner;

import client.Player;

public class Validate {
    public static String number;
    public static int num;
    public static String possibleMoves(){
        return "Digite o número de sua Escolha:"
            + "\n1 > pedra "
            + "\n2 > papel "
            + "\n3 > tesoura ";
    }
    public static Boolean strToInt(int from, int to) {
        try {
            num = Integer.parseInt(number);
            System.out.println(num); 
            if ( num > from && num < to ) {
                return true;
            } else {
                System.out.println("xxxxxxx escolha inálida, tente novamente.");
                return false;        
            }
        } catch (Exception e) {
            System.out.println("GM > xxxxxxx Erro na validação ... xxxxxxx"
                                + "\n " + e.getMessage()
            );
        }
        return null;
    }
    public static int match( Player player ) {
        int i = 0;
        do {
            System.out.println("ON VALIDA por " + i );
            player.getGameToPlayer().println(possibleMoves());;
            
            number = player.getGameFromPlayer().nextLine();
            if (strToInt(0, 4)) {
                return num;
            } else {
                player.getGameToPlayer().println("xxxxxxx escolha inálida, tente novamente.");
                i += 1;
            }            
            if ( i == 5 ) {
                player.getGameToPlayer().println("!!! GAME OVER > DESQUALIFICADO");
            }
        } while ( i < 5 );
        return 0;
    }
    public static void setReceivedString( Scanner str) {
        number = str.nextLine();
    }
}
