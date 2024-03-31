import java.io.*;
import java.util.*;
public class PostsManager {
    private static final String POST_FILE = "C:\\Users\\Hashim\\IdeaProjects\\Project05New.java\\src\\PostsDatabase.txt";
    private static ArrayList<Post> posts = new ArrayList<>();

    /**
     * Constructs a PostsManager instance and initializes the posts list by reading from the database file.
     * This constructor automatically calls readPostsDatabaseFile() to load existing posts into memory.
     */
    public PostsManager() {
        readPostsDatabaseFile();
    }

    /**
     * Retrieves the current list of posts.
     * @return A list of Post objects representing all current posts.
     */
    public static ArrayList<Post> getPosts() {
        return posts;
    }

    /**
     * Sets the current list of posts.
     * @param posts A list of Post objects to set as the current list of posts.
     */
    public static void setPosts(ArrayList<Post> posts) {
        PostsManager.posts = posts;
    }

    /**
     * Reads the posts from the database file and populates the posts list.
     * This method clears any existing posts in the list before reading from the file.
     */
    public void readPostsDatabaseFile() {
        posts.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(POST_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Post post = parseLineToPost(line);
                if (post != null) {
                    posts.add(post);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading Posts Database File: " + e.getMessage());
        }
    }

    /**
     * Parses a line from the database file into a Post object.
     * @param line A string line from the database file representing a post.
     * @return A Post object if the line can be parsed successfully, null otherwise.
     */
    private Post parseLineToPost(String line) {
        try {
            String[] parts = line.split(":~:");
            String postId = parts[0];
            User creator = UsersManager.searchUser(parts[1]);
            String content = parts[2];
            int upvotes = Integer.parseInt(parts[3]);
            int downvotes = Integer.parseInt(parts[4]);
            ArrayList<String> commentIds = new ArrayList<>();
            if (parts.length > 5 && parts[5].startsWith("[") && parts[5].endsWith("]")) {
                String commentsString = parts[5].substring(1, parts[5].length() - 1);
                if (!commentsString.isEmpty()) {
                    commentIds = (ArrayList<String>) Arrays.asList(commentsString.split(":~:"));
                }
            }
            return new Post(postId, creator, content, upvotes, downvotes, new ArrayList<>(commentIds));
        } catch (Exception e) {
            System.err.println("Error parsing post from line: " + e.getMessage());
            return null;
        }
    }

    /**
     * Writes all current posts in the list to the database file.
     * This method overwrites the existing file content with the current state of the posts list.
     */
    public static void writePostsDatabaseFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(POST_FILE))) {
            for (Post post : posts) {
                String postInfo = post.getPostId() + ":~:" +
                        post.getCreator().getUsername() + ":~:" +
                        post.getContent() + ":~:" +
                        post.getUpvotes() + ":~:" +
                        post.getDownvotes() + ":~:";
                String commentsStr = "[";
                List<String> commentIds = post.getComments();
                for (int i = 0; i < commentIds.size(); i++) {
                    commentsStr += commentIds.get(i);
                    if (i < commentIds.size() - 1) {
                        commentsStr += ":~:";
                    }
                }
                commentsStr += "]";
                postInfo += commentsStr;
                writer.write(postInfo);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to Posts Database File: " + e.getMessage());
        }
    }

    /**
     * Adds a new post to the list and returns its ID.
     * @param creatorUsername The username of the creator of the post.
     * @param content The content of the post.
     * @param upvotes The initial number of upvotes for the post.
     * @param downvotes The initial number of downvotes for the post.
     * @param commentIds A list of IDs of comments associated with the post.
     * @return The ID of the newly added post.
     * @throws SMPException If the creator username does not exist.
     */
    public static String addPost(String creatorUsername, String content, int upvotes, int downvotes, ArrayList<String> commentIds) throws SMPException {
        User creator = UsersManager.searchUser(creatorUsername);
        if (creator == null) {
            throw new SMPException("Creator username does not exist.");
        }
        Post newPost = new Post(creator, content, upvotes, downvotes, commentIds);
        posts.add(newPost);
        return newPost.getPostId();
    }

    /**
     * Clears all posts from the list.
     * This method is used to reset the state of the posts list, typically for testing or initialization purposes.
     */
    public static void clearAllPosts() {
        posts.clear();
    }

    /**
     * Updates a post in the list with new information.
     * @param updatedPost The post object containing the updated information.
     * @return true if the post was found and updated, false otherwise.
     * @throws SMPException If an error occurs during the update process.
     */
    public static boolean updatePost(Post updatedPost) throws SMPException {
        int postIndex = -1;
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).getPostId().equals(updatedPost.getPostId())) {
                postIndex = i;
                break;
            }
        }
        if (postIndex != -1) {
            posts.set(postIndex, updatedPost);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Searches for a post by its ID.
     * @param postId The ID of the post to search for.
     * @return The Post object if found, null otherwise.
     */
    public static Post searchPost(String postId) {
        for (Post post : posts) {
            if (post.getPostId().equals(postId)) {
                return post;
            }
        }
        return null;
    }

    /**
     * Finds the post ID for a given comment.
     * @param comment The comment object for which to find the associated post ID.
     * @return The ID of the post that contains the given comment, or null if not found.
     */
    public static String getPostIdFromComment(Comment comment) {
        for (Post post : posts) {
            if (post.getComments().contains(comment.getCommentId())) {
                return post.getPostId();
            }
        }
        return null;
    }
}
