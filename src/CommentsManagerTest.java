import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CommentsManagerTest {
    private CommentsManager commentsManager;
    private static final String TEST_COMMENTS_FILE = "CommentsDatabase.txt";
    private static String originalPostsContent = "";


    @Before
    public void setUp() throws SMPException, IOException {
        UsersManager.clearAllUsers();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_COMMENTS_FILE))) {
            writer.write(originalPostsContent);
        }
        UsersManager.registerUser("John", "Doe", "johndoe", "password", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        commentsManager = new CommentsManager();
    }

    @After
    public void tearDown() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_COMMENTS_FILE))) {
            writer.write(originalPostsContent);
        }
    }

    @Test
    public void testWriteAndReadCommentsDatabase() throws SMPException {
        User testUser = new User("Jane", "Doe", "janedoe", "pass", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        UsersManager.registerUser("Jane", "Doe", "janedoe", "pass", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        String testContent = "This is a test comment.";
        int testUpvotes = 5;
        int testDownvotes = 2;
        String commentId = CommentsManager.addComment(testUser.getUsername(), testContent, testUpvotes, testDownvotes);
        assertNotNull("Comment ID should not be null", commentId);
        commentsManager.writeCommentsDatabaseFile();
        commentsManager.clearAllComments();
        assertTrue("Comments list should be empty before reading from file", commentsManager.getComments().isEmpty());
        commentsManager.readCommentsDatabaseFile();
        assertFalse("Comments list should not be empty after reading from file", commentsManager.getComments().isEmpty());
        Comment readComment = commentsManager.searchComment(commentId);
        assertNotNull("Read comment should not be null", readComment);
        assertEquals("Content of the read comment should match", testContent, readComment.getContent());
        assertEquals("Upvotes of the read comment should match", testUpvotes, readComment.getUpvotes());
        assertEquals("Downvotes of the read comment should match", testDownvotes, readComment.getDownvotes());
    }

    @Test
    public void testAddComment() throws SMPException {
        String commentId = CommentsManager.addComment("johndoe", "This is a test comment", 0, 0);
        assertNotNull("Comment ID should not be null after adding a comment", commentId);
        assertEquals("There should be one comment after adding", 1, CommentsManager.getComments().size());
        Comment addedComment = CommentsManager.getComments().stream()
                .filter(comment -> comment.getCommentId().equals(commentId))
                .findFirst()
                .orElse(null);
        assertNotNull("Added comment should be retrievable", addedComment);
        assertEquals("Comment content should match", "This is a test comment", addedComment.getContent());
    }


    @Test
    public void testUpdateComment() throws SMPException {
        CommentsManager.addComment("johndoe", "Initial comment content", 0, 0);
        Comment originalComment = CommentsManager.getComments().get(0);
        Comment updatedComment = new Comment(originalComment.getCommentId(), originalComment.getAuthor(), "Updated comment content", originalComment.getUpvotes(), originalComment.getDownvotes());
        boolean updated = commentsManager.updateComment(updatedComment);
        assertTrue("Comment should be updated successfully", updated);
        Comment retrievedComment = CommentsManager.getComments().get(0);
        assertEquals("Updated comment content should match", "Updated comment content", retrievedComment.getContent());
    }

    @Test
    public void testDeleteComment_Success() throws SMPException {
        String commentId = CommentsManager.addComment("johndoe", "Comment to delete", 2, 3);
        assertTrue("The comment should be deleted successfully.", CommentsManager.deleteComment(commentId, "johndoe"));
        assertNull("Deleted comment should not be found.", CommentsManager.searchComment(commentId));
    }

    @Test
    public void testDeleteComment_Nonexistent() throws SMPException {
        assertFalse("Attempting to delete a non-existent comment should return false.", CommentsManager.deleteComment("nonExistingId", "johndoe"));
    }

    @Test
    public void testSearchComment_Existing() throws SMPException {
        String commentId = CommentsManager.addComment("johndoe", "Searching this comment", 3, 0);
        Comment foundComment = CommentsManager.searchComment(commentId);
        assertNotNull("The comment should exist.", foundComment);
        assertEquals("Searching this comment", foundComment.getContent());
    }

    @Test
    public void testSearchComment_Nonexistent() {
        Comment result = CommentsManager.searchComment("nonExistingId");
        assertNull("Search for a non-existent comment should return null.", result);
    }

    @Test
    public void testClearAllComments() throws SMPException {
        CommentsManager.addComment("johndoe", "Comment 1", 1, 1);
        CommentsManager.addComment("johndoe", "Comment 2", 2, 2);
        CommentsManager.clearAllComments();
        assertTrue("All comments should be cleared.", CommentsManager.getComments().isEmpty());
    }

    @Test
    public void testPersistence() throws SMPException {
        CommentsManager.addComment("johndoe", "Persistent comment", 5, 1);
        commentsManager.writeCommentsDatabaseFile();
        CommentsManager newManager = new CommentsManager();
        assertEquals("New manager should have the persisted comment", 1, newManager.getComments().size());
    }
}
