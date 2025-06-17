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
        frame.setLocationRelativeTo(null); // Center the window
        
        // Try to load logo, but don't crash if it doesn't exist
        try {
            ImageIcon logoIcon = new ImageIcon("img/logo.png");
            if (logoIcon.getIconWidth() > 0) {
                frame.setIconImage(logoIcon.getImage());
            }
        } catch (Exception e) {
            System.out.println("Logo not found, using default icon");
        }
        
        frame.setLayout(new BorderLayout());
        
        // Form panel setup
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);
        
        // Username field
        usernameField = new JTextField(20);
        gbc.gridy = 1;
        formPanel.add(createLabeledField("Username:", usernameField, labelFont, inputFont), gbc);
        
        // Password field
        passwordField = new JPasswordField(20);
        gbc.gridy = 2;
        formPanel.add(createLabeledField("Password:", passwordField, labelFont, inputFont), gbc);
        
        // Login button
        loginButton = new JButton("LogIn");
        loginButton.setBackground(new Color(255, 105, 97));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(e -> handleLogIn());
        
        gbc.gridy = 3;
        formPanel.add(loginButton, gbc);
        
        // Add sign up link
        JLabel signUpLabel = new JLabel("<html><u>Don't have an account? Sign up here</u></html>");
        signUpLabel.setForeground(new Color(0, 123, 255));
        signUpLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signUpLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                frame.dispose();
                new SignUpForm();
            }
        });
        
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 20, 15, 20);
        formPanel.add(signUpLabel, gbc);
        
        frame.add(formPanel, BorderLayout.CENTER);
        frame.setVisible(true);
        
        // Set focus to username field
        SwingUtilities.invokeLater(() -> usernameField.requestFocusInWindow());
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
        
        // Basic validation
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter both username and password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Simple validation (you might want to add more sophisticated validation)
        if (username.length() < 3) {
            JOptionPane.showMessageDialog(frame, "Username must be at least 3 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // In real app this would check against database
        int choice = JOptionPane.showConfirmDialog(frame, 
            "Login successful! Welcome back, " + username + "!\n\nWould you like to start chatting?", 
            "Login Successful", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.INFORMATION_MESSAGE);
        
        if (choice == JOptionPane.YES_OPTION) {
            // Create user object and open messages
            User currentUser = new User("User", "Name", username, "0123456789");
            frame.dispose();
            // Use the correct class name
            new EnhancedMessages(currentUser);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LogInForm::new);
    }
}