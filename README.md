# Social Media Project - Phase 2 - L28
## Overall Testing
The ClientHandler/Client class integrates tightly with I/O interactions, where each method involved is closely linked with the stream operations triggered within the run() method. This design choice means that direct unit testing with JUnit is complex due to the intertwined network and I/O dependencies. Consequently, traditional unit tests may not be feasible without substantial modification to the class design to decouple these dependencies.

To address this, we have equipped the main executable with the capability to accept user inputs in real time, which facilitates practical demonstrations of functionality and interaction with the underlying database. Below are some example user inputs that can be used to test the system manually:

User System
To test the login functionality, the following mock inputs can be used:
Valid Login:
Command: 1 (select login)
Username: testuser
Password: testpassword
Invalid Login:
Command: 1 (select login)
Username: wronguser
Password: wrongpassword
User Menu System
After a successful login, the user menu system can be tested with these inputs:
Add a Friend:
Command: 1 (select add friend)
Friend's Username: frienduser
Remove a Friend:
Command: 2 (select remove friend)
Friend's Username: frienduser
Block a User:
Command: 3 (select block user)
Username: blockuser
Logout:
Command: 8 (select logout)
Comments System
To test the comments system, use these inputs after selecting a specific post:
Add a Comment:
Command for selecting a post: Assume post is already selected
Command: 3 (select add comment)
Comment Content: Great post!
View Comments:
Command for selecting a post: Assume post is already selected
Command: 4 (select view comments)
Delete a Comment:
Command for selecting a post and comment: Assume both are already selected
Command: 3 (delete comment from comment options menu)
Posts System
To test the posts system, use the following inputs:
Add a Post:
Command: 4 (select add post)
Post Content: Exciting news today!
Hide a Post:
Command: 5 (select hide post)
Post Number: Assume the post number is known, e.g., 1
View/Search Profile:
Command: 6 (view/search profile)
Username: viewuser
View Feed:
Command: 7 (select view feed)
Friends' Post Number: Assume the post number is known, e.g., 1
Example Scenario for Full Interaction:
User Logs In:
1 -> testuser -> testpassword
User Adds a Post:
4 -> Exciting news today!
User Comments on a Post:
Select the post by command or interaction -> 3 -> Great post!
User Logs Out:
8


# User.java  

The User class creates the User object that will be used in all the other database classes. The user objects will have multiple fields that make up its contents and it will be used to instantiate new users. Some of the testing that will be performed on this class include making sure that the getters and setters work as intended when updating or retrieving specific fields of this user. In relation to the other classes, the user object created within this class will be used as the user who is using the platform. Implements the UserInterface.

## Fields

private final String firstName: User's first name

private final String lastName: User's last name

private final String password: User's password

private final String username: User's username

private ArrayList<User> friendList: Array List of friends

private ArrayList<User> blockList: Array List of blocked users

private ArrayList<String> postIds: Array List of postIDs

## Constructors

### public User (String username, String firstName, String lastName, String password, String photoId, ArrayList<String> friendList,ArrayList<String> blockList)

-   Instantiate and create this user using the given parameters

## Methods

### public synchronized String getFirstName

-   Return this user's first name

### public synchronized String getLastName

-   Return this user's last name

### public synchronized String getPassword

-   Return this user's password 

### public synchronized String getUsername()

-   Return this user's username 

### public synchronized ArrayList<User> getFriendList

-   Return this user's friends 

### public synchronized ArrayList<User> getBlockList

-   Return this user's blocked list

### public synchronized ArrayList<String> getPostIds()

-   Return this user's postId

### public synchronized boolean addFriend(String checkUsername) throws SMPException

-   Add the passed in friend to this user's friend list and update the user and return true

-   Return false if the username does not exist, the two users are already friends with each other, the user is blocked by this user, or the username of this user and the added friend are the same

### public synchronized boolean blockUser(String usernameToBlock) throws SMPException 

-   Add the user passed in as parameter to this user's blocked list and return true

-   Users that are blocked cannot be a friend

-   If the user does not exist, the block list already contains the user, or the username of the parameter and this user are the same, return false

### public synchronized boolean removeFriend(String checkUsername) throws SMPException 

-   Update the friend list to remove the passed in user from this user's friend list and return true

-   If the user does not exist in the database or the two users are not friends, return false

### public synchronized boolean addPost(String content) throws SMPException

-   Add the post to this user's post list and update this user's posts

-   Return true if updated successfully, otherwise return false

### public synchronized boolean hidePost(String postId) throws SMPException 

-   Remove the passed in post from this user's post list and return true, otherwise return false

### public synchronized ArrayList<String> getFriendsPosts() 

-   Return an array list of all of the posts from the friend

### public synchronized String toString() 

-   Return a string containing the first name, last name, username, and password, separated by ; and no spaces

-   Add on all the friends in the format of "("friends");", and then all the blocked users in the format of "("blocked users");", and then finally all of the postIds of this user in the format of "("postIds")" and return the string

# UserInterface.java

An interface for the user class

## Methods

String getFirstName()

String getLastName()

String getPassword()

String getUsername()

ArrayList<String> getFriendList()

ArrayList<String> getBlockList()

ArrayList<String> getPostIds()

boolean addFriend(String username) throws SMPException

