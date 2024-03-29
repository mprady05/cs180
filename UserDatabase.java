import java.io.*;
import java.util.HashMap;
import java.util.UUID;

public class UserDatabase {
    private HashMap<String,User> userMap = new HashMap<String,User>();
    public UserDatabase(){
        readUsersFromFile();
        //Message file Read
    }


    public boolean saveUser(String id,User user) {
        this.userMap.put(id,user);
        FileReadWriter.writeFile("User.db", this.userMap);
        return true;
    }

    public User getUserById(String id) {

        if (this.userMap.containsKey(id))
            return this.userMap.get(id);

        return null;

    }

    public User getUserByUsername(String username) {
        for (User user : userMap.values()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null; // User not found
    }

    public boolean deleteUser(String id) {
        if (userMap.containsKey(id)) {
            userMap.remove(id);
            FileReadWriter.writeFile("User.db", userMap); // Call FileReadWriter to update the file
            return true;
        }
        return false;
    }


    public HashMap<String, User> getUserMap() {
        return userMap;
    }
    public void setUserMap(HashMap<String, User> userMap) {
        this.userMap = userMap;
    }
    private void readUsersFromFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("User.db"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] tempArray = line.split(",");

                if (tempArray.length > 6 )
                {
                    User user = new User();
                    user.setId(tempArray[0]);
                    user.setFirstName(tempArray[1]);
                    user.setLastName(tempArray[2]);
                    user.setEmail(tempArray[3]);
                    user.setUsername(tempArray[4]);
                    user.setPassword(tempArray[5]);
                    this.userMap.put(tempArray[0],user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
