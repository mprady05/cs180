import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * CS18000 -- Project 5 -- Phase 3
 * Search User frame.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version April 28, 2024
 */
public class SearchUserPanel extends JPanel {
    private ClientGUI mainFrame;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private JTextField searchField;
    private JButton searchButton;
    private JButton viewPostsButton;
    private JPanel userCardPanel;
    private User foundUser;

    public SearchUserPanel(ClientGUI frame, ObjectOutputStream oos, ObjectInputStream ois) {
        this.mainFrame = frame;
        this.oos = oos;
        this.ois = ois;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(200, 220, 240));
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        backButton.setBackground(new Color(135, 206, 250));
        backButton.setOpaque(true);
        backButton.addActionListener(e -> mainFrame.switchToUserMenu());
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setOpaque(false);
        searchField = new JTextField(20);
        searchField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        searchButton = new JButton("Search");
        searchButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        searchButton.addActionListener(e -> {
            try {
                String username = searchField.getText().trim();
                searchProfile(username);
            } catch (SMPException ex) {
                throw new RuntimeException(ex);
            }
        });
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
        userCardPanel = new JPanel();
        userCardPanel.setLayout(new BoxLayout(userCardPanel, BoxLayout.Y_AXIS));
        userCardPanel.setOpaque(false);
        userCardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(userCardPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void searchProfile(String username) throws SMPException {
        UsersManager.readUsersDatabaseFile();
        try {
            oos.writeObject("6");
            oos.writeObject(username);
            String response = (String) ois.readObject();
            if (response.equals(ClientHandler.VIEW_PROFILE_SUCCESS)) {
                String user = (String) ois.readObject();
                foundUser = UsersManager.searchUser(user.trim());
                displayUserDetails(foundUser);
            } else {
                JOptionPane.showMessageDialog(this, "Profile not found.");
            }
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Failed to retrieve profile: " + ex.getMessage());
        }
    }

    private void displayUserDetails(User user) {
        userCardPanel.removeAll();
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createTitledBorder("User Profile"));
        card.setBackground(Color.WHITE);
        card.setOpaque(true);
        JPanel userDetailsPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        userDetailsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        userDetailsPanel.setBackground(Color.WHITE);
        JLabel nameLabel = new JLabel("Name: " + user.getFirstName() + " " + user.getLastName());
        nameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        userDetailsPanel.add(nameLabel);
        JLabel usernameLabel = new JLabel("Username: " + user.getUsername());
        usernameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        userDetailsPanel.add(usernameLabel);
        card.add(userDetailsPanel, BorderLayout.CENTER);
        userCardPanel.add(card);
        userCardPanel.revalidate();
        userCardPanel.repaint();
    }
}
