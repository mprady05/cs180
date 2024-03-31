public interface CommentInterface {
  String getCommentId();
  User getAuthor();
  String getContent();
  int getUpvotes();
  int getDownvotes();
  String toString();
}
