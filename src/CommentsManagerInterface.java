import java.util.List;

/**
 * CS18000 -- Project 5 -- Phase 1
 * Interface for operations on comment data, including reading, writing, and updating comments.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version March 31, 2024
 */
public interface CommentsManagerInterface {
    static List<Comment> getComments() {
        return CommentsManager.getComments();
    }

    static void readCommentsDatabaseFile() throws SMPException {
    }

    static void writeCommentsDatabaseFile() throws SMPException {
    }
    static Comment addComment(String authorUsername, String content, int upvotes, int downvotes)
            throws SMPException {
        return null;
    }
    static boolean updateComment(Comment updatedComment) throws SMPException {
        return false;
    }
    static boolean deleteComment(String commentId, String requesterUsername) throws SMPException {
        return false;
    }
    static Comment searchComment(String commentId) {
        return null;
    }
    static void clearAllComments() {
    }
}
