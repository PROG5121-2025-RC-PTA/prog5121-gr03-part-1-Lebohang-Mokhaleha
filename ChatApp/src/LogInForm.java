import javax.swing.*;
import java.awt.*;

public class LogInForm {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    
    public LogInForm() {
        frame = new JFrame("LogIn to Snappy Talk");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 700);
        
        ImageIcon logoIcon = new ImageIcon("img/logo.png");
        frame.setIconImage(logoIcon.getImage());
        
        frame.setLayout(new BorderLayout());
        
        //form panel setup
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);
        
        //username field
        usernameField = new JTextField(20);
        gbc.gridy = 1;
        formPanel.add(createLabeledField("Username:", usernameField, labelFont, inputFont), gbc);
        
        //password field
        passwordField = new JPasswordField(20);
        gbc.gridy = 2;
        formPanel.add(createLabeledField("Password:", passwordField, labelFont, inputFont), gbc);
        
        //login button
        loginButton = new JButton("LogIn");
        loginButton.setBackground(new Color(255, 105, 97));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(e -> handleLogIn());
        
        gbc.gridy = 3;
        formPanel.add(loginButton, gbc);
        
        frame.add(formPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    
    private JPanel createLabeledField(String labelText, JComponent field, Font labelFont, Font inputFont) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        field.setFont(inputFont);
        
        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }
    
    private void handleLogIn() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        //basic validation
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter both username and password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //in real app this would check against database
        int choice = JOptionPane.showConfirmDialog(frame, 
            "Login successful! Welcome back, " + username + "!\n\nWould you like to start chatting?", 
            "Login Successful", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.INFORMATION_MESSAGE);
        
        if (choice == JOptionPane.YES_OPTION) {
            //create user object and open messages
            User currentUser = new User("User", "Name", username, "0123456789");
            frame.dispose();
            new Messages(currentUser);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LogInForm::new);
    }
}