public interface CommentsManagerInterface {
    void readCommentsDatabaseFile() throws SMPException;
    void writeCommentsDatabaseFile() throws SMPException;
    boolean updateComment(Comment updatedComment) throws SMPException;
}
