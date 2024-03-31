import java.util.ArrayList;

public interface PostsManagerInterface {
    ArrayList<Post> getPosts();
    void setPosts(ArrayList<Post> posts);
    void readPostsDatabaseFile();
    void writePostsDatabaseFile();
    String addPost(String creatorUsername, String content, int upvotes, int downvotes, ArrayList<String> commentIds) throws SMPException;
    void clearAllPosts();
    boolean updatePost(Post updatedPost) throws SMPException;
    Post searchPost(String postId);
    String getPostIdFromComment(Comment comment);
}
