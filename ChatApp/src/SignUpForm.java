import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class SignUpForm {
    private JTextField firstNameField, lastNameField, usernameField, numberField;
    private JPasswordField passwordField;
    private JButton createAccountButton;
    private JFrame frame;
    
    public SignUpForm() {
        frame = new JFrame("Sign up to Snappy Talk");
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
        
        // Form setup with grid layout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);
        
        // First name input
        firstNameField = new JTextField(20);
        gbc.gridy = 0;
        formPanel.add(createLabeledField("First Name:", firstNameField, labelFont, inputFont), gbc);
        
        // Last name input
        lastNameField = new JTextField(20);
        gbc.gridy = 1;
        formPanel.add(createLabeledField("Last Name:", lastNameField, labelFont, inputFont), gbc);
        
        // Username input
        usernameField = new JTextField(20);
        gbc.gridy = 2;
        formPanel.add(createLabeledField("Username:", usernameField, labelFont, inputFont), gbc);
        
        // Phone number input
        numberField = new JTextField(20);
        gbc.gridy = 3;
        formPanel.add(createLabeledField("Phone Number:", numberField, labelFont, inputFont), gbc);
        
        // Password input
        passwordField = new JPasswordField(20);
        gbc.gridy = 4;
        formPanel.add(createLabeledField("Password:", passwordField, labelFont, inputFont), gbc);
        
        // Create account button
        createAccountButton = new JButton("Create Account");
        createAccountButton.setBackground(new Color(255, 105, 97));
        createAccountButton.setForeground(Color.WHITE);
        createAccountButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        createAccountButton.setFocusPainted(false);
        createAccountButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createAccountButton.addActionListener(e -> handleSignUp());
        
        gbc.gridy = 5;
        formPanel.add(createAccountButton, gbc);
        
        // Add login link
        JLabel loginLabel = new JLabel("<html><u>Already have an account? Login here</u></html>");
        loginLabel.setForeground(new Color(0, 123, 255));
        loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                frame.dispose();
                new LogInForm();
            }
        });
        
        gbc.gridy = 6;
        gbc.insets = new Insets(5, 20, 15, 20);
        formPanel.add(loginLabel, gbc);
        
        frame.add(formPanel, BorderLayout.CENTER);
        frame.setVisible(true);
        
        // Set focus to first name field
        SwingUtilities.invokeLater(() -> firstNameField.requestFocusInWindow());
    }
    
    private JPanel createLabeledField(String labelText, JComponent field, Font labelFont, Font inputFont) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        panel.add(label, BorderLayout.NORTH);
        
        field.setFont(inputFont);
        field.setBorder(new CompoundBorder(new RoundedBorder(10), new EmptyBorder(8, 10, 8, 10)));
        panel.add(field, BorderLayout.CENTER);
        
        return panel;
    }
    
    // Custom rounded border for inputs
    class RoundedBorder extends AbstractBorder {
        private final int radius;
        
        RoundedBorder(int radius) {
            this.radius = radius;
        }
        
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(220, 220, 220));
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }
        
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.set(radius, radius, radius, radius);
            return insets;
        }
    }
    
    private void handleSignUp() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String username = usernameField.getText().trim();
        String phone = numberField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        // Check if all fields are filled
        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if first name is valid (only letters)
        if (!isValidName(firstName)) {
            JOptionPane.showMessageDialog(frame, "First name must contain only letters.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if last name is valid (only letters)
        if (!isValidName(lastName)) {
            JOptionPane.showMessageDialog(frame, "Last name must contain only letters.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if username is valid
        if (!isValidUsername(username)) {
            JOptionPane.showMessageDialog(frame, "Username must be at least 3 letters and contain only letters.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if phone number is valid
        if (!isValidSouthAfricanPhone(phone)) {
            JOptionPane.showMessageDialog(frame, "Enter a valid South African phone number (e.g., 0123456789, +27123456789).", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if password is strong enough
        if (!isValidPassword(password)) {
            JOptionPane.showMessageDialog(frame, "Password must be at least 8 characters long and include a digit, uppercase, and lowercase letter.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Success message and option to start chatting
        int choice = JOptionPane.showConfirmDialog(frame, 
            "Account Created Successfully!\n\nWelcome " + firstName + " " + lastName + "!\n\nWould you like to start chatting now?", 
            "Success", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.INFORMATION_MESSAGE);
        
        if (choice == JOptionPane.YES_OPTION) {
            // Create user and open messages
            User newUser = new User(firstName, lastName, username, phone);
            frame.dispose();
            // Use the correct class name
            new EnhancedMessages(newUser);
        } else {
            frame.dispose();
        }
    }
    
    // Name validation - only letters
    private boolean isValidName(String name) {
        return name.matches("^[A-Za-z]+$") && name.length() >= 2;
    }
    
    // Username validation - only letters, at least 3 chars (fixed from original 5)
    private boolean isValidUsername(String username) {
        return username.matches("^[A-Za-z]{3,}$");
    }
    
    // South African phone number validation - improved to handle more formats
    private boolean isValidSouthAfricanPhone(String phone) {
        // Remove spaces and common separators
        phone = phone.replaceAll("[-\\s()]", "");
        
        // Check for valid South African phone patterns
        return phone.matches("^(\\+27|27|0)[6-8][0-9]{8}$") || 
               phone.matches("^0[6-8][0-9]{8}$");
    }
    
    // Password validation - 8 chars min, mixed case, digit
    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUpForm::new);
    }
}