boolean blockUser(String usernameToBlock) throws SMPException

boolean removeFriend(String username) throws SMPException

boolean addPost(String content) throws SMPException

boolean hidePost(String postId) throws SMPException

ArrayList<String> getFriendsPosts()

String toString()

# UsersManager.java

The UserManager class will be responsible with user side algorithms within the application. Some of the features that are included are creating a new user and updating the information of the user. Within the entirety of the code, this class will interact mainly with the UserDatabase in order to store information about each user as well as retrieve the necessary information to perform permutations such as updating information or making sure that the new user does not already have an account. Implements the UserManagerInterface. In order to make sure that the class and each method was working by making sure that it was able to successfully read and write to a file. Furthermore, it was made sure that users can be updated, created, and searched for, returning values that were expected.

## Fields

private static final String USER_FILE = "UsersDatabase.txt": text file where the user database will be stored

public static ArrayList<User> users = new ArrayList<>(): ArrayList where this users will be stored

## Constructors

### UsersManager() throws SMPException

-   Call the method to read the user database file

## Methods

### public synchronized static ArrayList<User> getUsers()

-   Return this users

### public synchronized static void readUersDatabaseFile() throws SMPException

-   Read this users database file and store into this users

-   Throw a new SMPException with the message "Error parsing user from line: " along with the error message on any exception

### private synchronized static User stringToUser(String line) throws SMPException

-   Parse the line from the user database file and return a new user object

-   The lines will have the format of the toString method in User.java

-   Throw a new SMPException with the message ("Error parsing file.") if there is invalid data and return null

### public synchronized static void writeUsersDatabseFile()

-   Write this user arraylist to the userdatabase file

-   Each line should have the same format as before

-   On an IOException, print the error message "Error writing to users Database File: " along with the error message

### public synchronized static boolean registerUser(String firstName, String lastName, String username, String password, ArrayList<String> friendList, ArrayList<String> blockList, ArrayList<String> postIds) throws SMPException

-   Create a new user object with passed in parameters if the user does not already exist in the database and return true

-   If the username already exists, throw a new SMPException with the message "Username already exists."

### public synchronized static User loginUser(String username, String password) throws SMPException

-   Make sure that the username matches the user's password and return the user object

-   Return null if the username and password do not match

### public synchronized static boolean updateUser(User updatedUser) throws SMPException

-   Look for the user in the user database and change update the old user object to the new user object if the username's match and return true

-   If the user does not exist in the database, throw a new SMPException with the message "User not found."

### public synchronized static User searchUser(String username)

-   If the username exists in the database, return the user, otherwise return null

### public synchronized static void clearAllUsers

-   Clear all users from this users ArrayList

# UsersManagerInterface.java

An interface for the UsersMangerInterface class

## Methods:

### static void readUsersDatabseFile() throws SMPException

### static ArrayList<User> getUsers

-   returns null

### static boolean registerUser(String firstName, String lastName, String username, String password, ArrayList<String> friendList, ArrayList<String> blockList, ArrayList<String> postIds) throws SMPException 

-   Returns false

### static User loginUser(String username, String password) throws SMPException 

-   Returns null

### static boolean updateUser(User updatedUser) throws SMPException

-   Returns false

### static User searchUser(String username) 

-   Returns null

### static void clearAllUsers()

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

### public synchronized static ArrayList<Post> getPosts

-   Return this array list of posts

### public synchronized static void setPost(ArrayList<Post> posts)

-   Set this post equal to the parameter

### public synchronized void readPostsDatabseFile

-   read the posts from the database file and populate this post array list

-   Each time this method is called, the previous post array list should be cleared

-   If there is an IOException, print "Error reading Posts Database File: " and the error message

### private synchronized static Post parseLineToPost(String line)

-   Parse the parameter, a line that is taken from the database file, and create a new Post object

-   Return the new post object

-   One any exception, print out "Error parsing post from line: " along with the error message and return null

### public synchronized static void writePostsDatabaseFile

-   Write this post to the database file ("PostsDatabase.txt")

-   Overwrite any existing content that is currently in the database file

-   On IOException, print the message "Error writing to Posts Database File: " along with the error message

### public synchronized static String addPost(String creatorUsername, String content, int upvotes, int downvotes, ArrayList<String> commentIds) throws SMPException

-   Find the user that is creating the post by searching for their username in the user database

-   Create a new post object from the parameters and add the post to this post

-   Return the newly created posts Id

-   If the inputted username does not exist in the user database, throw a new SMPException with the message "Creator username does not exist."

### public synchronized  static void clearAllPosts

-   Clear every single post from this post

### public synchronized static boolean updatePost(Post updatedPost) throws SMPException

-   Find a post within this post that has the same postId as the updatedPost

-   update the old post with the updatePost and return true if successfully updated, otherwise, return false

### public synchronized static Post searchPost(String postId)

-   return the post in this post that matches the postId

-   return null if the post cannot be found

### public synchronized static Post getPostIdFromComment(Comment comment)

-   Search for comments within posts that match the CommentId as the CommentId in the given parameter

-   If the post with the specific comment can be found, return the post, otherwise return null

# PostsManagerInterface.java

An interface for the PostsManager class

## Methods

