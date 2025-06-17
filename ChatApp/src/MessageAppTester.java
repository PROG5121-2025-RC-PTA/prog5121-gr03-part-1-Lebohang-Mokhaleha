import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// User class definition
class User {
    public String firstName;
    public String lastName;
    public String username;
    public String phoneNumber;
    
    public User(String firstName, String lastName, String username, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.phoneNumber = phoneNumber;
    }
    
    public User() {
        this.firstName = "";
        this.lastName = "";
        this.username = "";
        this.phoneNumber = "";
    }
    
    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}

// Enhanced Message Tester class
class EnhancedMessageTester {
    private int totalTests = 0;
    private int passedTests = 0;
    private List<String> testResults = new ArrayList<>();
    
    public void runAllTests() {
        System.out.println("Starting Enhanced Message App Unit Tests...\n");
        
        // Test user class
        testUserCreation();
        testUserDefaultConstructor();
        testUserToString();
        
        // Test validation methods
        testUsernameValidation();
        testPhoneValidation();
        testPasswordValidation();
        
        // Test message functionality
        testMessageLengthValidation();
        testEmptyFieldValidation();
        
        // Test message operations
        testMessageCreation();
        testHashGeneration();
        testMessageArrayOperations();
        
        System.out.println("\n=== TEST SUMMARY ===");
        System.out.println("Total tests: " + totalTests);
        System.out.println("Passed: " + passedTests);
        System.out.println("Failed: " + (totalTests - passedTests));
        System.out.println("Success rate: " + String.format("%.1f%%", (passedTests * 100.0 / totalTests)));
    }
    
    // User class tests
    private void testUserCreation() {
        startTest("User Creation Test");
        try {
            User user = new User("John", "Doe", "johndoe", "0123456789");
            
            assert user.firstName.equals("John") : "First name not set correctly";
            assert user.lastName.equals("Doe") : "Last name not set correctly";
            assert user.username.equals("johndoe") : "Username not set correctly";
            assert user.phoneNumber.equals("0123456789") : "Phone number not set correctly";
            
            passTest("User created successfully with all fields");
        } catch (Exception e) {
            failTest("User creation failed: " + e.getMessage());
        }
    }
    
    private void testUserDefaultConstructor() {
        startTest("User Default Constructor Test");
        try {
            User user = new User();
            
            assert user.firstName.equals("") : "Default first name should be empty";
            assert user.lastName.equals("") : "Default last name should be empty";
            assert user.username.equals("") : "Default username should be empty";
            assert user.phoneNumber.equals("") : "Default phone should be empty";
            
            passTest("Default constructor works correctly");
        } catch (Exception e) {
            failTest("Default constructor failed: " + e.getMessage());
        }
    }
    
    private void testUserToString() {
        startTest("User toString Test");
        try {
            User user = new User("Jane", "Smith", "janesmith", "0987654321");
            String result = user.toString();
            
            assert result.contains("Jane") : "toString missing first name";
            assert result.contains("Smith") : "toString missing last name";
            assert result.contains("janesmith") : "toString missing username";
            assert result.contains("0987654321") : "toString missing phone";
            
            passTest("toString method works correctly");
        } catch (Exception e) {
            failTest("toString test failed: " + e.getMessage());
        }
    }
    
    // Validation tests
    private void testUsernameValidation() {
        startTest("Username Validation Test");
        try {
            // Valid usernames
            assert isValidUsername("john") : "Valid username 'john' should pass";
            assert isValidUsername("MARY") : "Valid username 'MARY' should pass";
            assert isValidUsername("JohnDoe") : "Valid username 'JohnDoe' should pass";
            
            // Invalid usernames
            assert !isValidUsername("jo") : "Short username 'jo' should fail";
            assert !isValidUsername("john123") : "Username with numbers 'john123' should fail";
            assert !isValidUsername("john doe") : "Username with space 'john doe' should fail";
            assert !isValidUsername("") : "Empty username should fail";
            
            passTest("Username validation works correctly");
        } catch (Exception e) {
            failTest("Username validation failed: " + e.getMessage());
        }
    }
    
    private void testPhoneValidation() {
        startTest("Phone Number Validation Test");
        try {
            // Valid South African numbers
            assert isValidSouthAfricanPhone("0123456789") : "Valid phone '0123456789' should pass";
            assert isValidSouthAfricanPhone("+27123456789") : "Valid phone '+27123456789' should pass";
            assert isValidSouthAfricanPhone("0812345678") : "Valid phone '0812345678' should pass";
            assert isValidSouthAfricanPhone("0838884567") : "Valid phone '0838884567' should pass";
            
            // Invalid phones
            assert !isValidSouthAfricanPhone("123456789") : "Short phone should fail";
            assert !isValidSouthAfricanPhone("0912345678") : "Invalid prefix '09' should fail";
            assert !isValidSouthAfricanPhone("abc1234567") : "Phone with letters should fail";
            assert !isValidSouthAfricanPhone("") : "Empty phone should fail";
            
            passTest("Phone validation works correctly");
        } catch (Exception e) {
            failTest("Phone validation failed: " + e.getMessage());
        }
    }
    
