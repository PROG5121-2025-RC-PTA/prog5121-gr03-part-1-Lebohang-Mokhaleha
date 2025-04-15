import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class SignUpForm extends JFrame {
    private JTextField nameField, usernameField, numberField;
    private JPasswordField passwordField;
    private JButton createAccountButton;

    JFrame frame = new JFrame("Snappy Talk");

    public SignUpForm() {
        frame.setTitle("Sign up to Snappy Talk");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //set same size as first class
        frame.setSize(400, 700);
       
 
//set same logo image
        ImageIcon logoIcon = new ImageIcon("img/logo.png");
        frame.setIconImage(logoIcon.getImage());

 //set layout manager of the frame
        frame.setLayout(new BorderLayout());
//AI assistance with GridBagConstraints
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gridBag = new GridBagConstraints();
        gridBag.insets = new Insets(15, 20, 15, 20);
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);

        //user name Field
        gridBag.gridy = 1;
        formPanel.add(createLabeledField("Username:", usernameField = new JTextField(20), labelFont, inputFont), gridBag);

        //phone Number
        gridBag.gridy = 2;
        formPanel.add(createLabeledField("Phone Number:", numberField = new JTextField(20), labelFont, inputFont), gridBag);

    //password Field
        gridBag.gridy = 3;
        formPanel.add(createLabeledField("Password:", passwordField = new JPasswordField(20), labelFont, inputFont), gridBag);

        //create Account Button
        createAccountButton = new JButton("Create Account");
        createAccountButton.setBackground(new Color(255, 105, 97));
        createAccountButton.setForeground(Color.WHITE);

 
        createAccountButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createAccountButton.addActionListener(e -> handleSignUp());

        gridBag.gridy = 4;
        formPanel.add(createAccountButton, gridBag);

        frame.add(formPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createLabeledField(String labelText, JTextField field, Font labelFont, Font inputFont) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JLabel label = new JLabel(labelText);
        panel.add(label, BorderLayout.NORTH);

        field.setBorder(new CompoundBorder(new RoundedBorder(10), new EmptyBorder(8, 10, 8, 10)));
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    class RoundedBorder extends AbstractBorder {
        private final int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        //AI assisted
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(220, 220, 220));
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }
//AI assisted
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.set(radius, radius, radius, radius);
            return insets;
        }
    }

    private void handleSignUp() {
        
        String username = usernameField.getText().trim();
        String phone = numberField.getText().trim();  // Treated as phone number here
        String password = new String(passwordField.getPassword());

//Using JOption to display error messages
        if (!isValidUsername(username)) {
            JOptionPane.showMessageDialog(frame, "Username must be at least 3 letters and contain only letters.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidSouthAfricanPhone(phone)) {
            JOptionPane.showMessageDialog(frame, "Enter a valid South African phone number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidPassword(password)) {
            JOptionPane.showMessageDialog(frame, "Password must be at least 8 characters long and include a digit, uppercase, and lowercase letter.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(frame, "Account Created Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        frame.dispose();
    }

    // Validation methods
    //Chatgpt assisted
    private boolean isValidUsername(String username) {
        return username.matches("^[A-Za-z]{3,}$");
    }
//chatgpt assisted
    private boolean isValidSouthAfricanPhone(String phone) {
        return phone.matches("^(\\+27|27|0)[6-8][0-9]{8}$");
    }
//ChatGPT assisted
    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignUpForm::new);
    }
}
