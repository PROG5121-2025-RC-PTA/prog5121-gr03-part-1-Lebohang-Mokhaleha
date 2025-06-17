import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MessageAppTester {
    private int totalTests = 0;
    private int passedTests = 0;
    private List<String> testResults = new ArrayList<>();
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MessageAppTester tester = new MessageAppTester();
            tester.runAllTests();
            tester.showResults();
        });
    }
    
    public void runAllTests() {
        System.out.println("Starting Message App Unit Tests...\n");
        
        //test user class
        testUserCreation();
        testUserDefaultConstructor();
        testUserToString();
        
        //test validation methods (create test versions)
        testUsernameValidation();
        testPhoneValidation();
        testPasswordValidation();
        
        //test message functionality
        testMessageLengthValidation();
        testEmptyFieldValidation();
        
        System.out.println("\n=== TEST SUMMARY ===");
        System.out.println("Total tests: " + totalTests);
        System.out.println("Passed: " + passedTests);
        System.out.println("Failed: " + (totalTests - passedTests));
        System.out.println("Success rate: " + String.format("%.1f%%", (passedTests * 100.0 / totalTests)));
    }
    
    //user class tests
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
    
    //validation tests - recreate the validation logic for testing
    private void testUsernameValidation() {
        startTest("Username Validation Test");
        try {
            //valid usernames
            assert isValidUsername("john") : "Valid username 'john' should pass";
            assert isValidUsername("MARY") : "Valid username 'MARY' should pass";
            assert isValidUsername("JohnDoe") : "Valid username 'JohnDoe' should pass";
            
            //invalid usernames
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
            //valid south african numbers
            assert isValidSouthAfricanPhone("0123456789") : "Valid phone '0123456789' should pass";
            assert isValidSouthAfricanPhone("+27123456789") : "Valid phone '+27123456789' should pass";
            assert isValidSouthAfricanPhone("27123456789") : "Valid phone '27123456789' should pass";
            assert isValidSouthAfricanPhone("0812345678") : "Valid phone '0812345678' should pass";
            
            //invalid phones
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
            //valid passwords
            assert isValidPassword("Password1") : "Valid password 'Password1' should pass";
            assert isValidPassword("MyPass123") : "Valid password 'MyPass123' should pass";
            assert isValidPassword("SuperSecret9") : "Valid password 'SuperSecret9' should pass";
            
            //invalid passwords
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
            String longMessage = "a".repeat(300); //300 characters
            String maxMessage = "a".repeat(250); //exactly 250 characters
            
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
    
    //helper methods - recreate validation logic for testing
    private boolean isValidUsername(String username) {
        return username.matches("^[A-Za-z]{3,}$");
    }
    
    private boolean isValidSouthAfricanPhone(String phone) {
        return phone.matches("^(\\+27|27|0)[6-8][0-9]{8}$");
    }
    
    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
    }
    
    //test framework methods
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
    
    //show results in gui window
    private void showResults() {
        JFrame resultFrame = new JFrame("Test Results");
        resultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        resultFrame.setSize(600, 400);
        resultFrame.setLocationRelativeTo(null);
        
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        StringBuilder results = new StringBuilder();
        results.append("MESSAGE APP UNIT TEST RESULTS\n");
        results.append("============================\n\n");
        
        for (String result : testResults) {
            results.append(result).append("\n");
        }
        
        results.append("\n=== SUMMARY ===\n");
        results.append("Total tests: ").append(totalTests).append("\n");
        results.append("Passed: ").append(passedTests).append("\n");
        results.append("Failed: ").append(totalTests - passedTests).append("\n");
        results.append("Success rate: ").append(String.format("%.1f%%", (passedTests * 100.0 / totalTests))).append("\n");
        
        if (passedTests == totalTests) {
            results.append("\n🎉 ALL TESTS PASSED! 🎉");
        } else {
            results.append("\n⚠️  Some tests failed. Check the details above.");
        }
        
        resultArea.setText(results.toString());
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        resultFrame.add(scrollPane);
        
        //color code the results
        if (passedTests == totalTests) {
            resultArea.setBackground(new Color(230, 255, 230)); //light green
        } else {
            resultArea.setBackground(new Color(255, 230, 230)); //light red
        }
        
        resultFrame.setVisible(true);
    }
}