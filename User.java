import java.util.ArrayList;
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String photoId;
    private String username;

    public User (String firstName, String lastName, String email, String password, String photoId, String username){
    
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.photoId = photoId;
        this.username = username;
    }
    public User (){
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
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return this.firstName + "," +
                this.lastName + "," +
                this.email + "," +
                this.username + "," +
                this.password;
    }
}
