import java.util.ArrayList;
/**
 * CS18000 -- Project 5 -- Phase 1
 * Interface outlining the functionalities for a post.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version March 31, 2024
 */
public interface PostInterface {
    String getPostId();
    User getCreator();
    String getContent();
    int getUpvotes();
    int getDownvotes();
    ArrayList<String> getComments();
    void addUpvote() throws SMPException;
    void addDownvote() throws SMPException;
    void addComment(String author, String content) throws SMPException;
    void deleteComment(String commentId, String requesterUsername) throws SMPException;
    String toString();
}