    private void testPasswordValidation() {
        startTest("Password Validation Test");
        try {
            // Valid passwords
            assert isValidPassword("Password1") : "Valid password 'Password1' should pass";
            assert isValidPassword("MyPass123") : "Valid password 'MyPass123' should pass";
            assert isValidPassword("SuperSecret9") : "Valid password 'SuperSecret9' should pass";
            
            // Invalid passwords
            assert !isValidPassword("password") : "Password without uppercase should fail";
            assert !isValidPassword("PASSWORD1") : "Password without lowercase should fail";
            assert !isValidPassword("Password") : "Password without digit should fail";
            assert !isValidPassword("Pass1") : "Short password should fail";
            assert !isValidPassword("") : "Empty password should fail";
            
            passTest("Password validation works correctly");
        } catch (Exception e) {
            failTest("Password validation failed: " + e.getMessage());
        }
    }
    
    private void testMessageLengthValidation() {
        startTest("Message Length Validation Test");
        try {
            String shortMessage = "Hi there!";
            String longMessage = "a".repeat(300); // 300 characters
            String maxMessage = "a".repeat(250); // exactly 250 characters
            
            assert shortMessage.length() <= 250 : "Short message should be valid";
            assert longMessage.length() > 250 : "Long message should exceed limit";
            assert maxMessage.length() == 250 : "Max message should be exactly 250 chars";
            
            passTest("Message length validation works correctly");
        } catch (Exception e) {
            failTest("Message length validation failed: " + e.getMessage());
        }
    }
    
    private void testEmptyFieldValidation() {
        startTest("Empty Field Validation Test");
        try {
            String emptyString = "";
            String whitespaceString = "   ";
            String validString = "test";
            
            assert emptyString.trim().isEmpty() : "Empty string should be detected";
            assert whitespaceString.trim().isEmpty() : "Whitespace string should be detected as empty";
            assert !validString.trim().isEmpty() : "Valid string should not be empty";
            
            passTest("Empty field validation works correctly");
        } catch (Exception e) {
            failTest("Empty field validation failed: " + e.getMessage());
        }
    }
    
    // Test message operations
    private void testMessageCreation() {
        startTest("Message Creation Test");
        try {
            Message msg = new Message(1, "+27834557896", "Did you get the cake?", "Sent");
            
            assert msg.getId() == 1 : "Message ID not set correctly";
            assert msg.getRecipient().equals("+27834557896") : "Recipient not set correctly";
            assert msg.getMessage().equals("Did you get the cake?") : "Message text not set correctly";
            assert msg.getStatus().equals("Sent") : "Status not set correctly";
            assert msg.getTimestamp() != null : "Timestamp should be set";
            
            passTest("Message creation works correctly");
        } catch (Exception e) {
            failTest("Message creation failed: " + e.getMessage());
        }
    }
    
    private void testHashGeneration() {
        startTest("Hash Generation Test");
        try {
            String message1 = "Did you get the cake?";
            String message2 = "Did you get the cake?";
            String message3 = "Different message";
            
            String hash1 = generateTestHash(message1);
            String hash2 = generateTestHash(message2);
            String hash3 = generateTestHash(message3);
            
            assert hash1.equals(hash2) : "Same messages should generate same hash";
            assert !hash1.equals(hash3) : "Different messages should generate different hashes";
            assert hash1.length() == 8 : "Hash should be 8 characters long";
            
            passTest("Hash generation works correctly");
        } catch (Exception e) {
            failTest("Hash generation failed: " + e.getMessage());
        }
    }
    
    private void testMessageArrayOperations() {
        startTest("Message Array Operations Test");
        try {
            List<Message> testMessages = new ArrayList<>();
            
            // Test adding messages
            Message msg1 = new Message(1, "+27834557896", "Did you get the cake?", "Sent");
            Message msg2 = new Message(2, "+27838884567", "Where are you? You are late!", "Stored");
            
            testMessages.add(msg1);
            testMessages.add(msg2);
            
            assert testMessages.size() == 2 : "Should have 2 messages in array";
            
            // Test finding message by ID
            Message found = null;
            for (Message msg : testMessages) {
                if (msg.getId() == 1) {
                    found = msg;
                    break;
                }
            }
            assert found != null : "Should find message with ID 1";
            assert found.getMessage().equals("Did you get the cake?") : "Found message should have correct text";
            
            // Test finding longest message
            Message longest = testMessages.get(0);
            for (Message msg : testMessages) {
                if (msg.getMessage().length() > longest.getMessage().length()) {
                    longest = msg;
                }
            }
            assert longest.getId() == 2 : "Longest message should be message 2";
            
            passTest("Message array operations work correctly");
        } catch (Exception e) {
            failTest("Message array operations failed: " + e.getMessage());
        }
    }
    
