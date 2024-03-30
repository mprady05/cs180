import java.util.ArrayList;
public class User {
    private String firstName;
    private String lastName;
    private String password;
    private String photoId;
    private String username;
    private ArrayList<User> friendList;
    private ArrayList<User> blockList;

    public User (String firstName, String lastName, String password, String username, String photoId, ArrayList<User> friendList, ArrayList<User> blockList ){
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.photoId = photoId;
        this.friendList = friendList;
        this.blockList = blockList;
        this.username = username;
    }

    public User(){  
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPhotoId() {
        return photoId;
    }
    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<User> getFriendList() {
        return friendList;
    }

    public ArrayList<User> getBlockList() {
        return blockList;
    }

    public void setFriendList(ArrayList<User> friendList) {
        this.friendList = friendList;
    }

    public void setBlockList(ArrayList<User> blockList) {
        this.blockList = blockList;
    }

    @Override
    public String toString() {
        return this.firstName + "," +
                this.lastName + "," +
                this.username + "," +
                this.password;
    }
}
