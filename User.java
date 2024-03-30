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
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPhotoId() {
        return photoId;
    }
    public void setPhotoId(String photoId) {
        this.photoId = photoId;
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
        return this.id + "," +
                this.firstName + "," +
                this.lastName + "," +
                this.email + "," +
                this.username + "," +
                this.password;
    }
}
