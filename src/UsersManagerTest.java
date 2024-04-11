import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.*;
import java.util.*;
/**
 * CS18000 -- Project 5 -- Phase 1
 * Testing for {@link UsersManager}.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version March 31, 2024
 */
public class UsersManagerTest {

    private static final String TEST_USER_FILE = "UsersDatabase.txt";
    private UsersManager usersManager;

    @BeforeClass
    public static void setupBeforeClass() throws IOException {
        System.setProperty("user.file", TEST_USER_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_USER_FILE))) {
            writer.write("John;Doe;johndoe;password123;(alice,bob);(mike);()\n");
            writer.write("Alice;Smith;alice;12345;(johndoe);();()\n");
        }
    }

    @Before
    public void setUp() throws SMPException {
        UsersManager.clearAllUsers();
        usersManager = new UsersManager();
    }

    @Test
    public void testReadUsersDatabaseFile() {
        assertEquals("Users list should contain the correct number of users", 2, UsersManager.getUsers().size());
    }

    @Test
    public void testWriteUsersDatabaseFile() throws IOException, SMPException {
        UsersManager.registerUser("New", "User", "newuser", "password123");
        UsersManager.writeUsersDatabaseFile();
        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_USER_FILE))) {
            String lastLine = "";
            String line;
            while ((line = reader.readLine()) != null) {
                lastLine = line;
            }
            assertTrue("The new user should be written to the file", lastLine.contains("newuser"));
        }
    }

    @Test
    public void testRegisterUser() throws SMPException {
        UsersManager.registerUser("Chris", "Smith", "chriss", "chris123");
        assertTrue("User should be successfully registered",
                true);
        User user = UsersManager.registerUser("Chris", "Smith", "chriss", "chris123");
        assertNull("Incorrectly registered user.", user);
    }

    @Test
    public void testLoginUser() throws SMPException {
        assertNotNull("User should be able to login", UsersManager.loginUser("alice", "12345"));
        assertNull("User login should fail with wrong password",
                UsersManager.loginUser("alice", "wrongpassword"));
    }

    @Test
    public void testUpdateUser() throws SMPException {
        User user = UsersManager.registerUser("Bob", "Brown", "bobby", "password");
        ArrayList<String> updatedFriendsList = new ArrayList<>();
        updatedFriendsList.add("alice");
        assert user != null;
        User updatedUser = new User("Bob", "Brown", "bobby", "password", updatedFriendsList, user.getBlockList(), user.getPostIds());
        assertTrue("User should be updated successfully", UsersManager.updateUser(updatedUser));
        User fetchedUser = UsersManager.searchUser("bobby");
        assertNotNull("Updated user should exist", fetchedUser);
        assertTrue("Updated user should now have alice in friend list",
                fetchedUser.getFriendList().contains("alice"));
    }

    @Test
    public void testSearchUser() throws SMPException {
        UsersManager.registerUser("Charlie", "Green", "charlie", "pass");
        assertNotNull("User should be found", UsersManager.searchUser("charlie"));
        assertNull("Non-existent user should not be found", UsersManager.searchUser("nonexistent"));
    }

    @Test
    public void testClearAllUsers() throws SMPException {
        UsersManager.registerUser("Diana", "White", "diana", "1234");
        UsersManager.clearAllUsers();
        assertEquals("Users list should be empty", 0, UsersManager.getUsers().size());
    }

}
