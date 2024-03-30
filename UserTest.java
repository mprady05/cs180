import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class UserTest {
    private User user;
    private ArrayList<User> friends;
    private ArrayList<User> blocked;

    @Before
    public void setUp() {
        friends = new ArrayList<>();
        blocked = new ArrayList<>();
        user = new User("John", "Doe", "password123", "johndoe", "photo123", friends, blocked);
    }

    @Test
    public void testGettersAndSetters() {
        // Test all getters
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("password123", user.getPassword());
        assertEquals("johndoe", user.getUsername());
        assertEquals("photo123", user.getPhotoId());
        assertTrue(user.getFriendList().isEmpty());
        assertTrue(user.getBlockList().isEmpty());

        // Test all setters
        user.setFirstName("Jane");
        assertEquals("Jane", user.getFirstName());

        user.setLastName("Smith");
        assertEquals("Smith", user.getLastName());

        user.setPassword("newpassword");
        assertEquals("newpassword", user.getPassword());

        user.setUsername("janesmith");
        assertEquals("janesmith", user.getUsername());

        user.setPhotoId("photo456");
        assertEquals("photo456", user.getPhotoId());

        // Create test users to add to friends and blocked lists
        User friend = new User("Friend", "User", "friendpass", "frienduser", "friendphoto", new ArrayList<>(), new ArrayList<>());
        User blockedUser = new User("Blocked", "User", "blockedpass", "blockeduser", "blockedphoto", new ArrayList<>(), new ArrayList<>());
        ArrayList<User> newFriends = new ArrayList<>();
        newFriends.add(friend);
        user.setFriendList(newFriends);
        assertEquals(1, user.getFriendList().size());
        assertEquals(friend, user.getFriendList().get(0));

        ArrayList<User> newBlocked = new ArrayList<>();
        newBlocked.add(blockedUser);
        user.setBlockList(newBlocked);
        assertEquals(1, user.getBlockList().size());
        assertEquals(blockedUser, user.getBlockList().get(0));
    }

    @Test
    public void testToString() {
        String expectedString = "John,Doe,johndoe,password123";
        assertEquals(expectedString, user.toString());
    }
}
