import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * CS18000 -- Project 5 -- Phase 3
 * User menu frame.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version April 28, 2024
 */
public class UserMenuPanel extends JPanel {
    private ClientGUI mainFrame;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public UserMenuPanel(ClientGUI frame, ObjectOutputStream oos, ObjectInputStream ois) {
        configureLookAndFeel();
        this.mainFrame = frame;
        this.oos = oos;
        this.ois = ois;
        initializePanel();
    }

    private void configureLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(200, 220, 240));
        setBorder(BorderFactory.createEmptyBorder(7, 17, 7, 17));
        addComponentsToPanel();
    }

    private void addComponentsToPanel() {
        add(createLogoLabel());
        add(Box.createVerticalStrut(10)); // Space between logo and button panel
        add(createButtonPanel());
        add(Box.createVerticalStrut(60)); // Increased space before the logout button
        add(createLogoutButton()); // Adds the logout panel
    }


    private JPanel createLogoLabel() {
        JPanel logoLabel = mainFrame.createLogoLabel();
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return logoLabel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        buttonPanel.setBackground(getBackground());

        String[] buttonLabels = {"View My Posts", "View Feed", "Search Profile", "Add Friend", "Remove Friend", "Block Friend"};
        for (String label : buttonLabels) {
            JButton button = createButton(label);
            button.addActionListener(createActionListenerForButton(label));
            buttonPanel.add(button);
        }

        return buttonPanel;
    }


    private JButton createButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                if (!isOpaque()) {
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(isRolloverEnabled() && getModel().isRollover() ? getBackground().brighter() : getBackground());
                    g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));
                }
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setBackground(new Color(230, 240, 250));
        button.setForeground(new Color(50, 60, 70));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
        return button;
    }

    private JPanel createLogoutButton() {
        JPanel logoutPanel = new JPanel();
        logoutPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        logoutPanel.setBackground(getBackground());
        JButton logoutButton = createButton("Logout");
        logoutButton.setText("<html><font color='red'>Logout</font></html>");
        logoutButton.addActionListener(e -> logout());
        logoutButton.setFont(new Font("Arial", Font.BOLD, 12));
        logoutButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        logoutPanel.add(logoutButton);
        return logoutPanel;
    }



    private ActionListener createActionListenerForButton(String label) {
        switch (label) {
            case "View My Posts":
                return e -> viewPosts();
            case "View Feed":
                return e -> viewFeed();
            case "Search Profile":
                return e -> searchProfile();
            case "Add Friend":
                return e -> sendAddFriendCommand(promptUsername("Add Friend: Enter username"));
            case "Remove Friend":
                return e -> sendRemoveFriendCommand(promptUsername("Remove Friend: Enter username"));
            case "Block Friend":
                return e -> sendBlockFriendCommand(promptUsername("Block Friend: Enter username"));
            default:
                throw new IllegalArgumentException("No action defined for: " + label);
        }
    }

    private String promptUsername(String message) {
        return JOptionPane.showInputDialog(this, message);
    }

    private void viewPosts() {
        try {
            mainFrame.switchToViewPosts();
            revalidateAndRepaint();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void viewFeed() {
        try {
            mainFrame.switchToViewFeed(mainFrame.getUser());
            revalidateAndRepaint();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void searchProfile() {
        mainFrame.switchToSearchUser();
        revalidateAndRepaint();
    }

    private void revalidateAndRepaint() {
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void sendAddFriendCommand(String username) {
        performNetworkAction("1", username, "Add Friend");
    }

    private void sendRemoveFriendCommand(String username) {
        performNetworkAction("2", username, "Remove Friend");
    }

    private void sendBlockFriendCommand(String username) {
        performNetworkAction("3", username, "Block Friend");
    }

    private void logout() {
        try {
            oos.writeObject("8");
            JOptionPane.showMessageDialog(this, "You have been logged out.");
            mainFrame.reset();
            this.oos = mainFrame.getOos();
            this.ois = mainFrame.getOis();
            mainFrame.switchToLogin();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error during logout: " + ex.getMessage());
        }
    }

    private void performNetworkAction(String command, String username, String actionDescription) {
        try {
            this.oos.writeObject(command);
            this.oos.flush();
            this.oos.writeObject(username);
            String response = (String) ois.readObject();
            if (response.equals("Success")) {
                JOptionPane.showMessageDialog(this, actionDescription + " Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, actionDescription + " Failed: " + response, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Error during " + actionDescription + ": " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
