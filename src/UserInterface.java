import java.util.ArrayList;
/**
 * CS18000 -- Project 5 -- Phase 1
 * Interface for user operations.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version March 31, 2024
 */
public interface UserInterface {
    String getFirstName();
    String getLastName();
    String getPassword();
    String getUsername();
    ArrayList<String> getFriendList();
    ArrayList<String> getBlockList();
    ArrayList<String> getPostIds();
    void addFriend(String username) throws SMPException;
    void blockUser(String usernameToBlock) throws SMPException;
    void removeFriend(String username) throws SMPException;
    void addPost(String content) throws SMPException;
    void hidePost(String postId) throws SMPException;
    ArrayList<String> getFriendsPosts();
    String toString();
}
