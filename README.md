# Running and Compilation
=========================

In order for this project to run correctly, all of the classes must first be compiled. Then, in order to correctly start the application, the server class will be run first, then after, the client class will be run, and then finally, the clientGUI class will be run. There are already port numbers and host names that are included into the main method in each of these classes in order to ensure that the server and the client will all interact and connect with each other correctly.

# Team Member Submissions
=========================

Andrew Song - Submitted Report on Brightspace

Isha Yanamandra - Submitted Vocareum workspace

Isha Yanamandra - Submitted VIdeo Presentation on Brightspace

# ClientGUI.java
================

This class lays out the main framework without getting too much into the specific details of the presentation to the user. It is essentially a blueprint that will be called upon by all the other panel classes to develop the frontend side of things that the user will be able to see and interact with. The classes this class calls upon include the LoginPanel, RegistrationPanel, SearchUserPanel, ShowUserPostsPanel, UserMenuPanel, ViewCommentsPanel, ViewFeedCommentsPanel, ViewFeedPanel, and ViewPostsPanel. Essentially, the main functionality of this class is to make sure that the panels are being called correctly based on user input. In order to test this functionality, it was made sure that when the user wanted to switch to a login panel, this class would call upon the LoginPanel class in order to display the correct GUI to the user.

## Fields 

private CardLayout cardLayout = new CardLayout() 

private JPanel cardPanel = new JPanel(cardLayout)

private LoginPanel loginPanel

private RegistrationPanel registrationPanel

private UserMenuPanel userMenuPanel

private ViewPostsPanel viewPostsPanel

private SearchUserPanel searchUserPanel

private ShowUserPostsPanel showUserPosts

private ViewCommentsPanel viewComments

private ViewFeedPanel viewFeedPanel

private ViewFeedCommentsPanel viewFeedCommentsPanel

private Socket socket

private ObjectOutputStream oos

private ObjectInputStream ois

private User user

public static final Color DEEP_BLUE = new Color(10, 25, 49)

public static final Color RICH_LIGHT_BLUE = new Color(200, 220, 240)

public static final Font MAIN_FONT = new Font("Arial", Font.PLAIN, 14)

private String hostname

private int port

## Constructors

### public ClientGUI(String hostname, int port)

-   instantiate this hostname and port number to the parameters

-   Set the title to "Social Media Platform", size to (500, 400), location in the middle of the screen

-   Connect this oos and ois to a new socket with the passed in hostname and port number

-   Display error message on error when connecting to server

## Methods

### public ObjectOutputStream getOos()

-   Returns this oos

### public ObjectInputStream getOis()

-   Returns this ois

### private void setupUI()

-   Creates a new loginPanel, registrationPanel, and userMenuPanel, each with arguments of this, this oos, and this ois

-   Set the background to DEEP_BLUE

-   add the loginPanel, registrationPanel, and userMenuPanel and show the cardPanel

### public User getUser()

-   Returns this user

### public void setUser(User user)

-   Sets this user to the passed in parameter

### public void switchToRegistration()

-   Display a new registration panel

### public void switchToLogin()

-   Display a new login panel

### public void switchToUserMenu

-   Display a new user menu

### public void switchToViewPosts() throws SMPException()

-   Create a new viewPostsPanel with this, this oos, and this ois passed in as arguments

-   Display the posts

### public void switchToSearchUser

-   Create a new SearchUserPanel passing in this, this oos, and this ois as arguments

-   Display the panel

### public void switchToViewComments(Post post) throws SMPException

-   Create a new ViewCommentsPanel passing in this, this oos, this ois, and the post parameter as arguments

-   Display the comments panel

### public void switchToViewFeed(User user) throws Exception

-   Create a new ViewFeedPanel passing in this, this oos, this ois, and the user parameter as arguments

-   Display the user's feed

### public void switchToViewFeedComments(Post post) throws Exception

-   Create a new ViewFeedCommentsPanel passing in this, this oos, this ois, and the post parameter as arguments

-   Display the comment feed

### public void resetAllPanels(ObjectOutputStream oos, ObjectInputStream ois) throws SMPException

-   Reset this loginPanel, registrationPanel, userMenuPanel, and instantiate this oos and this ois to the passed in parameters

