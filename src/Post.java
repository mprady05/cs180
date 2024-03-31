import java.util.ArrayList;
import java.util.UUID;

public class Post implements PostInterface{
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
        this.postId = UUID.randomUUID().toString();;
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
    public String getPostId() {
        return postId;
    }

    /**
     * Retrieves the creator of the post.
     * @return A User object representing the creator of the post.
     */
    public User getCreator() {
        return creator;
    }

    /**
     * Retrieves the textual content of the post.
     * @return A string containing the main content of the post.
     */
    public String getContent() {
        return content;
    }

    /**
     * Retrieves the current number of upvotes for the post.
     * @return An integer value representing the number of upvotes the post has received.
     */
    public int getUpvotes() {
        return upvotes;
    }

    /**
     * Retrieves the current number of downvotes for the post.
     * @return An integer value representing the number of downvotes the post has received.
     */
    public int getDownvotes() {
        return downvotes;
    }

    /**
     * Retrieves the list of comments associated with the post.
     * @return An ArrayList of String objects, each representing a unique ID of a comment made on the post.
     */
    public ArrayList<String> getComments() {
        return commentIds;
    }

    /**
     * Increments the upvote count for the post and updates the corresponding post in the PostsManager.
     * @throws SMPException If there is an error updating the post.
     */
    public void addUpvote() throws SMPException {
        upvotes += 1;
        Post post = PostsManager.searchPost(this.postId);
        PostsManager.updatePost(post);
    }

    /**
     * Increments the downvote count for the post and updates the corresponding post in the PostsManager.
     * @throws SMPException If there is an error updating the post.
     */
    public void addDownvote() throws SMPException {
        downvotes += 1;
        Post post = PostsManager.searchPost(this.postId);
        PostsManager.updatePost(post);
    }

    /**
     * This method creates the comment in the CommentsManager, then adds the comment's ID to this post.
     * @param author The username of the author of the comment.
     * @param content The textual content of the comment.
     * @throws SMPException If there is an error creating the comment or updating the post.
     */
    public void addComment(String author, String content) throws SMPException {
        String commentId = CommentsManager.addComment(author, content, 0, 0);
        if (commentId == null || commentId.isEmpty()) {
            throw new SMPException("Could not add comment.");
        }
        commentIds.add(commentId);
        PostsManager.updatePost(this);
    }

    /**
     * Removes a comment from the post if the requester is authorized (either the post creator or the comment author).
     * @param commentId The ID of the comment to be removed.
     * @param requesterUsername The username of the requester.
     * @throws SMPException If the comment cannot be deleted.
     */
    public void deleteComment(String commentId, String requesterUsername) throws SMPException {
        Comment commentToDelete = CommentsManager.searchComment(commentId);
        if (commentToDelete == null) {
            throw new SMPException("Comment not found.");
        }
        User commentAuthor = commentToDelete.getAuthor();
        boolean isPostCreator = this.creator.getUsername().equals(requesterUsername);
        if (commentAuthor.getUsername().equals(requesterUsername)) {
            if (commentIds.remove(commentId)) {
                CommentsManager.deleteComment(commentId, commentAuthor.getUsername());
                PostsManager.updatePost(this);
            } else {
                throw new SMPException("Failed to delete comment from post.");
            }
        } else if (isPostCreator){
            if (commentIds.remove(commentId)) {
                CommentsManager.deleteComment(commentId, this.creator.getUsername());
                PostsManager.updatePost(this);
            } else {
                throw new SMPException("Failed to delete comment from post.");
            }
        } else {
            throw new SMPException("Failed to delete comment from post.");
        }
    }

    /**
     * Generates a string representation of the post, including its ID, creator, content, upvotes, downvotes, comments.
     * @return A string representation of the post.
     */
    @Override
    public String toString() {
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
