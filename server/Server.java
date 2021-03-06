package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server { 
    public static void main(String[] args) {
        final int PORT = 12345;
        ServerSocket serverSocket;
        Scanner input = null; 

        Socket clientSocketA = null;
        Socket clientSocketB = null;
        
        String clientNameA = null;
        String clientNameB = null;

        Validate val = new Validate();
        GameManager gameManager = null;

        // CRIA SOCKET + BIND
        try { 
            serverSocket = new ServerSocket(PORT); 
        } catch (Exception e) {
            System.out.println("S > xxxxxxx Erro" + e.getMessage() + " a " + PORT + " está em uso... xxxxxxx");
            return; 
        }

        // ESPERA CNX
        try {
            while( true ) {
                System.out.println("S > Aguardando pedido de conexão...");

                clientSocketA = serverSocket.accept(); 
                System.out.println("S > Conectado com " + clientSocketA.getInetAddress().getHostAddress());
                
                input = new Scanner(clientSocketA.getInputStream());
                clientNameA = input.nextLine(); 
                System.out.println("S > Jogador 1 = " + clientNameA);

                Boolean isGameTypeValid = false; // verifica se é válido
                int gameType;
                // ** ESCOLHE SE JOGA SOZINHO OU SE JOGA CONTRA O COMPUTADOR **
                do {
                    String msg = input.nextLine();
                    gameType = val.stringToInt(msg);
                    if ( gameType == 2) {
                        isGameTypeValid = true;

                        if (clientSocketB == null) {
                            clientSocketB = clientSocketA;
                            clientNameB = clientNameA;
                            // ** AGUARDAR O SEGUNDO SOCKET AQUI *****
                        } else {
                            // ** CREATE A GAME MANAGER **
                            gameManager = new GameManager( clientSocketA, clientNameA, clientSocketB, clientNameB );
                            gameManager.start(); // TROCA DE DADOS
                            System.out.println("S >>>>>>>>>>>>> foi p jogo de 2");

                            clientSocketB = null;
                            clientNameB = null;
                        }
                    } else if (gameType == 1){
                        isGameTypeValid = true;

                        // ** CREATE A GAME MANAGER **
                        gameManager = new GameManager( clientSocketA, clientNameA );
                        gameManager.start(); // TROCA DE DADOS
                        System.out.println("S >>>>>>>>>>>>> foi p jogo de 1");
                    }
                } while (!isGameTypeValid);
            }
        } catch (Exception e) {
            System.out.println("S > xxxxxxx Erro" + e.getMessage() + "na conexão... xxxxxxx");
        }

        // CLX CNX, nesse caso NUNCA
        try {
            input.close();
            serverSocket.close();
            System.out.println(
                "S > ========== Acabou a conexão do SERVIDOR ==============="
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}