### private void closeConnections

-   Close this ois, oos, and this socket

-   Print out error message on IOException

### private void createConnection(String hostname, int port)

-   Attempt to create a new socket connection with the passed in parameters

-   Display an error message on any IOException

-   Throw a new runtime exception on any SMPException

### public void reset()

-   Close the connection and create a new connection with "localhost" and 8080 passed in as arguments

### public JLabel createLogoLabel

-   Read the logo from the "logo.png" file and display the logo in the center of the screen

-   Print the stack trace on any IOException

### public JPanel createRow (String label, JComponent component)

-   Create a new JPanel and set the layout to a flow, set background to RICH_LIGHT_BLUE

-   Create a new JLabel with the label parameters and set font to MAIN_FONT, foreground to DEEP_BLUE

-   Add the JLabel and a component with font MAIN_FONT and foreground DEEP_BLUE to the JPanel and return this JPanel

### public JPanel createButtonPanel(Jbutton... buttons)

-   create a new JPanel, set the layout to a flow layout and the background to RICH_LIGHT_BLUE

-   for each button in the parameter, such the font to MAIN_FONT, background to RICH_LIGHT_BLUE, DEEP_BLUE, FocusPainted to false, border to rounded border with argument 5 and add the button to the JPanel

-   Return the JPanel

### static class RoundedBorder implements Border

-   Fields: int radius

-   Constructor

-   RoundedBorder (int radius): instantiate this radius to the passed in parameter

-   public Insets getBorderInsets(Component c): return a new inset with this radius, this radius+5, this radius, and this radius+5 passed in as parameters

-   public boolean isBorderOpaque: Returns false

-   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height): set the graphics color to the background of the component and draw a rectangle with x, y, width - 1, height -1 , radius, radius passed in as arguments

### public static void main(String[] args)

-   Initialize and display GUI window

# LoginPanel.java
=================

The functionality of this class is to take advantage of the login panel that is created in the UserGUI class in order to display to the user a panel that allows them to login by entering a username and a password. It also works with the userManager class to ensure that the command for logging in is received and to check that the inputted username and password exist and are valid. In order to ensure that all of the functionality within this class worked, it was made sure that the GUI would appear in the format that was expected and that when a correct username and password were entered, the panel would switch to the User Menu. This class is called by the UserGUI class in order to display the login panel. This class calls the UserManager class to handle all the operations required to handle a user logging on to the platform.

## Fields

private JTextField usernameField = new JTextField(10)

private JPasswordField passwordField = new JPasswordField(10)

private JButton loginButton = new JButton("Login")

private JButton registerButton = new JButton("Register")

private ClientGUI mainFrame

private ObjectOutputStream oos

private ObjectInputStream ois

## Constructors

### public LoginPanel(ClientGUI frame, ObjectOutputStream oos, ObjectInputStream ois)

-   instantiate this mainFrame, this oos, and this ois to the parameters

-   Set the layout to a Box Layout and the background color to RICH_LIGHT_BLUE

-   Add the logo to the panel, a row with the username, a row with the password, and a create button panel

## Methods

### private void login()

-   Get the username and password that the user inputs into the frame

-   If the username or password is empty, display an error message saying that either one of them is empty

-   Write the inputted username and password to the server and if successful, switch to the UserMenu

-   If login failed, display an error message

-   Display an error message on any IOException or ClassNotFoundException

-   Throw a new RuntimeException on any SMPException

### public void resetFields()

-   set the username and password fields to an empty string

# RegistrationPanel.java
========================

This class is used to display the registration screen GUI to users who are wishing to register as a new user with the platform. It will be called by the UserGUI class in order to display the screen to the user. This class will call the UserManager class to handle all the operations required to create a new user, such as creating a new user object with first name, last name, username, and password. In order to test this class and ensure functionality, it was made sure that all the buttons were working when expected, such as making sure that a new user was being created when the correct fields were entered and the button was clicked.

## Fields

private JTextField firstNameField = new JTextField(10)

private JTextField lastNameField = new JTextField(10)

private JTextField usernameField = new JTextField(10)

private JPasswordField passwordField = new JPasswordField(10)

private JButton registerButton = new JButton("Register")

