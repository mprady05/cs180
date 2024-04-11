import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
/**
 * CS18000 -- Project 5 -- Phase 1
 * Testing for {@link User}.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version March 31, 2024
 */
public class UserTest {
    private User userJohn;
    private User userAlice;

    @Before
    public void setUp() throws SMPException {
        UsersManager.clearAllUsers();
        PostsManager.clearAllPosts();
        UsersManager.registerUser("John", "Doe", "johndoe", "password123");
        UsersManager.registerUser("Alice", "Smith", "alice", "password1");
        userJohn = UsersManager.searchUser("johndoe");
        userAlice = UsersManager.searchUser("alice");
        userJohn.addFriend("alice");
        userAlice.addFriend("johndoe");
    }

    @Test
    public void testUserGetters() throws SMPException {
        String expectedFirstName = "John";
        String expectedLastName = "Doe";
        String expectedUsername = "johndoe";
        String expectedPassword = "password123";
        List<String> expectedFriends = Arrays.asList("alice");
        List<String> expectedBlocked = new ArrayList<>();
        userJohn.addPost("This is a test post");
        assertEquals("Unexpected first name", expectedFirstName, userJohn.getFirstName());
        assertEquals("Unexpected last name", expectedLastName, userJohn.getLastName());
        assertEquals("Unexpected username", expectedUsername, userJohn.getUsername());
        assertEquals("Unexpected password", expectedPassword, userJohn.getPassword());
        assertEquals("Unexpected friends list", expectedFriends, userJohn.getFriendList());
        assertEquals("Unexpected block list", expectedBlocked, userJohn.getBlockList());
        assertTrue("Post IDs list should not be empty after adding a post", !userJohn.getPostIds().isEmpty());
    }

    @Test
    public void testAddFriend() throws SMPException {
        User newuser = UsersManager.registerUser("new", "user", "newuser", "temp123");
        userJohn.addFriend("newuser");
        userAlice.addFriend("johndoe");
        assertTrue("John's friend list should include newuser", userJohn.getFriendList().contains("newuser"));
    }

    @Test
    public void testBlockUser() throws SMPException {
        userJohn.blockUser("alice");
        assertFalse("Alice should no longer be in John's friend list", userJohn.getFriendList().contains("alice"));
        assertTrue("Alice should be in John's block list", userJohn.getBlockList().contains("alice"));
    }

    @Test
    public void testRemoveFriend() throws SMPException {
        userJohn.removeFriend("bob");
        assertFalse("Bob should no longer be in John's friend list", userJohn.getFriendList().contains("bob"));
    }

    @Test
    public void testAddPost() throws SMPException {
        String content = "This is a new post by John";
        userJohn.addPost(content);
        assertFalse("John's post list should not be empty after adding a post", userJohn.getPostIds().isEmpty());
        String postId = userJohn.getPostIds().get(userJohn.getPostIds().size() - 1);
        Post post = PostsManager.searchPost(postId);
        assertNotNull("The post should exist in PostsManager after being added", post);
        assertEquals("The content of the post should match the added content", content, post.getContent());
        User updatedUser = UsersManager.searchUser(userJohn.getUsername());
        assert updatedUser != null;
        assertTrue("The updated user should contain the new post ID", updatedUser.getPostIds().contains(postId));
    }

    @Test
    public void testHidePost() throws SMPException {
        userJohn.addPost("Hello, my name is John!");
        assertFalse("List should not be empty.", userJohn.getPostIds().isEmpty());
        String postId = userJohn.getPostIds().get(0);
        userJohn.hidePost(postId);
        assertFalse("Post should no longer be in John's posts list", userJohn.getPostIds().contains(postId));
    }

    @Test
    public void testGetFriendsPosts() {
        List<String> friendsPosts = userJohn.getFriendsPosts();
        assertNotNull("Friends posts list should not be null", friendsPosts);
    }

    @Test
    public void testToString() {
        String expected = "John;Doe;johndoe;password123;(";
        for (int i = 0; i < userJohn.getFriendList().size(); i++) {
            expected += userJohn.getFriendList().get(i);
            if (i < userJohn.getFriendList().size() - 1) {
                expected += ",";
            }
        }
        expected += ");(";
        for (int i = 0; i < userJohn.getBlockList().size(); i++) {
            expected += userJohn.getBlockList().get(i);
            if (i < userJohn.getBlockList().size() - 1) {
                expected += ",";
            }
        }
        expected += ");(";
        for (int i = 0; i < userJohn.getPostIds().size(); i++) {
            expected += userJohn.getPostIds().get(i);
            if (i < userJohn.getPostIds().size() - 1) {
                expected += ",";
            }
        }
        expected += ")";
        String actual = userJohn.toString();
        assertEquals("The toString method does not format the user information as expected.", expected, actual);
    }

}
