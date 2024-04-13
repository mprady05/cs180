import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * CS18000 -- Project 5 -- Phase 2
 * Class that represents the client
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version April 13, 2024
 */
public class Client {
    private String hostname;
    private int port;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    Scanner scanner;
    Socket socket;
    private boolean loggedIn = false;
    private static final String WELCOME_MESSAGE = "Welcome to MySpace!";
    private static final String WELCOME_MENU = """
            Enter the number of the command you would like to execute:
            (1) - Login to your account
            (2) - Create a new account
            (3) - Exit""";
    private static final String LOGIN_SUCCESS = "Login success!";
    private static final String WELCOME_BACK = "Welcome back ";
    private static final String WELCOME_NEW = "Welcome new user ";
    private static final String LOGIN_FAIL = "Invalid login credentials. Please try again.";
    private static final String REGISTER_SUCCESS = "Account created successfully!";
    private static final String REGISTER_FAIL = "Invalid credentials. Please try again.";
    private static final String EXIT_MESSAGE = "Thank you for using MySpace! Come back soon!";
    private static final String INVALID_COMMAND = "Invalid command. Please try again.";
    private static final String USER_MENU = """
            Enter the number of the command you would like to execute:
            (1) - Add a friend
            (2) - Remove a friend
            (3) - Block user
            (4) - Add post
            (5) - Hide post
            (6) - View/Search profile
            (7) - View feed
            (8) - Logout""";
    private static final String ADD_FRIEND = "Enter friend's username you want to add: ";
    private static final String ADD_FRIEND_SUCCESS = "Successfully added friend!";
    private static final String ADD_FRIEND_FAIL = "Failed to add friend. Please try again.";
    private static final String REMOVE_FRIEND = "Enter the friend's username you want to remove: ";
    private static final String REMOVE_FRIEND_SUCCESS = "Successfully removed friend!";
    private static final String REMOVE_FRIEND_FAIL = "Failed to remove friend. Please try again.";
    private static final String BLOCK_FRIEND = "Enter the username of the friend you want to block: ";
    private static final String BLOCK_FRIEND_SUCCESS = "Successfully blocked friend!";
    public static final String BLOCK_FRIEND_FAIL = "Failed to block friend. Please try again.";
    private static final String ADD_POST = "Enter post contents:";
    private static final String ADD_POST_SUCCESS = "Successfully added post!";
    private static final String ADD_POST_FAIL = "Failed to add post. Please try again.";
    private static final String HIDE_POST = "Enter the number of the post you want to hide: ";
    private static final String HIDE_POST_SUCCESS = "Post successfully hidden!";
    private static final String HIDE_POST_FAIL = "Failed to hide post. Please try again.";
    private static final String VIEW_PROFILE = "Enter the user of the profile you want to view:";
    private static final String VIEW_PROFILE_SUCCESS = "Profile found.";
    private static final String VIEW_PROFILE_FAIL = "Profile not found. Please try again.";
    private static final String VIEW_FEED = "Enter the number of your friend's post you want to see:";
    private static final String VIEW_FEED_SUCCESS = "Successfully viewed feed!";
    private static final String VIEW_FEED_FAIL = "Could not view feed. Please try again.";
    private static final String POST_OPTIONS_MENU = """
            Enter the number of the command you would like to execute:
            (1) - Upvote post
            (2) - Downvote post
            (3) - Add comment
            (4) - View comments
            (5) - Exit""";
    private static final String ADD_COMMENT = "Enter comment contents: ";
    private static final String ADD_COMMENT_SUCCESS = "Successfully added comment!";
    private static final String ADD_COMMENT_FAIL = "Failed to add comment. Please try again.";

    private static final String COMMENT_OPTIONS_MENU = """
            Enter the number of the command you would like to execute:
            (1) - Upvote comment
            (2) - Downvote comment
            (3) - Delete comment
            (4) - Exit""";
    private static final String VIEW_COMMENT = "Enter the number of the comment you want to see:";
    private static final String VIEW_COMMENTS_SUCCESS = "Successfully viewed comments!";
    private static final String VIEW_COMMENTS_FAIL = "Failed to view comments. Please try again.";
    private static final String DELETE_COMMENT_SUCCESS = "Successfully deleted comment.";
    private static final String DELETE_COMMENT_FAIL = "Failed to delete comment. Please try again.";
    private boolean exit = false;

    // Constructor that create a new socket based on passed in hostname and port number
    public Client(String hostname, int port) throws IOException {
        this.hostname = hostname;
        this.port = port;
        Socket socket = new Socket(hostname, port);
        setupStreams(socket.getInputStream(), socket.getOutputStream());
        this.scanner = new Scanner(System.in);
    }