private JButton cancelButton = new JButton("Cancel")

private ClientGUI mainFrame

private ObjectOutputStream oos

private ObjectInputStream ois

## Constructors

### public RegistrationPanel(ClientGUI frame, ObjectOutputStream oos, ObjectInputStream ois) 

-   Instantiate this mainFrame, oos, and ois to the passed in parameters

-   Create a new box layout with the background as the RICH_LIGHT_BLUE and add the logo to the display

-   Create a place to enter first name, last name, username, and password, along with a create button that the user can press on to create a new user profile

## Methods

### private void register()

-   Create a new user object that is stored in the database from the inputted first name, last name, username, and password that is typed into the text boxes by the users

-   Display an error message if any textbox is empty, otherwise display a success message if the registration was successful

-   Switch over the the user menu when successful

-   If the registration was unsuccessful, display an error message

-   Show an error message on any IOException or ClassNotFoundException

-   Throw a new RuntimeException on any SMPException

### public void resetFields()

-   Reset the first name, last name, username, and password text fields back to an empty string

# SearchUserPanel.java
======================

The SearchUserPanel class is used to create the GUI display to the user when they choose to search for a user by the username. It will be called by the ClientGUI class when the user wants to search for a user. This class will call the UserManager class in order to perform the operation of searching for a user. In order to test this class and ensure functionality, it was made sure that all buttons when the displays from the GUIs appeared were working exactly as expected. Some examples include that when a user searches for another user form the database and that user exists, then the user card is able to appear.

## Fields

private ClientGUI mainFrame

private ObjectOutputStream oos

private ObjectInputStream ois

private JTextField searchField

private JButton searchButton

private JButton viewPostsButton

private JPanel userCardPanel

private User foundUser

## Constructors

### public SearchUserPanel(ClientGUI frame, ObjectOutputStream oos, ObjectInputStream ois)

-   Instantiate this mainFrame, this oos, and this ois to the passed in parameters and initialize the UI

## Methods

### private void initializeUI

-   Create a new border layout and the background should be a light blue shade (200, 220, 240)

-   The top panel will have a back button and a search bar that is a JTextField. The back button rings user back to previous screen and search bar allows user to enter a username

-   There will be a search button next to search bar that user will click to search for the inputted user

-   The central panel will have a userCarPanel that displays the user profile using a vertical BoxLayout

-   Create a scroll pane that wraps the userCardPanel and enables the user to scroll through

### private void searchProfile(String username) throws SMPException

-   Call the correct command from the userManager class to search for a user and display the user details upon success.

-   If the user cannot be found, show the message to the user

-   Display an error message on any IOException or ClassNotFoundException

### private void displayUserDetails(User user)

-   create a new border layout with background as white 

-   The panel should display the user's name and username

# ShowUserPostsPanel.java
=========================

This class is used to format the GUI that will be used to show the user using the application the posts from friends. This class will be called upon by the UserGUI class in order to format the GUI to the user. In order to ensure functionality, during testing, different combinations/permutations were employed on the different GUIs in order to sure that each button/feature was working as expected. 

## Fields

private ClientGUI mainFrame

private ObjectOutputStream oos

private ObjectInputStream ois

private User user

private JPanel postsPanel

private JScrollPane scrollPane

private JButton refreshButton

## Constructors

### public ShowUserPostsPanel(ClientGUI frame, ObjectOutputStream oos, ObjectInputStream ois, User user) throws SMPException

-   Instantiate this mainFrame, this oos, this ois, and this user to the passed in parameters

-   Initialize the UI

## Methods

### private void initializeUI() throws SMPException

-   Create a new border layout and include a panel of posts and a scroll layout so that the user can scroll through posts

### private JPanel createTopPanel

-   The top panel should have a back to menu button and a refresh button that can refresh the posts

-   Return the top panel that has been created

### public void refreshPosts() throws SMPException

-   Remove all posts from the panel and read the posts from the usersmanager and postsmanager databases. Redisplay the posts to the user

### private JPanel createPostCard(String content)

-   Create a new panel that is the post that is created by the other user that is being viewed by this user

-   Return this view

### private void viewPost(String content)

-   Show a message dialog that the user is viewing the post and the content in the post

