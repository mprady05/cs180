import java.util.HashMap;

public interface UserDatabaseInterface {
    boolean saveUser(String username, User user);
    boolean deleteUser(String username);
    HashMap<String, User> getUserMap();
    void setUserMap(HashMap<String, User> userMap);
}
