import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Final Project: SodaTracker
 * UsersCreate class to create a user CSV file:
 * 
 * @author Robert Jackson - 12/6/24
 * @version 1.0
 * @since 1.0
 */

public class UsersCreate {

    private static final String USERS_FILE = "users.csv";

    // Inner User class
    public static class User {
        private String name;
        private String primaryFlavor;
        private String secondaryFlavor;
        private String avgDailyConsumption;

        // Constructor
        public User(String name, String primaryFlavor, String secondaryFlavor, String avgDailyConsumption) {
            this.name = name;
            this.primaryFlavor = primaryFlavor;
            this.secondaryFlavor = secondaryFlavor;
            this.avgDailyConsumption = avgDailyConsumption;
        }

        // Getter methods
        public String getName() {
            return name;
        }

        public String getPrimaryFlavor() {
            return primaryFlavor;
        }

        public String getSecondaryFlavor() {
            return secondaryFlavor;
        }

        public String getAvgDailyConsumption() {
            return avgDailyConsumption;
        }
    }

    /**
     * Method to reset CSV file based on pre-set data
     * 
     * @param args
     */
    public static void resetUsers() {

        List<User> userList = new ArrayList<>();

        userList.add(new User("Robert Jackson", "Diet Mountain Dew", "Diet Dr Pete", "2"));
        userList.add(new User("Olivia Jackson", "Mountain Dew Zero", "Pepsi Zero", "1.5"));
        userList.add(new User("Everett Jackson", "Pepsi Zero", "Diet Root Beer", "2.5"));
        userList.add(new User("Isabel Jackson", "Starry", "Lemonade", "0.5"));
        userList.add(new User("Sierra Jackson", "Lemonade", "Diet Mountain Dew", "3.0"));

        // Write users to a file
        writeUsersToFile(userList, USERS_FILE);
        System.out.println("*** Users file has been reset ***");
    }

    /**
     * Method to write user data to a file
     * 
     * @param userList
     * @param fileName
     */
    public static void writeUsersToFile(List<User> userList, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (User user : userList) {
                writer.write(user.getName() + "," + user.getPrimaryFlavor() + "," + user.getSecondaryFlavor() + ","
                        + user.getAvgDailyConsumption());
                writer.newLine();
            }
            System.out.println("User data written to " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Method to read user data from a file
     * 
     * @param fileName
     * @return
     */
    public static List<User> readUsersFromFile(String fileName) {
        List<User> userList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    userList.add(new User(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        return userList;
    }

    /**
     * Main method to build CSV file based on pre-set data
     * 
     * @param args
     */
    public static void main(String[] args) {

        List<User> userList = new ArrayList<>();

        userList.add(new User("Robert Jackson", "Diet Mountain Dew", "Diet Dr Pete", "2"));
        userList.add(new User("Olivia Jackson", "Mountain Dew Zero", "Pepsi Zero", "1.5"));
        userList.add(new User("Everett Jackson", "Pepsi Zero", "Diet Root Beer", "2.5"));
        userList.add(new User("Isabel Jackson", "Starry", "Lemonade", "0.5"));
        userList.add(new User("Sierra Jackson", "Lemonade", "Diet Mountain Dew", "3.0"));

        // Write users to a file
        writeUsersToFile(userList, USERS_FILE);

        // Read users from a file and display them
        List<User> readUsers = readUsersFromFile(USERS_FILE);
        System.out.println("Users read from file:");
        for (User user : readUsers) {
            System.out.println(user);
        }
    }

}