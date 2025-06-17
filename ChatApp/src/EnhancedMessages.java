import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EnhancedMessages extends JFrame {
    private JTextField recipientField;
    private JTextArea messageArea;
    private JButton sendButton;
    private JButton storeButton;
    private JButton disregardButton;
    private JButton viewReportButton;
    private JButton searchButton;
    private JButton deleteButton;
    private JLabel messageCountLabel;
    private JLabel characterCountLabel;
    private User currentUser;
    
    // Arrays for different message types
    private List<Message> sentMessages;
    private List<Message> storedMessages;
    private List<Message> disregardedMessages;
    private List<String> messageHashes;
    private List<Integer> messageIds;
    
    // Message counter for unique IDs
    private int nextMessageId = 1;
    
    public EnhancedMessages(User user) {
        this.currentUser = user;
        initializeArrays();
        initializeGUI();
        createMenuBar();
      
    }
    
    // Constructor for testing
    public EnhancedMessages() {
        this.currentUser = new User("Test", "User", "testuser", "0123456789");
        initializeArrays();
        initializeGUI();
        createMenuBar();
          }
    
    private void initializeArrays() {
        sentMessages = new ArrayList<>();
        storedMessages = new ArrayList<>();
        disregardedMessages = new ArrayList<>();
        messageHashes = new ArrayList<>();
        messageIds = new ArrayList<>();
    }
    
    
    
    private void initializeGUI() {
        setTitle("Snappy Talk - Enhanced Messages");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Header with welcome message
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel welcomeLabel = new JLabel(String.format("Welcome, %s %s!", 
            currentUser.firstName, currentUser.lastName));
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(welcomeLabel);
        
        // Message composition area
        JPanel composePanel = new JPanel(new GridBagLayout());
        composePanel.setBorder(BorderFactory.createTitledBorder("Compose Message"));
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Recipient field
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
        
        // Message text area
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        composePanel.add(new JLabel("Message:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        messageArea = new JTextArea(6, 30);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setBorder(BorderFactory.createLoweredBevelBorder());
        
        // Character counter
        messageArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateCharCount(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateCharCount(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateCharCount(); }
        });
        
        JScrollPane messageScrollPane = new JScrollPane(messageArea);
        composePanel.add(messageScrollPane, gbc);
        
        // Character count display
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weighty = 0;
        characterCountLabel = new JLabel("Characters: 0/250");
        characterCountLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        composePanel.add(characterCountLabel, gbc);
        
        // Button panels
        JPanel buttonPanel1 = new JPanel(new FlowLayout());
        JPanel buttonPanel2 = new JPanel(new FlowLayout());
        
        // Message action buttons
        sendButton = new JButton("Send Message");
        sendButton.setBackground(new Color(0, 123, 255));
        sendButton.setForeground(Color.WHITE);
        sendButton.addActionListener(e -> sendMessage());
        
        storeButton = new JButton("Store Message");
        storeButton.setBackground(new Color(255, 193, 7));
        storeButton.setForeground(Color.BLACK);
        storeButton.addActionListener(e -> storeMessage());
        
        disregardButton = new JButton("Disregard Message");
        disregardButton.setBackground(new Color(220, 53, 69));
        disregardButton.setForeground(Color.WHITE);
        disregardButton.addActionListener(e -> disregardMessage());
        
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearFields());
        
        buttonPanel1.add(sendButton);
        buttonPanel1.add(storeButton);
        buttonPanel1.add(disregardButton);
        buttonPanel1.add(clearButton);
        
        // Management buttons
        viewReportButton = new JButton("Display Report");
        viewReportButton.addActionListener(e -> displayReport());
        
        searchButton = new JButton("Search Messages");
        searchButton.addActionListener(e -> searchMessages());
        
        deleteButton = new JButton("Delete Message");
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(e -> deleteMessage());
        
        JButton findLongestButton = new JButton("Find Longest Message");
        findLongestButton.addActionListener(e -> findLongestMessage());
        
        buttonPanel2.add(viewReportButton);
        buttonPanel2.add(searchButton);
        buttonPanel2.add(deleteButton);
        buttonPanel2.add(findLongestButton);
        
        // Status panel
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        messageCountLabel = new JLabel();
        updateMessageCount();
        statusPanel.add(messageCountLabel);
        
        // Combine button panels
        JPanel allButtonsPanel = new JPanel(new GridLayout(2, 1));
        allButtonsPanel.add(buttonPanel1);
        allButtonsPanel.add(buttonPanel2);
        
        // Add all panels to main
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(composePanel, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(allButtonsPanel, BorderLayout.CENTER);
        bottomPanel.add(statusPanel, BorderLayout.SOUTH);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        setVisible(true);
        
        recipientField.requestFocusInWindow();
    }
    
    private void updateCharCount() {
        int charCount = messageArea.getText().length();
        characterCountLabel.setText("Characters: " + charCount + "/250");
        
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
        int totalMessages = sentMessages.size() + storedMessages.size() + disregardedMessages.size();
        messageCountLabel.setText("Total Messages: " + totalMessages + 
            " (Sent: " + sentMessages.size() + 
            ", Stored: " + storedMessages.size() + 
            ", Disregarded: " + disregardedMessages.size() + ")");
    }
    
    private void sendMessage() {
        if (validateMessage()) {
            String recipient = recipientField.getText().trim();
            String messageText = messageArea.getText().trim();
            
            Message message = new Message(nextMessageId++, recipient, messageText, "Sent");
            sentMessages.add(message);
            messageIds.add(message.getId());
            messageHashes.add(generateHash(messageText));
            
            JOptionPane.showMessageDialog(this, 
                "Message sent successfully!\nMessage ID: " + message.getId(), 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
            updateMessageCount();
            clearFields();
        }
    }
    
    private void storeMessage() {
        if (validateMessage()) {
            String recipient = recipientField.getText().trim();
            String messageText = messageArea.getText().trim();
            
            Message message = new Message(nextMessageId++, recipient, messageText, "Stored");
            storedMessages.add(message);
            messageIds.add(message.getId());
            messageHashes.add(generateHash(messageText));
            
            JOptionPane.showMessageDialog(this, 
                "Message stored successfully!\nMessage ID: " + message.getId(), 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
            updateMessageCount();
            clearFields();
        }
    }
    
    private void disregardMessage() {
        if (validateMessage()) {
            String recipient = recipientField.getText().trim();
            String messageText = messageArea.getText().trim();
            
            Message message = new Message(nextMessageId++, recipient, messageText, "Disregard");
            disregardedMessages.add(message);
            messageIds.add(message.getId());
            messageHashes.add(generateHash(messageText));
            
            JOptionPane.showMessageDialog(this, 
                "Message disregarded!\nMessage ID: " + message.getId(), 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
            updateMessageCount();
            clearFields();
        }
    }
    
    private boolean validateMessage() {
        String recipient = recipientField.getText().trim();
        String messageText = messageArea.getText().trim();
        
        if (recipient.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a recipient number.", 
                "Missing Recipient", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (messageText.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a message.", 
                "Missing Message", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (messageText.length() > 250) {
            JOptionPane.showMessageDialog(this, 
                "Message is too long. Maximum 250 characters allowed.", 
                "Message Too Long", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void displayReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== MESSAGE REPORT ===\n\n");
        
        report.append("SENT MESSAGES (" + sentMessages.size() + "):\n");
        report.append("=".repeat(30) + "\n");
        for (Message msg : sentMessages) {
            report.append("ID: " + msg.getId() + " | ");
            report.append("To: " + msg.getRecipient() + " | ");
            report.append("Hash: " + generateHash(msg.getMessage()) + "\n");
            report.append("Message: " + msg.getMessage() + "\n\n");
        }
        
        report.append("STORED MESSAGES (" + storedMessages.size() + "):\n");
        report.append("=".repeat(30) + "\n");
        for (Message msg : storedMessages) {
            report.append("ID: " + msg.getId() + " | ");
            report.append("To: " + msg.getRecipient() + " | ");
            report.append("Hash: " + generateHash(msg.getMessage()) + "\n");
            report.append("Message: " + msg.getMessage() + "\n\n");
        }
        
        report.append("DISREGARDED MESSAGES (" + disregardedMessages.size() + "):\n");
        report.append("=".repeat(30) + "\n");
        for (Message msg : disregardedMessages) {
            report.append("ID: " + msg.getId() + " | ");
            report.append("To: " + msg.getRecipient() + " | ");
            report.append("Hash: " + generateHash(msg.getMessage()) + "\n");
            report.append("Message: " + msg.getMessage() + "\n\n");
        }
        
        showMessageDialog("Full Message Report", report.toString());
    }
    
    private void searchMessages() {
        String[] options = {"Search by Message ID", "Search by Recipient"};
        int choice = JOptionPane.showOptionDialog(this, 
            "What would you like to search by?", 
            "Search Messages", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE, 
            null, options, options[0]);
        
        if (choice == 0) {
            searchByMessageId();
        } else if (choice == 1) {
            searchByRecipient();
        }
    }
    
    private void searchByMessageId() {
        String idStr = JOptionPane.showInputDialog(this, "Enter Message ID:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int searchId = Integer.parseInt(idStr.trim());
                Message found = findMessageById(searchId);
                
                if (found != null) {
                    String result = "Message Found!\n\n" +
                        "ID: " + found.getId() + "\n" +
                        "Recipient: " + found.getRecipient() + "\n" +
                        "Status: " + found.getStatus() + "\n" +
                        "Message: " + found.getMessage();
                    
                    JOptionPane.showMessageDialog(this, result, "Search Result", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Message with ID " + searchId + " not found.", 
                        "Not Found", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.", 
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void searchByRecipient() {
        String recipient = JOptionPane.showInputDialog(this, "Enter recipient number:");
        if (recipient != null && !recipient.trim().isEmpty()) {
            List<Message> found = findMessagesByRecipient(recipient.trim());
            
            if (!found.isEmpty()) {
                StringBuilder result = new StringBuilder();
                result.append("Found " + found.size() + " message(s) for " + recipient + ":\n\n");
                
                for (Message msg : found) {
                    result.append("ID: " + msg.getId() + " | Status: " + msg.getStatus() + "\n");
                    result.append("Message: " + msg.getMessage() + "\n\n");
                }
                
                showMessageDialog("Search Results", result.toString());
            } else {
                JOptionPane.showMessageDialog(this, "No messages found for recipient: " + recipient, 
                    "Not Found", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    private void deleteMessage() {
        String hashStr = JOptionPane.showInputDialog(this, 
            "Enter message hash to delete:\n(You can get the hash from the report)");
        
        if (hashStr != null && !hashStr.trim().isEmpty()) {
            boolean deleted = deleteMessageByHash(hashStr.trim());
            
            if (deleted) {
                JOptionPane.showMessageDialog(this, "Message deleted successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                updateMessageCount();
            } else {
                JOptionPane.showMessageDialog(this, "Message with that hash not found.", 
                    "Not Found", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    private void findLongestMessage() {
        Message longest = null;
        int maxLength = 0;
        
        // Check all message arrays
        List<List<Message>> allArrays = List.of(sentMessages, storedMessages, disregardedMessages);
        
        for (List<Message> messageList : allArrays) {
            for (Message msg : messageList) {
                if (msg.getMessage().length() > maxLength) {
                    maxLength = msg.getMessage().length();
                    longest = msg;
                }
            }
        }
        
        if (longest != null) {
            String result = "Longest Message Found!\n\n" +
                "ID: " + longest.getId() + "\n" +
                "Recipient: " + longest.getRecipient() + "\n" +
                "Status: " + longest.getStatus() + "\n" +
                "Length: " + longest.getMessage().length() + " characters\n\n" +
                "Message: " + longest.getMessage();
            
            showMessageDialog("Longest Message", result);
        } else {
            JOptionPane.showMessageDialog(this, "No messages found.", 
                "No Messages", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Helper methods
    private Message findMessageById(int id) {
        List<List<Message>> allArrays = List.of(sentMessages, storedMessages, disregardedMessages);
        
        for (List<Message> messageList : allArrays) {
            for (Message msg : messageList) {
                if (msg.getId() == id) {
                    return msg;
                }
            }
        }
        return null;
    }
    
    private List<Message> findMessagesByRecipient(String recipient) {
        List<Message> found = new ArrayList<>();
        List<List<Message>> allArrays = List.of(sentMessages, storedMessages, disregardedMessages);
        
        for (List<Message> messageList : allArrays) {
            for (Message msg : messageList) {
                if (msg.getRecipient().equals(recipient)) {
                    found.add(msg);
                }
            }
        }
        return found;
    }
    
    private boolean deleteMessageByHash(String hash) {
        List<List<Message>> allArrays = List.of(sentMessages, storedMessages, disregardedMessages);
        
        for (List<Message> messageList : allArrays) {
            for (int i = 0; i < messageList.size(); i++) {
                Message msg = messageList.get(i);
                if (generateHash(msg.getMessage()).equals(hash)) {
                    messageList.remove(i);
                    // Also remove from other arrays
                    messageIds.removeIf(id -> id.equals(msg.getId()));
                    messageHashes.remove(hash);
                    return true;
                }
            }
        }
        return false;
    }
    
    private String generateHash(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashInBytes = md.digest(message.getBytes());
            
            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString().substring(0, 8); // First 8 characters for readability
        } catch (NoSuchAlgorithmException e) {
            return String.valueOf(message.hashCode());
        }
    }
    
    private void showMessageDialog(String title, String message) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(600, 500);
        dialog.setLocationRelativeTo(this);
        
        JTextArea textArea = new JTextArea(message);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        dialog.add(scrollPane);
        
        dialog.setVisible(true);
    }
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        
        JMenu toolsMenu = new JMenu("Tools");
        JMenuItem runTestsItem = new JMenuItem("Run Unit Tests");
        runTestsItem.addActionListener(e -> runUnitTests());
        toolsMenu.add(runTestsItem);
        
        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);
        setJMenuBar(menuBar);
    }
    
    private void runUnitTests() {
        SwingUtilities.invokeLater(() -> {
            EnhancedMessageTester tester = new EnhancedMessageTester();
            tester.runAllTests();
            tester.showResults();
        });
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EnhancedMessages());
    }
}

// Message class to store message data
class Message {
    private int id;
    private String recipient;
    private String message;
    private String status;
    private java.util.Date timestamp;
    
    public Message(int id, String recipient, String message, String status) {
        this.id = id;
        this.recipient = recipient;
        this.message = message;
        this.status = status;
        this.timestamp = new java.util.Date();
    }
    
    // Getters
    public int getId() { return id; }
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }
    public String getStatus() { return status; }
    public java.util.Date getTimestamp() { return timestamp; }
    
    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", recipient='" + recipient + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}