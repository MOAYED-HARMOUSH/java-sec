
import Atm.service.UserService;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the ATM Simulation!");

         System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        UserService userService = new UserService();
        boolean isAuthenticated = userService.authenticate(username, password);

        if (isAuthenticated) {
            System.out.println("Login successful! Welcome, " + username);
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }

        scanner.close();
    }
}
