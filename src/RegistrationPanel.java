import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RegistrationPanel extends JPanel {
    private JTextField firstNameField = new JTextField(10);
    private JTextField lastNameField = new JTextField(10);
    private JTextField usernameField = new JTextField(10);
    private JPasswordField passwordField = new JPasswordField(10);
    private JButton registerButton = new JButton("Register");
    private JButton cancelButton = new JButton("Cancel");
    private ClientGUI mainFrame;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public RegistrationPanel(ClientGUI frame, ObjectOutputStream oos, ObjectInputStream ois) {
        this.mainFrame = frame;
        this.oos = oos;
        this.ois = ois;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(ClientGUI.RICH_LIGHT_BLUE);
        JLabel logo = mainFrame.createLogoLabel();
        add(logo);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(frame.createRow("First Name:", firstNameField));
        add(frame.createRow("Last Name:", lastNameField));
        add(frame.createRow("Username:", usernameField));
        add(frame.createRow("Password:", passwordField));
        add(frame.createButtonPanel(registerButton, cancelButton));
        registerButton.addActionListener(e -> register());
        cancelButton.addActionListener(e -> mainFrame.switchToLogin());
    }

    private void register() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "At least one of your fields are invalid.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            oos.writeObject("2");
            oos.writeObject(firstName);
            oos.writeObject(lastName);
            oos.writeObject(username);
            oos.writeObject(password);
            String response = (String) ois.readObject();
            if ("Account created successfully!".equals(response.trim())) {
                JOptionPane.showMessageDialog(this, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                String strUser = (String) ois.readObject();
                User user = UsersManager.stringToUser(strUser);
                mainFrame.setUser(user);
                mainFrame.switchToUserMenu();
            } else {
                JOptionPane.showMessageDialog(this, response, "Registration Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Registration Error", JOptionPane.ERROR_MESSAGE);
        } catch (SMPException e) {
            throw new RuntimeException(e);
        }
    }

    public void resetFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        usernameField.setText("");
        passwordField.setText("");
    }
}
