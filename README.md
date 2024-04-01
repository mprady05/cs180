# User.java  

The User class creates the User object that will be used in all the other database classes. The user objects will have multiple fields that make up its contents and it will be used to instantiate new users. Some of the testing that will be performed on this class include making sure that the getters and setters work as intended when updating or retrieving specific fields of this user. In relation to the other classes, the user object created within this class will be used as the user who are using the platform. Implements the UserInterface.java

## Fields: 

private String firstName: User's first name

private String lastName: User's last name

private String password: User's password

private String photoId: User's profile picture file

private ArrayList<User> friendList: Array List of friends

private ArrayList<User> blockList: Array List of blocked users

private String username: Unique Identifier for the user

## Constructors:

### public User (String username, String firstName, String lastName, String password, String photoId, ArrayList<String> friendList,ArrayList<String> blockList)

-   Instantiate and create this user using the given parameters

## Methods:

### public String getFirstName

-   Return this user's first name

### public void setFirstName(String firstName)

-   Set this user's first name to the parameter 

### public String getLastName

-   Return this user's last name

### public void setLastName(String lastName)

-   Set this user's last name to the parameter  

### public String getPassword

-   Return this user's password 

### public void setPassword(String password)

-   Set this user's password to the given parameter 

### public String getUsername()

-   Return this user's username 

### public void setUsername(String username)

-   Set this user's username to the given parameter 

### public String getPhotoId

-   Return this user's photo Id 

### public void setPhotoId(String photoId)

-   Set this user's photo Id to the parameter 

### public ArrayList<User> getFriendList

-   Return this user's friends 

### public void setFriendList(ArrayList<User> friendList)

-   Set this user's friends list to the parameter 

### public ArrayList<User> getBlockList

-   Return this user's blocked list 

### public void setUserList(ArrayList<User> blockList)

-   Set this user's blocked list to the parameter

# UserInterface.java

An interface for the user class

## Methods

String getFirstName()

void setFirstName(String firstName)

String getLastName()

void setLastName(String lastName)

String getPassword()

void setPassword(String password)

String getPhotoId()

void setPhotoId(String photoId)

String getUsername()

void setUsername(String username)

ArrayList<User> getFriendList()

ArrayList<User> getBlockList()

void setFriendList(ArrayList<User> friendList)

void setBlockList(ArrayList<User> blockList)

# UserManager.java

The UserManager.java class will be responsible with user side algorithms within the application. Some of the features that are included are creating a new user, updating the information of the user, authenticating the login through a password, resetting the user's passwords, as well as managing who the user will be blocking or adding as friends. Within the entirety of the code, this class will interact mainly with the UserDatabase in order to store information about each user as well as retrieve the necessary information to perform permutations such as updating information or making sure that the new user does not already have an account. Implements the UserManagerInterface.java

## Fields:

Private UserDatabase userDB: The user database, set to null

## Constructors:

### Public UserManager(UserDatabase userDB)

-   Instantiate this userDB to the passed in argument

## Methods:

### public User createUser(Map<String, String> userMap) throws SMPException

-   This method will be used to create the user object. 

-   Assign the user with a firstName, lastName, password, and a userName

-   Get the user object from the userdatabase using the user's unique username

-   If the specified username does not exist within the database, create a new user object and set the first name, last name, username, and password and save the user within the database

-   Return the user

-   Throw a new SMP Exception if:

-   The passed in firstName, lastName, password, or userName are null and provide the message "Missing required user information."

-   The password is invalid, throw a new message that "Invalid Username format."

-   The username is already present in the database, throw a new message that "Usernmane already exists."

### public String updateuser(String username, Map<String, String> userMap) throws SMPException

-   Use the passed in Map to get the new first name and last name

-   Use the passed in username parameter to find the user within the database

-   Update and replace the user in the user database with the new first name and last

-   Throw a new SMPException if the user is not in the userDatabase along with the message ("User not found.")

### public String login(Map<String, String> userMap) throws SMPException

-   Use the passed in Map parameter to get the inputted username and password

-   Find the user in the database by using the inputted username

-   If the inputted username and password match, return the user's user name

-   If the user does not exist within the database or the username and password do not match, throw a new SMPException error with message "Invalid username or password."

### public boolean resetPassword(String username, String newPassword) throws SMPException

-   Get the user from the database and update the user's password with the pass in parameter

-   Recheck the validity of the password

-   Return true if successful

-   Throw a new SMPException with message "New Password cannot be the same as the old one" if new password is the same as the old password

### private boolean isValidPassword(String password)

-   Check the validity of the user's created password

-   A valid password has at least 6 characters, at least one upper case letter, and at least one special character

-   Return true if and only if the password is valid

### private boolean isValidEmail(String email)

-   Check that the user's email has both an @ symbol and a ".com"

-   Check that the @ and .com are in the correct places

-   Return true if the email is valid, otherwise return false

### private boolean isValidUsername(String username)

-   A valid username is not null and does not contain an @ symbol

-   Return if the username is valid or not

### public boolean removeFriend(User friend)

-   Remove the friend from the inputted user's friends list

