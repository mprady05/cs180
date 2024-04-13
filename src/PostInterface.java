import java.util.ArrayList;
/**
 * CS18000 -- Project 5 -- Phase 2
 * Interface outlining the functionalities for a post.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version April 13, 2024
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
    boolean addComment(String author, String content) throws SMPException;
    boolean deleteComment(String commentId, String requesterUsername) throws SMPException;
    String toString();
}
