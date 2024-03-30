import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserManager {
    private UserDatabase userDB = null;

    public UserManager(UserDatabase userDB) {
        this.userDB = userDB;
    }

    public User createUser(Map<String, String> userMap) throws SMPException {
        String firstName = userMap.get("firstName");
        String lastName = userMap.get("lastName");
        String password = userMap.get("password");
        String email = userMap.get("email");
        String userName = userMap.get("userName");

        if (firstName == null || lastName == null || password == null || email == null || userName == null) {
            throw new SMPException("Missing required user information.");
        }

        User user = getUserByEmail(email);

        if (getUserByEmail(email) != null) {
            throw new SMPException("User with this email already exists.");
        }

        if (!isValidPassword(password)) {
            throw new SMPException("Invalid password format.");
        }

        if (!isValidEmail(email)) {
            throw new SMPException("Invalid email format.");
        }

        if (!isValidUsername(userName)) {
            throw new SMPException("Invalid Username format.");
        }

        if (UserDatabase.getUserByUsername(userName) != null) {
            throw new SMPException("Username already exists.");
        }

        if (getUserByEmail(email) != null) {
            throw new SMPException("Email already exists.");
        }

        if (user == null) {
            user = new User();

            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setUsername(userName.toLowerCase());
            user.setPassword(password);
            user.setEmail(email);

            this.userDB.saveUser(userName, user);
        }
        return user;
    }

    private User getUserByEmail(String email) {
        HashMap<String, User> userMap = this.userDB.getUserMap();

        if (userMap.isEmpty()) {
            return null;
        }

        for (User user : userMap.values()) {
            if (user.getEmail().trim().equals(email)) {
                return user;
            }
        }

        return null;
    }
    

    public String updateUser(String userId, Map<String, String> userMap) throws SMPException {
        String firstName = userMap.get("firstName");
        String lastName = userMap.get("lastName");
        String newEmail = userMap.get("email");

        User user = getUserById(userId);

        if (user == null) {
            throw new SMPException("User not found.");
        }

        // Update first name and last name
        user.setFirstName(firstName);
        user.setLastName(lastName);

        // Update email if it's different from the current email
        if (newEmail != null && !newEmail.equals(user.getEmail())) {
            // Validate the new email format
            if (!isValidEmail(newEmail)) {
                throw new SMPException("Invalid email format.");
            }
            // Check if the new email already exists
            if (getUserByEmail(newEmail) != null) {
                throw new SMPException("Email already exists.");
            }
            // Update the email
            user.setEmail(newEmail);
        }

        // Save the updated user
        this.userDB.saveUser(user.getUsername(), user);

        return user.getUsername();
    }

    public String login(Map<String, String> userMap) throws SMPException {
        String enteredUsername = userMap.get("username");
        String password = userMap.get("password");

        User user = userDB.getUserByUsername(enteredUsername);

        if (user != null) {
            if (user.getUsername().equalsIgnoreCase(enteredUsername) && user.getPassword().equals(password)) {
                return user.getUsername();
            } else {
                throw new SMPException("Invalid username or password.");
            }
        } else {
            throw new SMPException("Invalid username or password.");
        }
    }


    public User getUserById(String Id) {
        User user = this.userDB.getUserById(Id);
        return user;
    }

    public boolean resetPassword(String userId, String newPassword) throws SMPException {
        User user = this.userDB.getUserById(userId);

        if (user == null) {
            return false; // User not found
        }

        // Check if the new password is different from the old one
        if (newPassword.equals(user.getPassword())) {
            throw new SMPException("New password cannot be the same as the old one.");
        }

        // Update the password
        user.setPassword(newPassword);
        this.userDB.saveUser(user.getUsername(), user);

        return true;
    }

    private boolean isValidPassword(String password) {
        // Minimum length of 6 characters
        if (password.length() < 6) {
            return false;
        }

        // At least one uppercase letter
        Pattern uppercasePattern = Pattern.compile("[A-Z]");
        Matcher uppercaseMatcher = uppercasePattern.matcher(password);
        if (!uppercaseMatcher.find()) {
            return false;
        }

        // At least one special character
        Pattern specialCharPattern = Pattern.compile("[^A-Za-z0-9]");
        Matcher specialCharMatcher = specialCharPattern.matcher(password);
        if (!specialCharMatcher.find()) {
            return false;
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        // Check if email contains '@' and ends with '.com'
        int atIndex = email.indexOf('@');
        int dotComIndex = email.lastIndexOf(".com");

        return atIndex > 0 && dotComIndex == email.length() - 4 && atIndex < dotComIndex;
    }

    private boolean isValidUsername(String username) {
        return username != null && !username.contains("@");
    }

    //Removes the user as a friend from this user
    public boolean addFriend(User user, User friend) {

        if (user.getFriendList().contains(friend)) {
            return false; // User is already a friend
        } else {
            user.getFriendList().add(friend); // Add the User object to the friendList
            return true; // Successfully added friend
        }
    }

    //Removes the user as a friend from this user
    public boolean removeFriend(User friend) {
        try {
            for (int i = 0; i < friend.getFriendList().size(); i++) {
                if (friend.getFriendList().get(i).equals(friend)) {
                    friend.getFriendList().remove(i); // Remove the friend at index i
                    return true;
                }
            }
        } catch (Exception e) {
            return false; // Handle exceptions, such as IndexOutOfBoundsException
        }
        return false; // Return false if the friend was not found
    }

    //Blocks user from this user
    public boolean blockUser(User userBlocked) {
        if (userBlocked.getBlockList().contains(userBlocked)) {
            return false;
        } else {
            userBlocked.getBlockList().add(userBlocked);
            return true;
        }
    }

    //Unblocks user from this user
    public boolean unblockUser(User userBlocked) {
        try {
            for (int i = 0; i < userBlocked.getBlockList().size(); i++) {
                if (userBlocked.getBlockList().get(i).equals(userBlocked)) {
                    userBlocked.getBlockList().remove(userBlocked);
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    //Finds mutual friends between two users
    public ArrayList<User> MutualFriends(User one, User two) {
        if (one.getFriendList() == null || two.getFriendList() == null) {
            return null;
        }
        ArrayList<User> friendOneFriends = new ArrayList<>();
        friendOneFriends = one.getFriendList();
        ArrayList<User> friendTwoFriends = new ArrayList<>();
        friendTwoFriends = two.getFriendList();
        ArrayList<User> mutualFriends = new ArrayList<>();
        for (int i = 0; i < friendOneFriends.size(); i++) {
            if (friendTwoFriends.contains(friendOneFriends.get(i))) {
                mutualFriends.add(friendOneFriends.get(i));
            }
        }
        return mutualFriends;
    }

    //mutualFriendsToString similar to Insta's following feature. Will be outputted in GUI
    public String mutualFriendsToString(ArrayList<User> mutuals) {
        ArrayList<User> mutual;
        mutual = mutuals;
        if (mutual.isEmpty()) {
            return null;
        }
        int numberMutual = mutual.size();
        if (numberMutual <= 3) {
            if (numberMutual == 1) {
                return String.format("Friends with: %s", mutual.getFirst().getUsername());
            } else if (numberMutual == 2) {
                return String.format("Friends with: %s, %s", mutual.getFirst().getUsername(), mutual.getLast().getUsername());
            } else {
                return String.format("Friends with %s, %s, %s", mutual.getFirst().getUsername(), mutual.get(2).getUsername(),
                        mutual.getLast().getUsername());
            }
        }
        return String.format("Friends with: %s, %s, %s and %d others", mutual.getFirst().getUsername(), mutual.get(2).getUsername(),
                mutual.getLast().getUsername(), numberMutual - 3);
    }

    //Recommends friends save blocked users.
    public ArrayList<User> recommendedFriends(User user) {
        ArrayList<User> output = new ArrayList<>();

        // Get the list of friends for the input user
        ArrayList<User> userFriends = user.getFriendList();

        // Iterate over the friends of the input user
        for (User friend : userFriends) {
            // Get the friends of each friend
            ArrayList<User> friendFriends = friend.getFriendList();

            // Iterate over the friends of each friend
            for (User recommendedFriend : friendFriends) {
                // Add the recommended friend to the output list if not already present
                if (!userFriends.contains(recommendedFriend) && !output.contains(recommendedFriend) &&
                        !user.getBlockList().contains(recommendedFriend))  {
                    output.add(recommendedFriend);
                }
            }
        }

        return output;
    }
}
