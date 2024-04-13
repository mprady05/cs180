# User.java  

The User class creates the User object that will be used in all the other database classes. The user objects will have multiple fields that make up its contents and it will be used to instantiate new users. Some of the testing that will be performed on this class include making sure that the getters and setters work as intended when updating or retrieving specific fields of this user. In relation to the other classes, the user object created within this class will be used as the user who is using the platform. Implements the UserInterface.

## Fields

private String firstName: User's first name

private String lastName: User's last name

private String password: User's password

private String photoId: User's profile picture file

private ArrayList<User> friendList: Array List of friends

private ArrayList<User> blockList: Array List of blocked users

private String username: Unique Identifier for the user

## Constructors

### public User (String username, String firstName, String lastName, String password, String photoId, ArrayList<String> friendList,ArrayList<String> blockList)

-   Instantiate and create this user using the given parameters

## Methods

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

# UsersManager.java

The UserManager class will be responsible with user side algorithms within the application. Some of the features that are included are creating a new user and updating the information of the user. Within the entirety of the code, this class will interact mainly with the UserDatabase in order to store information about each user as well as retrieve the necessary information to perform permutations such as updating information or making sure that the new user does not already have an account. Implements the UserManagerInterface. In order to make sure that the class and each method was working by making sure that it was able to successfully read and write to a file. Furthermore, it was made sure that users can be updated, created, and searched for, returning values that were expected.

## Fields

private static final String USER_FILE = "UsersDatabase.txt": text file where the user database will be stored

public static ArrayList<User> users = new ArrayList<>(): ArrayList where this users will be stored

## Constructors

### UsersManager() throws SMPException

-   Call the method to read the user database file

## Methods

### public static ArrayList<User> getUsers()

-   Return this users

### public static void readUersDatabaseFile() throws SMPException

-   Read this users database file and store into this users

-   Throw a new SMPException with the message "Error parsing user from line: " along with the error message on any exception

### private static User stringToUser(String line) throws SMPException

-   Parse the line from the user database file and return a new user object

-   The lines will have the format "firstname;lastname;username;password;friend list"

-   Throw a new SMPException with the message ("Error parsing file.") if there is invalid data

### public static void writeUsersDatabseFile()

-   Write this user arraylist to the userdatabase file

-   Each line should have the same format as before

-   On an IOException, print the error message "Error writing to users Database File: " along with the error message

### public static boolean registerUser(String firstName, String lastName, String username, String password, ArrayList<String> friendList, ArrayList<String> blockList, ArrayList<String> postIds) throws SMPException

-   Create a new user object with passed in parameters if the user does not already exist in the database and return true

-   If the username already exists, throw a new SMPException with the message "Username already exists."

### public static User loginUser(String username, String password) throws SMPException

-   Make sure that the username matches the user's password and return the user object

-   Return null if the username and password do not match

### public static boolean updateUser(User updatedUser) throws SMPException

-   Look for the user in the user database and change update the old user object to the new user object if the username's match and return true

-   If the user does not exist in the database, throw a new SMPException with the message "User not found."

### public static User searchUser(String username)

-   If the username exists in the database, return the user, otherwise return null

### public static void clearAllUsers

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

private static final String COMMENTS_FILE = "CommentsDatabase.txt": File where comments will be stored

private static ArrayList<Comment> comments = new ArrarList<>(): An array list of comments

## Constructors

### public CommentsManager() throws SMPException

-   Read the comments database file

## Methods

### public static ArrayList<Comment> getComments

-   Return this comments

### public void readCommentsDatabaseFile() throws SMPException

-   Read the contents from the comments database

-   If there is an IOException, throw a new SMPException with the message "Error reading Comments Database File " and the error message

### private Comment parseLineToComment(String line) throws SMPException

-   Parse the parameter, a line that is taken from the database file, and create a new Comment object

-   Return the new comment object

-   If the author of the comment does not exist, print the error message "Author not found for comment: " and the commentId and return null