static ArrayList<Post> getPosts()

-   returns null

static void setPosts(ArrayList<Post> posts)

static void readPostsDatabaseFile()

static void writePostsDatabaseFile()

static String addPost(String creatorUsername, String content, int upvotes, int downvotes, ArrayList<String> commentIds) throws SMPException

-   returns null

static void clearAllPosts()

static boolean updatePost(Post updatedPost) throws SMPException

-   returns false

static Post searchPost(String postId)

-   returns null

static String getPostIdFromComment(Comment comment)

-   returns null

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

### public synchronized String getPostId

-   Return this postId

### public synchronized User getCreator

-   Return this creator

### public synchronized String getContent

-   Return this content

### public synchronized int getDownvotes

-   Return the number of this downvotes

### public synchronized int getUpvotes

-   Return the number of this upvotes

### public synchronized ArrayList<String> getComments

-   Return this ArrayList of comments

### public synchronized void addUpvotes() throws SMPException

-   Increase the number of this upvotes by 1 and update the number of upvotes in the post

### public synchronized void addDownvote() throws SMPException

-   Increase the number of this downvotes by 1 and update the number of downvotes in the post

### public synchronized  void addComment(String author, String content) throws SMPException

-   Create a comment in the CommentManager and generate a commentId

-   Add the commentId to this commentIds and update the post

-   If the commentId is null or empty, throw a new SMPException with the message "Could not add comment."

### public synchronized void deleteComment(String commentId, String requesterUsername) throws SMPException

-   Check that the User who created the comment is the same as the User who is deleting the comment or is the User who created the post

-   Delete the comment from the post using the commentId

-   If the comment is unable to be deleted, throw a new SMPException with the message "Failed to delete comment from post."

-   If the comment does not exist, throw a new SMPException with the message "Comment not found."

### public synchronized String toString()

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

### public synchronized String getCommentID()

-   Return this commentId

### public synchronized User getAuthor()

-   Return this author

### public synchronized String getContent()

-   Return this content

### public synchronized int getUpvotes()

-   Return this number of upvotes

### public synchronized int getDownvotes()

-   Return this number of downvotes

### public synchronized void addUpvote() throws SMPException

-   Add an upvotes to this comment

### public synchronized void addDownvote() throws SMPException

-   Add a downvote to this comment

### public synchronized String toString()

-   Return the String in the format of commentId:!:the author's username:!:content:!:upvotes:!downvotes

# CommentInterface.java

An interface for the Comment class

## Methods

String getCommentId()

User getAuthor()

String getContent()

int getUpvotes()

int getDownvotes()

void addUpvote() throws SMPException

void addDownvote() throws SMPException

String toString()

# CommentsManager.java

A class that manages the comments from and is able to read and write from the database file. In order to test the functionality, it was made sure that the database file would be updated when permutations were performed on it.

## Fields

private static final String COMMENTS_FILE = "CommentsDatabase.txt": File where comments will be stored

private static ArrayList<Comment> comments = new ArrarList<>(): An array list of comments

## Constructors

### public CommentsManager() throws SMPException

-   Read the comments database file

## Methods

### public synchronized static ArrayList<Comment> getComments

-   Return this comments

### public synchronized static void readCommentsDatabaseFile() throws SMPException

-   Read the contents from the comments database

-   If there is an IOException, throw a new SMPException with the message "Error reading Comments Database File " and the error message

### private synchronized static Comment parseLineToComment(String line) throws SMPException

-   Parse the parameter, a line that is taken from the database file, and create a new Comment object

-   Return the new comment object

-   If the author of the comment does not exist, print the error message "Author not found for comment: " and the commentId and return null

-   If the format of the comment in the database is invalid or on any exception, return null

### public synchronized static void writeCommentsDatabaseFile() throws SMPException

-   Write this comment to this database file

-   Overwrite any existing content that is currently in the database file

-   On IOException, print the message "Error writing to Comments Database File: " along with the error message

### public synchronized static String addComment(String authorUsername, String content, int upvotes, int downvotes) throws SMPException

-   If the username of the author does not exist in the user database, print out the error message "Cannot add comment. Author username not found: " along with the error message and return null

-   Using the parameters, create a new comment object and add it to this comments

-   Return the new comment's commentId

### public synchronized static boolean updateComment(Comment updatedComment) throws SMPException

-   Search for the comment in the comment database using the commentId and update the old comment with the new comment

-   Return true if the operation was successful, otherwise return false

### public synchronized static boolean deleteComment(String commentId, String requesterUsername) throws SMPException

-   Look for the comment in the comment database

-   If the User who wrote the comment matches the User in the parameter, remove the comment from this comments and return true

-   If the operations as unsuccessful, return false

### public synchronized static Comment searchComment(String commentId)

-   Return the comment from this comments with the matching commentId

-   If unable to find the comment, return null

### public synchronized static void clearAllComments

-   Clear this comments ArrayList

# CommentsManagerInterface.java

An interface for the CommentsManager class.

## Methods

### static List<Comment> getComments()

-   Returns the comments

### static void readCommentsDatabaseFile() throws SMPException

### static void writeCommentsDatabaseFile() throws SMPException

### static Comment addComment(String authorUsername, String content, int upvotes, int downvotes) throws SMPException {

