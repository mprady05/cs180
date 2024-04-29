import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * CS18000 -- Project 5 -- Phase 3
 * View feed comments frame.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version April 28, 2024
 */
public class ViewFeedCommentsPanel extends JPanel {
    private ClientGUI mainFrame;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Post post;
    private JPanel commentsPanel;
    private JScrollPane scrollPane;

    public ViewFeedCommentsPanel(ClientGUI frame, ObjectOutputStream oos, ObjectInputStream ois, Post post) throws SMPException {
        this.mainFrame = frame;
        this.oos = oos;
        this.ois = ois;
        this.post = post;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 255));
        initializeUI();
    }

    private void initializeUI() throws SMPException {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(245, 245, 255));
        topPanel.add(createBackButton(), BorderLayout.WEST);
        topPanel.add(createPostDetailsPanel(), BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
        commentsPanel = new JPanel();
        commentsPanel.setLayout(new BoxLayout(commentsPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(commentsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(scrollPane, BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
        refreshComments();
    }

    private JButton createBackButton() {
        JButton backButton = new JButton("Back to Posts");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        backButton.addActionListener(e -> {
            try {
                mainFrame.switchToViewFeed(mainFrame.getUser());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        return backButton;
    }

    private JPanel createPostDetailsPanel() {
        JPanel postPanel = new JPanel(new BorderLayout());
        postPanel.setBackground(new Color(230, 240, 250));
        postPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextArea postContent = new JTextArea(post.getContent());
        postContent.setEditable(false);
        postContent.setWrapStyleWord(true);
        postContent.setLineWrap(true);
        postContent.setFont(new Font("SansSerif", Font.PLAIN, 16));
        postContent.setBackground(new Color(230, 240, 250));
        JLabel titleLabel = new JLabel("Post Details", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        postPanel.add(titleLabel, BorderLayout.NORTH);
        postPanel.add(postContent, BorderLayout.CENTER);
        return postPanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(new Color(230, 240, 250));
        JButton addCommentButton = new JButton("Add Comment");
        addCommentButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        addCommentButton.addActionListener(this::addCommentAction);
        bottomPanel.add(addCommentButton);
        return bottomPanel;
    }

    private void addCommentAction(ActionEvent e) {
        String commentContent = JOptionPane.showInputDialog(this, "Enter your comment:");
        if (commentContent != null && !commentContent.trim().isEmpty()) {
            try {
                oos.writeObject("addComment");
                oos.writeObject(post.getPostId());
                oos.writeObject(mainFrame.getUser().getUsername());
                oos.writeObject(commentContent);
                String response = (String) ois.readObject();
                JOptionPane.showMessageDialog(this, response);
                refreshComments();
            } catch (IOException | ClassNotFoundException | SMPException ex) {
                JOptionPane.showMessageDialog(this, "Failed to add comment: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void refreshComments() throws SMPException {
        CommentsManager.readCommentsDatabaseFile();
        commentsPanel.removeAll();
        if (post.getComments().isEmpty()) {
            JLabel noCommentsLabel = new JLabel("No comments found.");
            noCommentsLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
            noCommentsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            noCommentsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            commentsPanel.add(noCommentsLabel);
        } else {
            for (String commentId : post.getComments()) {
                Comment comment = CommentsManager.searchComment(commentId);
                if (comment != null) {
                    commentsPanel.add(createCommentCard(comment));
                }
            }
        }
        commentsPanel.revalidate();
        commentsPanel.repaint();
        CommentsManager.writeCommentsDatabaseFile();
    }

    private JPanel createCommentCard(Comment comment) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        card.setBackground(new Color(200, 220, 240));
        JTextArea commentContent = new JTextArea(comment.getContent());
        commentContent.setEditable(false);
        commentContent.setWrapStyleWord(true);
        commentContent.setLineWrap(true);
        commentContent.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        commentContent.setBackground(new Color(200, 220, 240));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(200, 220, 240));
        JButton upvoteButton = new JButton("Upvote (" + comment.getUpvotes() + ")");
        JButton downvoteButton = new JButton("Downvote (" + comment.getDownvotes() + ")");
        JButton deleteButton = new JButton("Delete");
        upvoteButton.setFont(new Font("Times New Roman", Font.BOLD, 12));
        downvoteButton.setFont(new Font("Times New Roman", Font.BOLD, 12));
        deleteButton.setFont(new Font("Times New Roman", Font.BOLD, 12));
        upvoteButton.addActionListener(e -> {
            try {
                upvoteComment(comment);
            } catch (SMPException ex) {
                throw new RuntimeException(ex);
            }
        });
        downvoteButton.addActionListener(e -> {
            try {
                downvoteComment(comment);
            } catch (SMPException ex) {
                throw new RuntimeException(ex);
            }
        });
        deleteButton.addActionListener(e -> {
            try {
                deleteComment(comment);
            } catch (SMPException ex) {
                throw new RuntimeException(ex);
            }
        });
        buttonPanel.add(upvoteButton);
        buttonPanel.add(downvoteButton);
        buttonPanel.add(deleteButton);
        card.add(commentContent, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.SOUTH);
        return card;
    }

    private void upvoteComment(Comment comment) throws SMPException {
        comment.addUpvote();
        CommentsManager.writeCommentsDatabaseFile();
        PostsManager.writePostsDatabaseFile();
        refreshComments();
    }

    private void downvoteComment(Comment comment) throws SMPException {
        comment.addDownvote();
        CommentsManager.writeCommentsDatabaseFile();
        PostsManager.writePostsDatabaseFile();
        refreshComments();
    }

    private void deleteComment(Comment comment) throws SMPException {
        post.deleteComment(comment.getCommentId(), mainFrame.getUser().getUsername());
        CommentsManager.writeCommentsDatabaseFile();
        PostsManager.writePostsDatabaseFile();
        refreshComments();
    }
}
