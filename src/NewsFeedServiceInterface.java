import java.util.*;
public interface NewsFeedServiceInterface {
    /**
     * Creates a new post.
     * @param username The username of the user creating the post.
     * @param content The content of the post.
     * @return A new Post object.
     */
    Post createPost(String username, String content);

    /**
     * Deletes a post.
     * @param postId The unique ID of the post to be deleted.
     * @return true if the post was successfully deleted; false otherwise.
     */
    boolean deletePost(String postId);

    /**
     * Increments the upvote count for a post.
     * @param postId The unique ID of the post to be upvoted.
     */
    void upvotePost(String postId);

    /**
     * Increments the downvote count for a post.
     * @param postId The unique ID of the post to be downvoted.
     */
    void downvotePost(String postId);

    /**
     * Creates a comment on a post.
     * @param postId The unique ID of the post to comment on.
     * @param username The username of the user making the comment.
     * @param content The content of the comment.
     * @return A new Comment object.
     */
    Comment createComment(String postId, String username, String content);

    /**
     * Deletes a comment.
     * @param commentId The unique ID of the comment to be deleted.
     * @return true if the comment was successfully deleted; false otherwise.
     */
    boolean deleteComment(String commentId);

    /**
     * Retrieves a list of posts made by the friends of a user.
     * @param username The username of the user whose friends' posts are to be retrieved.
     * @return A list of Post objects.
     */
    ArrayList<Post> getFriendPosts(String username);
}
