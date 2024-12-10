import java.io.*;
import java.util.ArrayList;
import java.util.List;

// import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 * Final Project: SodaTracker
 * Users class to perform user operations:
 * Write new user to CSV file
 * Read users from CSV file
 * Fetch user's usage
 * 
 * @author Robert Jackson - 12/6/24
 * @version 1.0
 * @since 1.0
 */

public class Users {

    public static List<User> userList = new ArrayList<>();

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

        @Override
        public String toString() {
            return "User{name='" + name + "', Primary Flavor='" + primaryFlavor + "', Secondary Flavor='"
                    + secondaryFlavor + "', AVG Daily Consumption='" + avgDailyConsumption + "'}";
        }
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
        // List<User> userList = new ArrayList<>();
        userList.clear();
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
        // return userList;
        return new ArrayList<>(userList);
    }

    /**
     * Method to get and display user usage history
     * 
     * @param fileName
     * @param currentUser
     * @param trendsPanel
     * @param usageTable
     */
    public static void displayUserUsage(String fileName, String currentUser, JPanel trendsPanel, JTable trendsTable) {
        List<String[]> userLogs = new ArrayList<>();
        String[] columnNames = { "User", "Date", "Action", "Details", "Flavor" };

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                if (isHeader) { // Skip header
                    isHeader = false;
                    continue;
                }

                String[] parts = line.split(",", 5);
                if (parts.length == 5 && parts[0].trim().equalsIgnoreCase(currentUser.trim())) {
                    userLogs.add(new String[] {
                            parts[0].trim(), // User
                            parts[1].trim(), // Date
                            parts[2].trim(), // Action
                            parts[3].trim(), // Details
                            parts[4].trim() // Flavor
                    });
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(trendsPanel, "Error with Log File: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[][] data = userLogs.toArray(new String[0][0]);

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        trendsTable.setModel(model);

        TableColumnModel columnModel = trendsTable.getColumnModel();
        // Set column widths
        columnModel.getColumn(0).setPreferredWidth(150); // User
        columnModel.getColumn(1).setPreferredWidth(100); // Date
        columnModel.getColumn(2).setPreferredWidth(100); // Action
        columnModel.getColumn(3).setPreferredWidth(80); // Details
        columnModel.getColumn(4).setPreferredWidth(200); // Flavor

        trendsPanel.revalidate();
        trendsPanel.repaint();

    }

}