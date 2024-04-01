/**
 * CS18000 -- Project 5 -- Phase 1
 * Interface for operations on comment data, including reading, writing, and updating comments.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version March 31, 2024
 */
public interface CommentsManagerInterface {
    void readCommentsDatabaseFile() throws SMPException;
    void writeCommentsDatabaseFile() throws SMPException;
    boolean updateComment(Comment updatedComment) throws SMPException;
}
