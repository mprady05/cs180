import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.net.Socket;
import static org.junit.Assert.*;
/**
 * CS18000 -- Project 5 -- Phase 2
 * Server test from Phase 2.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version April 13, 2024
 */
public class ServerTest {
    private Server server;
    private final int testPort = 1234;
    private Thread serverThread;

    @Before
    public void setUp() {
        server = new Server(testPort);
        serverThread = new Thread(new Runnable() {
            public void run() {
                server.start();
            }
        });
        serverThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testServerAcceptsConnections() {
        try {
            Socket clientSocket = new Socket("localhost", testPort);
            System.out.println("clientsocket");
            assertTrue("Connection should be successful", clientSocket.isConnected());
            clientSocket.close();
        } catch (IOException e) {
            fail("Should have connected to the server but received an exception: " + e.getMessage());
        }
    }

//    @After
//    public void tearDown() {
//        // Stop the server thread if it's still running
//        if (serverThread.isAlive()) {
//            serverThread.interrupt();
//        }
//    }
}