    //Sets up the object input and output streams
    private void setupStreams(InputStream input, OutputStream output) throws IOException {
        this.oos = new ObjectOutputStream(output);
        this.ois = new ObjectInputStream(input);
    }

    //Deals with commands that are passed in from the user
    public void start() throws IOException {
        try {
            System.out.println(WELCOME_MESSAGE);
            while (!loggedIn) {
                System.out.println(WELCOME_MENU);
                String command = scanner.nextLine();
                oos.writeObject(command);
                handleWelcomeMenu(command);
                if (exit) {
                    return;
                }
            }
            while (loggedIn) {
                System.out.println(USER_MENU);
                String command = scanner.nextLine();
                oos.writeObject(command);
                handleUserMenu(command);
            }
        } catch (IOException | ClassNotFoundException | SMPException e) {
            System.out.println(EXIT_MESSAGE);
            socket.close();
        }
    }

    //Handles the beginning of whether the user wants to login, register, or exit
    private void handleWelcomeMenu(String command) throws IOException, ClassNotFoundException, SMPException {
        User user;
        switch (command) {
            case "1":
                user = handleLogin();
                if (user != null) {
                    loggedIn = true;
                }
                break;
            case "2":
                user = handleRegistration();
                if (user != null) {
                    loggedIn = true;
                }
                break;
            default:
                System.out.println((String) ois.readObject());
                break;
            case "3":
                System.out.println((String) ois.readObject());
                exit = true;
        }
    }

    //Handles commands from the user on actions taken (see comments for each case)
    private void handleUserMenu(String command) throws IOException, ClassNotFoundException, SMPException {
        switch (command) {
            case "1": // add a friend
                handleAddFriend();
                break;
            case "2": // remove a friend
                handleRemoveFriend();
                break;
            case "3": // block a friend
                handleBlockFriend(oos, ois, scanner);
                break;
            case "4": // add post
                handleAddPost(oos, ois, scanner);
                break;
            case "5": // hide post
                handleHidePost(oos, ois, scanner);
                break;
            case "6": // view/search profile
                handleViewSearchProfile(oos, ois, scanner);
                break;
            case "7": // view feed
                handleViewFeed(oos, ois, scanner);
                break;
            case "8": // logout
                System.out.println(EXIT_MESSAGE);
                loggedIn = false;
                return;
        }
    }

    //Handles login of the user
    public User handleLogin()
            throws IOException, ClassNotFoundException, SMPException {
        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();
        oos.writeObject(username);
        oos.writeObject(password);
        String response = (String) ois.readObject();
        if (LOGIN_SUCCESS.trim().equals(response)) {
            User user = UsersManager.stringToUser((String) ois.readObject());
            System.out.println(LOGIN_SUCCESS);
            System.out.println(WELCOME_BACK + user.getFirstName() + " " + user.getLastName() + "!");
            return user;
        } else {
            System.out.println(LOGIN_FAIL);
            return null;
        }
    }

    //Handles registering a new user
    public User handleRegistration()
            throws IOException, ClassNotFoundException, SMPException {
        System.out.println("Enter first name: ");
        String firstname = scanner.nextLine();
        oos.writeObject(firstname);
        System.out.println("Enter last name: ");
        String lastname = scanner.nextLine();
        oos.writeObject(lastname);
        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        oos.writeObject(username);
        System.out.println("Enter password: ");
        String password = scanner.nextLine();
        oos.writeObject(password);
        String response = (String) ois.readObject();
        if (REGISTER_SUCCESS.equalsIgnoreCase(response)) {
            User user = UsersManager.stringToUser((String) ois.readObject());
            System.out.println(REGISTER_SUCCESS);
            System.out.println(WELCOME_NEW + user.getFirstName() + " " + user.getLastName() + "!");
            return user;
        } else {
            System.out.println(REGISTER_FAIL);
            return null;
        }
    }

    //Handles adding a friend to the user based on username
    public void handleAddFriend()
            throws SMPException, IOException, ClassNotFoundException {
        System.out.println(ADD_FRIEND);
        String friendUsername = scanner.nextLine();
        oos.writeObject(friendUsername);
        String respond = (String) ois.readObject();
        if (ADD_FRIEND_SUCCESS.equalsIgnoreCase(respond)) {
            System.out.println(ADD_FRIEND_SUCCESS);
        } else {
            System.out.println(ADD_FRIEND_FAIL);
        }
    }