-   Returns null

### static boolean updateComment(Comment updatedComment) throws SMPException

-   Returns False

### static boolean deleteComment(String commentId, String requesterUsername) throws SMPException

-   Returns false

### static Comment searchComment(String commentId)

-   Returns Null

### static void clearAllComments()

# Client.java

The client class is used as a client and has many functionalities that allow it to communicate with the server and display posts and comments. Some of the key functionalities include handling adding and removing posts and comments, logging onto the platform or registering a new user, blocking users, adding and removing friends, and communicating with the server. In order to test the functionality of this code, there were false inputs that were given to the client to ensure that the correct messages were being printed out by the client.

## Fields

private String hostName: The name of the socket

private int port: The socket number

ObjectInputStream ois: this object input stream

ObjectOutputStream oos: this object output stream

Scanner scanner: the scanner object

Socket socket: the socket object

## Global Variables

private boolean loggedIn: Whether or not the user is logged in, set to false

private boolean exit: Whether or not the user wants to exit, set to false

## Static Final Prompts

private static final String WELCOME_MESSAGE = "Welcome to MySpace!"

private static final String WELCOME_MENU = """

            Enter the number of the command you would like to execute:

            (1) - Login to your account

            (2) - Create a new account

            (3) - Exit"""

private static final String LOGIN_SUCCESS = "Login success!"

private static final String WELCOME_BACK = "Welcome back "

private static final String WELCOME_NEW = "Welcome new user "

private static final String LOGIN_FAIL = "Invalid login credentials. Please try again."

private static final String REGISTER_SUCCESS = "Account created successfully!"

private static final String REGISTER_FAIL = "Invalid credentials. Please try again."

private static final String EXIT_MESSAGE = "Thank you for using MySpace! Come back soon!"

private static final String INVALID_COMMAND = "Invalid command. Please try again."

private static final String USER_MENU = """

            Enter the number of the command you would like to execute:

            (1) - Add a friend

            (2) - Remove a friend

            (3) - Block user

            (4) - Add post

            (5) - Hide post

            (6) - View/Search profile

            (7) - View feed

            (8) - Logout"""

private static final String ADD_FRIEND = "Enter friend's username you want to add: "

private static final String ADD_FRIEND_SUCCESS = "Successfully added friend!"

private static final String ADD_FRIEND_FAIL = "Failed to add friend. Please try again."

private static final String REMOVE_FRIEND = "Enter the friend's username you want to remove: "

private static final String REMOVE_FRIEND_SUCCESS = "Successfully removed friend!"

private static final String REMOVE_FRIEND_FAIL = "Failed to remove friend. Please try again."

private static final String BLOCK_FRIEND = "Enter the username of the friend you want to block: "

private static final String BLOCK_FRIEND_SUCCESS = "Successfully blocked friend!"

public static final String BLOCK_FRIEND_FAIL = "Failed to block friend. Please try again."

private static final String ADD_POST = "Enter post contents:"

private static final String ADD_POST_SUCCESS = "Successfully added post!"

private static final String ADD_POST_FAIL = "Failed to add post. Please try again."

private static final String HIDE_POST = "Enter the number of the post you want to hide: "

private static final String HIDE_POST_SUCCESS = "Post successfully hidden!"

private static final String HIDE_POST_FAIL = "Failed to hide post. Please try again."

private static final String VIEW_PROFILE = "Enter the user of the profile you want to view:"

private static final String VIEW_PROFILE_SUCCESS = "Profile found."

private static final String VIEW_PROFILE_FAIL = "Profile not found. Please try again."

private static final String VIEW_FEED = "Enter the number of your friend's post you want to see:"

private static final String VIEW_FEED_SUCCESS = "Successfully viewed feed!"

private static final String VIEW_FEED_FAIL = "Could not view feed. Please try again."

private static final String POST_OPTIONS_MENU = """

            Enter the number of the command you would like to execute:

            (1) - Upvote post

            (2) - Downvote post

            (3) - Add comment

            (4) - View comments

            (5) - Exit"""

private static final String ADD_COMMENT = "Enter comment contents: "

private static final String ADD_COMMENT_SUCCESS = "Successfully added comment!"

private static final String ADD_COMMENT_FAIL = "Failed to add comment. Please try again."

private static final String COMMENT_OPTIONS_MENU = """

            Enter the number of the command you would like to execute:

            (1) - Upvote comment

            (2) - Downvote comment

            (3) - Delete comment

            (4) - Exit"""

private static final String VIEW_COMMENT = "Enter the number of the comment you want to see:"

private static final String VIEW_COMMENTS_SUCCESS = "Successfully viewed comments!"

private static final String VIEW_COMMENTS_FAIL = "Failed to view comments. Please try again."

private static final String DELETE_COMMENT_SUCCESS = "Successfully deleted comment."

private static final String DELETE_COMMENT_FAIL = "Failed to delete comment. Please try again."

## Constructors

### public Client (String hostname, int port) throws IOException

-   set this hostName and port to the passed in parameters

-   create a new socket with this hostName and this port

## Methods

### private void setupStreams(InputStream input, OutputStream output) throws IOException

-   Instantiate this oos and ois to new ObjectOutputStream and new ObjectInputStream, passing in the output and input parameters