# UserMenuPanel.java
====================

This class is used to create the user menu panel GUIs. It works with the UserGUI class in order to update the GUI whenever the user wishes to enter the UserMenu. Some of the main functionalities include adding GUIs  where a user can add friends, remove friends, or block users. It is called by the UserGUI class in order to update the GUIs to the necessary panels so that the given functionality can be performed. It calls upon the Client class in order for the action of adding friends, removing friends, or blocking friends is performed. In order to test for the functionality of the code, it was made sure that the GUIs had the expected layout, such as where specific buttons are placed or what color the background is. The functionality of these buttons was also tested to make sure that friends were actually being added/removed or users were being blocked.

## Fields

private ClientGUI mainFrame

private ObjectOutputStream oos

private ObjectInputStream ois

## Constructors

### public UserMenuPanel(ClientGUI frame, ObjectOutputStream oos, ObjectInputStream ois)

-   Configure the look and feel

-   Instantiate this manFrame, this oos, and this ois to the passed in parameters

-   Initialize the panel

## Methods

### private void configureLookAndFeel()

-   Call the UIManager to set the look and feel of the GUI

-   Print the stack trace on any Exception

### private void initializePanel()

-   create a new box layout with color RGB: 200,220,240 and addComponentsToPanel

### private void addComponentsToPanel

-   Add the logo, a button panel, and a logout button

### private JLabel createLogoLabel()

-   Call the mainFrame to create a LogoLabel and align the logo to the center. Return this label

### private JPanel createButtonPanel()

-   Create a panel with buttons "View MyPosts", "View Feed", "Search Profile", "Add Friend", "Remove Friend", and "Block Friend"

-   Return the panel

### private JButton createButton(String test)

-   Creates a new button with the passed in parameter as the test inside of the button

-   The font is SanSerif, bold, 12 point, with a background color of RGB: 230, 240, 250 and a foreground color of RGB: 50, 60 ,70

### private JPanel createLogoutButton()

-   Create a new panel with a logout button inside of it and return the panel

### private ActionListener createActionListenerForButton(String label)

-   The passed in parameter should be the action that the user wants to perform after clicking on the button from the createButtonPanel options

-   If "View My Posts", call the view posts method

-   If "View Feed", call the viewFeed method

-   If "Search Profile" call the searchProfile method

-   If "Add Friend" call the method to add a friend

-   If "Remove Friend" call the method to remove a friend

-   If "Block Friend" call the method to block a friend

-   Return e on any case, if the case is not available, throw  new IllegalArgumentException with "No actions refined for: " and the passed in label as the argument

### private String promptUsername(String message)

-   Return a JOptionPane with the passed in parameter as the argument

### private void viewPosts()

-   Use the mainFrame to switchToViewPosts and then call the revalidate and repaint method

-   On any exception, throw a new RuntimeException

### private void viewFeed()

-   Use the mainFrame and switch to the View Feed and then revalidate and repaint

-   On any exception, throw a new RunTimeException

### private void searchProfile()

-   Use the mainFrame to switch to search for a user and then repaint the GUI

### private void revalidateAndRepaint

-   Revalidate and repaint the mainFrame

### private void sendAddFriendCommand(String username)

-   Call the perform network action method to add a friend with the passed in parameter as the username

### private void sendRemoveFriendCommand(String username)

-   Call the perform network action method to remove a friend with the passed in parameter as the username

## private void sendBlockFriendCommand(String username)

-   Call the perform network action method to block a friend with the username in the parameter

### private void logout

-   Write the specific command for logging out to the server and show a success message when successful

-   Reset the GUI to the original login screen

-   Display an error message on any IO Exception

### private void performNetworkAction(String command, String username, String actionDescription)

-   Write the command and username to the server to perform the action

-   Display a success message on success and a failure message if not successful

-   Display an error message on any IOException or ClassNotFoundException

# ViewCommentsPanel.java
========================

This class is used to handle the GUIs and user actions/inputs that are associated with the comment section of the posts and this social media application. It is called by the UserGUI class in order to provide the necessary GUIs when any actions with comments are performed. It also calls on the database classes to retrieve any of the comments on the post that is being selected as well as on the CommentsManager class to upvote a comment, downvote a comment, or check if a comment can be deleted by this user. In order to test for functionality, it was made sure that the GUIs had the correct appearance and all of the correct features available, ie buttons and text. Then, it was made sure that each feature was working correctly, such as upvoting a comment correctly or refreshing the comment section correctly or displaying the comments correctly.