-   Return true if the friend was removed and return false on any exception

### public boolean blockUser(User userBlocked)

-   Add the user in the parameter to this user's blocked list

-   Return true if the operation was successful, otherwise return false

### public boolean unblockUser(User userBlocker)

-   Remove the user in the parameter from this user's blocked list

-   Return true if the operation was successful, otherwise return false

### public ArrayList<User> MutualFriends(User one, User two)

-   Return an array list of mutual friends between the two users that are inputted as parameters

### public String mutualFriendsToString(ArrayList<User> mutuals)

-   Return a string in the format that has at most 3 usernames printed out, and then the number of other mutuals

-   For example, if only "testuser" was a mutual, the method should return "Friends with: testuser". If mutual friends include "user1", "user2", user3", and "user4", the method should return "Friends with: user1, user2, user3, and 1 others"

# UserManagerInterface.java

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

String mutualFriendsToString(ArrayList<User> mutuals)

# UserDatabase.java

Implements the UserDatabaseInterface.java. The UserDatabase class is used to store user objects within a hashmap. Some of its functionality include saving users, finding users by their usernames, and deleting users. This class is connected with other classes by allowing other classes, such as UserManager, to retrieve user objects from the database by searching for them through their usernames.

## Fields

private static HasMap<String,user> userMap = new HashMap<String,User>()

## Constructors:

### public UserDatabase

-   call the readUsersfromFile method

## Methods

### public HashMap<String, User> getUserMap

-   Return this userMap

### public void setUserMap(<HashMap<String, user> userMap)

-   Set this userMap to the parameter

### public boolean saveUser(String username, User user)

-   Save the username and user associated to the userMap

-   return true

### public static user getUserByUsername(String username)

-   Returns the user from the userMap database based on the username parameter

-   return null if the user cannot be found

### public boolean deleteUser(String username)

-   Find the username in the userMap and remove the the associated user 

-   Update the output file with the user deleted

-   return true if the username exists, and false if it doesn't

### private void readUsersFromFile

-   Read from the "User.db" file and populate the userMap with the users from the file

-   Print the stack trace for any IOExceptions

# UserDatabaseInterface.java

An interface for the UserDatabase class

## Methods

boolean saveUser(String username, User user)

boolean deleteUser(String id)

HashMap<String, User> getUserMap()

void setUserMap(HashMap<String, User> userMap)

# SMPException.java

Exception to be thrown when there is bad data/inputs

## Constructors

### SMPException(String message)

-   Calls the constructor of the exception superclass with the message passed in as the parameter

# PostsManager.java

The PostsManager class implements the PostManageriInterface. The purpose of this class is to provide a central place where posts by users can be managed. Some of the primary functionalities include being able to store posts into a file and write to the file any time there is a new post or an old post is modified. Furthermore, old posts and comments can be found using their post and comment id's. Some of the relevant tests that were performed on this class included making up fake posts that operations can be performed upon. For example, fake parameters were passed into some of the methods to make sure that new posts were created, a file was being written to, or this posts ArrayList was being cleared. Essentially, fake posts were created to make sure that each method was outputting what was expected. This class will work with the Post class in order to make a side of the social media application where news posts can be made by users.

## Fields

private static final String POST_FILE = "PostsDatabase.txt": Use local file path to get the posts database

private static ArrayList<Post> posts = new ArrayList<>(): An array list to store the posts

## Constructors

### public postsManager()

-   Read the posts database file

## Methods

### public static ArrayList<Post> getPosts

-   Return this array list of posts

### public static void setPost(ArrayList<Post> posts)

-   Set this post equal to the parameter

### public void readPostsDatabseFile

-   read the posts from the database file and populate this post array list

-   Each time this method is called, the previous post array list should be cleared

-   If there is an IOException, print "Error reading Posts Database File: " and the error message

### private Post parseLineToPost(String line)

-   Parse the parameter, a line that is taken from the database file, and create a new Post object

-   Return the new post object

-   One any exception, print out "Error parsing post from line: " along with the error message and return null

### public static void writePostsDatabaseFile

-   Write this post to the database file ("PostsDatabase.txt")

-   Overwrite any existing content that is currently in the database file

-   On IOException, print the message "Error writing to Posts Database File: " along with the error message

### public static String addPost(String creatorUsername, String content, int upvotes, int downvotes, ArrayList<String> commentIds) throws SMPException

-   Find the user that is creating the post by searching for their username in the user database

-   Create a new post object from the parameters and add the post to this post

-   Return the newly created posts Id

-   If the inputted username does not exist in the user database, throw a new SMPException with the message "Creator username does not exist."

### public static void clearAllPosts

-   Clear every single post from this post

### public static boolean updatePost(Post updatedPost) throws SMPException

-   Find a post within this post that has the same postId as the updatedPost

-   update the old post with the updatePost and return true if successfully updated, otherwise, return false

### public static Post searchPost(String postId)

-   return the post in this post that matches the postId

-   return null if the post cannot be found

### public static String getPostIdFromComment(Comment comment)

-   Search for comments within posts that match the CommentId as the CommentId in the given parameter

