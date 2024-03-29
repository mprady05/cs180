import java.util.*;

public class BlockedAndFriends implements BlockedUsers {
    private ArrayList<User> friends;
    private ArrayList<User> blocked;
    private ArrayList<User> mutualFriends;
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

    public void setMutualFriends(User one, User two) {
        if (one.getFriends == null || two.getFriends == null) {
            return null;
        }
        ArrayList<User> friendOneFriends = new ArrayList<>;
        friendOneFriends = one.getFriends;
        ArrayList<User> friendTwoFriends= new ArrayList<>;
        friendTwoFriends = two.getFriends;
        ArrayList<User> mutualFriends= new ArrayList<>;
        for (int i = 0; i < one.getSize(), i++){
            if (two.contains(one.get(i))){
                mutualFriends.add(one.get(i));
            }
        }
        this.mutualFriends = mutualFriends;
    }
    public void getMutualFriends(){
        return this.mutualFriends;
    }
    public String mutualFriendsToString (User user) {
        ArrayList<User> mutuals = new ArrayList<>;
        if (mutuals.getSize()==0 || mutuals == null){
            return null;
        }
        int numberMutuals = mutuals.getSize();
        if (numberMutuals <= 3) {
            if (numberMutuals == 1){
                return String.format("Friends with: %s",mutuals.getFirst());
            } else if (numberMutuals == 2){
                return String.format("Friends with: %s, %s", mutuals.getFirst(), mutuals.getLast());
            } else {
                return String.format("Friends with %s, %s, %s",mutuals.getFirst(), mutuals.get(2), mutuals.getLast());
            }
        }
        return String.format("Friends with: %s, %s, %s and %d others",mutuals.getFirst(),mutuals.get(2),mutuals.getLast(),numberMutuals-3);
    } 
    public ArrayList<User> recommendedFriends(User user){
        ArrayList<User> inputFriends= new ArrayList<>;
        
    }
}
