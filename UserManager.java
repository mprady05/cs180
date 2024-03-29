import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserManager {
    private UserDatabase userDB = null ;

    public UserManager(UserDatabase userDB) {
        this.userDB =  userDB;
    }

    public User createUser(Map<String,String> userMap) throws SMPException {
        String firstName = userMap.get("firstName");
        String lastName = userMap.get("lastName");
        String password =  userMap.get("password");
        String email = userMap.get("email");
        String userName = userMap.get("userName");

        if (firstName == null || lastName == null || password == null || email == null || userName == null) {
            throw new SMPException("Missing required user information.");
        }

        User user = getUserByEmail(email);

        if (getUserByEmail(email) != null) {
            throw new SMPException("User with this email already exists.");
        }

        if (!isValidPassword(password)){
            throw new SMPException("Invalid password format.");
        }

        if (!isValidEmail(email)){
            throw new SMPException("Invalid email format.");
        }

        if (!isValidUsername(userName)){
            throw new SMPException("Invalid Username format.");
        }

        if (userDB.getUserByUsername(userName) != null) {
            throw new SMPException("Username already exists.");
        }

        if (getUserByEmail(email) != null) {
            throw new SMPException("Email already exists.");
        }

        if (user == null) {
            user = new User();
            UUID uuid = UUID.randomUUID();
            String userId = uuid.toString();

            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setUsername(userName.toLowerCase());
            user.setPassword(password);
            user.setEmail(email);

            user.setId(userId);
            this.userDB.saveUser(userId, user);
        } else {
            // throw Exception here "UserName alreay exist";
        }

        return user;
    }

    private User getUserByEmail(String email)
    {
        HashMap<String,User> userMap  = this.userDB.getUserMap();

        if (userMap.isEmpty()){
            return null;
        }

        for(User user:userMap.values()) {
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
        this.userDB.saveUser(user.getId(), user);

        return user.getId();
    }

    public String login(Map<String,String> userMap) throws SMPException {
        String enteredUsername = userMap.get("username");
        String password = userMap.get("password");

        User user = userDB.getUserByUsername(enteredUsername);

        if (user != null) {
            if (user.getUsername().equalsIgnoreCase(enteredUsername) && user.getPassword().equals(password)) {
                return user.getId();
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
        this.userDB.saveUser(user.getId(), user);

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
}
