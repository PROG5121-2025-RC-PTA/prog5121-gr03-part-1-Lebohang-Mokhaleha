import javax.swing.*;
import java.awt.*;

public class SignUp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snappy Talk");
        
        //app icon
        ImageIcon logoIcon = new ImageIcon("img/logo.png");
        frame.setIconImage(logoIcon.getImage());
        
        //basic window setup
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 700);
        frame.setLayout(new BorderLayout());
        
        //title at the top
        JLabel titleLabel = new JLabel("Welcome to Snappy Talk!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(new Color(255, 105, 97));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
        frame.add(titleLabel, BorderLayout.NORTH);
        
        //center panel for buttons
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        
        //sign up button
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBackground(new Color(255, 105, 97));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUpButton.setMaximumSize(new Dimension(200, 40));
        signUpButton.setPreferredSize(new Dimension(200, 40));
        
        //login button
        JButton loginButton = new JButton("Log In");
        loginButton.setBackground(Color.WHITE);
        loginButton.setForeground(new Color(255, 105, 97));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(255, 105, 97)));
        loginButton.setMaximumSize(new Dimension(200, 40));
        loginButton.setPreferredSize(new Dimension(200, 40));
        
        //button actions
        signUpButton.addActionListener(e -> {
            frame.dispose();
            new SignUpForm();
        });
        
        loginButton.addActionListener(e -> {
            frame.dispose();
            new LogInForm();
        });
        
        //add spacing and buttons to center
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(signUpButton);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(loginButton);
        centerPanel.add(Box.createVerticalGlue());
        
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}