-   If the post with the specific comment can be found, return the post's PostId, otherwise return null

# PostsManagerInterface.java

An interface for the PostsManager class

## Methods

ArrayList<Post> getPosts()

void setPosts(ArrayList<Post> posts)

void readPostsDatabaseFile()

void writePostsDatabaseFile()

String addPost(String creatorUsername, String content, int upvotes, int downvotes, ArrayList<String> commentIds) throws SMPException

void clearAllPosts()

boolean updatePost(Post updatedPost) throws SMPException

Post searchPost(String postId)

String getPostIdFromComment(Comment comment)

# Post.java

This class will implement that PostInterface. The main functionality is to create a post that the User wishes to create. Some other important functionality include being able to add and delete comments as well as alter the number of upvotes and downvotes on this post. In order to make sure all of the code works, fake parameters were passed into the methods to make sure that what was being returned was what was expected. Furthermore, after making the new posts, we made sure that the content, upvotes, downvotes, comments list, post id, and creator all matched with the fake post object that was created. Within the grand scheme of things, this class will be called upon whenever a user decides to create a new post, alter the post, control comment flow, and delete comments/

## Fields

private final String postId: A agenerated string for an id of the post

private final User creator: The user who created the post

private final String content: What is included inside of the post

private int upvotes: The number of upvotes on the post

private int downvotes: The number of downvotes on the post

private ArrayList<String> commendIds: The id's of the comments on the post

## Constructors

### public Post(User creator, String content, int upvotes, int downvotes, ArrayList<String> comment Ids)

-   Instantiate this postId to a random UUID

-   Instantiate all other fields to the parameters that are passed in

### public Post(String postId, User creator, String content, int upvotes, int downvotes, ArrayList<String> commentIds)

-   A constructor that will create a post with an existing Id

-   Instantiate the fields to the parameters

## Methods

### public String getPostId

-   Return this postId

### public User getCreator

-   Return this creator

### public String getContent

-   Return this content

### public int getDownvotes

-   Return the number of this downvotes

### public int getUpdates

-   Return the number of this upvotes

### public ArrayList<String> getComments

-   Return this ArrayList of comments

### public void addUpvotes() throws SMPException

-   Increase the number of this upvotes by 1 and update the number of upvotes in the post

### public void addDownvote() throws SMPException

-   Increase the number of this downvotes by 1 and update the number of downvotes in the post

### public void addComment(String author, String content) throws SMPException

-   Create a comment in the CommentManager and generate a commentId

-   Add the commentId to this commentIds and update the post

-   If the commentId is null or empty, throw a new SMPException with the message "Could not add comment."

### public void deleteComment(String commentId, String requesterUsername) throws SMPException

-   Check that the User who created the comment is the same as the User who is deleting the comment or is the User who created the post

-   Delete the comment from the post using the commentId

-   If the comment is unable to be deleted, throw a new SMPException with the message "Failed to delete comment from post."

-   If the comment does not exist, throw a new SMPException with the message "Comment not found."

### public String toString()

-   Return a string in the format of postId:~:content:~:upvotes:~:downvotes[commentIds]

-   Each comment Id should be separated with a :~:

# PostInterface.java

An interface for the Post class.

## Methods

String getPostId()

User getCreator()

String getContent()

int getUpvotes()

int getDownvotes()

ArrayList<String> getComments()

void addUpvote() throws SMPException

void addDownvote() throws SMPException

void addComment(User author, String content) throws SMPException

void deleteComment(String commentId, String requesterUsername) throws SMPException

String toString()

# Comment.java

This class will implement the CommentInterface. The main purpose of this class is to create a comment object with the fields of the commentId, the author of the comment, the contents of the comment, and the number of upvotes and downvotes. It will interact with the Posts and other comments classes to create a seamless side of the application where users will be able to comment on other user's posts as well as like or dislike the comments of other users. Some of the testing that was performed on this class was passing in fake parameters to create a comment object and making sure that the constructors worked by testing it with expected outputs using the getters as well as making sure the toString is formatted correctly.

## Fields

private String commentId: The identification number of this comment

private User author: The user who posted this comment

private String content: The content of this comment

private int upvotes: The number of upvotes on this comment

private int downvotes: The number of downvotes on this comment

## Constructors

### public Comment(User author, String content, int upvotes, int downvotes)

-   Instantiate the commentId to a random UUID

-   Instantiate the other fields to the parameters that are passed in

## public Comment(String commentId, User author, String content, int upvotes, int downvotes)

-   Instantiate the fields to the parameters that are passed in

## Methods

### public String getCommentID()

-   Return this commentId

### public User getAuthor()

-   Return this author

### public String getContent()

-   Return this content

### public int getUpvotes()

-   Return this number of upvotes

### public int getDownvotes()

-   Return this number of downvotes

### public String toString()

-   Return the String in the format of commentId:~:the author's username:~:content:~:upvotes:~:downvotes

# CommentInterface.java

An interface for the Comment class/

## Methods

String getCommentId()

User getAuthor()

String getContent()

int getUpvotes()

int getDownvotes()

String toString()

# CommentsManager.java

## Fields

## Constructors

## Methods