## Fields

private ClientGUI mainFrame

private ObjectOutputStream oos

private ObjectInputStream ois

private Post post

private JPanel commentsPanel

private JScrollPane scrollPane

## Constructors

### public ViewCommentsPanel(ClientGUI frame, ObjectOutputStream oos, ObjectInputStream ois, Post post) throws SMPException 

-   Instantiate this mainFrame, this oos, this ois, and this post to the passed in parameters

-   Create a new border layout and initialize the UI

## Methods

### private void initializeUI() throws SMPException

-   The UI will have a top panel with a back button and a posts detail panel. 

-   There will also be a comments panel for where the comments will go and a scroll pane to scroll up and down

### private JButton createBackButton()

-   Create a new button with test "Back to Posts" and return it 

### private JPanel createPostDetailsPanel()

-   Create a new JPanel with this post's content inside of it and return the panel

### public void refreshComments() throws SMPException

-   Reread all the comments from the comments database

-   If there are no comments, create a "No comments found" label, otherwise add all the comments associated with this post to the panel

### private JPanel createCommentCard(Comment comment)

-   Create a display associated with the comment that is passed in as a parameter

-   The comment card should have the number of upvotes, the number of downvotes, and a delete button

-   Return the comment card

### private void upvoteComment(Comment comment) throws SMPException

-   Add an upvote to the passed in comment and update databases

### private void downvoteComment(Comment comment) throws SMPException

-   Add a downvote to the passed in comment and update databases

### private void deleteComment(Comment comment) throws SMPException

-   Delete the comment from the post and refresh the comments as well as the databases

# ViewFeedCommentsPanel.java
============================

This class is used to create the GUI where the user is able to view all the comments on the given post. Some of the main functionalities include being able to upvote a comment, downvote a comment, delete a comment from a post, or add a comment to the post. It works in conjunction with the UserGUI class in order to continuously update the GUI based on user input when clicking on the buttons that are present in the panel. This class calls upon the CommentsManager and Client class in order to perform the actions expected of the functionality that is presented in this class. The client class will handle all of the backend operations, such as adding an upvote, whereas this class will handle all of the frontend appearance side of things. In order to ensure that everything is working, it was made sure that each panel and GUI had an optimal appearance. Furthermore, it was made sure that each button was working correctly in updating the comments on the posts as well as the number of upvotes or downvotes on a comment. 

## Fields

private ClientGUI mainFrame

private ObjectOutputStream oos

private ObjectInputStream ois

private Post post

private JPanel commentsPanel

private JScrollPane scrollPane

## Constructors

public ViewFeedCommentsPanel(ClientGUI frame, ObjectOutputStream oos, ObjectInputStream ois, Post post) throws SMPException 

-   Instantiate this mainFrame, this oos, this ois, and this post to the passed in parameters

-   Set a new border layout and initialize the UI

## Methods

### private void initializeUI() throws SMPException

-   Create a new border layout with a back button and this commentsPanel

### private JButton createBackButton()

-   Create a new button with test "Back to Posts" and return it 

### private JPanel createPostDetailsPanel()

-   Create a new JPanel with this post's content inside of it and return the panel

### private JPanel createBottomPanel

-   The bottom Panel will have a button with "Add Comment" and return this panel

### private void addCommentAction(ActionEvent e)

-   Ask the user to add a comment and add a comment to the post

-   Display an error message on any IOException, ClassNotFoundException, or SMPException

### public void refreshComments() throws SMPException

-   Reread all the comments from the comments database

-   If there are no comments, create a "No comments found" label, otherwise add all the comments associated with this post to the panel

### private JPanel createCommentCard(Comment comment)

-   Create a display associated with the comment that is passed in as a parameter

-   The comment card should have the number of upvotes, the number of downvotes, and a delete button

-   Return the comment card

### private void upvoteComment(Comment comment) throws SMPException

-   Add an upvote to the passed in comment and update databases

