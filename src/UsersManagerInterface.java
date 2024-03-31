import java.util.ArrayList;
import java.util.Map;

public interface UsersManagerInterface {
    User createUser(Map<String, String> userMap) throws SMPException;
    String updateUser(String username, Map<String, String> userMap) throws SMPException;
    String login(Map<String, String> userMap) throws SMPException;
    boolean resetPassword(String username, String newPassword) throws SMPException;
    boolean addFriend(User user, User friend);
    boolean removeFriend(User friend);
    boolean blockUser(User userBlocked);
    boolean unblockUser(User userBlocked);
    ArrayList<User> MutualFriends(User one, User two);
    String mutualFriendsToString(ArrayList<User> mutuals);
}
