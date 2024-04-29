import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * CS18000 -- Project 5 -- Phase 3
 * View feed frame.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version April 28, 2024
 */
public class ViewFeedPanel extends JPanel {
    private ClientGUI mainFrame;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private JPanel postPanel;
    private User user;

    public ViewFeedPanel(ClientGUI frame, ObjectOutputStream oos, ObjectInputStream ois, User user) {
        this.mainFrame = frame;
        this.oos = oos;
        this.ois = ois;
        this.user = user;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(ClientGUI.RICH_LIGHT_BLUE);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        northPanel.setBackground(getBackground());
        northPanel.add(createTopPanel(), BorderLayout.CENTER);
        add(northPanel, BorderLayout.NORTH);

        postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
        postPanel.setBackground(getBackground());
        postPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(postPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(getBackground());
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
    }


    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton backButton = new JButton("Back");
        styleButton(backButton);
        backButton.addActionListener(e -> mainFrame.switchToUserMenu());
        topPanel.add(backButton, BorderLayout.WEST);
        JLabel titleLabel = new JLabel("Your Feed");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        JButton refreshButton = new JButton("Refresh");
        styleButton(refreshButton);
        refreshButton.addActionListener(e -> {
            refreshButton.setEnabled(false);
            try {
                refreshFeed();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            refreshButton.setEnabled(true);
        });
        topPanel.add(refreshButton, BorderLayout.EAST);
        return topPanel;
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(90, 25));
        button.setBackground(ClientGUI.RICH_LIGHT_BLUE);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
    }

    public void refreshFeed() throws Exception {
        UsersManager.readUsersDatabaseFile();
        PostsManager.readPostsDatabaseFile();
        CommentsManager.readCommentsDatabaseFile();
        postPanel.removeAll();
        try {
            oos.writeObject("getFriendsPosts");
            oos.writeObject(user.getUsername());
            oos.flush();
            ArrayList<Post> posts = new ArrayList<>();
            String postId = (String) ois.readObject();
            while (!postId.equals("end")) {
                posts.add(PostsManager.searchPost(postId));
                postId = (String) ois.readObject();
            }

            for (Post post : posts) {
                JPanel postCard = createPostCard(post);
                postPanel.add(postCard);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        postPanel.revalidate();
        postPanel.repaint();
    }

    private JPanel createPostCard(Post post) {
        JPanel card = new JPanel(new BorderLayout(5, 10));
        card.setBorder(new RoundedBorder(10, Color.LIGHT_GRAY, 2));
        card.setBackground(ClientGUI.RICH_LIGHT_BLUE);

        JLabel contentLabel = new JLabel("Post: " +
                post.getContent().substring(0, Math.min(post.getContent().length(), 20)) + "...");
        contentLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        card.add(contentLabel, BorderLayout.NORTH);

        JTextArea contentArea = new JTextArea(post.getContent());
        contentArea.setEditable(false);
        contentArea.setWrapStyleWord(true);
        contentArea.setLineWrap(true);
        contentArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        contentArea.setBackground(card.getBackground());
        contentArea.setOpaque(true);
        card.add(contentArea, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(card.getBackground());

        // Style and add buttons
        JButton upvoteButton = new JButton("Upvote (" + post.getUpvotes() + ")");
        styleButton(upvoteButton);
        upvoteButton.addActionListener(e -> handleUpvote(post));
        buttonPanel.add(upvoteButton);

        JButton downvoteButton = new JButton("Downvote (" + post.getDownvotes() + ")");
        styleButton(downvoteButton);
        downvoteButton.addActionListener(e -> handleDownvote(post));
        buttonPanel.add(downvoteButton);

        JButton viewCommentsButton = new JButton("View Comments");
        styleButton(viewCommentsButton);
        viewCommentsButton.addActionListener(e -> {
            try {
                viewComments(post);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        buttonPanel.add(viewCommentsButton);
        card.add(buttonPanel, BorderLayout.SOUTH);

        return card;
    }


    private void handleUpvote(Post post) {
        try {
            oos.writeObject("upvotePost");
            oos.writeObject(post.getPostId());
            refreshFeed();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error upvoting post: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void handleDownvote(Post post) {
        try {
            oos.writeObject("downvotePost");
            oos.writeObject(post.getPostId());
            refreshFeed();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error downvoting post: " + e.getMessage());
        }
    }

    private void viewComments(Post post) throws Exception {
        mainFrame.switchToViewFeedComments(post);
    }

    /**
     * CS18000 -- Project 5 -- Phase 3
     * Creates a rounded border
     *
     * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
     * @version April 28, 2024
     */
    class RoundedBorder implements Border {
        private int radius;
        private Color borderColor;
        private int borderWidth;

        public RoundedBorder(int radius, Color borderColor, int borderWidth) {
            this.radius = radius;
            this.borderColor = borderColor;
            this.borderWidth = borderWidth;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.borderWidth, this.borderWidth, this.borderWidth, this.borderWidth);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(ClientGUI.RICH_LIGHT_BLUE);
            g2d.fillRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2d.setColor(borderColor);
            g2d.setStroke(new BasicStroke(borderWidth));
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

}
