import java.util.ArrayList;

public class User {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String photoId;
    private ArrayList<User> friendList;
    private ArrayList<User> blockList;

    public User (String id, String firstName, String lastName, String email, String password, String photoId,
                 ArrayList<String> friendList,ArrayList<String> blockList){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.photoId = photoId;
        this.friendList = friendList;
        this.blockList = blockList;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        email = email.toLowerCase();
        if (isValidEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email format.");
        }
    }

    private boolean isValidEmail(String email) {
        // Check if email contains '@' and ends with '.com'
        int atIndex = email.indexOf('@');
        int dotComIndex = email.lastIndexOf(".com");

        return atIndex > 0 && dotComIndex == email.length() - 4 && atIndex < dotComIndex;
    }
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) throws IllegalArgumentException{
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Invalid password format.");
        }
        this.password = password;
    }

    public void setUsername(String username) {
        // Ensure username has no '@' symbol and no capitalization
        if (username != null && !username.contains("@")) {
            this.username = username.toLowerCase();
        } else {
            throw new IllegalArgumentException("Invalid username format.");
        }
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
    public String getPhotoId() {
        return photoId;
    }
    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public ArrayList<String> getFriendList() {
        return friendList;
    }
    public ArrayList<String> getBlockList() {
        return blockList;
    }
    public void setFriendList(ArrayList<String> friendList) {
        this.friendList = friendList;
    }
    public void setBlockList(ArrayList<String> blockList) {
        this.blockList = blockList;
    }

    @Override
    public String toString() {
        return this.id + "," +
                this.firstName + "," +
                this.lastName + "," +
                this.email + "," +
                this.username + "," +
                this.password;
    }
}
