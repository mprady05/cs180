import java.util.ArrayList;

public class User {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String photoId;
    private ArrayList<String> friendList;
    private ArrayList<String> blockList;


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
                this.email + "," + this.password;
    }
}
