import java.io.IOException;
import java.util.ArrayList;
/**
 * CS18000 -- Project 5 -- Phase 2
 * Interface for ClientHandlers.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version April 13, 2024
 */
public interface ClientHandlerInterface {
    void run();
    void processCommand(String command) throws SMPException, IOException, ClassNotFoundException;
    void processLogin() throws IOException, ClassNotFoundException, SMPException;
    void processRegister() throws IOException, ClassNotFoundException, SMPException;
    void sendExitMessage() throws IOException, SMPException;
    void sendInvalidMessage() throws IOException, SMPException;
    void processLoginCommand(String command) throws SMPException, IOException, ClassNotFoundException;
    void processAddFriend() throws SMPException, IOException, ClassNotFoundException;
    void processRemoveFriend() throws SMPException, IOException, ClassNotFoundException;
    void processBlockFriend() throws SMPException, IOException, ClassNotFoundException;
    void processAddPost() throws SMPException, IOException, ClassNotFoundException;
    void processGetPosts() throws SMPException, IOException;
    void processHidePost() throws SMPException, IOException, ClassNotFoundException;
    void processViewSearchUser() throws IOException, SMPException, ClassNotFoundException;
    void processViewFeed() throws SMPException, IOException, ClassNotFoundException;
    Post getPostFromChoice(ArrayList<Post> posts, int postNumber);
    void processLogout() throws SMPException;
    void processGetFriendsPosts() throws SMPException, IOException;
    void processAddUpvote() throws SMPException;
    void processDownvotePost() throws SMPException;
    void processAddComment() throws SMPException, IOException, ClassNotFoundException;
    void processGetPostsComments() throws SMPException, IOException;
    void processViewComments() throws IOException, SMPException, ClassNotFoundException;
    Comment getCommentFromChoice(ArrayList<Comment> comments, int commentNumber);
    void processUpvoteComment() throws SMPException;
    void processDownvoteComment() throws SMPException;
    void processDeleteComment() throws SMPException, IOException;
    void readAllDatabases() throws SMPException;
    void writeAllDatabases() throws SMPException;
}
