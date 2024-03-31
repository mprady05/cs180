import java.io.*;
import java.util.HashMap;

public class UserDatabase implements UserDatabaseInterface {
    private static HashMap<String,User> userMap = new HashMap<String,User>();
    public UserDatabase(){
        readUsersFromFile();
    }


    public boolean saveUser(String username,User user) {
        userMap.put(username,user);
        FileReadWriter.writeFile("User.db", userMap);
        return true;
    }


    public static User getUserByUsername(String username) {
        for (User user : userMap.values()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null; // User not found
    }

    public boolean deleteUser(String username) {
        if (userMap.containsKey(username)) {
            userMap.remove(username);
            FileReadWriter.writeFile("User.db", userMap); // Call FileReadWriter to update the file
            return true;
        }
        return false;
    }


    public HashMap<String, User> getUserMap() {
        return userMap;
    }
    public void setUserMap(HashMap<String, User> userMap) {
        UserDatabase.userMap = userMap;
    }
    private void readUsersFromFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("User.db"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] tempArray = line.split(",");

                if (tempArray.length > 6 )
                {
                    User user = new User();
                    user.setFirstName(tempArray[0]);
                    user.setLastName(tempArray[1]);
                    user.setUsername(tempArray[2]);
                    user.setPassword(tempArray[3]);
                    userMap.put(tempArray[0],user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
