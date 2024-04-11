import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
/**
 * CS18000 -- Project 5 -- Phase 1
 * Testing for {@link Comment}
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version March 31, 2024
 */
public class CommentTest {
    private Comment comment;
    private User author;

    @Before
    public void setUp() {
        author = new User("JohnDoe", "John", "Doe", "password123",
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        comment = new Comment(author, "This is a test comment", 10, 2);
    }

    @Test
    public void testGetCommentIdNotNull() {
        assertNotNull("Comment ID should not be null", comment.getCommentId());
    }

    @Test
    public void testGetAuthor() {
        assertEquals("Author should match the one provided", author, comment.getAuthor());
    }

    @Test
    public void testGetContent() {
        assertEquals("Content should match the one provided", "This is a test comment", comment.getContent());
    }

    @Test
    public void testGetUpvotes() {
        assertEquals("Upvotes should match the one provided", 10, comment.getUpvotes());
    }

    @Test
    public void testGetDownvotes() {
        assertEquals("Downvotes should match the one provided", 2, comment.getDownvotes());
    }

    @Test
    public void testToStringFormat() {
        String expected = comment.getCommentId() + ":!:" +
                author.getUsername() + ":!:" +
                "This is a test comment" + ":!:" +
                10 + ":!:" +
                2;
        assertEquals("toString method should return the formatted string correctly", expected, comment.toString());
    }
}