### private void downvoteComment(Comment comment) throws SMPException

-   Add a downvote to the passed in comment and update databases

### private void deleteComment(Comment comment) throws SMPException

-   Delete the comment from the post and refresh the comments as well as the databases 

# ViewFeedPanel.java
====================

The purpose of this class is for the user to view all of the posts that are in their feed. It mostly works on the front end side of things, displaying to the user all of the posts from their friends. Some of the main functionalities include being able to add upvotes, downvotes, or to view the comment section of a selected post. It works with the UserGUi class to create a display for the user that is both pleasing to the eye and functional. It works with all of the database classes in order to retrieve all of the posts that will be shown in the user feed as well as the comments that are associated with these posts. It calls upon the Client class in order to handle all of the backend side of things, such as adding an upvote to a post. In order to test the functionality of this class, it is made sure that the appearance of the GUI is correct and that the buttons/features all function as intended. For example, the refresh button works to refresh the user's feed.

## Fields

private ClientGUI mainFrame

private ObjectOutputStream oos

private ObjectInputStream ois

private JPanel postPanel

private User user

## Constructors

### public ViewFeedPanel(ClientGUI frame, ObjectOutputStream oos, ObjectInputStream ois, User user) 

-   Instantiate this manFrame, this oos, this ois, and this user to the passed in parameters

-   Initialize the UI

## Methods

### private void initializeUI()

-   Set the layout to a border layout, with background color RGB: 200, 220, 240

-   The UI will have a top panel, a post panel, and a scroll pane 

### private JPanel createTopPanel()

-   The top panel will have a back button in the top left that says "Back to Menu"

-   The top panel will be titled "Feed" in the middle

-   There will be a refresh button on the right to refresh the feed

### private void styleButton(Jbutton button)

-   Make the style of the button parameter times new roman 12 point font, with dimension 90x25, color RGB: 230, 240,250

### public void refreshFeed() throws Exception

-   Reread all the database files in order to completely refresh all of the posts that are being viewed by the user

### private JPanel createPostCard(Post post)

-   The post card should use the passed in parameter and display the contents of the post

-   Display the number of upvotes, the number of downvotes, and a view comments button in the panel

-   There is an upvote button and a downvote button along with the panel

-   Return this panel

### private void handleUpvote(Post post)

-   Handle upvoting the post in the parameter

-   On any IOException, display an error message

-   On any other exception, throw a new RuntimeException

### private void handleDownvote(Post post)

-   Handle downvoting the post passed in as the parameter

-   On any IOException, display an error message

-   On any other exception, throw a new RuntimeException

### private void viewComments(Post post) throws Exception

-   View the feed comments

# ViewPostsPanel.java
=====================

This class is the frontend side of displaying the posts panel to the user. It works in conjunction with the UserGUI class in order to create displays that the user will see. Some of the main functionalities include allowing the user to upvote or downvote posts, as well as create a new post that can be posted for other people to see. It calls upon the Client class in order to perform the backend operations such as upvoting a post or downvoting a post. In order to test the functionality of this class, it was made sure that the panels were placed in the correct orientation. Then, it was made sure that each button/text input were receiving user input and causing the backend operations to be performed correctly. For example, if the user wanted to add a post, it was a made sure that after the user entered their content into the textbox and clicked on the create post button, the database would receive that post and attribute it to the user who created that post. 

## Fields

private ClientGUI mainFrame

private ObjectOutputStream oos

private ObjectInputStream ois

private JPanel postsPanel

private JScrollPane scrollPane

## Constructors

### public ViewPostsPanel(ClientGUI frame, ObjectOutputStream oos, ObjectInputStream ois) throws SMPException 

-   Instantiate this mainFrame, this oos, and this ois with the passed in parameters

-   Initialize the UI

## Methods

### private void initializeUI() throws SMPException

-   Create a new border layout with a top panel, a center panel, and a bottom panel

### private JPanel createNorthPanel()

-   Contains a back button and a refresh button, return this panel

### private JButton createButton(String test, ActionListener action)

-   Create a new Jbutton styled button and add an action listener

-   Return this button

### private JLabel createTitleLabel()

-   Title label contains the label "first name' Posts"

-   Return the label

