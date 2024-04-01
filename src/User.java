/**
 * CS18000 -- Project 5 -- Phase 1
 * Class representing a user.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version March 31, 2024
 */
import java.util.ArrayList;
public class User implements UserInterface {
    private final String firstName;
    private final String lastName;
    private final String password;
    private final String username;
    private ArrayList<String> friendList;
    private ArrayList<String> blockList;
    private ArrayList<String> postIds;

    /**
     * Initializes a new User object with specified details.
     * @param firstName User's first name.
     * @param lastName User's last name.
     * @param username User's username (must be unique).
     * @param password User's password.
     * @param friendList List of user's friends' usernames.
     * @param blockList List of usernames blocked by the user.
     * @param postIds List of post IDs created by the user.
     */
    public User (String firstName, String lastName, String username, String password, ArrayList<String> friendList, ArrayList<String> blockList, ArrayList<String> postIds) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.friendList = friendList;
        this.blockList = blockList;
        this.postIds = postIds;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public ArrayList<String> getFriendList() {
        return friendList;
    }
    public ArrayList<String> getBlockList() {
        return blockList;
    }
    public ArrayList<String> getPostIds() {
        return postIds;
    }

    /**
     * Adds a friend to the user's friend list and updates the user in the UsersManager.
     * @param username Username of the new friend.
     */
    public void addFriend(String username) throws SMPException {
        friendList.add(username);
        UsersManager.updateUser(this);
    }

    /**
     * Blocks a user, removing them from the friend list if present and adding to the block list.
     * Updates both users' data in the UsersManager accordingly.
     * @param usernameToBlock Username of the user to be blocked.
     */
    public void blockUser(String usernameToBlock) throws SMPException {
        if (friendList.contains(usernameToBlock)) {
            friendList.remove(usernameToBlock);
        }
        if (!blockList.contains(usernameToBlock)) {
            blockList.add(usernameToBlock);
        }
        User userToBlock = UsersManager.searchUser(usernameToBlock);
        if (userToBlock != null && userToBlock.getFriendList().contains(this.username)) {
            userToBlock.getFriendList().remove(this.username);
            UsersManager.updateUser(userToBlock);
        }
        UsersManager.updateUser(this);
    }

    /**
     * Removes a friend from the user's friend list and updates the user in the UsersManager.
     * @param username Username of the friend to remove.
     */
    public void removeFriend(String username) throws SMPException {
        friendList.remove(username);
        UsersManager.updateUser(this);
    }

    /**
     * Adds a new post with the given content created by the user.
     * @param content Content of the new post.
     */
    public void addPost(String content) throws SMPException {
        String postId = PostsManager.addPost(this.username, content, 0, 0, new ArrayList<>());
        Post post = PostsManager.searchPost(postId);
        if (post == null) {
            throw new SMPException("Not able to add post.");
        }
        postIds.add(postId);
        UsersManager.updateUser(this);
        PostsManager.updatePost(post);
    }

    /**
     * Hides a post from the user's view by removing it from their postIds list.
     * @param postId ID of the post to hide.
     */
    public void hidePost(String postId) throws SMPException {
        if (postIds.contains(postId)) {
            postIds.remove(postId);
            UsersManager.updateUser(this);
        }
    }

    /**
     * Retrieves a list of post IDs created by the user's friends.
     * @return A list of post IDs.
     */
    public ArrayList<String> getFriendsPosts() {
        ArrayList<String> friendsPosts = new ArrayList<>();
        for (String friendUsername : friendList) {
            User friend = UsersManager.searchUser(friendUsername);
            if (friend != null) {
                friendsPosts.addAll(friend.getPostIds());
            }
        }
        return friendsPosts;
    }

    /**
     * Provides a string representation of the User object.
     * @return A string containing the user's data.
     */
    public String toString() {
        String result = "";
        result += firstName + ';';
        result += lastName + ';';
        result += username + ';';
        result += password + ';';
        result += "(";
        for (int i = 0; i < friendList.size(); i++) {
            result += friendList.get(i);
            if (i < friendList.size() - 1) {
                result += ",";
            }
        }
        result += ");";
        result += "(";
        for (int i = 0; i < blockList.size(); i++) {
            result += blockList.get(i);
            if (i < blockList.size() - 1) {
                result += ",";
            }
        }
        result += ");";
        result += "(";
        for (int i = 0; i < postIds.size(); i++) {
            result += postIds.get(i);
            if (i < postIds.size() - 1) {
                result += ",";
            }
        }
        result += ")";
        return result;
    }
}
