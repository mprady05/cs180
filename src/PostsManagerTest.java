import org.junit.*;

import static org.junit.Assert.*;

import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.io.*;
/**
 * CS18000 -- Project 5 -- Phase 1
 * Testing for {@link PostsManager}.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version March 31, 2024
 */
public class PostsManagerTest {
    private PostsManager postsManager;
    private User testUser;
    private static final String TEST_POSTS_FILE = "PostsDatabase.txt";
    private static final String BACKUP_POSTS_FILE = "PostsDatabaseBackup.txt";
    private static String originalPostsContent = "";

    @BeforeClass
    public static void backupOriginalFile() throws IOException {
        File originalFile = new File(TEST_POSTS_FILE);
        File backupFile = new File(BACKUP_POSTS_FILE);
        if (originalFile.exists()) {
            Files.copy(originalFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @AfterClass
    public static void restoreOriginalFile() throws IOException {
        File originalFile = new File(TEST_POSTS_FILE);
        File backupFile = new File(BACKUP_POSTS_FILE);
        if (backupFile.exists()) {
            if (originalFile.exists()) {
                originalFile.delete();
            }
            Files.move(backupFile.toPath(), originalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @Before
    public void setUp() throws SMPException, IOException {
        UsersManager.clearAllUsers();
        PostsManager.clearAllPosts();
        CommentsManager.clearAllComments();
        UsersManager.clearAllUsers();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_POSTS_FILE))) {
            writer.write(originalPostsContent);
        }
        testUser = new User("Test", "User", "testuser", "password",
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        UsersManager.registerUser(testUser.getFirstName(), testUser.getLastName(),
                testUser.getUsername(), testUser.getPassword());
        UsersManager.registerUser("John", "Doe", "johndoe", "password123");
        postsManager = new PostsManager();
    }

    @Test
    public void testAddPost() throws SMPException {
        String postId = PostsManager.addPost(testUser.getUsername(), "Test Post Content", 0, 0, new ArrayList<>());
        assertNotNull("Post ID should not be null after adding a post", postId);
    }

    @Test
    public void testSearchPost() throws SMPException {
        String postId = PostsManager.addPost(testUser.getUsername(), "Test Post Content", 0, 0, new ArrayList<>());
        Post post = PostsManager.searchPost(postId);
        assertNotNull("Post should be found by ID after being added", post);
    }

    @Test
    public void testUpdatePost() throws SMPException {
        String postId = PostsManager.addPost(testUser.getUsername(), "Initial Content", 1, 1, new ArrayList<>());
        assertNotNull("Post ID should not be null", postId);
        Post existingPost = PostsManager.searchPost(postId);
        assertNotNull("Post should exist", existingPost);
        Post updatedPost = new Post(existingPost.getPostId(),
                existingPost.getCreator(), "Updated Content", 2, 2,
                existingPost.getComments());
        boolean updateResult = PostsManager.updatePost(updatedPost);
        assertTrue("Post should be successfully updated", updateResult);
        Post postAfterUpdate = PostsManager.searchPost(postId);
        assertNotNull("Updated post should exist", postAfterUpdate);
        assertEquals("Content should be updated", "Updated Content", postAfterUpdate.getContent());
        assertEquals("Upvotes should be updated", 2, postAfterUpdate.getUpvotes());
        assertEquals("Downvotes should be updated", 2, postAfterUpdate.getDownvotes());
    }

    @Test
    public void testClearAllPosts() throws SMPException {
        PostsManager.addPost(testUser.getUsername(), "Test Post for Clear", 1, 1, new ArrayList<>());
        PostsManager.clearAllPosts();
        assertTrue("All posts should be cleared", PostsManager.getPosts().isEmpty());
    }

    @Test
    public void testWriteAndReadPostsDatabase() throws SMPException {
        PostsManager.addPost("johndoe", "Persistent Post Content", 5, 3, new ArrayList<>());
        PostsManager.writePostsDatabaseFile(); // This writes the test data to the file
        PostsManager.clearAllPosts(); // Clear the in-memory list of posts
        postsManager.readPostsDatabaseFile(); // Re-read the file into memory
        assertEquals("There should be exactly one post after reading from database",
                1 + originalPostsContent.lines().count(),
                PostsManager.getPosts().size()); // Adjust for original content size
    }

    @Test
    public void testGetPostIdFromComment() throws SMPException {
        String postId = PostsManager.addPost("johndoe", "Example post content", 5, 2, new ArrayList<>());
        assertNotNull("Post should have been added successfully", postId);
        Comment commentId = CommentsManager.addComment("johndoe", "Example comment", 0, 0);
        assertNotNull("Comment should have been added successfully", commentId);
        Post post = PostsManager.searchPost(postId);
        assertNotNull("Post should exist", post);
        post.getComments().add(commentId.getCommentId());
        Comment comment = new Comment(commentId.getCommentId(), UsersManager.searchUser("johndoe"),
                "Example comment", 0, 0);
        Post foundPost = PostsManager.getPostIdFromComment(comment);
        assertEquals("The post ID retrieved should match the one associated with the comment", postId, foundPost.getPostId());
    }

}
