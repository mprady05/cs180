import java.util.ArrayList;

public interface UsersManagerInterface {
    static void readUsersDatabaseFile() throws SMPException {

    }

    static ArrayList<User> getUsers() {
        return null;
    }

    static boolean registerUser(String firstName, String lastName, String username, String password, ArrayList<String> friendList, ArrayList<String> blockList, ArrayList<String> postIds) throws SMPException {
        return false;
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
