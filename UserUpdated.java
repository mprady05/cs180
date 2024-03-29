import java.util.*;

import java.util.ArrayList;

public class UserUpdated {
    private String id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String photoId;
    private ArrayList<User> friendList;
    private ArrayList<User> blockList;

    public User(String id, String firstName, String lastName, String email, String password, String photoId,
                ArrayList<User> friendList, ArrayList<User> blockList) {
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
                this.email + "," + this.password;
    }

    public boolean addFriend(User friend) {
        if (friendList.contains(friend)) {
            return false;
        } else {
            friendList.add(friend);
            return true;
        }
    }

    public boolean removeFriend(User friend) {
        try {
            for (int i = 0; i < friendList.size(); i++) {
                if (friendList.get(i).equals(friend)) {
                    friendList.remove(friend);
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean blockUser(User userBlocked) {
        if (blockList.contains(userBlocked)) {
            return false;
        } else {
            blockList.add(userBlocked);
            return true;
        }
    }

    public boolean unblockUser(User userBlocked) {
        try {
            for (int i = 0; i < blockList.size(); i++) {
                if (blockList.get(i).equals(userBlocked)) {
                    blockList.remove(userBlocked);
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public ArrayList<User> MutualFriends(User one, User two) {
        if (one.friendList == null || two.friendList == null) {
            return null;
        }
        ArrayList<User> friendOneFriends = new ArrayList<>();
        friendOneFriends = one.friendList;
        ArrayList<User> friendTwoFriends = new ArrayList<>();
        friendTwoFriends = two.friendList;
        ArrayList<User> mutualFriends = new ArrayList<>();
        for (int i = 0; i < friendOneFriends.size(); i++) {
            if (friendTwoFriends.contains(friendOneFriends.get(i))) {
                mutualFriends.add(friendOneFriends.get(i));
            }
        }
        return mutualFriends;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String mutualFriendsToString(ArrayList<User> mutuals) {
        ArrayList<User> mutual;
        mutual = mutuals;
        if (mutual.isEmpty()) {
            return null;
        }
        int numberMutual = mutual.size();
        if (numberMutual <= 3) {
            if (numberMutual == 1) {
                return String.format("Friends with: %s", mutual.getFirst().getUserName());
            } else if (numberMutual == 2) {
                return String.format("Friends with: %s, %s", mutual.getFirst().getUserName(), mutual.getLast().getUserName());
            } else {
                return String.format("Friends with %s, %s, %s", mutual.getFirst().getUserName(), mutual.get(2).getUserName(),
                        mutual.getLast().getUserName());
            }
        }
        return String.format("Friends with: %s, %s, %s and %d others", mutual.getFirst().getUserName(), mutual.get(2).getUserName(),
                mutual.getLast().getUserName(), numberMutual - 3);
    }

    public ArrayList<User> recommendedFriends(User user) {
        ArrayList<User> inputFriends = new ArrayList<>();
        ArrayList<User> output = new ArrayList<>();
        for (int i = 0; i < users.getSize(); i++) {
            user.get(i).getFriends();
        }
    }
}
