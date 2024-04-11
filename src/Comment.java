import java.util.UUID;
/**
 * CS18000 -- Project 5 -- Phase 1
 * Class representing user comments.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version March 31, 2024
 */
public class Comment implements CommentInterface {
    private String commentId;
    private User author;
    private String content;
    private int upvotes;
    private int downvotes;

    /**
     * Constructs a new Comment with a new UUID, author, content, upvotes, and downvotes.
     * @param author The author of the comment.
     * @param content The textual content of the comment.
     * @param upvotes The initial number of upvotes for the comment.
     * @param downvotes The initial number of downvotes for the comment.
     */
    public Comment(User author, String content, int upvotes, int downvotes) {
        this.commentId = UUID.randomUUID().toString();
        this.author = author;
        this.content = content;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
    }

    /**
     * Constructs a Comment with a specified ID (used for loading comments from storage, for example).
     * @param commentId The unique identifier for the comment.
     * @param author The author of the comment.
     * @param content The content of the comment.
     * @param upvotes Number of upvotes the comment has received.
     * @param downvotes Number of downvotes the comment has received.
     */
    public Comment(String commentId, User author, String content, int upvotes, int downvotes) {
        this.commentId = commentId;
        this.author = author;
        this.content = content;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
    }

    /**
     * Gets the unique ID of the comment.
     * @return The unique ID of the comment.
     */
    public String getCommentId() {
        return commentId;
    }

    /**
     * Gets the author of the comment.
     * @return The User object representing the author of the comment.
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Gets the content of the comment.
     * @return The textual content of the comment.
     */
    public String getContent() {
        return content;
    }

    /**
     * Gets the number of upvotes the comment has received.
     * @return The number of upvotes.
     */
    public int getUpvotes() {
        return upvotes;
    }

    /**
     * Gets the number of downvotes the comment has received.
     * @return The number of downvotes.
     */
    public int getDownvotes() {
        return downvotes;
    }

    /**
     * Increments the upvote count for the comment and updates the corresponding post in the CommentsManager.
     * @throws SMPException If there is an error updating the comment.
     */
    public void addUpvote() throws SMPException {
        upvotes += 1;
        CommentsManager.updateComment(this);
    }

    /**
     * Increments the downvote count for the comment and updates the corresponding post in the CommentsManager.
     * @throws SMPException If there is an error updating the comment.
     */
    public void addDownvote() throws SMPException {
        downvotes += 1;
        CommentsManager.updateComment(this);
    }

    /**
     * Returns a string representation of the comment.
     * @return A string containing the comment's ID, author's username, content, upvotes, and downvotes.
     */
    @Override
    public String toString() {
        return commentId + ":!:" +
                author.getUsername() + ":!:" +
                content + ":!:" +
                upvotes + ":!:" +
                downvotes;
    }
}