-   If the format of the comment in the database is invalid or on any exception, return null

### public void writeCommentsDatabaseFile() throws SMPException

-   Write this comment to this database file

-   Overwrite any existing content that is currently in the database file

-   On IOException, print the message "Error writing to Comments Database File: " along with the error message

### public static String addComment(String authorUsername, String content, int upvotes, int downvotes) throws SMPException

-   If the username of the author does not exist in the user database, print out the error message "Cannot add comment. Author username not found: " along with the error message and return null

-   Using the parameters, create a new comment object and add it to this comments

-   Return the new comment's commentId

### public boolean updateComment(Comment updatedComment) throws SMPException

-   Search for the comment in the comment database using the commentId and update the old comment with the new comment

-   Return true if the operation was successful, otherwise return false

### public static boolean deleteComment(String commentId, String requesterUsername) throws SMPException

-   Look for the comment in the comment database

-   If the User who wrote the comment matches the User in the parameter, remove the comment from this comments and return true

-   If the operations as unsuccessful, return false

### public static Comment searchComment(String commentId)

-   Return the comment from this comments with the matching commentId

-   If unable to find the comment, return null

### public static void clearAllComments

-   Clear this comments ArrayList

# CommentsManagerInterface.java

An interface for the CommentsManager class.

## Methods

### static ArrayList<Comment> getComments()

-   Returns null

### static void readCommentsDatabaseFile() throws SMPException

### static void writeCommentsDatabaseFile() throws SMPException

### static String addComment(String authorUsername, String content, int upvotes, int downvotes) throws SMPException {

-   Returns null

### static boolean updateComment(Comment updatedComment) throws SMPException

-   Returns False

### static boolean deleteComment(String commentId, String requesterUsername) throws SMPException

-   Returns false

### static Comment searchComment(String commentId)

-   Returns Null

### static void clearAllComments()

# CommunicationInterface.java

An interface for the communication between clients and server

## Methods

void sendRequest(Object request)

Object receiveResponse()

# NewsFeedClientInterface.java

An interface for user interactions with the news feed

## Methods

voidNewsFeed()

void createPost(String content)

void commentOnPost(String postId, String content)

void upvotePost(String postId)

void downvotePost(String postId)

void hidePost(String postId)

# NewsFeedServiceInterface.java

An interface for defining the operations in managing posts and comments in the news feed

## Methods

Post createPost(String username, String content)

boolean deletePost(String postId)

void upvotePost(String postId)

void downvotePost(String postId)

Comment createComment(String postId, String username, String content)

boolean deleteComment(String commentId)

ArrayList<Post> getFriendPosts(String username)

# UserClientInterface.java

An interface that defines user operations

## Methods

void loginUser(String username, String password)

void logoutUser()

void addFriend(String friendUsername)

void removeFriend(String friendUsername)

void updateProfile(User user)

# UserServiceInterface.java

An interface that defines server-side user account management

## Methods

User createUser(String username, String password)

boolean deleteUser(String username)

User getUserDetails(String username)

void updateUserProfile(User user)

boolean addFriend(String username, String friendUsername)

boolean removeFriend(String username, String friendUsername)

# Client.java

The client class is used as a client and has many functionalities that allow it to communicate with the server and display posts and comments. Some of the key functionalities include handling adding and removing posts and comments, logging onto the platform or registering a new user, blocking users, adding and removing friends, and communicating with the server. In order to test the functionality of this code, there were false inputs that were given to the client to ensure that the correct messages were being printed out by the client.

## Fields

private String hostName: The name of the socket

private int port: The socket number

ObjectInputStream ois: this object input stream

ObjectOutputStream oos: this object output stream

Scanner scanner: the scanner object

Socket socket: the socket object

private boolean loggedIn: Whether or not the user is logged in, set to false

private boolean exit: Whether or not the user wants to exit, set to false

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

### private void handleViewFeed(ObjectOutputStream oos, ObjectInputStream ois, Scanner scanner) throws IOException, ClassNotFoundException

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
