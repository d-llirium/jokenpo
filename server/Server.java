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

        // CRIA SOCKET + BIND
        try { 
            serverSocket = new ServerSocket(PORT); 
        } catch (Exception e) {
            System.out.println(
                "S > porta " + PORT + " já está em uso."
            );
            return; 
        }

        // ESPERA CNX
        try {
            while( true ) {
                System.out.println(
                    "S > Aguardando pedido de conexão..."
                );
                clientSocketA = serverSocket.accept(); 
                System.out.println(
                    "S > Conectado com " + clientSocketA.getInetAddress().getHostAddress()
                );
                
                input = new Scanner(clientSocketA.getInputStream());
                clientNameA = input.nextLine(); 
                System.out.println(
                    "S > Jogador 1 = " + clientNameA
                );
                
                // ** ESCOLHE SE JOGA SOZINHO OU SE JOGA CONTRA O COMPUTADOR **
                int gameType = input.nextInt();
                if (gameType == 1) {
                    if (clientSocketB == null) {
                        clientSocketB = clientSocketA;
                        clientNameB = clientNameA;
                        
                        // ** AGUARDAR O SEGUNDO SOCKET AQUI *****
                        System.out.println(
                            "S > Aguardando um próximo jogador ... "
                        );
                    } else {
                        GameManager gameManager = new GameManager( clientSocketA, clientNameA, clientSocketB, clientNameB );
                        gameManager.start(); // TROCA DE DADOS
                        System.out.println(
                            "S >>>>>>>>>>>>> foi p jogo de 2"
                        );
                    }
                } else {
                    GameManager gameManager = new GameManager( clientSocketA, clientNameA );
                    gameManager.start(); // TROCA DE DADOS
                    System.out.println(
                        "S >>>>>>>>>>>>> foi p jogo de 1"
                    );
                }
            }
        } catch (Exception e) {
            System.out.println(
                "S > xxxxxxx Erro na conexão... xxxxxxx"
            );
            System.out.println(e.getMessage());
        }

        // CLX CNX
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