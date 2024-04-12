import java.util.*;
import java.io.*;
/**
 * CS18000 -- Project 5 -- Phase 1
 * Manages user accounts.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version March 31, 2024
 */
public class UsersManager implements UsersManagerInterface {
    private static final String USER_FILE = "UsersDatabase.txt";
    public static ArrayList<User> users = new ArrayList<>();

    /**
     * Constructor that initializes the UsersManager by reading the users from the database file.
     * @throws SMPException if there's an error reading the users database file.
     */
    public UsersManager() throws SMPException {
        readUsersDatabaseFile();
    }

    /**
     * Returns the current list of users.
     * @return A list of User objects representing all users.
     */
    public static ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Reads users from the database file and adds them to the 'users' list.
     * Each line in the file represents a single user's data, which is parsed and converted into a User object.
     * @throws SMPException if there's an error reading the file or parsing user data.
     */
    public static void readUsersDatabaseFile() throws SMPException {
        users.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    User user = stringToUser(line);
                    if (user != null) {
                        users.add(user);
                    }
                } catch (Exception e) {
                    throw new SMPException("Error parsing user from line: " + e.getMessage());
                }
            }
        } catch (IOException | SMPException e) {
            throw new SMPException("Error parsing user from line: " + e.getMessage());
        }
    }

    /**
     * Converts a line from the users database file into a User object.
     * @param line A string representing a line from the users database file.
     * @return A User object created from the parsed line.
     * @throws SMPException if parsing fails due to incorrect format.
     */
    private static User stringToUser(String line) throws SMPException {
        try {
            String[] parts = line.split(";");
            String firstName = parts[0];
            String lastName = parts[1];
            String username = parts[2];
            String password = parts[3];
            ArrayList<String> friendList = new ArrayList<>();
            if (parts[4].length() > 2) {
                String friendListStr = parts[4].substring(1, parts[4].length() - 1);
                for (String friend : friendListStr.split(",")) {
                    friendList.add(friend.trim());
                }
            }
            ArrayList<String> blockList = new ArrayList<>();
            if (parts[5].length() > 2) {
                String blockListStr = parts[5].substring(1, parts[5].length() - 1);
                for (String blocked : blockListStr.split(",")) {
                    blockList.add(blocked.trim());
                }
            }
            ArrayList<String> postIds = new ArrayList<>();
            if (parts[6].length() > 2) {
                String postIdsStr = parts[6].substring(1, parts[6].length() - 1);
                for (String postId : postIdsStr.split(",")) {
                    postIds.add(postId.trim());
                }
            }
            return new User(firstName, lastName, username, password, friendList, blockList, postIds);
        } catch (NumberFormatException e) {
            throw new SMPException("Error parsing file.");
        }
    }

    /**
     * Writes the current list of users into the users database file.
     * Converts each User object into a string representation and writes it to the file.
     */
    public static void writeUsersDatabaseFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
            for (User user : users) {
                String userInfo = user.getFirstName() + ";" +
                        user.getLastName() + ";" +
                        user.getUsername() + ";" +
                        user.getPassword() + ";";
                userInfo += "(";
                for (int i = 0; i < user.getFriendList().size(); i++) {
                    userInfo += user.getFriendList().get(i);
                    if (i < user.getFriendList().size() - 1) {
                        userInfo += ",";
                    }
                }
                userInfo += ");";
                userInfo += "(";
                for (int i = 0; i < user.getBlockList().size(); i++) {
                    userInfo += user.getBlockList().get(i);
                    if (i < user.getBlockList().size() - 1) {
                        userInfo += ",";
                    }
                }
                userInfo += ");";
                userInfo += "(";
                for (int i = 0; i < user.getPostIds().size(); i++) {
                    userInfo += user.getPostIds().get(i);
                    if (i < user.getPostIds().size() - 1) {
                        userInfo += ",";
                    }
                }
                userInfo += ")";
                writer.write(userInfo);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to Users Database File: " + e.getMessage());
        }
    }

    /**
     * Registers a new user by adding them to the 'users' list, if the username does not already exist.
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param friendList A list of usernames representing the user's friends.
     * @param blockList A list of usernames representing the users blocked by the user.
     * @param postIds A list of post IDs created by the user.
     * @return true if the user was successfully registered; false if the username already exists.
     * @throws SMPException if the username already exists.
     */
    public static boolean registerUser(String firstName, String lastName, String username,
                                       String password, ArrayList<String> friendList,
                                       ArrayList<String> blockList, ArrayList<String> postIds)
            throws SMPException {
        if (doesUsernameExist(username)) {
            throw new SMPException("Username already exists.");
        }
        User newUser = new User(firstName, lastName, username, password, friendList, blockList, postIds);
        users.add(newUser);
        return true;
    }
    private static boolean doesUsernameExist(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Attempts to log in a user with the given username and password.
     * @param username The username of the user attempting to log in.
     * @param password The password provided by the user.
     * @return A User object if the login is successful; null if the credentials do not match any user.
     * @throws SMPException if there's an error during the login process.
     */
    public static User loginUser(String username, String password) throws SMPException {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Updates the information of an existing user in the 'users' list.
     * @param updatedUser A User object containing the updated information.
     * @return true if the user was successfully updated; false if the user could not be found.
     * @throws SMPException if the user to be updated cannot be found.
     */
    public static boolean updateUser(User updatedUser) throws SMPException {
        int userIndex = -1;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(updatedUser.getUsername())) {
                userIndex = i;
                break;
            }
        }
        if (userIndex != -1) {
            users.set(userIndex, updatedUser);
            return true;
        } else {
            throw new SMPException("User not found.");
        }
    }

    /**
     * Searches for a user by their username.
     * @param username The username of the user to search for.
     * @return A User object if a user with the specified username exists; null otherwise.
     */
    public static User searchUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Clears all users from the 'users' list. Used for testing.
     */
    public static void clearAllUsers() {
        UsersManager.users.clear();
    }

}
