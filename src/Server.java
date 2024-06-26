import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * CS18000 -- Project 5 -- Phase 2
 * Server class.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version April 13, 2024
 */
public class Server implements ServerInterface {
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.out.println("Error in the server. Please try again.");
        }
    }

    public static void main(String[] args) {
        int port = 8080;
        Server server = new Server(port);
        server.start();
    }
}