### public void start() throws IOException

-   Display the WELCOME_MESSAGE 

-   While the user is not logged in, display the WELCOME_MENU, receive the command from the user, and write the command to the oos and call the handleWelcomeMenu method with the input command passed in

-   While the user is logged in, display the USER_MENU and ask the user for a command. Write the command to the oos and call the handleUserMenu to deal with the command

-   Handle cases where the user wants to exit

-   Print out the EXIT_MESSAGE and close the socket on any IOException, ClassNotFoundException, or SMPException

### private void handleWelcomeMenu(String command) throws IOException, ClassNotFoundException, SMPException

-   If the user wants to login, validate the user's credentials and if the credentials are correct, set loggedIn to true

-   If the user wants to register an account, create a new user in the system by calling the handleRegistration method, and after creation, set loggedIn to true

-   If the user wants to exit, display the ois to the user and exit

### private void handleUserMenu(String command) throws IOException,

ClassNotFoundException, SMPException

-   Call the respective methods corresponding to the passed in command. For example, if the user picks command one, call the handleAddFriend() method

-   If the user wants to logout, display the EXIT_MESSAGE and set this loggedIn to false

### public User handleLogin()

-   Get the username and password from the user and write it to the object out stream

-   Receive whether or not the user loggedin successfully from the object input stream

-   If logged in successfully, print out LOGIN_SUCCESS, the WELCOME_BACK message along with the user's first name and last name, and return the user object

-   If logged in unsuccessfully, display the LOGIN_FAIL message to the user and return null

### public User handleRegistration()

-   Receive a first name, last name, username, and password from the user and write them to the object output stream

-   If the registration was successful, print out REGISTER_SUCCESS to the user along with WELCOME_NEW and the new user's first and last name and return the user object

-   If registration failed, display the REGISTER_FAIL message and return null

### public void handleAddFriend() throws SMPException, IOException, ClassNotFoundException

-   Display the ADD_FRIEND message to the user and get the friends username from the user

-   Write the friend's username to the object output stream and receive a response from the object input stream

-   If adding the friend was successful, print out the ADD_FRIEND_SUCCESS message, otherwise print out the ADD_FRIEND_FAIL message

### public void handleRemoveFriend() throws SMPException, IOException, ClassNotFoundException

-   Display the REMOVE_FRIEND message and get the username of the friend to be removed from the user and write it to the object output stream

-   Receive a successful or not message from the server and if successful, display the REMOVE_FRIEND_SUCCESS message, otherwise display the REMOVE_FRIEND_FAIL message

### private void handleBlockFriend(ObjectOutputStream oos, ObjectInputStream ois, Scanner scanner) throws IOException, ClassNotFoundException

-   Display the BLOCK_FRIEND message to the user and get the username of the user to block and write it to the object output stream

-   Receive a response of success or failure from the object input stream and if successful, display the BLOCK_FRIEND_SUCCESS message, otherwise display the BLOCK_FRIEND_FAIL message

### private void handleAddPost(ObjectOutputStream oos, ObjectInputStream ois, Scanner scanner) throws IOException, ClassNotFoundException

-   Display the ADD_POST message and receive what the user would like to post from the user. Write this content to the object output stream 

-   Receive response from server about whether or not adding of the post was successful

-   Print out ADD_POST_SUCCESS message on success and ADD_POST_FAIL failure

### private boolean displayPosts(ObjectOutputStream oos, ObjectInputStream ois, Scanner scanner) throws IOException

-   Write "getPosts" to the object output stream to start a request to get the posts

-   Read responses from the object input stream until it reaches "end"

-   If there are posts available, display the message "Viewing your posts:" along with each post until there are none available

-   Print out error message and return false on any IOException or ClassNotFoundException

-   Return true if all posts are displayed

### private void handleHidePost(ObjectOutputStream oos, ObjectInputStream ois, Scanner scanner) throws IOException, ClassNotFoundException, SMPException

-   Display the HIDE_POST message and receive the number of the post that the user wants to hide

-   Write "hidePost" to the object outpost stream to start the process and then write the post number

-   If the post was hidden successfully, print out the HIDE_POST_SUCCESS message, otherwise display the HIDE_POST_FAIL message

-   print the stack trace on any IO related exception

-   If the post cannot be found, display the HIDE_POST_FAIL message

### private void handleViewSearchProfile(ObjectOutputStream oos, ObjectInputStream ois, Scanner scanner) throws IOException, ClassNotFoundException

-   Receive the username of the user that is to be viewed and write it to the object output stream

-   Print out the VIEW_PROFILE_SUCCESS message, the first and last name of the user, and the username if successful, otherwise display the VIEW_PROFILE_FAIL message

### private boolean displayFeed(ObjectOutputStream oos, ObjectInputStream ois, Scanner scanner) throws IOException 

-   Write "getFriendsPosts" to the server to initiate the process

-   If there are no posts, return false, otherwise display the posts of the friends and return true if all posts are displayed

-   On any IOException or ClassNotFound exception, print the error message and return false

### private void handleViewFeed(ObjectOutputStream oos, ObjectInputStream ois, Scanner scanner) throws IOException, ClassNotFoundException

-   Check that the feed can be read and that there are posts present in the feed

