import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Messages extends JFrame {
    private JTextField recipientField;
    private JTextArea messageArea;
    private JButton sendButton;
    private JButton viewMessagesButton;
    private JButton clearButton;
    private JLabel messageCountLabel;
    private JLabel characterCountLabel;
    private User currentUser;
    private List<String> sentMessages;
    
    public Messages(User user) {
        this.currentUser = user;
        this.sentMessages = new ArrayList<>();
        initializeGUI();
        createMenuBar();
    }
    
    //constructor for testing
    public Messages() {
        this.currentUser = new User("Test", "User", "testuser", "0123456789");
        this.sentMessages = new ArrayList<>();
        initializeGUI();
        createMenuBar();
    }
    
    private void initializeGUI() {
        setTitle("Snappy Talk - Messages");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        
        //main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        //header with welcome message
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel welcomeLabel = new JLabel(String.format("Welcome, %s %s!", 
            currentUser.firstName, currentUser.lastName));
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(welcomeLabel);
        
        //message composition area
        JPanel composePanel = new JPanel(new GridBagLayout());
        composePanel.setBorder(BorderFactory.createTitledBorder("Compose Message"));
        GridBagConstraints gbc = new GridBagConstraints();
        
        //recipient field
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        composePanel.add(new JLabel("Recipient Number:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        recipientField = new JTextField(20);
        recipientField.setToolTipText("Enter phone number (e.g., +27778905902 or 0123456789)");
        composePanel.add(recipientField, gbc);
        
        //message text area
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        composePanel.add(new JLabel("Message:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        messageArea = new JTextArea(8, 30);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setBorder(BorderFactory.createLoweredBevelBorder());
        
        //character counter
        messageArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateCharCount(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateCharCount(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateCharCount(); }
        });
        
        JScrollPane messageScrollPane = new JScrollPane(messageArea);
        composePanel.add(messageScrollPane, gbc);
        
        //character count display
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weighty = 0;
        characterCountLabel = new JLabel("Characters: 0/250");
        characterCountLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        composePanel.add(characterCountLabel, gbc);
        
        //button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        sendButton = new JButton("Send Message");
        sendButton.setBackground(new Color(0, 123, 255));
        sendButton.setForeground(Color.WHITE);
        sendButton.addActionListener(new SendMessageListener());
        
        clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearFields());
        
        viewMessagesButton = new JButton("View All Messages");
        viewMessagesButton.addActionListener(new ViewMessagesListener());
        
        buttonPanel.add(sendButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(viewMessagesButton);
        
        //status panel
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        messageCountLabel = new JLabel("Total Messages Sent: 0");
        statusPanel.add(messageCountLabel);
        
        //add all panels to main
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(composePanel, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(statusPanel, BorderLayout.SOUTH);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        setVisible(true);
        
        recipientField.requestFocusInWindow();
    }
    
    private void updateCharCount() {
        int charCount = messageArea.getText().length();
        characterCountLabel.setText("Characters: " + charCount + "/250");
        
        //color coding for character count
        if (charCount > 250) {
            characterCountLabel.setForeground(Color.RED);
        } else if (charCount > 200) {
            characterCountLabel.setForeground(Color.ORANGE);
        } else {
            characterCountLabel.setForeground(Color.BLACK);
        }
    }
    
    private void clearFields() {
        recipientField.setText("");
        messageArea.setText("");
        recipientField.requestFocusInWindow();
    }
    
    private void updateMessageCount() {
        messageCountLabel.setText("Total Messages Sent: " + sentMessages.size());
    }
    
    private class SendMessageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String recipient = recipientField.getText().trim();
            String messageText = messageArea.getText().trim();
            
            //validate recipient
            if (recipient.isEmpty()) {
                JOptionPane.showMessageDialog(Messages.this, 
                    "Please enter a recipient number.", 
                    "Missing Recipient", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            //validate message
            if (messageText.isEmpty()) {
                JOptionPane.showMessageDialog(Messages.this, 
                    "Please enter a message.", 
                    "Missing Message", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            //check message length
            if (messageText.length() > 250) {
                JOptionPane.showMessageDialog(Messages.this, 
                    "Message is too long. Maximum 250 characters allowed.", 
                    "Message Too Long", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            //simulate sending message
            String fullMessage = "To: " + recipient + "\nMessage: " + messageText + "\nSent at: " + new java.util.Date();
            sentMessages.add(fullMessage);
            
            JOptionPane.showMessageDialog(Messages.this, 
                "Message sent successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
            updateMessageCount();
            clearFields();
        }
    }
    
    private class ViewMessagesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (sentMessages.isEmpty()) {
                JOptionPane.showMessageDialog(Messages.this, 
                    "No messages sent yet.", 
                    "No Messages", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            StringBuilder allMessages = new StringBuilder();
            for (int i = 0; i < sentMessages.size(); i++) {
                allMessages.append("Message ").append(i + 1).append(":\n");
                allMessages.append(sentMessages.get(i)).append("\n\n");
            }
            
            //show messages in dialog
            JDialog messageDialog = new JDialog(Messages.this, "All Messages", true);
            messageDialog.setSize(500, 400);
            messageDialog.setLocationRelativeTo(Messages.this);
            
            JTextArea messageDisplay = new JTextArea(allMessages.toString());
            messageDisplay.setEditable(false);
            messageDisplay.setFont(new Font("Monospaced", Font.PLAIN, 12));
            
            JScrollPane scrollPane = new JScrollPane(messageDisplay);
            messageDialog.add(scrollPane);
            
            messageDialog.setVisible(true);
        }
    }
    
    //simple menu bar
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Messages());
    }
}