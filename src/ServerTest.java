import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.*;

public class ServerTest {
    private Server server;
    private final int testPort = 8080;
    private Thread serverThread;

    @Before
    public void setUp() {
        server = new Server(testPort);
        // Start the server in a new thread to avoid blocking the test execution
        serverThread = new Thread(new Runnable() {
            public void run() {
                server.start();
            }
        });
        serverThread.start();
        // Allow some time for the server to start
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
            assertTrue("Connection should be successful", clientSocket.isConnected());
            clientSocket.close();
        } catch (IOException e) {
            fail("Should have connected to the server but received an exception: " + e.getMessage());
        }
    }

    @After
    public void tearDown() {
        // Stop the server thread if it's still running
        if (serverThread.isAlive()) {
            serverThread.interrupt();
        }
    }
}