-   Ask the user how many posts they want to see and write "viewFeed" to the server to initiate the process and also write the number of posts

-   If the process was a success, display the number of posts, otherwise display the message VIEW_FEED_FAIL

-   If there was an error getting posts from the feed or there are no posts in the feed, display the VIEW_FEED_FAIL message

### private void handlePostOptions(ObjectOutputStream oos, ObjectInputStream ois, Scanner scanner) throws IOException, ClassNotFoundException

-   Display the post options menu and receive the command that the user wants to perform

-   If user wishes to upvote the post, write "upvotePost" to the object output stream and display that the post was successfully upvoted to the user

-   If the user wishes to downvote the post, write "downvotePost" to the oos and display that the post was successfully downvoted to the user

-   If the user wishes to add a comment, call the handleAddComment method

-   If the user wishes to view the comments on the post, call the handelViewComments method

-   If the user wishes to exit, exit

### private void handleAddComment(ObjectOutputStream oos, ObjectInputStream ois, Scanner scanner) throws IOException, ClassNotFoundException

-   Write "addComment" to the server to start the process and receive what comment should be added from the user

-   Check that the comment was added successfully from the server and if successful, display the ADD_COMMENT_SUCCESS message to the user, otherwise display the ADD_COMMENT_FAILL message to the user

### private boolean displayComments(ObjectOutputStream oos, ObjectInputStream ois) throws IOException 

-   Write "getPostsComments" to the object output stream to start a request to get the posts

-   Read responses from the object input stream until it reaches "end"

-   If there are posts available, display the message "Viewing this post's comments:" along with each post until there are none available

-   Print out error message and return false on any IOException or ClassNotFoundException

-   Return true if all posts are displayed

### private void handleViewComments(ObjectOutputStream oos, ObjectInputStream ois, Scanner scanner) throws IOException, ClassNotFoundException

-   Check that there are comments on the post and that the comments can be read successfully from the server

-   Write "viewComments" to the server to start the process and ask the user what post number they want to see the comments on and write that to the server

-   if the comments can be viewed, call the handleCommentOptions method, otherwise display the VIEW_COMMENTS_FAIL message

### private void handleCommentOptions(ObjectOutputStream oos, ObjectInputStream ois, Scanner scanner) throws IOException, ClassNotFoundException

-   Display the comment options menu and receive the command that the user wants to perform

-   If user wishes to upvote the comment, write "upvoteComment" to the object output stream and display that the comment was successfully upvoted to the user

-   If the user wishes to downvote the post, write "downvoteComment" to the oos and display that the comment was successfully downvoted to the user

-   If the user wishes to delete a comment, call the handleDeleteComment method

-   If the user wishes to exit, exit

### private void handleDeleteComment(ObjectOutputStream oos, ObjectInputStream ois) throws IOException, ClassNotFoundException

-   Write "deleteComment" to the server to begin the process and receive a response from the server of whether or not the process was successful

-   If successful, display the DELETE_COMMENT_SUCCESS message, otherwise display the DELETE_COMMENT_FAIL message

# ClientInterface.java

An interface for the client class

## Methods

void start() throws IOException

void handleWelcomeMenu(String command) throws IOException, ClassNotFoundException, SMPException

void handleUserMenu(String command) throws IOException, ClassNotFoundException, SMPException

User handleLogin() throws IOException, ClassNotFoundException, SMPException

User handleRegistration() throws IOException, ClassNotFoundException, SMPException

void handleAddFriend() throws IOException, ClassNotFoundException

void handleRemoveFriend() throws IOException, ClassNotFoundException

void handleAddPost() throws IOException, ClassNotFoundException

boolean displayPosts() throws IOException

void handleHidePost() throws IOException, ClassNotFoundException

void handleViewSearchProfile() throws IOException, ClassNotFoundException

boolean displayFeed() throws IOException

void handleViewFeed() throws IOException, ClassNotFoundException

void handlePostOptions() throws IOException, ClassNotFoundException

void handleAddComment () throws IOException, ClassNotFoundException

boolean displayComments() throws IOException

void handleViewComments() throws IOException, ClassNotFoundException

void handleCommentOptions() throws IOException, ClassNotFoundException

void handleDeleteComment() throws IOException, ClassNotFoundException

# ClientHandler.java

Server side of the social media platform. Handles all the main operations based on commands that are passed in from the Client class. Some of the main functionality includes adding friends to the user, removing friends from the user, adding users to the blocked list, adding posts to the user's posts list, and adding comments to the selected post's post list. In order to make sure that all functionality was working, false commands and methods were insured into the parameters and made sure that friends lists, blocked lists, etc. were being updated on each go if there was a need to update them.

## Fields

private Socket clientSocket

private ObjectOutputStream oos

private ObjectInputStream ois;

## Global Variables

private boolean loggedIn = false

User currentUser

ArrayList<Post> usersPosts = new ArrayList<>()

ArrayList<Post> allFriendsPosts = new ArrayList<>()

Post chosenPost

Comment chosenComment

ArrayList<Comment> postsComments = new ArrayList<>();

## Static Final Prompts

public static final String LOGIN_SUCCESS = "Login success!"

public static final String LOGIN_FAIL = "Invalid login credentials. Please try again."

