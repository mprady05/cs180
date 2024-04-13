import java.util.ArrayList;
/**
 * CS18000 -- Project 5 -- Phase 2
 * Class representing a user.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version April 13, 2024
 */
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
    public User(String firstName, String lastName, String username, String password,
                ArrayList<String> friendList, ArrayList<String> blockList, ArrayList<String> postIds) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.friendList = friendList;
        this.blockList = blockList;
        this.postIds = postIds;
    }

    public synchronized String getFirstName() {
        return firstName;
    }
    public synchronized String getLastName() {
        return lastName;
    }
    public synchronized String getUsername() {
        return username;
    }
    public synchronized String getPassword() {
        return password;
    }
    public synchronized ArrayList<String> getFriendList() {
        return friendList;
    }
    public synchronized ArrayList<String> getBlockList() {
        return blockList;
    }
    public synchronized ArrayList<String> getPostIds() {
        return postIds;
    }

    /**
     * Adds a friend to the user's friend list and updates the user in the UsersManager.
     * @param checkUsername Username of the new friend.
     * @return true if friend is added, false otherwise.
     */
    public synchronized boolean addFriend(String checkUsername) throws SMPException {
        if (UsersManager.searchUser(checkUsername) != null &&
                !friendList.contains(checkUsername) &&
                !checkUsername.equals(this.username) &&
                !blockList.contains(checkUsername)) {
            friendList.add(checkUsername);
            UsersManager.updateUser(this);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Blocks a user, removing them from the friend list if present and adding to the block list.
     * Updates both users' data in the UsersManager accordingly.
     * @param usernameToBlock Username of the user to be blocked.
     * @return true if user is blocked, false otherwise.
     */
    public synchronized boolean blockUser(String usernameToBlock) throws SMPException {
        if (friendList.contains(usernameToBlock) &&
                !blockList.contains(usernameToBlock) &&
                !usernameToBlock.equals(this.username)) {
            friendList.remove(usernameToBlock);
            blockList.add(usernameToBlock);
            User userToBlock = UsersManager.searchUser(usernameToBlock);
            if (userToBlock != null) {
                if (userToBlock.getFriendList().contains(this.username)) {
                    userToBlock.getFriendList().remove(this.username);
                    UsersManager.updateUser(userToBlock);
                }
            }
            UsersManager.updateUser(this);
            return true;
        } else {
            return false;
        }
    }


    /**
     * Removes a friend from the user's friend list and updates the user in the UsersManager.
     * @param checkUsername Username of the friend to remove.
     * @return true if friend is removed, false otherwise.
     */
    public synchronized boolean removeFriend(String checkUsername) throws SMPException {
        if (UsersManager.searchUser(checkUsername) != null &&
                friendList.contains(checkUsername)) {
            friendList.remove(checkUsername);
            UsersManager.updateUser(this);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds a new post with the given content created by the user.
     * @param content Content of the new post.
     * @return true if post is added, false otherwise.
     */
    public synchronized boolean addPost(String content) throws SMPException {
        String postId = PostsManager.addPost(this.username, content, 0, 0, new ArrayList<>());
        Post post = PostsManager.searchPost(postId);
        if (post == null) {
            return false;
        }
        postIds.add(postId);
        UsersManager.updateUser(this);
        PostsManager.updatePost(post);
        return true;
    }

    /**
     * Hides a post from the user's view by removing it from their postIds list.
     * @param postId ID of the post to hide.
     * @return true if post is added, false otherwise.
     */
    public synchronized boolean hidePost(String postId) throws SMPException {
        if (postIds.contains(postId)) {
            postIds.remove(postId);
            UsersManager.updateUser(this);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retrieves a list of post IDs created by the user's friends.
     * @return A list of post IDs.
     */
    public synchronized ArrayList<String> getFriendsPosts() {
        ArrayList<String> friendsPosts = new ArrayList<>();
        ArrayList<String> copyFriendList;
        synchronized(this) {
            copyFriendList = new ArrayList<>(friendList);
        }
        for (String friendUsername : copyFriendList) {
            User friend = UsersManager.searchUser(friendUsername);
            if (friend != null) {
                synchronized(friend) {
                    friendsPosts.addAll(new ArrayList<>(friend.getPostIds()));
                }
            }
        }
        return friendsPosts;
    }


    /**
     * Provides a string representation of the User object.
     * @return A string containing the user's data.
     */
    public synchronized String toString() {
        String result = "";
        result += firstName + ';';
        result += lastName + ';';
        result += username + ';';
        result += password + ';';
        result += "(";
        for (int i = 0; i < friendList.size(); i++) {
            result.append(friendList.get(i));
            if (i < friendList.size() - 1) {
                result.append(",");
            }
        }
        result.append(");").append("(");
        for (int i = 0; i < blockList.size(); i++) {
            result.append(blockList.get(i));
            if (i < blockList.size() - 1) {
                result.append(",");
            }
        }
        result.append(");").append("(");
        for (int i = 0; i < postIds.size(); i++) {
            result.append(postIds.get(i));
            if (i < postIds.size() - 1) {
                result.append(",");
            }
        }
        result.append(")");
        return result.toString();
    }
}
