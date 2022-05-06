package server;

// import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server { 
    public static void main(String[] args) {
        final int PORT = 12345;
        ServerSocket serverSocket;
        Socket clientSocketA = null;
        Socket clientSocketB = null;
        Scanner input = null; // para receber o nome do jogador e se ele quer jogar em com o computador
        
        String clientNameA = null;
        String clientNameB = null;

        // criar o socket e fazer bind 
        try { // tratar excessão
            serverSocket = new ServerSocket(PORT); 
        } catch (Exception e) {
            System.out.println("S > porta " + PORT + " já está em uso.");
            return; // para o código
        }

        // aguardar pedido de conexão (listen)
        try {
            while( true ) {
                System.out.println("S > Aguardando pedido de conexão...");
                clientSocketA = serverSocket.accept(); // para e fica esperando a conexão... RETORNA o client socket qnd volta
                System.out.println("S > Conectado com " + clientSocketA.getInetAddress().getHostAddress());
                
                input = new Scanner(clientSocketA.getInputStream()); // ler a mensagem do cliente A

                clientNameA = input.nextLine(); // recebe a mensagem do cliente
                System.out.println("S > Jogador 1 = " + clientNameA);
                
                // ** ESCOLHE SE JOGA SOZINHO OU SE JOGA CONTRA O COMPUTADOR **
                int gameType = input.nextInt();
                if (gameType == 1) {
                    if (clientSocketB == null) {
                        clientSocketA = clientSocketB;
                        clientNameA = clientNameB;
                        // ** AGUARDAR O SEGUNDO SOCKET AQUI *****
                        System.out.println("S > Aguardando um próximo jogador ... ");
                    } else {
                        ManageGame manageGame = new ManageGame( clientSocketA, clientNameA, clientSocketB, clientNameB );
                        manageGame.start(); // COMUNICAÇÃO FEITA AQUI troca de dados

                        clientSocketB = null;
                        clientNameB = null;
                    }
                } else {
                    ManageGame manageGame = new ManageGame( clientSocketA, clientNameA );
                    manageGame.start(); // COMUNICAÇÃO FEITA AQUI troca de dados
                }
            }

        } catch (Exception e) {
            System.out.println("xxxxxxx Erro na conexão... xxxxxxx");
            System.out.println(e.getMessage());
        }

        // fase de encerramento da conexão === o servidor fica conectado
        try {
            serverSocket.close();
            System.out.println("========== Acabou a conexão do SERVIDOR ===============");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}