//simple user class to store user info
public class User {
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
    
    //empty constructor
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