public static final String REGISTER_SUCCESS = "Account created successfully!"

public static final String REGISTER_FAIL = "Invalid credentials. Please try again."

private static final String EXIT_MESSAGE = "Thank you for using MySpace! Come back soon!"

private static final String INVALID_COMMAND = "Invalid command. Please try again."

private static final String ADD_FRIEND_SUCCESS = "Successfully added friend!"

private static final String ADD_FRIEND_FAIL = "Failed to add friend. Please try again."

private static final String REMOVE_FRIEND_SUCESS = "Successfully removed friend!"

private static final String REMOVE_FRIEND_FAIL = "Failed to remove friend. Please try again."

private static final String BLOCK_FRIEND_SUCCESS = "Successfully blocked friend!"

private static final String BLOCK_FRIEND_FAIL = "Failed to block friend. Please try again."

private static final String ADD_POST_SUCCESS = "Successfully added post!"

private static final String ADD_POST_FAIL = "Failed to add post. Please try again."

private static final String HIDE_POST_SUCCESS = "Post successfully hidden!"

private static final String HIDE_POST_FAIL = "Failed to hide post. Please try again."

private static final String VIEW_PROFILE_SUCCESS = "Profile found."

private static final String VIEW_PROFILE_FAIL = "Profile not found. Please try again."

private static final String VIEW_FEED_SUCCESS = "Successfully viewed feed!"

private static final String VIEW_FEED_FAIL = "Could not view feed. Please try again."

private static final String VIEW_COMMENTS_SUCCESS = "Successfully viewed comments!"

private static final String VIEW_COMMENTS_FAIL = "Failed to view comments. Please try again."

private static final String ADD_COMMENT_SUCCESS = "Successfully added comment!"

private static final String ADD_COMMENT_FAIL = "Failed to add comment. Please try again."

private static final String DELETE_COMMENT_SUCCESS = "Successfully deleted comment."

private static final String DELETE_COMMENT_FAIL = "Failed to delete comment. Please try again."

## Constructors

### public ClientHandle(Socket socket) throws IOException

-   set this clientSocket to the passed in parameter

-   Instantiate this ois and this oos to new object input and output streams using this clientSocket

## Methods

### public void run()

-   while the user is not logged in, get the command from the object input stream and process the command

-   while the user is logged in, get the command from the object input stream and process the LoginCommand

-   Print out the error message along with the EXIT_MESSAGE on any IOException, ClassNotFoundException, or SMPException

### public void processCommand(String command) throws SMPException, IOException, ClassNotFoundException 

-   Read the databases and call the respective method based on the passed in command

-   1: processLogin()

-   2: processRegister()

-   3: sendExitMessage()

### public void processLogin() throws IOException, ClassNotFoundException, SMPException

-   Get the username and the password from the client

-   Get the user object based on the username and password from the UsersManager class

-   If the username and password match was found, write LOGIN_SUCCESS and the user to the client, otherwise write LOGIN_FAIL

-   Set this currentUser to the found user and this loggedIn to true

### public void processRegister() throws IOException, ClassNotFoundException, SMPException

-   Get the first name, last name, username, and password from the client and register the user using those parameters from the method that is in the UsersManager class

-   If the user was created, write REGISTER_SUCCESS and the user to the object output stream and set this loggedIn to true and this currentUser to the created user

-   If the user could not be created, write REGISTER_FAIL to the object output stream

### public void sendExitMessage() throws IOException, SMPException

-   Write the EXIT_MESSAGE to the object output stream and send it to the client

### public void sendInvalidMessage() throws IOException, SMPException

-   Write the INVALID_COMMAND to the object output stream and send it to the client

### public void processLoginCommand(String command) throws SMPException, IOException, ClassNotFoundException

-   Call the correct method based on the given command from the parameter

### public void processAddFriend() throws SMPException, IOException, ClassNotFoundException 

-   get the friends username from the client and if the friend can be added to this currentUser's friend list, send the ADD_FRIEND_SUCCESS message to the client, otherwise send the ADD_FRIEND_FAIL message to the client

### private void processRemoveFriend() throws SMPException, IOException, ClassNotFoundException

-   Get the username from the client and remove the friend from this currentUser's friend list

-   If the friend was successfully removed, send the REMOVE_FRIEND_SUCCESS message to the client, otherwise send the REMOVE_FRIEND_FAIL message to the client

### private void processBlockFriend() throws SMPException, IOException, ClassNotFoundException 

-   get the username that is being blocked from the client and add it to this currentUser's blocked list

-   If the user was successfully blocked, send the BLOCK_FRIEND_SUCCESS message to the client, otherwise send the BLOCK_FRIEND_FAIL message to the client

### private void processAddPost() throws SMPException, IOException, ClassNotFoundException

-   Get the content that is being added from the client and add the post to this currentUser

-   If the post was added successfully, send the message ADD_POST_SUCCESS to the client, otherwise send the ADD_POST_FAIL message to the client

### private void processGetPosts() throws SMPException, IOException

-   Get all the posts from this user and write it to the output object stream and send it to the client

-   After all posts have been read, write "end" to the client

### private void processHidePost() throws SMPException, IOException, ClassNotFoundException

-   Get the post number from the client and get the post from this user corresponding to the post number

