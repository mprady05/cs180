import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
/**
 * CS18000 -- Project 5 -- Phase 3
 * Show Posts frame.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version April 28, 2024
 */
public class ShowUserPostsPanel extends JPanel {
    private ClientGUI mainFrame;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private User user;
    private JPanel postsPanel;
    private JScrollPane scrollPane;
    private JButton refreshButton;

    public ShowUserPostsPanel(ClientGUI frame, ObjectOutputStream oos, ObjectInputStream ois, User user) throws SMPException {
        this.mainFrame = frame;
        this.oos = oos;
        this.ois = ois;
        this.user = user;
        initializeUI();
    }

    private void initializeUI() throws SMPException {
        setLayout(new BorderLayout());
        setBackground(new Color(200, 220, 240));
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        northPanel.add(createTopPanel(), BorderLayout.SOUTH);
        add(northPanel, BorderLayout.NORTH);
        postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));
        postsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane = new JScrollPane(postsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
        refreshPosts();
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> mainFrame.switchToUserMenu());
        backButton.setPreferredSize(new Dimension(90, 25));
        topPanel.add(backButton, BorderLayout.WEST);
        JLabel titleLabel = new JLabel(user.getFirstName() + "'s Posts");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            refreshButton.setEnabled(false);
            try {
                refreshPosts();
            } catch (SMPException ex) {
                throw new RuntimeException(ex);
            }
            refreshButton.setEnabled(true);
        });
        refreshButton.setPreferredSize(new Dimension(90, 25));
        topPanel.add(refreshButton, BorderLayout.EAST);
        return topPanel;
    }

    public void refreshPosts() throws SMPException {
        postsPanel.removeAll();
        UsersManager.readUsersDatabaseFile();
        PostsManager.readPostsDatabaseFile();
        ArrayList<String> usersPostIds = user.getPostIds();
        ArrayList<Post> usersPosts = new ArrayList<>();
        for (String id : usersPostIds) {
            usersPosts.add(PostsManager.searchPost(id));
        }
        for (Post post : usersPosts) {
            JPanel postCard = createPostCard(post.getContent());
            postsPanel.add(postCard);
        }
        postsPanel.revalidate();
        postsPanel.repaint();
    }

    private JPanel createPostCard(String content) {
        JPanel card = new JPanel(new BorderLayout(5, 10));
        card.setBorder(BorderFactory.createCompoundBorder(
                new ViewPostsPanel.RoundedBorder(10, Color.LIGHT_GRAY, 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        card.setBackground(new Color(200, 220, 240));
        JLabel postLabel = new JLabel("Post: " + content.substring(0, Math.min(content.length(), 20)) + "...");
        postLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
        card.add(postLabel, BorderLayout.NORTH);
        JTextArea contentArea = new JTextArea(content);
        contentArea.setEditable(false);
        contentArea.setWrapStyleWord(true);
        contentArea.setLineWrap(true);
        contentArea.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        contentArea.setBackground(card.getBackground());
        contentArea.setOpaque(true);
        contentArea.setPreferredSize(new Dimension(300, contentArea.getPreferredSize().height));
        card.add(contentArea, BorderLayout.CENTER);
        JButton viewPostButton = new JButton("View Post");
        viewPostButton.addActionListener(e -> viewPost(content));
        viewPostButton.setFont(new Font("Times New Roman", Font.BOLD, 12));
        viewPostButton.setPreferredSize(new Dimension(90, 25));
        viewPostButton.setBackground(new Color(200, 220, 240));
        card.add(viewPostButton, BorderLayout.SOUTH);
        return card;
    }

    private void viewPost(String content) {
        JOptionPane.showMessageDialog(this, "Viewing post: " + content);
    }

}
