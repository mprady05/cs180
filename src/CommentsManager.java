import java.util.*;
import java.io.*;
/**
 * CS18000 -- Project 5 -- Phase 1
 * Class managing the storage and manipulation of user comments.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version March 31, 2024
 */
public class CommentsManager implements CommentsManagerInterface {
    private static final String COMMENTS_FILE = "CommentsDatabase.txt";
    private static ArrayList<Comment> comments = new ArrayList<>();

    /**
     * Constructs a CommentsManager and initializes the comments list by reading from the comments database file.
     */
    public CommentsManager() throws SMPException {
        readCommentsDatabaseFile();
    }

    /**
     * Returns the list of all comments.
     * @return A list containing all comments.
     */
    public static ArrayList<Comment> getComments() {
        return comments;
    }

    /**
     * Reads comments from the database file and loads them into the comments list.
     * Each line in the file is expected to represent a single comment.
     * @throws SMPException If there's an error reading the file.
     */
    public void readCommentsDatabaseFile() throws SMPException {
        comments.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(COMMENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Comment comment = parseLineToComment(line);
                if (comment != null) {
                    comments.add(comment);
                }
            }
        } catch (IOException e) {
            throw new SMPException("Error reading Comments Database File: " + e.getMessage());
        }
    }

    /**
     * Parses a line from the comments database file into a Comment object.
     * @param line A line from the comments database file.
     * @return A Comment object if parsing is successful, null otherwise.
     * @throws SMPException If parsing fails.
     */
    private Comment parseLineToComment(String line) throws SMPException {
        try {
            String[] parts = line.split(":~:");
            if (parts.length != 5) {
                throw new IllegalArgumentException("Invalid comment format in database");
            }
            String commentId = parts[0];
            String authorUsername = parts[1];
            String content = parts[2];
            int upvotes = Integer.parseInt(parts[3]);
            int downvotes = Integer.parseInt(parts[4]);
            User author = UsersManager.searchUser(authorUsername);
            if (author == null) {
                System.err.println("Author not found for comment: " + commentId);
                return null;
            }
            return new Comment(commentId, author, content, upvotes, downvotes);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Writes the current list of comments to the comments database file.
     * @throws SMPException If there's an error writing to the file.
     */
    public void writeCommentsDatabaseFile() throws SMPException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COMMENTS_FILE))) {
            for (Comment comment : comments) {
                String commentLine = comment.toString();
                writer.write(commentLine);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new SMPException("Error writing to Comments Database File: " + e.getMessage());
        }
    }

    /**
     * Adds a new comment.
     * @param authorUsername Username of the comment's author.
     * @param content Content of the comment.
     * @param upvotes Initial upvotes count.
     * @param downvotes Initial downvotes count.
     * @return The ID of the newly added comment, or null if the author doesn't exist.
     * @throws SMPException If the author doesn't exist.
     */
    public static String addComment(String authorUsername, String content, int upvotes, int downvotes) throws SMPException {
        User author = UsersManager.searchUser(authorUsername);
        if (author == null) {
            System.err.println("Cannot add comment. Author username not found: " + authorUsername);
            return null;
        }
        Comment newComment = new Comment(author, content, upvotes, downvotes);
        comments.add(newComment);
        return newComment.getCommentId();
    }

    /**
     * Updates an existing comment.
     * @param updatedComment The updated comment.
     * @return true if the comment is updated successfully, false otherwise.
     * @throws SMPException If updating fails.
     */
    public boolean updateComment(Comment updatedComment) throws SMPException {
        for (int i = 0; i < comments.size(); i++) {
            Comment comment = comments.get(i);
            if (comment.getCommentId().equals(updatedComment.getCommentId())) {
                comments.set(i, updatedComment);
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes a comment.
     * @param commentId ID of the comment to delete.
     * @param requesterUsername Username of the user requesting the deletion.
     * @return true if the comment is deleted successfully, false otherwise.
     * @throws SMPException If deletion fails.
     */
    public static boolean deleteComment(String commentId, String requesterUsername) throws SMPException {
        for (int i = 0; i < comments.size(); i++) {
            Comment comment = comments.get(i);
            String postCreatorUsername = PostsManager.getPostIdFromComment(comment);
            if (comment.getCommentId().equals(commentId) &&
                    (requesterUsername.equals(comment.getAuthor().getUsername()) || requesterUsername.equals(postCreatorUsername))) {
                comments.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Searches for a comment by its ID.
     * @param commentId The ID of the comment to search for.
     * @return The Comment object if found, null otherwise.
     */
    public static Comment searchComment(String commentId) {
        for (Comment comment : comments) {
            if (comment.getCommentId().equals(commentId)) {
                return comment;
            }
        }
        return null;
    }

    /**
     * Clears all comments from the list.
     */
    public static void clearAllComments() {
        comments.clear();
    }
}
