import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ViewPostsPanel extends JPanel {
    private ClientGUI mainFrame;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private JPanel postsPanel;
    private JScrollPane scrollPane;

    public ViewPostsPanel(ClientGUI frame, ObjectOutputStream oos, ObjectInputStream ois) throws SMPException {
        this.mainFrame = frame;
        this.oos = oos;
        this.ois = ois;
        initializeUI();
        frame.setSize(500, 600);
    }

    private void initializeUI() throws SMPException {
        setLayout(new BorderLayout());
        setBackground(new Color(200, 220, 240));
        add(createNorthPanel(), BorderLayout.NORTH);
        add(createPostsPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
        refreshPosts();
    }

    private JPanel createNorthPanel() {
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(new Color(230, 236, 245));
        northPanel.add(createButton("Back", e -> mainFrame.switchToUserMenu()), BorderLayout.WEST);
        northPanel.add(createTitleLabel(), BorderLayout.CENTER);
        northPanel.add(createButton("Refresh", this::refreshButtonAction), BorderLayout.EAST);
        return northPanel;
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new StyledButton(text);
        button.addActionListener(action);
        return button;
    }

    private JLabel createTitleLabel() {
        JLabel titleLabel = new JLabel(mainFrame.getUser().getFirstName() + "'s Posts", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        return titleLabel;
    }

    private void refreshButtonAction(ActionEvent e) {
        try {
            refreshPosts();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error refreshing posts: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JScrollPane createPostsPanel() {
        postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));
        postsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        scrollPane = new JScrollPane(postsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.setBackground(new Color(245, 248, 255));
        return scrollPane;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(new Color(230, 236, 245));
        bottomPanel.add(createButton("Add New Post", e -> addPost()));
        return bottomPanel;
    }

    public void refreshPosts() throws SMPException {
        UsersManager.readUsersDatabaseFile();
        PostsManager.readPostsDatabaseFile();
        CommentsManager.readCommentsDatabaseFile();
        postsPanel.removeAll();
        try {
            oos.writeObject("getPosts");
            oos.flush();
            String postId;
            while (!(postId = (String) ois.readObject()).equals("end")) {
                postsPanel.add(createPostCard(postId));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error while refreshing posts: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        postsPanel.revalidate();
        postsPanel.repaint();
    }

    private JPanel createPostCard(String postId) {
        Post post = PostsManager.searchPost(postId);
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(15, Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(new Color(250, 250, 250));
        card.add(new JLabel("| " + post.getCreator().getUsername() + " |", JLabel.CENTER), BorderLayout.NORTH);
        JTextArea contentArea = new JTextArea(post.getContent());
        styleContentArea(contentArea);
        JScrollPane contentScrollPane = new JScrollPane(contentArea);
        contentScrollPane.setBorder(null);
        card.add(contentScrollPane, BorderLayout.CENTER);
        card.add(createButtonPanel(post), BorderLayout.SOUTH);
        return card;
    }

    private void styleContentArea(JTextArea contentArea) {
        contentArea.setEditable(false);
        contentArea.setWrapStyleWord(true);
        contentArea.setLineWrap(true);
        contentArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        contentArea.setBackground(new Color(250, 250, 250));
    }

    private JPanel createButtonPanel(Post post) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(230, 240, 255));
        buttonPanel.add(createPostButton("Upvote (" + post.getUpvotes() + ")", e -> upvotePost(post)));
        buttonPanel.add(createPostButton("Downvote (" + post.getDownvotes() + ")", e -> downvotePost(post)));
        buttonPanel.add(createPostButton("Hide", e -> hidePost(post)));
        buttonPanel.add(createPostButton("View Comments", e -> {
            try {
                viewComments(post);
            } catch (SMPException ex) {
                throw new RuntimeException(ex);
            }
        }));
        return buttonPanel;
    }

    private JButton createPostButton(String text, ActionListener action) {
        JButton button = new StyledButton(text);
        button.addActionListener(action);
        return button;
    }

    private void upvotePost(Post post) {
        try {
            post.addUpvote();
            oos.writeObject("upvotePost");
            oos.writeObject(post.getPostId());
            refreshPosts();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error upvoting post: " + e.getMessage());
        }
    }

    private void downvotePost(Post post) {
        try {
            post.addDownvote();
            oos.writeObject("downvotePost");
            oos.writeObject(post.getPostId());
            refreshPosts();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error downvoting post: " + e.getMessage());
        }
    }


    private void hidePost(Post post) {
        try {
            oos.writeObject("hidePost");
            oos.writeObject(post.getPostId());
            String response = (String) ois.readObject();
            JOptionPane.showMessageDialog(this, response.equals(ClientHandler.HIDE_POST_SUCCESS) ? "Post hidden!" : "Failed to hide post");
            refreshPosts();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error hiding post: " + e.getMessage());
        }
    }

    private void viewComments(Post post) throws SMPException {
         mainFrame.switchToViewComments(post);
    }

    private void addPost() {
        String postContent = JOptionPane.showInputDialog(this, "Enter post content:");
        if (postContent != null && !postContent.isEmpty()) {
            try {
                oos.writeObject("addPost");
                oos.writeObject(postContent);
                String response = (String) ois.readObject();
                JOptionPane.showMessageDialog(this, response.equals(ClientHandler.ADD_POST_SUCCESS) ? "Post added successfully!" : response);
                refreshPosts();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error posting: " + e.getMessage());
            }
        }
    }

    private class StyledButton extends JButton {
        StyledButton(String text) {
            super(text);
            setFont(new Font("SansSerif", Font.BOLD, 12));
            setBackground(new Color(215, 225, 240));
            setForeground(Color.BLACK);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(true);
            setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(getBackground().brighter());
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(new Color(215, 225, 240));
                }
            });
        }
    }

    static class RoundedBorder implements Border {
        private int radius;
        private Color color;
        private int thickness;
        RoundedBorder(int radius, Color color, int thickness) {
            this.radius = radius;
            this.color = color;
            this.thickness = thickness;
        }
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius, this.radius, this.radius, this.radius);
        }
        @Override
        public boolean isBorderOpaque() {
            return false;
        }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(this.color);
            g.drawRoundRect(x, y, width - this.thickness, height - this.thickness, this.radius, this.radius);
        }
    }
}