    //Handles removing a friend from the user based on username
    public void handleRemoveFriend()
            throws SMPException, IOException, ClassNotFoundException {
        System.out.println(REMOVE_FRIEND);
        String friendUsername = scanner.nextLine();
        oos.writeObject(friendUsername);
        String respond = (String) ois.readObject();
        if (REMOVE_FRIEND_SUCCESS.equalsIgnoreCase(respond)) {
            System.out.println(REMOVE_FRIEND_SUCCESS);
        } else {
            System.out.println(REMOVE_FRIEND_FAIL);
        }
    }

    //Handles blocking a friend based on username
    private void handleBlockFriend(ObjectOutputStream oos, ObjectInputStream ois, Scanner scanner)
            throws IOException, ClassNotFoundException {
        System.out.println(BLOCK_FRIEND);
        String blockUsername = scanner.nextLine();
        oos.writeObject(blockUsername);
        String respond = (String) ois.readObject();
        if (BLOCK_FRIEND_SUCCESS.equalsIgnoreCase(respond)) {
            System.out.println(BLOCK_FRIEND_SUCCESS);
        } else {
            System.out.println(BLOCK_FRIEND_FAIL);
        }
    }

    //Handles adding a post based on content passed in from the user
    private void handleAddPost(ObjectOutputStream oos, ObjectInputStream ois, Scanner scanner)
            throws IOException, ClassNotFoundException {
        System.out.println(ADD_POST);
        String content = scanner.nextLine();
        oos.writeObject(content);
        String respond = (String) ois.readObject();
        if (ADD_POST_SUCCESS.equalsIgnoreCase(respond)) {
            System.out.println(ADD_POST_SUCCESS);
        } else {
            System.out.println(ADD_POST_FAIL);
        }
    }

