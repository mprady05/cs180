import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private boolean loggedIn = false;
    User currentUser;
    ArrayList<Post> usersPosts = new ArrayList<>();
    ArrayList<Post> allFriendsPosts = new ArrayList<>();
    Post chosenPost;
    Comment chosenComment;
    ArrayList<Comment> postsComments = new ArrayList<>();
    private static final String LOGIN_SUCCESS = "Login success!";
    private static final String LOGIN_FAIL = "Invalid login credentials. Please try again.";
    private static final String REGISTER_SUCCESS = "Account created successfully!";
    private static final String REGISTER_FAIL = "Invalid credentials. Please try again.";
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
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream())) {
            while (!loggedIn) {
                UsersManager.readUsersDatabaseFile();
                PostsManager.readPostsDatabaseFile();
                String command = (String) ois.readObject();
                String username, password, firstname, lastname;
                User user;
                switch (command) {
                    case "1": { // login
                        username = (String) ois.readObject();
                        password = (String) ois.readObject();
                        user = UsersManager.loginUser(username, password);
                        if (user != null) {
                            oos.writeObject(LOGIN_SUCCESS);
                            oos.writeObject(user.toString());
                            currentUser = user;
                            loggedIn = true;
                        } else {
                            oos.writeObject(LOGIN_FAIL);
                            continue;
                        }
                        break;
                    } case "2": { // register
                        firstname = (String) ois.readObject();
                        lastname = (String) ois.readObject();
                        username = (String) ois.readObject();
                        password = (String) ois.readObject();
                        user = UsersManager.registerUser(firstname, lastname, username, password);
                        if (user != null) {
                            oos.writeObject(REGISTER_SUCCESS);
                            oos.writeObject(user.toString());
                            UsersManager.writeUsersDatabaseFile();
                            loggedIn = false;
                            currentUser = user;
                        } else {
                            oos.writeObject(REGISTER_FAIL);
                            oos.flush();
                        }
                        break;
                    } case "3": {
                        oos.writeObject(EXIT_MESSAGE);
                        return;
                    } default: {
                        oos.writeObject(INVALID_COMMAND);
                        break;
                    }
                }
            }
            while (loggedIn) {
                readAllDatabases();
                String command = (String) ois.readObject();
                String friendUsername;
                String blockUsername;
                boolean checkFriend;
                boolean checkBlocked;
                switch (command) {
                    case "1": // add friend
                        readAllDatabases();
                        friendUsername = (String) ois.readObject();
                        checkFriend = currentUser.addFriend(friendUsername);
                        if (checkFriend) {
                            oos.writeObject(ADD_FRIEND_SUCCESS);
                        } else {
                            oos.writeObject(ADD_FRIEND_FAIL);
                        }
                        writeAllDatabases();
                        break;
                    case "2": // remove friend
                        readAllDatabases();
                        friendUsername = (String) ois.readObject();
                        checkFriend = currentUser.removeFriend(friendUsername);
                        if (checkFriend) {
                            oos.writeObject(REMOVE_FRIEND_SUCESS);
                        } else {
                            oos.writeObject(REMOVE_FRIEND_FAIL);
                        }
                        writeAllDatabases();
                        break;
                    case "3": // block friend
                        readAllDatabases();
                        blockUsername = (String) ois.readObject();
                        checkBlocked = currentUser.blockUser(blockUsername);
                        if (checkBlocked) {
                            oos.writeObject(BLOCK_FRIEND_SUCCESS);
                        } else {
                            oos.writeObject(BLOCK_FRIEND_FAIL);
                        }
                        writeAllDatabases();
                        break;
                    case "4": // add post
                        readAllDatabases();
                        String content = (String) ois.readObject();
                        boolean checkAddPost = currentUser.addPost(content);
                        if (checkAddPost) {
                            oos.writeObject(ADD_POST_SUCCESS);
                        } else {
                            oos.writeObject(ADD_POST_FAIL);
                        }
                        writeAllDatabases();
                        break;
                    case "getPosts":
                        readAllDatabases();
                        for (int i = 0; i < currentUser.getPostIds().size(); i++) {
                            usersPosts.add(PostsManager.searchPost(currentUser.getPostIds().get(i)));
                        }
                        for (Post post : usersPosts) {
                            oos.writeObject(post.getContent());
                        }
                        oos.writeObject("end");
                        writeAllDatabases();
                        break;
                    case "hidePost": // hide post
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
                        break;
                    case "6": // view/search
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
                        break;
                    case "viewFeed": // view feed
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
                            break;
                        } catch (NumberFormatException e) {
                            oos.writeObject(VIEW_FEED_FAIL);
                        }
                        writeAllDatabases();
                        break;
                    case "getFriendsPosts":
                        readAllDatabases();
                        ArrayList<String> allFriendsPostIds = currentUser.getFriendsPosts();
                        allFriendsPosts = new ArrayList<>();
                        for (String friendsPostId : allFriendsPostIds) {
                            allFriendsPosts.add(PostsManager.searchPost(friendsPostId));
                        }
                        for (Post friendsPost : allFriendsPosts) {
                            oos.writeObject(friendsPost.getContent());
                        }
                        oos.writeObject("end");
                        writeAllDatabases();
                        break;
                    case "upvotePost":
                        readAllDatabases();
                        chosenPost.addUpvote();
                        writeAllDatabases();
                        break;
                    case "downvotePost":
                        readAllDatabases();
                        chosenPost.addDownvote();
                        writeAllDatabases();
                        break;
                    case "viewComments":
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
                            break;
                        } catch (NumberFormatException e) {
                            oos.writeObject(VIEW_COMMENTS_FAIL);
                        }
                        writeAllDatabases();
                        break;
                    case "upvoteComment":
                        readAllDatabases();
                        chosenComment.addUpvote();
                        writeAllDatabases();
                        break;
                    case "downvoteComment":
                        readAllDatabases();
                        chosenComment.addDownvote();
                        writeAllDatabases();
                        break;
                    case "deleteComment":
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
                        break;
                    case "8": // logout
                        readAllDatabases();
                        writeAllDatabases();
                        return;
                    case "getPostsComments":
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
                        break;
                    case "addComment":
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
                        break;
                }
                writeAllDatabases();
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

    private void readAllDatabases() throws SMPException {
        CommentsManager.readCommentsDatabaseFile();
        PostsManager.readPostsDatabaseFile();
        UsersManager.readUsersDatabaseFile();
    }

    private void writeAllDatabases() throws SMPException {
        CommentsManager.writeCommentsDatabaseFile();
        PostsManager.writePostsDatabaseFile();
        UsersManager.writeUsersDatabaseFile();
    }

}