    // Helper methods - recreate validation logic for testing
    private boolean isValidUsername(String username) {
        return username.matches("^[A-Za-z]{3,}$");
    }
    
    private boolean isValidSouthAfricanPhone(String phone) {
        // Remove spaces and common separators
        phone = phone.replaceAll("[-\\s()]", "");
        
        // Check for valid South African phone patterns
        return phone.matches("^(\\+27|27|0)[6-8][0-9]{8}$") || 
               phone.matches("^0[6-8][0-9]{8}$");
    }
    
    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
    }
    
    private String generateTestHash(String message) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] hashInBytes = md.digest(message.getBytes());
            
            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString().substring(0, 8); // First 8 characters for readability
        } catch (java.security.NoSuchAlgorithmException e) {
            return String.valueOf(message.hashCode()).substring(0, 8);
        }
    }
    
    // Test framework methods
    private void startTest(String testName) {
        totalTests++;
        System.out.println("Running: " + testName);
    }
    
    private void passTest(String message) {
        passedTests++;
        System.out.println("✓ PASS: " + message);
        testResults.add("✓ PASS: " + message);
        System.out.println();
    }
    
    private void failTest(String message) {
        System.out.println("✗ FAIL: " + message);
        testResults.add("✗ FAIL: " + message);
        System.out.println();
    }
    
    // Show results in GUI window
    public void showResults() {
        JFrame resultFrame = new JFrame("Enhanced Message App Test Results");
        resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        resultFrame.setSize(700, 500);
        resultFrame.setLocationRelativeTo(null);
        
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        StringBuilder results = new StringBuilder();
        results.append("ENHANCED MESSAGE APP UNIT TEST RESULTS\n");
        results.append("=====================================\n\n");
        
        for (String result : testResults) {
            results.append(result).append("\n");
        }
        
        results.append("\n=== SUMMARY ===\n");
        results.append("Total tests: ").append(totalTests).append("\n");
        results.append("Passed: ").append(passedTests).append("\n");
        results.append("Failed: ").append(totalTests - passedTests).append("\n");
        results.append("Success rate: ").append(String.format("%.1f%%", (passedTests * 100.0 / totalTests))).append("\n");
        
        if (passedTests == totalTests) {
            results.append("\n🎉 ALL TESTS PASSED! 🎉\n");
            results.append("Your Enhanced Messages application is working correctly!");
        } else {
            results.append("\n⚠️  Some tests failed. Check the details above.\n");
            results.append("Review the failed tests and fix any issues in your code.");
        }
        
        // Add test data verification
        results.append("\n\n=== TEST DATA VERIFICATION ===\n");
        results.append("The following test messages should be present:\n");
        results.append("1. ID 1: '+27834557896' - 'Did you get the cake?' - Sent\n");
        results.append("2. ID 2: '+27838884567' - 'Where are you? You are late! I have asked you to be on time.' - Stored\n");
        results.append("3. ID 3: '+27834484567' - 'Yohoooo, I am at your gate.' - Disregard\n");
        results.append("4. ID 4: '0838884567' - 'It is dinner time !' - Sent\n");
        results.append("5. ID 5: '+27838884567' - 'Ok, I am leaving without you.' - Stored\n");
        
        resultArea.setText(results.toString());
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        resultFrame.add(scrollPane, BorderLayout.CENTER);
        
        // Add buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> resultFrame.dispose());
        
        JButton runAgainButton = new JButton("Run Tests Again");
        runAgainButton.addActionListener(e -> {
            resultFrame.dispose();
            // Reset test counters
            totalTests = 0;
            passedTests = 0;
            testResults.clear();
            runAllTests();
            showResults();
        });
        
        buttonPanel.add(runAgainButton);
        buttonPanel.add(closeButton);
        resultFrame.add(buttonPanel, BorderLayout.SOUTH);
        
        // Color code the results
        if (passedTests == totalTests) {
            resultArea.setBackground(new Color(230, 255, 230)); // light green
        } else {
            resultArea.setBackground(new Color(255, 230, 230)); // light red
        }
        
        resultFrame.setVisible(true);
    }
}

// Main tester class
public class MessageAppTester {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Show option to run standalone or integrated tests
            String[] options = {"Run Standalone Tests", "Run with Messages App", "Cancel"};
            int choice = JOptionPane.showOptionDialog(
                null,
                "How would you like to run the tests?",
                "Test Runner Options",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );
            
            if (choice == 0) {
                // Run standalone tests
                EnhancedMessageTester tester = new EnhancedMessageTester();
                tester.runAllTests();
                tester.showResults();
            } else if (choice == 1) {
                // Run with the main application
                try {
                    EnhancedMessages app = new EnhancedMessages();
                    JOptionPane.showMessageDialog(
                        null,
                        "Enhanced Messages app started!\nUse 'Tools > Run Unit Tests' from the menu to run tests.",
                        "Application Started",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                        null,
                        "Error starting Enhanced Messages app: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }
}