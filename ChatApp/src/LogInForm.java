//most of the code in this class is copied from the SignUpForm.Java
import javax.swing.*;
import java.awt.*;

public class LogInForm {

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginAccountButton;

    public LogInForm() {
        frame = new JFrame("LogIn to Snappy Talk");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 700);

        ImageIcon logoIcon = new ImageIcon("img/logo.png");
        frame.setIconImage(logoIcon.getImage());

        frame.setLayout(new BorderLayout());

        // Create and add the form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gridBag = new GridBagConstraints();
        gridBag.insets = new Insets(15, 20, 15, 20);
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);

        //enter your user name
        usernameField = new JTextField(20);
        gridBag.gridy = 1;
        formPanel.add(createLabeledField("Username:", usernameField, labelFont, inputFont), gridBag);

        //enter saved password
        passwordField = new JPasswordField(20);
        gridBag.gridy = 3;
        formPanel.add(createLabeledField("Password:", passwordField, labelFont, inputFont), gridBag);

        //login your account button
        loginAccountButton = new JButton("LogIn");
        loginAccountButton.setBackground(new Color(255, 105, 97));
        loginAccountButton.setForeground(Color.WHITE);
        loginAccountButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginAccountButton.setFocusPainted(false);

        loginAccountButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginAccountButton.addActionListener(e -> handleSignUp());

        gridBag.gridy = 4;
        formPanel.add(loginAccountButton, gridBag);

        frame.add(formPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createLabeledField(String labelText, JComponent field, Font labelFont, Font inputFont) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        field.setFont(inputFont);

        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private void handleSignUp() {
        // You can add your validation logic here later
        JOptionPane.showMessageDialog(frame, "Welcome back");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LogInForm::new);
    }
}
