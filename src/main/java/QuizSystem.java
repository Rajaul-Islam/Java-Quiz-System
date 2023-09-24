import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Scanner;

public class QuizSystem {
    private static final String USERS_FILE_PATH = "./src/main/resources/users.json";
    private static JSONArray users;

    public static void main(String[] args) {
        loadUsers();

        Scanner scanner = new Scanner(System.in);
        System.out.print("System:> Enter your username\nUser:> ");
        String username = scanner.nextLine().trim();

        System.out.print("System:> Enter password\nUser:> ");
        String password = scanner.nextLine().trim();

        String role = authenticateUser(username, password);

        if (role.equals("admin")) {
            System.out.println("System:> Welcome admin! Please create new questions in the question bank.");
            // Add logic for admin actions
        } else if (role.equals("student")) {
            System.out.println("System:> Welcome " + username + " to the quiz! We will throw you 10 questions. " +
                    "Each MCQ mark is 1 and no negative marking. Are you ready? Press 's' to start.");
            // Add logic for student actions
        } else {
            System.out.println("System:> Invalid username or password. Access denied.");
        }
    }

    private static void loadUsers() {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(USERS_FILE_PATH)) {
            // Parse the JSON file and cast it to a JSONArray
            users = (JSONArray) parser.parse(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String authenticateUser(String username, String password) {
        for (Object userObj : users) {
            JSONObject user = (JSONObject) userObj;
            String storedUsername = (String) user.get("username");
            String storedPassword = (String) user.get("password");
            String role = (String) user.get("role");

            if (storedUsername.equals(username) && storedPassword.equals(password)) {
                return role;
            }
        }

        return "invalid";
    }
}
