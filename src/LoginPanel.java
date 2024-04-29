import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * CS18000 -- Project 5 -- Phase 3
 * Login frame.
 *
 * @author Andrew Song, Archit Malviya, Pradyumn Malik, Isha Yanamandra
 * @version April 28, 2024
 */
public class LoginPanel extends JPanel {
    private JTextField usernameField = new JTextField(10);
    private JPasswordField passwordField = new JPasswordField(10);
    private JButton loginButton = new JButton("Login");
    private JButton registerButton = new JButton("Register");
    private ClientGUI mainFrame;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public LoginPanel(ClientGUI frame, ObjectOutputStream oos, ObjectInputStream ois) {
        this.mainFrame = frame;
        this.oos = oos;
        this.ois = ois;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(ClientGUI.RICH_LIGHT_BLUE);
        JPanel logo = frame.createLogoLabel();
        add(logo);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(frame.createRow("Username:", usernameField));
        add(frame.createRow("Password:", passwordField));
        add(frame.createButtonPanel(loginButton, registerButton));
        loginButton.addActionListener(e -> login());
        registerButton.addActionListener(e -> mainFrame.switchToRegistration());
        resetFields();
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "At least one of your fields are invalid.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            oos.writeObject("1");
            oos.writeObject(username);
            oos.writeObject(password);
            String response = (String) ois.readObject();
            System.out.println(response);
            if ("Login success!".equals(response.trim())) {
                String strUser = (String) ois.readObject();
                User user = UsersManager.stringToUser(strUser);
                mainFrame.setUser(user);
                mainFrame.switchToUserMenu();
            } else {
                JOptionPane.showMessageDialog(this, response, "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
        } catch (SMPException e) {
            throw new RuntimeException(e);
        }
    }

    public void resetFields() {
        usernameField.setText("");
        passwordField.setText("");
    }
}
