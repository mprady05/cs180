import java.io.IOException;
/**
 * CS18000 -- Project 5 -- Phase 2
 * Class that represents the client
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version April 13, 2024
 */
public interface ClientInterface {
    void start() throws IOException;
    void handleWelcomeMenu(String command) throws IOException, ClassNotFoundException, SMPException;
    void handleUserMenu(String command) throws IOException, ClassNotFoundException, SMPException;
    User handleLogin() throws IOException, ClassNotFoundException, SMPException;
    User handleRegistration() throws IOException, ClassNotFoundException, SMPException;
    void handleAddFriend() throws IOException, ClassNotFoundException;
    void handleRemoveFriend() throws IOException, ClassNotFoundException;
    void handleAddPost() throws IOException, ClassNotFoundException;
    boolean displayPosts() throws IOException;
    void handleHidePost() throws IOException, ClassNotFoundException;
    void handleViewSearchProfile() throws IOException, ClassNotFoundException;
    boolean displayFeed() throws IOException;
    void handleViewFeed() throws IOException, ClassNotFoundException;
    void handlePostOptions() throws IOException, ClassNotFoundException;
    void handleAddComment () throws IOException, ClassNotFoundException;
    boolean displayComments() throws IOException;
    void handleViewComments() throws IOException, ClassNotFoundException;
    void handleCommentOptions() throws IOException, ClassNotFoundException;
    void handleDeleteComment() throws IOException, ClassNotFoundException;
}
