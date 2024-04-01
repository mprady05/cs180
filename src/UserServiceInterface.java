public interface UserServiceInterface {
    /**
     * Creates a new user.
     * @param username The username for the new user.
     * @param password The password for the new user.
     * @return A new User object.
     */
    User createUser(String username, String password);

    /**
     * Deletes a user.
     * @param username The username of the user to be deleted.
     * @return true if the user was successfully deleted; false otherwise.
     */
    boolean deleteUser(String username);

    /**
     * Retrieves detailed information about a user.
     * @param username The username of the user whose details are to be retrieved.
     * @return A User object containing the user's details.
     */
    User getUserDetails(String username);

    /**
     * Updates the profile information of a user.
     * @param user The User object containing the updated information.
     */
    void updateUserProfile(User user);

    /**
     * Adds a friend to a user's friend list.
     * @param username The username of the user who is adding a friend.
     * @param friendUsername The username of the friend to be added.
     * @return true if the friend was successfully added; false otherwise.
     */
    boolean addFriend(String username, String friendUsername);

    /**
     * Removes a friend from a user's friend list.
     * @param username The username of the user who is removing a friend.
     * @param friendUsername The username of the friend to be removed.
     * @return true if the friend was successfully removed; false otherwise.
     */
    boolean removeFriend(String username, String friendUsername);
}
