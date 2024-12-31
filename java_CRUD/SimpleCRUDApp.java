import java.sql.*;
import java.util.Scanner;

public class SimpleCRUDApp {
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/crud_demo", "root", "password")) {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Choose an option: 1. View Users 2. Add User 3. Delete User 4. Exit");
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                if (choice == 1) {
                    // View Users
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM users");
                    while (rs.next()) {
                        System.out.println("ID: " + rs.getInt("id") +
                                ", Name: " + rs.getString("name") +
                                ", Email: " + rs.getString("email") +
                                ", Age: " + rs.getInt("age"));
                    }
                } else if (choice == 2) {
                    // Add User
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter age: ");
                    int age = scanner.nextInt();

                    PreparedStatement stmt = conn.prepareStatement(
                            "INSERT INTO users (name, email, age) VALUES (?, ?, ?)");
                    stmt.setString(1, name);
                    stmt.setString(2, email);
                    stmt.setInt(3, age);
                    stmt.executeUpdate();

                    System.out.println("User added successfully!");
                } else if (choice == 3) {
                    // Delete User
                    System.out.print("Enter user ID to delete: ");
                    int id = scanner.nextInt();

                    PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE id = ?");
                    stmt.setInt(1, id);
                    stmt.executeUpdate();

                    System.out.println("User deleted successfully!");
                } else if (choice == 4) {
                    // Exit
                    break;
                } else {
                    System.out.println("Invalid choice.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
