import java.util.*;

public class BlockedAndFriends implements BlockedUsers {
    private ArrayList<User> friends;
    private ArrayList<User> blocked;
    private String username;

    public User() {
        
    }

    public ArrayList<User> getFriends() {
        return this.friends;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public ArrayList<User> getBlocked() {
        return this.blocked;
    }

    public void setBlocked(ArrayList<User> blocked) {
        this.blocked = blocked;
    }

    public boolean addFriend(User this, User friend) {
        if (friends.contains(friend)){
            return false;
        } else {
            friends.add(friend);
            return true;
        }
    }

    public boolean removeFriend(User this, User friend) {
        try {
            for (int i = 0; i < friends.size(); i++) {
                if (friends.get(i).equals(friend)){
                    friends.remove(friend);
                    return true;
                }
            }
        } catch (Exception e){
            return false;
        }
        return false;
    }

    public boolean blockUser(User this, User userBlocked) {
        if (blocked.contains(userBlocked)){
            return false;
        } else{
            blocked.add(userBlocked);
            return true;
        }
    }

    public boolean unblockUser(User this, User userBlocked) {
        try {
            for (int i = 0; i < blocked.size(); i++) {
                if (blocked.get(i).equals(userBlocked)){
                    blocked.remove(userBlocked);
                    return true;
                }
            }
        } catch (Exception e){
            return false;
        }
        return false;
    }
}
