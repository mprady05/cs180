/**
 * CS18000 -- Project 5 -- Phase 1
 * Interface for user operations.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version March 31, 2024
 */
public interface UserClientInterface {
    /**
     * Logs in a user.
     * @param username The username of the user trying to log in.
     * @param password The password of the user trying to log in.
     */
    User loginUser(String username, String password);

    /**
     * Logs out the current user.
     */
    void logoutUser();

    /**
     * Adds a friend to the user's friend list.
     * @param friendUsername The username of the friend to be added.
     */
    void addFriend(String friendUsername);

    /**
     * Removes a friend from the user's friend list.
     * @param friendUsername The username of the friend to be removed.
     */
    void removeFriend(String friendUsername);

    /**
     * Updates the user's profile information.
     * @param user The User object containing the updated information.
     */
    void updateProfile(User user);
}
