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
=======
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