### private void refreshButtonAction(ActionEvent e)

-   Refreshes the posts

-   Display an error message on any exception

### private JScrollPane createPostsPanel()

-   Create a new vertical scroll bar and return

## private JPanel createBottomPanel()

-   The bottom panel will have a button that says "Add New Post" and return the bottom panel

### public void refreshPosts() throws SMPException

-   Remove all posts from the panel and read the posts from the usersmanager and postsmanager databases. Redisplay the posts to the user

### private JPanel createPostCard(String postId)

-   Create a new panel that is the post that is created by the other user that is being viewed by this user

-   Return this view

### private void styleContentArea(JTestArea contentArea)

-   Editable is false, wrap style is true, line wrap is true, font is Sans Serif size 14, and background color is RGB: 250, 250, 250

### private JPanel createButtonPanel(Post post)

-   Panel that has an upvote button, downvote button, hide button, and a view comments button

-   Return the panel

### private JButton createPostButton(String test, ActionListener action)

-   A new styled button with the parameter text and return the button

### private void upvotePost(Post post)

-   Upvote the post that is passed in as a parameter and refresh the screen

-   Display an error message on any Exception

### private void downvotePost(Post post)

-   Downvote the post that is passed in as a parameter and refresh the screen

-   Display an error message on any Exception

### private void hidePost(Post post)

-   Hide the post and refresh the screen

-   Display an error message on any Exception

### private void viewCOmments(Post post) throws SMPException

-   Switch to the view comments GUI with the given post as the parameter

### private void addPost()

-   Display an input dialog and ask the user to enter the content of the post and write it to the database

-   On any exception, display an error message

### private class StyledButton extends JButton

-   Subclass of Jbutton with text, font Sans Serif bolded size 12, background color RGB: 215, 225, 240, foreground color black, focus painted false, border painted false, content area filled false, opaque true, along with a mouse listener

### static class RoundedBorder implements Border

-   Fields: private int radius, private Color color, private int thickness

-   Constructor: RoundedBorder(int radius, Color color, int thickness): Instantiate

-   Methods: 

-   public Insets getBorderInsets(Component c): returns a new Insets with this radius as the four arguments

-   public boolean isBorderOpaque(): returns false

-   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height): set graphics to this color and draw a round rectangle with the passed in parameters

# User.java  
=============

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

###  public synchronized boolean removeFriend(String checkUsername) throws SMPException 

-   Update the friend list to remove the passed in user from this user's friend list and return true

-   If the user does not exist in the database or the two users are not friends, return false

### public synchronized boolean addPost(String content) throws SMPException

-   Add the post to this user's post list and update this user's posts

-   Return true if updated successfully, otherwise return false

###  public synchronized boolean hidePost(String postId) throws SMPException 

-   Remove the passed in post from this user's post list and return true, otherwise return false

###  public synchronized ArrayList<String> getFriendsPosts() 

-   Return an array list of all of the posts from the friend

###  public synchronized String toString() 

-   Return a string containing the first name, last name, username, and password, separated by ; and no spaces

-   Add on all the friends in the format of "("friends");", and then all the blocked users in the format of "("blocked users");", and then finally all of the postIds of this user in the format of "("postIds")" and return the string

# UserInterface.java
====================

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
===================

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
============================

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
===================

Exception to be thrown when there is bad data/inputs

## Constructors

### SMPException(String message)

-   Calls the constructor of the exception superclass with the message passed in as the parameter

# PostsManager.java
===================

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
============================

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
===========

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
====================

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
==============

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
=======================

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
======================

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
===============================

An interface for the CommentsManager class.

## Methods

### static List<Comment> getComments()

-   Returns the comments

### static void readCommentsDatabaseFile() throws SMPException

###  static void writeCommentsDatabaseFile() throws SMPException

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
=============

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

###  private void handleViewFeed(ObjectOutputStream oos, ObjectInputStream ois, Scanner scanner) throws IOException, ClassNotFoundException

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

###  private void handleViewComments(ObjectOutputStream oos, ObjectInputStream ois, Scanner scanner) throws IOException, ClassNotFoundException

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
======================

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
====================

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
=============================

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
=============

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
======================

An interface for the server class

## Methods

### void start()
