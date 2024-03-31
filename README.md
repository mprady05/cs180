# User.java  
The User class creates the User object that will be used in all the other database classes. The user objects will have multiple fields that make up its contents and it will be used to instantiate new users. Some of the testing that will be performed on this class include making sure that the getters and setters work as intended when updating or retrieving specific fields of this user. In relation to the other classes, the user object created within this class will be used as the user who are using the platform. Implements the UserInterface.java

## Fields: 
private String firstName: User’s first name
private String lastName: User’s last name
private String password: User’s password
private String photoId: User’s profile picture file
private ArrayList<User> friendList: Array List of friends
private ArrayList<User> blockList: Array List of blocked users
private String username: Unique Identifier for the user

## Constructors:
public User (String username, String firstName, String lastName, String password, String photoId, ArrayList<String> friendList,ArrayList<String> blockList)
Instantiate and create this user using the given parameters 

## Methods:
### public String getFirstName
Return this user’s first name
### public void setFirstName(String firstName)
Set this user’s first name to the parameter 
### public String getLastName
Return this user’s last name
### public void setLastName(String lastName)
Set this user’s last name to the parameter  
### public String getPassword
Return this user’s password 
### public void setPassword(String password)
Set this user’s password to the given parameter 
### public String getUsername()
Return this user’s username 
### public void setUsername(String username)
Set this user’s username to the given parameter 
### public String getPhotoId
Return this user’s photo Id 
### public void setPhotoId(String photoId)
Set this user’s photo Id to the parameter 
### public ArrayList<User> getFriendList
Return this user’s friends 
### public void setFriendList(ArrayList<User> friendList)
Set this user’s friends list to the parameter 
### public ArrayList<User> getBlockList
Return this user’s blocked list 
### public void setUserList(ArrayList<User> blockList)
Set this user’s blocked list to the parameter 



# UserInterface.java
An interface for the user class

## Methods
String getFirstName()
void setFirstName(String firstName)
String getLastName()
void setLastName(String lastName);
String getPassword()
void setPassword(String password)
String getPhotoId()
void setPhotoId(String photoId)
String getUsername()
void setUsername(String username)
ArrayList<User> getFriendList()
ArrayList<User> getBlockList()
void setFriendList(ArrayList<User> friendList)
void setBlockList(ArrayList<User> blockList);



# UserManager.java
The UserManager.java class will be responsible with user side algorithms within the application. Some of the features that are included are creating a new user, updating the information of the user, authenticating the login through a password, resetting the user’s passwords, as well as managing who the user will be blocking or adding as friends. Within the entirety of the code, this class will interact mainly with the UserDatabase in order to store information about each user as well as retrieve the necessary information to perform permutations such as updating information or making sure that the new user does not already have an account. Implements the UserManagerInterface.java

## Fields:
Private UserDatabase userDB: The user database, set to null

## Constructors:
Public UserManager(UserDatabase userDB)
Instantiate this userDB to the passed in argument

## Methods:
### public User createUser(Map<String, String> userMap) throws SMPException
This method will be used to create the user object. 
Assign the user with a firstName, lastName, password, and a userName
Get the user object from the userdatabase using the user’s unique username
If the specified username does not exist within the database, create a new user object and set the first name, last name, username, and password and save the user within the database
Return the user
Throw a new SMP Exception if:
The passed in firstName, lastName, password, or userName are null and provide the message “Missing required user information.”
The password is invalid, throw a new message that “Invalid Username format.”
The username is already present in the database, throw a new message that “Usernmane already exists.”
### public String updateuser(String username, Map<String, String> userMap) throws SMPException
Use the passed in Map to get the new first name and last name
Use the passed in username parameter to find the user within the database
Update and replace the user in the user database with the new first name and last
Throw a new SMPException if the user is not in the userDatabase along with the message (“User not found.”)
### public String login(Map<String, String> userMap) throws SMPException
Use the passed in Map parameter to get the inputted username and password
Find the user in the database by using the inputted username
If the inputted username and password match, return the user’s user name
If the user does not exist within the database or the username and password do not match, throw a new SMPException error with message “Invalid username or password.”
### public boolean resetPassword(String username, String newPassword) throws SMPException
Get the user from the database and update the user’s password with the pass in parameter
Recheck the validity of the password
Return true if successful
Throw a new SMPException with message “New Password cannot be the same as the old one” if new password is the same as the old password
### private boolean isValidPassword(String password)
Check the validity of the user’s created password
A valid password has at least 6 characters, at least one upper case letter, and at least one special character
Return true if and only if the password is valid
### private boolean isValidEmail(String email)
Check that the user’s email has both an @ symbol and a “.com”
Check that the @ and .com are in the correct places
Return true if the email is valid, otherwise return false
### private boolean isValidUsername(String username)
A valid username is not null and does not contain an @ symbol
Return if the username is valid or not
### public boolean removeFriend(User friend)
Remove the friend from the inputted user’s friends list
Return true if the friend was removed and return false on any exception
### public boolean blockUser(User userBlocked)
Add the user in the parameter to this user’s blocked list
Return true if the operation was successful, otherwise return false
### public boolean unblockUser(User userBlocker)
Remove the user in the parameter from this user’s blocked list
Return true if the operation was successful, otherwise return false
### public ArrayList<User> MutualFriends(User one, User two)
Return an array list of mutual friends between the two users that are inputted as parameters
### public String mutualFriendsToString(ArrayList<User> mutuals)
Return a string in the format that has at most 3 usernames printed out, and then the number of other mutuals
For example, if only “testuser” was a mutual, the method should return “Friends with: testuser”. If mutual friends include “user1”, “user2”, user3”, and “user4”, the method should return “Friends with: user1, user2, user3, and 1 others”



# UserManagerInteface.java
An interface for the UserMangerInterface.java class

## Methods:
User createUser(Map<String, String> userMap) throws SMPException
String updateUser(String username, Map<String, String> userMap) throws SMPException
String login(Map<String, String> userMap) throws SMPException
boolean resetPassword(String username, String newPassword) throws SMPException
boolean addFriend(User user, User friend)
boolean removeFriend(User friend)
boolean blockUser(User userBlocked)
boolean unblockUser(User userBlocked)
ArrayList<User> MutualFriends(User one, User two)
String mutualFriendsToString(ArrayList<User> mutuals);



# UserDatabase.java
## Constructors:
