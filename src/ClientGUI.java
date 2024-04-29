import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import javax.imageio.ImageIO;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;
import java.awt.*;
/**
 * CS18000 -- Project 5 -- Phase 3
 * GUI mainframe for the Client.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version April 28, 2024
 */
public class ClientGUI extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel = new JPanel(cardLayout);
    private LoginPanel loginPanel;
    private RegistrationPanel registrationPanel;
    private UserMenuPanel userMenuPanel;
    private ViewPostsPanel viewPostsPanel;
    private SearchUserPanel searchUserPanel;
    private ShowUserPostsPanel showUserPosts;
    private ViewCommentsPanel viewComments;
    private ViewFeedPanel viewFeedPanel;
    private ViewFeedCommentsPanel viewFeedCommentsPanel;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private User user;
    public static final Color DEEP_BLUE = new Color(10, 25, 49);
    public static final Color RICH_LIGHT_BLUE = new Color(200, 220, 240);
    public static final Font MAIN_FONT = new Font("Arial", Font.PLAIN, 14);
    private String hostname;
    private int port;

    public ObjectOutputStream getOos() {
        return oos;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public ClientGUI(String hostname, int port)
            throws UnsupportedLookAndFeelException,
            ClassNotFoundException,
            InstantiationException,
            IllegalAccessException {
        this.hostname = hostname;
        this.port = port;
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        setTitle("Social Media Platform");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        try {
            socket = new Socket(hostname, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error connecting to server: " + e.getMessage(),
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        setupUI();
    }

    private void setupUI() {
        loginPanel = new LoginPanel(this, oos, ois);
        registrationPanel = new RegistrationPanel(this, oos, ois);
        userMenuPanel = new UserMenuPanel(this, oos, ois);
        cardPanel.setBackground(DEEP_BLUE);
        cardPanel.add(loginPanel, "Login");
        cardPanel.add(registrationPanel, "Registration");
        cardPanel.add(userMenuPanel, "UserMenu");
        add(cardPanel);
        cardLayout.show(cardPanel, "Login");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void switchToRegistration() {
        cardPanel.add(registrationPanel, "Registration");
        registrationPanel.resetFields();
        this.setSize(500, 450);
        cardLayout.show(cardPanel, "Registration");
    }

    public void switchToLogin() {
        cardPanel.add(loginPanel, "Login");
        loginPanel.resetFields();
        this.setSize(500, 400);
        cardLayout.show(cardPanel, "Login");
    }

    public void switchToUserMenu() {
        this.setSize(500, 400);
        cardLayout.show(cardPanel, "UserMenu");
    }

    public void switchToViewPosts() throws SMPException {
        viewPostsPanel = new ViewPostsPanel(this, oos, ois);
        cardPanel.add(viewPostsPanel, "ViewPosts");
        viewPostsPanel.refreshPosts();
        this.setSize(600, 500);
        cardLayout.show(cardPanel, "ViewPosts");
        cardPanel.revalidate();
        cardPanel.repaint();
    }

    public void switchToSearchUser() {
        searchUserPanel = new SearchUserPanel(this, oos, ois);
        cardPanel.add(searchUserPanel, "SearchUser");
        this.setSize(600, 500);
        cardLayout.show(cardPanel, "SearchUser");
        cardPanel.revalidate();
        cardPanel.repaint();
    }

    public void switchToViewComments(Post post) throws SMPException {
        viewComments = new ViewCommentsPanel(this, oos, ois, post);
        cardPanel.add(viewComments, "ViewComments");
        this.setSize(600, 500);
        cardLayout.show(cardPanel, "ViewComments");
        cardPanel.revalidate();
        cardPanel.repaint();
        viewComments.refreshComments();
    }

    public void switchToViewFeed(User thisuser) throws Exception {
        viewFeedPanel = new ViewFeedPanel(this, oos, ois, thisuser);
        cardPanel.add(viewFeedPanel, "ViewFeed");
        this.setSize(600, 500);
        cardLayout.show(cardPanel, "ViewFeed");
        cardPanel.revalidate();
        cardPanel.repaint();
        viewFeedPanel.refreshFeed();
    }

    public void switchToViewFeedComments(Post post) throws Exception {
        viewFeedCommentsPanel = new ViewFeedCommentsPanel(this, oos, ois, post);
        cardPanel.add(viewFeedCommentsPanel, "ViewFeedComments");
        this.setSize(600, 500);
        cardLayout.show(cardPanel, "ViewFeedComments");
        cardPanel.revalidate();
        cardPanel.repaint();
        viewFeedCommentsPanel.refreshComments();
    }

    public void resetAllPanels(ObjectOutputStream thisoos, ObjectInputStream thisois) throws SMPException {
        loginPanel = new LoginPanel(this, thisoos, thisois);
        registrationPanel = new RegistrationPanel(this, thisoos, thisois);
        userMenuPanel = new UserMenuPanel(this, thisoos, thisois);
        this.oos = thisoos;
        this.ois = thisois;
    }

    private void closeConnection() {
        try {
            if (ois != null) {
                ois.close();
            }
            if (oos != null) {
                oos.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException ex) {
            System.err.println("Error closing connection: " + ex.getMessage());
        }
    }

    private void createConnection(String thishostname, int thisport) {
        try {
            socket = new Socket(thishostname, thisport);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            resetAllPanels(oos, ois);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reconnecting to server: " + e.getMessage(),
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
        } catch (SMPException e) {
            throw new RuntimeException(e);
        }
    }

    public void reset() {
        closeConnection();
        createConnection("localhost", 8080);
    }

    public JPanel createLogoLabel() {
        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.LINE_AXIS));
        logoPanel.setBackground(RICH_LIGHT_BLUE);
        try {
            BufferedImage logo = ImageIO.read(new File("logo.png"));
            ImageIcon icon = new ImageIcon(logo.getScaledInstance(80, 80, Image.SCALE_SMOOTH));
            JLabel logoLabel = new JLabel(icon);
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            logoPanel.add(logoLabel);
        } catch (IOException e) {
            System.err.println("Logo file not found.");
            logoPanel.add(new JLabel("Logo Not Found"));
        }
        JLabel textLabel = new JLabel("TextFeed");
        textLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        textLabel.setForeground(new Color(50, 60, 70));
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        textLabel.setOpaque(false);
        textLabel.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
        textLabel.setBackground(new Color(230, 240, 250));

        logoPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        logoPanel.add(textLabel);

        return logoPanel;
    }



    public JPanel createRow(String label, JComponent component) {
        JPanel row = new JPanel();
        row.setLayout(new FlowLayout(FlowLayout.LEFT));
        row.setBackground(RICH_LIGHT_BLUE);
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(MAIN_FONT);
        jLabel.setForeground(DEEP_BLUE);
        component.setFont(MAIN_FONT);
        component.setForeground(DEEP_BLUE);
        if (component instanceof JTextField || component instanceof JTextArea) {
            ((JTextComponent) component).setCaretColor(DEEP_BLUE);
        }
        row.add(jLabel);
        row.add(component);
        return row;
    }

    public JPanel createButtonPanel(JButton... buttons) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(RICH_LIGHT_BLUE);
        for (JButton button : buttons) {
            button.setFont(MAIN_FONT);
            button.setBackground(RICH_LIGHT_BLUE);
            button.setForeground(DEEP_BLUE);
            button.setFocusPainted(false);
            button.setBorder(new RoundedBorder(5));
            buttonPanel.add(button);
        }
        return buttonPanel;
    }

    /**
     * Custom border class with rounded corners.
     */
    static class RoundedBorder implements Border {
        private int radius;
        RoundedBorder(int radius) {
            this.radius = radius;
        }
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius, this.radius + 5, this.radius, this.radius + 5);
        }
        @Override
        public boolean isBorderOpaque() {
            return false;
        }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(c.getBackground());
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new ClientGUI("localhost", 8080).setVisible(true);
            } catch (UnsupportedLookAndFeelException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
