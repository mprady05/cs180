import java.util.ArrayList;

public interface UserInterface {
    String getFirstName();
    void setFirstName(String firstName);
    String getLastName();
    void setLastName(String lastName);
    String getPassword();
    void setPassword(String password);
    String getPhotoId();
    void setPhotoId(String photoId);
    String getUsername();
    void setUsername(String username);
    ArrayList<User> getFriendList();
    ArrayList<User> getBlockList();
    void setFriendList(ArrayList<User> friendList);
    void setBlockList(ArrayList<User> blockList);
}
