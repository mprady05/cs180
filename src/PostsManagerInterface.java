import java.util.ArrayList;

public interface PostsManagerInterface.java {
  ArrayList<Post> getPosts();
  void setPosts(ArraryList<Post> posts)
  void readPostsDatabaseFile();
  void writePostsDatabaseFile();
  String addPost(String creatorUsername, String content, int upvotes, int downvotes, ArrayList,String> throws SMPException;
  void clearAllPosts();
  boolean updatePost(Post updatedPost) throws SMPException;
  Post searchPost(String postId);
  String getPostIdFromComment(Comment comment);
}
