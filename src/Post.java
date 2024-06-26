import java.util.ArrayList;
import java.util.UUID;
/**
 * CS18000 -- Project 5 -- Phase 2
 * Class defining posts in the news feed.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version April 13, 2024
 */
public class Post implements PostInterface {
    private final String postId;
    private final User creator;
    private final String content;
    private int upvotes;
    private int downvotes;
    private ArrayList<String> commentIds;

    /**
     * Constructor for creating a new Post with generated ID.
     * @param creator The User who created the post.
     * @param content The textual content of the post.
     * @param upvotes Initial count of upvotes.
     * @param downvotes Initial count of downvotes.
     * @param commentIds List of IDs of comments attached to the post.
     */
    public Post(User creator, String content, int upvotes, int downvotes, ArrayList<String> commentIds) {
        this.postId = UUID.randomUUID().toString();
        this.creator = creator;
        this.content = content;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.commentIds = commentIds;
    }

    /**
     * Constructor for creating a Post with an existing ID (e.g., when loading from a database).
     * @param postId Unique identifier for the post.
     * @param creator The User who created the post.
     * @param content The textual content of the post.
     * @param upvotes Count of upvotes.
     * @param downvotes Count of downvotes.
     * @param commentIds List of IDs of comments attached to the post.
     */
    public Post(String postId, User creator, String content, int upvotes, int downvotes, ArrayList<String> commentIds) {
        this.postId = postId;
        this.creator = creator;
        this.content = content;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.commentIds = commentIds;
    }

    /**
     * Retrieves the unique identifier of the post.
     * @return A string representing the unique ID of the post.
     */
    public synchronized String getPostId() {
        return postId;
    }

    /**
     * Retrieves the creator of the post.
     * @return A User object representing the creator of the post.
     */
    public synchronized User getCreator() {
        return creator;
    }

    /**
     * Retrieves the textual content of the post.
     * @return A string containing the main content of the post.
     */
    public synchronized String getContent() {
        return content;
    }

    /**
     * Retrieves the current number of upvotes for the post.
     * @return An integer value representing the number of upvotes the post has received.
     */
    public synchronized int getUpvotes() {
        return upvotes;
    }

    /**
     * Retrieves the current number of downvotes for the post.
     * @return An integer value representing the number of downvotes the post has received.
     */
    public synchronized int getDownvotes() {
        return downvotes;
    }

    /**
     * Retrieves the list of comments associated with the post.
     * @return An ArrayList of String objects, each representing a unique ID of a comment made on the post.
     */
    public synchronized ArrayList<String> getComments() {
        return commentIds;
    }

    /**
     * Increments the upvote count for the post and updates the corresponding post in the PostsManager.
     * @throws SMPException If there is an error updating the post.
     */
    public synchronized void addUpvote() throws SMPException {
        upvotes += 1;
        PostsManager.updatePost(this);
    }

    /**
     * Increments the downvote count for the post and updates the corresponding post in the PostsManager.
     * @throws SMPException If there is an error updating the post.
     */
    public synchronized void addDownvote() throws SMPException {
        downvotes += 1;
        PostsManager.updatePost(this);
    }

    /**
     * This method creates the comment in the CommentsManager, then adds the comment's ID to this post.
     * @param author The username of the author of the comment.
     * @param contents The textual content of the comment.
     * @return true if added comment, false otherwise.
     * @throws SMPException If there is an error creating the comment or updating the post.
     */

    public synchronized boolean addComment(String author, String contents) throws SMPException {
        Comment commentId = CommentsManager.addComment(author, contents, 0, 0);
        if (commentId == null) {
            return false;
        }
        commentIds.add(commentId.getCommentId());
        PostsManager.updatePost(this);
        return true;
    }

    /**
     * Removes a comment from the post if the requester is authorized (either the post creator or the comment author).
     * @param commentId The ID of the comment to be removed.
     * @param requesterUsername The username of the requester.
     * @throws SMPException If the comment cannot be deleted.
     * @return true if comment is deleted, false otherwise.
     */

    public synchronized boolean deleteComment(String commentId, String requesterUsername) throws SMPException {
        Comment commentToDelete = CommentsManager.searchComment(commentId);
        if (commentToDelete == null) {
            return false;
        }
        User commentAuthor = commentToDelete.getAuthor();
        boolean isPostCreator = this.creator.getUsername().equals(requesterUsername);
        if (commentAuthor.getUsername().equals(requesterUsername)) {
            if (commentIds.remove(commentId)) {
                CommentsManager.deleteComment(commentId, commentAuthor.getUsername());
                PostsManager.updatePost(this);
                return true;
            } else {
                return false;
            }
        } else if (isPostCreator) {
            if (commentIds.remove(commentId)) {
                CommentsManager.deleteComment(commentId, this.creator.getUsername());
                PostsManager.updatePost(this);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Generates a string representation of the post, including its ID, creator, content, upvotes, downvotes, comments.
     * @return A string representation of the post.
     */
    @Override
    public synchronized String toString() {
        String result = postId + ":~:" +
                creator.getUsername() + ":~:" +
                content + ":~:" +
                upvotes + ":~:" +
                downvotes + ":~:" +
                "[";
        for (int i = 0; i < commentIds.size(); i++) {
            result += commentIds.get(i);
            if (i < commentIds.size() - 1) {
                result += ":~:";
            }
        }
        result += "]";
        return result;
    }
}
