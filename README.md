# Project 5 -- Phase 1 -- ReadMe
To run and compile this project on an IDE, put the contents into an src file. Then, add Maven and Junit4 to your file path (used for testing purposes). After, you should be able to run the project. You should have JDK 14 or higher.


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

###  static void writeCommentsDatabaseFile() throws SMPException

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