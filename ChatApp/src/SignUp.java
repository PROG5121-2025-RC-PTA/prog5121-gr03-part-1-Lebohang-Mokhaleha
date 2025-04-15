import javax.swing.JFrame;
import java.awt.*;
import javax.swing.*;

public class SignUp {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snappy Talk");

        //this is for the icon
        ImageIcon logoIcon = new ImageIcon("img/logo.png");//remember to import image
        frame.setVisible(true);//show the running frame. If FALSE, frame wont show up.
        frame.setIconImage(logoIcon.getImage());
        //close the app
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //set a size
        frame.setSize(400, 700);
 
        frame.setLayout(new BorderLayout());

        //welcome message goes here
        //set it on top-centre
        JLabel titleLabel = new JLabel("Welcome to Snappy Talk!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        
        titleLabel.setForeground(new Color(255, 105, 97));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Center the buttons
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JButton signUpButton = new JButton("Sign Up");
        //button features
        signUpButton.setBackground(new Color(255, 105, 97));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUpButton.setMaximumSize(new Dimension(200, 40));
        signUpButton.setPreferredSize(new Dimension(200, 40));

        JButton loginButton = new JButton("Log In");
       //button features
        loginButton.setBackground(Color.WHITE);
        loginButton.setForeground(new Color(255, 105, 97));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(255, 105, 97)));
        loginButton.setMaximumSize(new Dimension(200, 40));
        loginButton.setPreferredSize(new Dimension(200, 40));

        //link Sign Up button to SignUpForm
        signUpButton.addActionListener(e -> {
            frame.dispose(); //Close welcome window
            new SignUpForm(); //Open the sign-up form
        });
        
      //link Log In button to LogInForm
        loginButton.addActionListener(e -> {
            frame.dispose(); //Close welcome window
            new LogInForm(); //Open the sign-up form
        });

        //add space and buttons
        //assisted by chatGpt
       centerPanel.add(Box.createVerticalGlue());
       centerPanel.add(signUpButton);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(loginButton);
        centerPanel.add(Box.createVerticalGlue());

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
      
    }
}
