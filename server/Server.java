package server;

// import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
// import java.util.Scanner;

public class Server { 
    public static void main(String[] args) {
        final int PORT = 12345;
        ServerSocket serverSocket;
        Socket clientSocket = null;
        // Scanner input = null;
        // PrintStream output = null;

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
                clientSocket = serverSocket.accept(); // para e fica esperando a conexão... RETORNA o client socket qnd volta
                System.out.println("S > Conectado com " + clientSocket.getInetAddress().getHostAddress());
                GerenciaJogo gerenciaJogo = new GerenciaJogo( clientSocket );
                gerenciaJogo.start(); // cCOMUNICAÇÃO FEITA AQUI troca de dados
            }

            // ** ESCOLHE SE JOGA SOZINHO OU SE JOGA CONTRA O COMPUTADOR **

            // ** AGUARDAR O SEGUNDO SOCKET AQUI *****

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