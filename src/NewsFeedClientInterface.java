/**
 * CS18000 -- Project 5 -- Phase 1
 * Interface for user interactions with the news feed.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version March 31, 2024
 */
public interface NewsFeedClientInterface {
    /**
     * Displays the news feed to the user.
     * This includes posts from friends, allowing the user to interact with them (e.g., upvote, downvote, comment).
     */
    void viewNewsFeed();

    /**
     * Allows the user to create a new post.
     * @param content The content of the new post to be created.
     */
    void createPost(String content);

    /**
     * Allows the user to comment on a post.
     * @param postId The unique ID of the post to be commented on.
     * @param content The content of the comment to be added to the post.
     */
    void commentOnPost(String postId, String content);

    /**
     * Allows the user to upvote a post.
     * @param postId The unique ID of the post to be upvoted.
     */
    void upvotePost(String postId);

    /**
     * Allows the user to downvote a post.
     * @param postId The unique ID of the post to be downvoted.
     */
    void downvotePost(String postId);

    /**
     * Allows the user to hide a post from their news feed.
     * @param postId The unique ID of the post to be hidden.
     */
    void hidePost(String postId);
}
