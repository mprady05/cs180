/**
 * CS18000 -- Project 5 -- Phase 1
 * Interface for user comments, detailing ID, author, content, and votes.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version March 31, 2024
 */
public interface CommentInterface {
    String getCommentId();
    User getAuthor();
    String getContent();
    int getUpvotes();
    int getDownvotes();
    void addUpvote() throws SMPException;
    void addDownvote() throws SMPException;
    String toString();
}
