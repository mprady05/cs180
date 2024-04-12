import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    // FIELDS
    private Socket clientSocket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    // GLOBAL VARIABLES
    private boolean loggedIn = false;
    User currentUser;
    ArrayList<Post> usersPosts = new ArrayList<>();
    ArrayList<Post> allFriendsPosts = new ArrayList<>();
    Post chosenPost;
    Comment chosenComment;
    ArrayList<Comment> postsComments = new ArrayList<>();

    // STATIC FINAL PROMPTS
    public static final String LOGIN_SUCCESS = "Login success!";
    public static final String LOGIN_FAIL = "Invalid login credentials. Please try again.";
    public static final String REGISTER_SUCCESS = "Account created successfully!";
    public static final String REGISTER_FAIL = "Invalid credentials. Please try again.";
    private static final String EXIT_MESSAGE = "Thank you for using MySpace! Come back soon!";
    private static final String INVALID_COMMAND = "Invalid command. Please try again.";
    private static final String ADD_FRIEND_SUCCESS = "Successfully added friend!";
    private static final String ADD_FRIEND_FAIL = "Failed to add friend. Please try again.";
    private static final String REMOVE_FRIEND_SUCESS = "Successfully removed friend!";
    private static final String REMOVE_FRIEND_FAIL = "Failed to remove friend. Please try again.";
    private static final String BLOCK_FRIEND_SUCCESS = "Successfully blocked friend!";
    private static final String BLOCK_FRIEND_FAIL = "Failed to block friend. Please try again.";
    private static final String ADD_POST_SUCCESS = "Successfully added post!";
    private static final String ADD_POST_FAIL = "Failed to add post. Please try again.";
    private static final String HIDE_POST_SUCCESS = "Post successfully hidden!";
    private static final String HIDE_POST_FAIL = "Failed to hide post. Please try again.";
    private static final String VIEW_PROFILE_SUCCESS = "Profile found.";
    private static final String VIEW_PROFILE_FAIL = "Profile not found. Please try again.";
    private static final String VIEW_FEED_SUCCESS = "Successfully viewed feed!";
    private static final String VIEW_FEED_FAIL = "Could not view feed. Please try again.";
    private static final String VIEW_COMMENTS_SUCCESS = "Successfully viewed comments!";
    private static final String VIEW_COMMENTS_FAIL = "Failed to view comments. Please try again.";
    private static final String ADD_COMMENT_SUCCESS = "Successfully added comment!";
    private static final String ADD_COMMENT_FAIL = "Failed to add comment. Please try again.";
    private static final String DELETE_COMMENT_SUCCESS = "Successfully deleted comment.";
    private static final String DELETE_COMMENT_FAIL = "Failed to delete comment. Please try again.";

    // CONSTRUCTOR
    public ClientHandler(Socket socket, ObjectInputStream ois, ObjectOutputStream oos) throws IOException {
        this.clientSocket = socket;
        this.ois = ois;
        this.oos = oos;
    }
    public ClientHandler(Socket socket) throws IOException {
        this.clientSocket = socket;
        this.ois = new ObjectInputStream(clientSocket.getInputStream());
        this.oos = new ObjectOutputStream(clientSocket.getOutputStream());
    }

    public void setInputStream(ObjectInputStream ois) {
        this.ois = ois;
    }

    public void setOutputStream(ObjectOutputStream oos) {
        this.oos = oos;
    }

    /*
    Run Method ======================================================== Run Method
     */
    public void run() {
        try {
            while (!loggedIn) {
                String command = (String) ois.readObject();
                processCommand(command);
            }
            while (loggedIn) {
                String command = (String) ois.readObject();
                processLoginCommand(command);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error in ClientHandler: " + e.getMessage());
        } catch (SMPException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
        }
    }

    /*
    Process Pre-Login Command ======================================================== Process Pre-Login Command
     */
    public void processCommand(String command) throws SMPException, IOException, ClassNotFoundException {
        readAllDatabases();
        switch (command) {
            case "1":
                processLogin();
                break;
            case "2":
                processRegister();
                break;
            case "3":
                sendExitMessage();
                return;

        }
    }

    /*
    Login/Register Menu ======================================================== Login/Register Menu
     */
    public void processLogin() throws IOException, ClassNotFoundException, SMPException {
        String username = (String) ois.readObject();
        String password = (String) ois.readObject();
        User user = UsersManager.loginUser(username, password);
        if (user != null) {
            oos.writeObject(LOGIN_SUCCESS);
            oos.writeObject(user.toString());
            currentUser = user;
            loggedIn = true;
        } else {
            oos.writeObject(LOGIN_FAIL);
        }
    }

    public void processRegister() throws IOException, ClassNotFoundException, SMPException {
        String firstname = (String) ois.readObject();
        String lastname = (String) ois.readObject();
        String username = (String) ois.readObject();
        String password = (String) ois.readObject();
        User user = UsersManager.registerUser(firstname, lastname, username, password);
        if (user != null) {
            oos.writeObject(REGISTER_SUCCESS);
            oos.writeObject(user.toString());
            UsersManager.writeUsersDatabaseFile();
            loggedIn = true;
            currentUser = user;
        } else {
            oos.writeObject(REGISTER_FAIL);
            oos.flush();
        }
        writeAllDatabases();
    }

    public void sendExitMessage() throws IOException, SMPException {
        oos.writeObject(EXIT_MESSAGE);
        writeAllDatabases();
    }

    /*
    Process Login Command ======================================================== Process Login Command
     */
    public void processLoginCommand(String command) throws SMPException, IOException, ClassNotFoundException {
        readAllDatabases();
        switch (command) {
            case "1":
                processAddFriend();
                break;
            case "2":
                processRemoveFriend();
                break;
            case "3":
                processBlockFriend();
                break;
            case "4":
                processAddPost();
                break;
            case "getPosts":
                processGetPosts();
                break;
            case "hidePost":
                processHidePost();
                break;
            case "6":
                processViewSearchUser();
                break;
            case "viewFeed": // view feed
                processViewFeed();
                break;
            case "getFriendsPosts":
                processGetFriendsPosts();
                break;
            case "upvotePost":
                processAddUpvote();
                break;
            case "downvotePost":
                processDownvotePost();
                break;
            case "addComment":
                processAddComment();
                break;
            case "viewComments":
                processViewComments();
                break;
            case "upvoteComment":
                processUpvoteComment();
                break;
            case "downvoteComment":
                processDownvoteComment();
                break;
            case "deleteComment":
                processDeleteComment();
                break;
            case "getPostsComments":
                processGetPostsComments();
                break;
            case "8":
                processLogout();
                return;

        }
    }

    /*
    User Menu ======================================================== User Menu
     */
    public void processAddFriend() throws SMPException, IOException, ClassNotFoundException {
        readAllDatabases();
        String friendUsername = (String) ois.readObject();
        boolean checkFriend = currentUser.addFriend(friendUsername);
        if (checkFriend) {
            oos.writeObject(ADD_FRIEND_SUCCESS);
        } else {
            oos.writeObject(ADD_FRIEND_FAIL);
        }
        writeAllDatabases();
    }

    private void processRemoveFriend() throws SMPException, IOException, ClassNotFoundException {
        readAllDatabases();
        String friendUsername = (String) ois.readObject();
        boolean checkFriend = currentUser.removeFriend(friendUsername);
        if (checkFriend) {
            oos.writeObject(REMOVE_FRIEND_SUCESS);
        } else {
            oos.writeObject(REMOVE_FRIEND_FAIL);
        }
        writeAllDatabases();
    }

    private void processBlockFriend() throws SMPException, IOException, ClassNotFoundException {
        readAllDatabases();
        String blockUsername = (String) ois.readObject();
        boolean checkBlocked = currentUser.blockUser(blockUsername);
        if (checkBlocked) {
            oos.writeObject(BLOCK_FRIEND_SUCCESS);
        } else {
            oos.writeObject(BLOCK_FRIEND_FAIL);
        }
        writeAllDatabases();
    }

    private void processAddPost() throws SMPException, IOException, ClassNotFoundException {
        readAllDatabases();
        String content = (String) ois.readObject();
        boolean checkAddPost = currentUser.addPost(content);
        if (checkAddPost) {
            oos.writeObject(ADD_POST_SUCCESS);
        } else {
            oos.writeObject(ADD_POST_FAIL);
        }
        writeAllDatabases();
    }

    private void processGetPosts() throws SMPException, IOException {
        readAllDatabases();
        usersPosts = new ArrayList<>();
        for (int i = 0; i < currentUser.getPostIds().size(); i++) {
            usersPosts.add(PostsManager.searchPost(currentUser.getPostIds().get(i)));
        }
        for (Post post : usersPosts) {
            oos.writeObject(post.getContent());
        }
        oos.writeObject("end");
        writeAllDatabases();
    }

    private void processHidePost() throws SMPException, IOException, ClassNotFoundException {
        readAllDatabases();
        String postNumberStr = (String) ois.readObject();
        try {
            int postNumber = Integer.parseInt(postNumberStr);
            Post postChoice = getPostFromChoice(usersPosts, postNumber);
            for (Post post : usersPosts) {
                if (post.equals(postChoice)) {
                    boolean checkIfHidden = currentUser.hidePost(postChoice.getPostId());
                    if (checkIfHidden) {
                        oos.writeObject(HIDE_POST_SUCCESS);
                    } else {
                        oos.writeObject(HIDE_POST_FAIL);
                    }
                    writeAllDatabases();
                    break;
                }
            }
        } catch (NumberFormatException e) {
            oos.writeObject(HIDE_POST_FAIL);
        }
        writeAllDatabases();
    }

    private void processViewSearchUser() throws IOException, SMPException, ClassNotFoundException {
        readAllDatabases();
        String profileUsername = (String) ois.readObject();
        User isUserThere = UsersManager.searchUser(profileUsername);
        if (isUserThere != null && !currentUser.getBlockList().contains(profileUsername)) {
            oos.writeObject(VIEW_PROFILE_SUCCESS);
            oos.writeObject(isUserThere.getFirstName() + " " + isUserThere.getLastName());
            oos.writeObject(isUserThere.getUsername());
        } else {
            oos.writeObject(VIEW_PROFILE_FAIL);
        }
        writeAllDatabases();
    }

    private void processViewFeed() throws SMPException, IOException, ClassNotFoundException {
        readAllDatabases();
        String friendsPostNumberStr = (String) ois.readObject();
        try {
            boolean checkFeed = false;
            int friendsPostNumber = Integer.parseInt(friendsPostNumberStr);
            chosenPost = getPostFromChoice(allFriendsPosts, friendsPostNumber);
            for (Post post : allFriendsPosts) {
                if (post.equals(chosenPost)) {
                    checkFeed = true;
                    oos.writeObject(VIEW_FEED_SUCCESS);
                }
            }
            if (!checkFeed) {
                oos.writeObject(VIEW_PROFILE_FAIL);
            }
            writeAllDatabases();
        } catch (NumberFormatException | IOException e) {
            oos.writeObject(VIEW_FEED_FAIL);
        }
        writeAllDatabases();
    }

    private Post getPostFromChoice(ArrayList<Post> posts, int postNumber) {
        Post postIdChoice = null;
        if (postNumber <= posts.size() && postNumber > 0) {
            for (int i = 1; i <= posts.size(); i++) {
                if (i == postNumber) {
                    postIdChoice = posts.get(i - 1);
                }
            }
        }
        return postIdChoice;
    }

    private void processLogout() throws SMPException {
        readAllDatabases();
        writeAllDatabases();
    }

    /*
    Feed Menu ======================================================== Feed Menu
     */
    private void processGetFriendsPosts() throws SMPException, IOException {
        readAllDatabases();
        ArrayList<String> allFriendsPostIds = currentUser.getFriendsPosts();
        allFriendsPosts = new ArrayList<>();
        for (String friendsPostId : allFriendsPostIds) {
            allFriendsPosts.add(PostsManager.searchPost(friendsPostId));
        }
        System.out.println(allFriendsPosts);
        for (Post friendsPost : allFriendsPosts) {
            oos.writeObject(friendsPost.getContent());
        }
        oos.writeObject("end");
        writeAllDatabases();
    }

    private void processAddUpvote() throws SMPException {
        readAllDatabases();
        chosenPost.addUpvote();
        writeAllDatabases();
    }

    private void processDownvotePost() throws SMPException {
        readAllDatabases();
        chosenPost.addDownvote();
        writeAllDatabases();
    }

    private void processAddComment() throws SMPException, IOException, ClassNotFoundException {
        readAllDatabases();
        String content1 = (String) ois.readObject();
        boolean checkAddComment = chosenPost.addComment(currentUser.getUsername(), content1);
        if (checkAddComment) {
            oos.writeObject(ADD_COMMENT_SUCCESS);
        } else {
            oos.writeObject(ADD_COMMENT_FAIL);
        }
        writeAllDatabases();
        chosenPost = PostsManager.searchPost(chosenPost.getPostId());
    }

    private void processGetPostsComments() throws SMPException, IOException {
        readAllDatabases();
        postsComments = new ArrayList<>();
        ArrayList<String> postsCommentIds = chosenPost.getComments();
        for (String commentId : postsCommentIds) {
            postsComments.add(CommentsManager.searchComment(commentId));
        }
        for (Comment comment : postsComments) {
            oos.writeObject(comment.getContent());
        }
        oos.writeObject("end");
        writeAllDatabases();
    }

    private void processViewComments() throws IOException, SMPException, ClassNotFoundException {
        readAllDatabases();
        String commentNumberStr = (String) ois.readObject();
        try {
            boolean checkComment = false;
            int commentNumber = Integer.parseInt(commentNumberStr);
            chosenComment = getCommentFromChoice(postsComments, commentNumber);
            for (Comment comment : postsComments) {
                if (chosenComment.equals(comment)) {
                    checkComment = true;
                    oos.writeObject(VIEW_COMMENTS_SUCCESS);
                }
            }
            if (!checkComment) {
                oos.writeObject(VIEW_COMMENTS_FAIL);
            }
            writeAllDatabases();
        } catch (NumberFormatException | IOException e) {
            oos.writeObject(VIEW_COMMENTS_FAIL);
        }
        writeAllDatabases();
    }

    private Comment getCommentFromChoice(ArrayList<Comment> comments, int commentNumber) {
        Comment commentIdChoice = null;
        if (commentNumber <= comments.size() && commentNumber > 0) {
            for (int i = 1; i <= comments.size(); i++) {
                if (i == commentNumber) {
                    commentIdChoice = comments.get(i - 1);
                }
            }
        }
        return commentIdChoice;
    }

    /*
    Comments Menu ======================================================== Comments Menu
     */
    private void processUpvoteComment() throws SMPException {
        readAllDatabases();
        chosenComment.addUpvote();
        writeAllDatabases();
    }

    private void processDownvoteComment() throws SMPException {
        readAllDatabases();
        chosenComment.addDownvote();
        writeAllDatabases();
    }

    private void processDeleteComment() throws SMPException, IOException {
        readAllDatabases();
        boolean checkDelete = chosenPost.deleteComment(chosenComment.getCommentId(),
                currentUser.getUsername());
        if (checkDelete) {
            oos.writeObject(DELETE_COMMENT_SUCCESS);
        } else {
            oos.writeObject(DELETE_COMMENT_FAIL);
        }
        System.out.println("current post" + chosenPost);
        writeAllDatabases();
        chosenComment = null;
        chosenPost = PostsManager.searchPost(chosenPost.getPostId());
    }

    private void readAllDatabases() throws SMPException {
        UsersManager.readUsersDatabaseFile();
        PostsManager.readPostsDatabaseFile();
        CommentsManager.readCommentsDatabaseFile();
    }

    private void writeAllDatabases() throws SMPException {
        UsersManager.writeUsersDatabaseFile();
        PostsManager.writePostsDatabaseFile();
        CommentsManager.writeCommentsDatabaseFile();
    }

}