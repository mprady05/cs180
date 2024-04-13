import java.util.ArrayList;
/**
 * CS18000 -- Project 5 -- Phase 2
 * Interface for user operations.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version April 13, 2024
 */
public interface UserInterface {
    String getFirstName();
    String getLastName();
    String getPassword();
    String getUsername();
    ArrayList<String> getFriendList();
    ArrayList<String> getBlockList();
    ArrayList<String> getPostIds();
    boolean addFriend(String username) throws SMPException;
    boolean blockUser(String usernameToBlock) throws SMPException;
    boolean removeFriend(String username) throws SMPException;
    boolean addPost(String content) throws SMPException;
    boolean hidePost(String postId) throws SMPException;
    ArrayList<String> getFriendsPosts();
    String toString();
}