    //Retrieves and displays the posts
    private boolean displayPosts(ObjectOutputStream oos, ObjectInputStream ois, Scanner scanner)
            throws IOException {
        oos.writeObject("getPosts");
        try {
            String response = (String) ois.readObject();
            if (response.equals("end")) {
                return false;
            }
            System.out.println("Viewing your posts:");
            int index = 1;
            while (!response.equals("end")) {
                System.out.println("(" + index++ + ") - " + response);
                response = (String) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error retrieving posts: " + e.getMessage());
            return false;
        }
        return true;
    }

    //Hides the post based on what post number the user specifies
    private void handleHidePost(ObjectOutputStream oos, ObjectInputStream ois, Scanner scanner)
            throws IOException, ClassNotFoundException, SMPException {
        boolean checkPosts = displayPosts(oos, ois, scanner);
        if (checkPosts) {
            try {
                System.out.println(HIDE_POST);
                String postNumber = scanner.nextLine();
                oos.writeObject("hidePost");
                oos.flush();
                oos.writeObject(postNumber);
                String respond = (String) ois.readObject();
                if (HIDE_POST_SUCCESS.equalsIgnoreCase(respond)) {
                    System.out.println(HIDE_POST_SUCCESS);
                } else {
                    System.out.println(HIDE_POST_FAIL);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(HIDE_POST_FAIL);
        }
    }

    //Searches for a user based on username and prints out first and last name as well as username
    private void handleViewSearchProfile(ObjectOutputStream oos, ObjectInputStream ois, Scanner scanner)
            throws IOException, ClassNotFoundException {
        System.out.println(VIEW_PROFILE);
        String profileUsername = scanner.nextLine();
        oos.writeObject(profileUsername);
        String respond = (String) ois.readObject();
        if (VIEW_PROFILE_SUCCESS.equalsIgnoreCase(respond)) {
            System.out.println(VIEW_PROFILE_SUCCESS);
            String firstLastName = (String) ois.readObject();
            String username = (String) ois.readObject();
            System.out.println(firstLastName);
            System.out.println(username);
        } else {
            System.out.println(VIEW_PROFILE_FAIL);
        }
    }

    //Displays the feed to the user based on posts from their friends
    private boolean displayFeed(ObjectOutputStream oos, ObjectInputStream ois, Scanner scanner)
            throws IOException {
        oos.writeObject("getFriendsPosts");
        try {
            String response = (String) ois.readObject();
            if (response.equals("end")) {
                return false;
            }
            System.out.println("Viewing your friends posts:");
            int index = 1;
            while (!response.equals("end")) {
                System.out.println("(" + index++ + ") - " + response);
                response = (String) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error retrieving posts: " + e.getMessage());
            return false;
        }
        return true;
    }

    //Shows the number of posts that the user wants to see from friends
    private void handleViewFeed(ObjectOutputStream oos, ObjectInputStream ois, Scanner scanner)
            throws IOException, ClassNotFoundException {
        boolean checkFeed = displayFeed(oos, ois, scanner);
        if (checkFeed) {
            System.out.println(VIEW_FEED);
            String postNumber = scanner.nextLine();
            oos.writeObject("viewFeed");
            oos.flush();
            oos.writeObject(postNumber);
            String respond = (String) ois.readObject();
            if (VIEW_FEED_SUCCESS.equalsIgnoreCase(respond)) {
                handlePostOptions(oos, ois, scanner);
            } else {
                System.out.println(VIEW_FEED_FAIL);
            }
        } else {
            System.out.println(VIEW_FEED_FAIL);
        }
    }

    //Receives commands from the user on what they want to do regarding the post
    private void handlePostOptions(ObjectOutputStream oos, ObjectInputStream ois, Scanner scanner)
            throws IOException, ClassNotFoundException {
        System.out.println("Post options menu:");
        while (true) {
            System.out.println(POST_OPTIONS_MENU);
            String option = scanner.nextLine();
            switch (option) {
                case "1": // upvote post
                    oos.writeObject("upvotePost");
                    System.out.println("Post successfully upvoted.");
                    break;
                case "2": // downvote post
                    oos.writeObject("downvotePost");
                    System.out.println("Post successfully downvoted.");
                    break;
                case "3": // add comment
                    handleAddComment(oos, ois, scanner);
                    break;
                case "4": // view comments
                    handleViewComments(oos, ois, scanner);
                    break;
                case "5": // exit
                    return;
            }
        }
    }

    //Handles adding a comment to the post
    private void handleAddComment(ObjectOutputStream oos, ObjectInputStream ois, Scanner scanner)
            throws IOException, ClassNotFoundException {
        oos.writeObject("addComment");
        System.out.println(ADD_COMMENT);
        String content = scanner.nextLine();
        oos.writeObject(content);
        String respond = (String) ois.readObject();
        if (ADD_COMMENT_SUCCESS.equalsIgnoreCase(respond)) {
            System.out.println(ADD_COMMENT_SUCCESS);
        } else {
            System.out.println(ADD_COMMENT_FAIL);
        }
    }

    //Displays the comments to the user
    private boolean displayComments(ObjectOutputStream oos, ObjectInputStream ois) throws IOException {
        oos.writeObject("getPostsComments");
        try {
            String response = (String) ois.readObject();
            if (response.equals("end")) {
                return false;
            }
            System.out.println("Viewing this post's comments:");
            int index = 1;
            while (!response.equals("end")) {
                System.out.println("(" + index++ + ") - " + response);
                response = (String) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error retrieving posts: " + e.getMessage());
            return false;
        }
        return true;
    }

    //Views the comments
    private void handleViewComments(ObjectOutputStream oos, ObjectInputStream ois, Scanner scanner)
            throws IOException, ClassNotFoundException {
        boolean checkComments = displayComments(oos, ois);
        if (checkComments) {
            System.out.println(VIEW_COMMENT);
            String postNumber = scanner.nextLine();
            oos.writeObject("viewComments");
            oos.flush();
            oos.writeObject(postNumber);
            String respond = (String) ois.readObject();
            if (VIEW_COMMENTS_SUCCESS.equalsIgnoreCase(respond)) {
                handleCommentOptions(oos, ois, scanner);
            } else {
                System.out.println(VIEW_COMMENTS_FAIL);
            }
        } else {
            System.out.println(VIEW_COMMENTS_FAIL);
        }
    }

    //Handles actions that can be performed on the comments
    private void handleCommentOptions(ObjectOutputStream oos, ObjectInputStream ois, Scanner scanner)
            throws IOException, ClassNotFoundException {
        while (true) {
            System.out.println(COMMENT_OPTIONS_MENU);
            String option = scanner.nextLine();
            switch (option) {
                case "1": // upvote post
                    oos.writeObject("upvoteComment");
                    System.out.println("Comment successfully upvoted.");
                    break;
                case "2": // downvote post
                    oos.writeObject("downvoteComment");
                    System.out.println("Comment successfully downvoted.");
                    break;
                case "3": // delete comment
                    handleDeleteComment(oos, ois);
                    return;
                case "4": // exit
                    System.out.println("Leaving comment options menu.");
                    return;
            }
        }
    }

    //Deletes comments from the post
    private void handleDeleteComment(ObjectOutputStream oos, ObjectInputStream ois)
            throws IOException, ClassNotFoundException {
        oos.writeObject("deleteComment");
        String respond = (String) ois.readObject();
        if (DELETE_COMMENT_SUCCESS.equalsIgnoreCase(respond)) {
            System.out.println(DELETE_COMMENT_SUCCESS);
        } else {
            System.out.println(DELETE_COMMENT_FAIL);
        }
    }

    public static void main(String[] args) throws IOException {
        String hostname = "localhost";
        int port = 8080;
        Client client = new Client(hostname, port);
        client.start();
    }
}
