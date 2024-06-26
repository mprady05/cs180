import java.util.ArrayList;
/**
 * CS18000 -- Project 5 -- Phase 2
 * Interface for Users Manager.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version April 13, 2024
 */
public interface UsersManagerInterface {
    static void readUsersDatabaseFile() throws SMPException {
    }
    static ArrayList<User> getUsers() {
        return null;
    }
    static User registerUser(String firstName, String lastName,
                                String username, String password,
                                ArrayList<String> friendList,
                                ArrayList<String> blockList,
                                ArrayList<String> postIds)
            throws SMPException {
        return null;
    }
    static User loginUser(String username, String password) throws SMPException {
        return null;
    }
    static boolean updateUser(User updatedUser) throws SMPException {
        return false;
    }
    static User searchUser(String username) {
        return null;
    }
    static void clearAllUsers() {
    }
}