-   Hide the selected post and if successfully hidden, send the HIDE_POST_SUCCESS message to the client, otherwise send the HIDE_POST_FAIL message to the client

-   Send the HIDE_POST_FAIL message on any NumberFormatException

### private void processViewSearchUser() throws IOException, SMPException, ClassNotFoundException

-   get the username of the user being searched from the client

-   Find the user from the user database and send the VIEW_PROFILE_SUCCESS message, the first and last name of the user, and the username of the user to the client

-   If the user is not found, send the VIEW_PROFILE_FAIL message to the client

### private void processViewFeed() throws SMPException, IOException, ClassNotFoundException

-   Get the post that is to be viewed from the friends posts

-   If the post is found, send the message VIEW_FEED_SUCCESS to the client, otherwise send the VIEW_PROFILE_FAIL message to the client

### private Post getPostFromChoice(ArrayList<Post> posts, int postNumber)

-   Return the post based on the post number

### private void processLogout() throws SMPException

-   Read and write all databases

### private void processGetFriendsPosts() throws SMPException, IOException

-   Get all the posts from this user's friends and send it to the client, and at the end, send "end" to the client

### private void processAddUpvote() throws SMPException

-   Add a upvote to the chosen post

### private void processDownvotePost() throws SMPException

-   Add a downvote to the chosen post

### private void processAddComment() throws SMPException, IOException, ClassNotFoundException

-   Get the comment that is to be added from the client and add it to the currentUser's post

-   If the comment was added successfully, send the ADD_COMMENT_SUCCESS message to the client, otherwise send the ADD_COMMENT_FAIL message to the client

### private void processGetPostsComments() throws SMPException, IOException

-   Get all the comments from the chosen post and write all the comments to the client and end it with "end"

### private void processViewComments() throws IOException, SMPException, ClassNotFoundException

-   Get the comment number that is to be viewed from the client

-   If the comment can be viewed and exists, send the VIEW_COMMENTS_SUCCESS message to the client, otherwise send the VIEW_COMMENTS_FAIL message to the client

-   Send the VIEW_COMMENTS_FAIL message to the client on any NumberFormatException or IOException

### private Comment getCommentFromChoice(ArrayList<Comment> comments, int commentNumber) 

-   Find the comment that matches the commentNumber and return that comment

### private void processUpvoteComment() throws SMPException

-   Add an upvote to the chosen comment

### private void processDownvoteComment() throws SMPException

-   Add a downvote to the chosen comment

### private void processDeleteComment() throws SMPException, IOException

-   Look for the chosen comment on the chosen post and delta the comment from the post

-   If successful, send the DELETE_COMMENT_SUCCESS message to the client, otherwise send the DELETE_COMMENT_FAIL message to the client

### private void readAllDatabases() throws SMPException

-   Read the database files of the users, the posts, and the comments

### private void writeAllDatabases() throws SMPException

-   Write to the database of the users, the posts, and the comments

# ClientHandlerInterface.java

An interface for the client handler class

## Methods

void run()

void processCommand(String command) throws SMPException, IOException, ClassNotFoundException

void processLogin() throws IOException, ClassNotFoundException, SMPException

void processRegister() throws IOException, ClassNotFoundException, SMPException

void sendExitMessage() throws IOException, SMPException

void sendInvalidMessage() throws IOException, SMPException

void processLoginCommand(String command) throws SMPException, IOException, ClassNotFoundException

void processAddFriend() throws SMPException, IOException, ClassNotFoundException

void processRemoveFriend() throws SMPException, IOException, ClassNotFoundException

void processBlockFriend() throws SMPException, IOException, ClassNotFoundException

void processAddPost() throws SMPException, IOException, ClassNotFoundException

void processGetPosts() throws SMPException, IOException

void processHidePost() throws SMPException, IOException, ClassNotFoundException

void processViewSearchUser() throws IOException, SMPException, ClassNotFoundException

void processViewFeed() throws SMPException, IOException, ClassNotFoundException

Post getPostFromChoice(ArrayList<Post> posts, int postNumber)

void processLogout() throws SMPException

void processGetFriendsPosts() throws SMPException, IOException

void processAddUpvote() throws SMPException

void processDownvotePost() throws SMPException

void processAddComment() throws SMPException, IOException, ClassNotFoundException

void processGetPostsComments() throws SMPException, IOException

void processViewComments() throws IOException, SMPException, ClassNotFoundException

Comment getCommentFromChoice(ArrayList<Comment> comments, int commentNumber)

void processUpvoteComment() throws SMPException

void processDownvoteComment() throws SMPException

void processDeleteComment() throws SMPException, IOException

void readAllDatabases() throws SMPException

void writeAllDatabases() throws SMPException

# Server.java

A server class for the client. In order to test the functionality, a server number was passed in to make sure that the client could connect to the server and vice versa.

## Fields

private int port: the port number for the connection

## Constructors

### public Server (int port)

-   Instantiate the port number to the passed in parameter

## Methods

### public void start()

-   Connect the server and the client together

-   Keep the server open and create a new Socket clientSocket that accepts the server socket

-   Pass the clientSocket to create a new clientHandler and start the clientHandler on a new thread

# ServerInterface.java

An interface for the server class

## Methods

### void start()
