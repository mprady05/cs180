import java.util.ArrayList;

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
