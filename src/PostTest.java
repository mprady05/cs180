import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class PostTest {
    private Post post;
    private User creator;
    private String content;
    private int upvotes;
    private int downvotes;
    private ArrayList<String> commentIds;

    @BeforeClass
    public static void setUpBeforeClass() throws SMPException {
        UsersManager.clearAllUsers();
        UsersManager.registerUser("Jane", "Doe", "janedoe", "password", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    @Before
    public void setUp() {
        creator = UsersManager.searchUser("janedoe");
        content = "This is a test post.";
        upvotes = 10;
        downvotes = 2;
        commentIds = new ArrayList<>();
        post = new Post(creator, content, upvotes, downvotes, commentIds);
    }

    @Test
    public void testPostCreationAndGetters() {
        assertEquals("Content should match", content, post.getContent());
        assertEquals("Upvotes should match", upvotes, post.getUpvotes());
        assertEquals("Downvotes should match", downvotes, post.getDownvotes());
        assertEquals("Comments list should match", commentIds, post.getComments());
        assertNotNull("Post ID should not be null", post.getPostId());
        assertEquals("Creator should match", creator, post.getCreator());
    }

    @Test
    public void testAddUpvote() throws SMPException {
        int initialUpvotes = post.getUpvotes();
        post.addUpvote();
        assertEquals("Upvotes should be incremented", initialUpvotes + 1, post.getUpvotes());
    }

    @Test
    public void testAddDownvote() throws SMPException {
        int initialDownvotes = post.getDownvotes();
        post.addDownvote();
        assertEquals("Downvotes should be incremented", initialDownvotes + 1, post.getDownvotes());
    }

    @Test
    public void testAddComment() throws SMPException {
        post.addComment(creator.getUsername(), "Hi! My name is Jane.");
        String latestCommentId = post.getComments().get(post.getComments().size() - 1);
        Comment addedComment = CommentsManager.searchComment(latestCommentId);
        assertNotNull("The comment should exist in CommentsManager after being added", addedComment);
        assertEquals("Comment content should match", "Hi! My name is Jane.", addedComment.getContent());
        assertEquals("Comment author should match", creator.getUsername(), addedComment.getAuthor().getUsername());
        assertTrue("Post should contain the new comment ID", post.getComments().contains(latestCommentId));
    }

    @Test(expected = SMPException.class)
    public void testDeleteCommentNotAuthorized() throws SMPException {
        String commentId = "12345";
        post.deleteComment(commentId, "notCreatorOrAuthor");
    }

    @Test
    public void testToString() {
        String expectedString = post.getPostId() + ":~:" +
                creator.getUsername() + ":~:" +
                content + ":~:" +
                upvotes + ":~:" +
                downvotes + ":~:" +
                "[]";
        assertEquals("toString method should return the correct string", expectedString, post.toString());
    }
}
