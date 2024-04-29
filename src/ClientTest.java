import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
/**
 * CS18000 -- Project 5 -- Phase 2
 * Test class for Client Phase 2
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version April 13, 2024
 */
public class ClientTest {
    private static final String ADD_FRIEND_SUCCESS = "Successfully added friend!";
    private static final String ADD_FRIEND_FAIL = "Failed to add friend. Please try again.";
    private static final String REMOVE_FRIEND_SUCCESS = "Successfully removed friend!";
    private static final String REMOVE_FRIEND_FAIL = "Failed to remove friend. Please try again.";
    private Client client;
    private ByteArrayInputStream inputRegistration;
    private ByteArrayInputStream inputLogin;
    private ByteArrayInputStream name;
    private ByteArrayOutputStream output;
    Server server;
    Thread serverThread;


    @Before
    public void setUp() throws IOException {
        server = new Server(1234);
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

        output = new ByteArrayOutputStream();

        String registrationData = "John\nDoe\njohndoe\npassword123\n";
        String loginData = "johndoe\npassword123\n";
        String nameData = "johndoe\n";
        inputRegistration = new ByteArrayInputStream(registrationData.getBytes());
        inputLogin = new ByteArrayInputStream(loginData.getBytes());
        name = new ByteArrayInputStream(loginData.getBytes());


        System.setIn(inputRegistration);
        System.setIn(inputLogin);
        System.setIn(name);

        System.setOut(new PrintStream(output));
        client = new Client("localhost", 1234);
//        client.start();
    }

    @Test
    public void testHandleLoginValidCredentials() throws IOException, ClassNotFoundException, SMPException {

        User loggedInUser = client.handleLogin();
        assertNotNull("Logging in with valid credentials should return a User object", loggedInUser);
    }

    @Test
    public void testHandleLoginInvalidCredentials() throws IOException, ClassNotFoundException, SMPException {
        User loggedInUser = client.handleLogin();
        assertNull("Logging in with invalid credentials should return null", loggedInUser);
    }

    @Test
    public void testHandleRegistrationSuccess() throws IOException, ClassNotFoundException, SMPException {

        User expectedUser = new User("John", "Doe", "johndoe", "password123",
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        try (ObjectOutputStream oos = new ObjectOutputStream(output);
             ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream("2".getBytes()))) {
            new ObjectInputStream(inputRegistration);
            ois.readObject();
            User registeredUser = client.handleRegistration();
            assertNotNull("Registration should return a User object", registeredUser);
            assertEquals("Registered user should match the expected user", expectedUser, registeredUser);
        }
    }

    @Test
    public void testHandleRegistrationFail() throws IOException, ClassNotFoundException, SMPException {
        try (ObjectOutputStream oos = new ObjectOutputStream(output);
             ObjectInputStream ois = new ObjectInputStream(inputRegistration)) {
            ois.readObject();
            User registeredUser = client.handleRegistration();
            assertNull("Failed registration should return null", registeredUser);
        }
    }
    @Test
    public void testHandleAddFriendSuccess() throws IOException, ClassNotFoundException, SMPException {

        try (ObjectOutputStream oos = new ObjectOutputStream(output);
             ObjectInputStream ois = new ObjectInputStream(name)) {
            ois.readObject();
            client.handleAddFriend();
            assertTrue("Add friend success message should be displayed",
                    output.toString().contains(ADD_FRIEND_SUCCESS));
        }
    }

    @Test
    public void testHandleAddFriendFail() throws IOException, ClassNotFoundException, SMPException {

        try (ObjectOutputStream oos = new ObjectOutputStream(output);
             ObjectInputStream ois = new ObjectInputStream(name)) {
            ois.readObject();
            client.handleAddFriend();
            assertTrue("Add friend fail message should be displayed", output.toString().contains(ADD_FRIEND_FAIL));
        }
    }
    @Test
    public void testHandleRemoveFriendSuccess() throws IOException, ClassNotFoundException, SMPException {
        try (ObjectOutputStream oos = new ObjectOutputStream(output);
             ObjectInputStream ois = new ObjectInputStream(name)) {
            String check = (String) ois.readObject();
            System.out.println(check);
            client.handleRemoveFriend();
            assertTrue("Remove friend success message should be displayed",
                    output.toString().contains(REMOVE_FRIEND_SUCCESS));
        }
    }

    @Test
    public void testHandleRemoveFriendFail() throws IOException, ClassNotFoundException, SMPException {
        try (ObjectOutputStream oos = new ObjectOutputStream(output);
             ObjectInputStream ois = new ObjectInputStream(name)) {
            ois.readObject();
            client.handleRemoveFriend();
            assertTrue("Remove friend fail message should be displayed",
                    output.toString().contains(REMOVE_FRIEND_FAIL));
        }
    }
}