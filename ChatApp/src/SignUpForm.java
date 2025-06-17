import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class SignUpForm {
    private JTextField usernameField, numberField;
    private JPasswordField passwordField;
    private JButton createAccountButton;
    private JFrame frame;
    
    public SignUpForm() {
        frame = new JFrame("Sign up to Snappy Talk");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 700);
        
        //same logo as main window
        ImageIcon logoIcon = new ImageIcon("img/logo.png");
        frame.setIconImage(logoIcon.getImage());
        
        frame.setLayout(new BorderLayout());
        
        //form setup with grid layout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);
        
        //username input
        usernameField = new JTextField(20);
        gbc.gridy = 1;
        formPanel.add(createLabeledField("Username:", usernameField, labelFont, inputFont), gbc);
        
        //phone number input
        numberField = new JTextField(20);
        gbc.gridy = 2;
        formPanel.add(createLabeledField("Phone Number:", numberField, labelFont, inputFont), gbc);
        
        //password input
        passwordField = new JPasswordField(20);
        gbc.gridy = 3;
        formPanel.add(createLabeledField("Password:", passwordField, labelFont, inputFont), gbc);
        
        //create account button
        createAccountButton = new JButton("Create Account");
        createAccountButton.setBackground(new Color(255, 105, 97));
        createAccountButton.setForeground(Color.WHITE);
        createAccountButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        createAccountButton.setFocusPainted(false);
        createAccountButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createAccountButton.addActionListener(e -> handleSignUp());
        
        gbc.gridy = 4;
        formPanel.add(createAccountButton, gbc);
        
        frame.add(formPanel, BorderLayout.CENTER);
        frame.setVisible(true);
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
    
    //custom rounded border for inputs
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
        String username = usernameField.getText().trim();
        String phone = numberField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        //check if username is valid
        if (!isValidUsername(username)) {
            JOptionPane.showMessageDialog(frame, "Username must be at least 3 letters and contain only letters.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //check if phone number is valid
        if (!isValidSouthAfricanPhone(phone)) {
            JOptionPane.showMessageDialog(frame, "Enter a valid South African phone number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //check if password is strong enough
        if (!isValidPassword(password)) {
            JOptionPane.showMessageDialog(frame, "Password must be at least 8 characters long and include a digit, uppercase, and lowercase letter.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //success message and option to start chatting
        int choice = JOptionPane.showConfirmDialog(frame, 
            "Account Created Successfully!\n\nWould you like to start chatting now?", 
            "Success", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.INFORMATION_MESSAGE);
        
        if (choice == JOptionPane.YES_OPTION) {
            //create user and open messages
            User newUser = new User("User", "Name", username, phone);
            frame.dispose();
            new Messages(newUser);
        } else {
            frame.dispose();
        }
    }
    
    //username validation - only letters, at least 3 chars
    private boolean isValidUsername(String username) {
        return username.matches("^[A-Za-z]{3,}$");
    }
    
    //south african phone number validation
    private boolean isValidSouthAfricanPhone(String phone) {
        return phone.matches("^(\\+27|27|0)[6-8][0-9]{8}$");
    }
    
    //password validation - 8 chars min, mixed case, digit
    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUpForm::new);
    }
}