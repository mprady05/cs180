import java.util.ArrayList;

/**
 * CS18000 -- Project 5 -- Phase 2
 * Interface for managing all posts.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version April 13, 2024
 */
public interface PostsManagerInterface {
    static ArrayList<Post> getPosts() {
        return null;
    }
    static void setPosts(ArrayList<Post> posts) {
    }
    static void readPostsDatabaseFile() {
    }
    static void writePostsDatabaseFile() {
    }
    static String addPost(String creatorUsername, String content, int upvotes,
                          int downvotes, ArrayList<String> commentIds)
            throws SMPException {
        return null;
    }
    static void clearAllPosts() {
    }
    static boolean updatePost(Post updatedPost) throws SMPException {
        return false;
    }
    static Post searchPost(String postId) {
        return null;
    }
    static String getPostIdFromComment(Comment comment) {
        return null;
    }
}
