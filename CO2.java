import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Final Project: SodaTracker
 * CO2 class to perform CO2 operations:
 * * Read data from CSV file
 * * Write data to CSV file
 * 
 * @author Robert Jackson - 12/6/24
 * @version 1.0
 * @since 1.0
 */

public class CO2 {

    public static class Tank {
        private String name;
        private String date;
        private double size;
        private double level;

        // Constructor
        public Tank(String name, String date, double size, double level) {
            this.name = name;
            this.date = date;
            this.size = size;
            this.level = level;
        }

        // Getter methods
        public String getName() {
            return name;
        }

        public String getDate() {
            return date;
        }

        public double getSize() {
            return size;
        }

        public double getLevel() {
            return level;
        }

        // Setter methods
        public void setName(String name) {
            this.name = name;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setSize(double size) {
            this.size = size;
        }

        public void setLevel(double level) {
            this.level = Math.round(level * 100.0) / 100.0; // show level with 2 decimal places
        }

        @Override
        public String toString() {
            return "Tank{name='" + name + "', date='" + date + "', size='" + size + "', level='" + level + "'}";
        }

    }

    /**
     * Method to write CO2 data to a file
     * 
     */
    public static void writeCO2ToFile(List<Tank> tankList, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Tank name : tankList) {
                writer.write(name.getName() + "," + name.getDate() + "," + name.getSize() + "," + name.getLevel());
                writer.newLine();
            }
            System.out.println("Tank data written to " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Method to read CO2 data from a file
     * 
     * @param fileName - name of file to read data from
     * @return
     */
    public static List<Tank> readCO2FromFile(String fileName) {
        List<Tank> tankList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isHeader = true;
            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false; // Skip first row (headers)
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length != 4) { // if a line does not have 4 columns
                    System.err.println("Skip malformed line: " + line);
                    continue;
                }
                String name = parts[0].trim();
                String date = parts[1].trim();
                double size = Double.parseDouble(parts[2].trim());
                double level = Double.parseDouble(parts[3].trim());
                tankList.add(new Tank(parts[0], parts[1], Double.parseDouble(parts[2]), Double.parseDouble(parts[3])));
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        return tankList;
    }

    public static double getCurrentTankLevel(String tankName, String fileName) {
        List<Tank> tankList = readCO2FromFile(fileName);

        for (Tank tank : tankList) {
            if (tank.getName().equalsIgnoreCase(tankName)) {
                return tank.getLevel();
            }
        }
        return 0.0; // return 0 when no matching tank exists
    }

    public static void updateTankLevel(String tankName, double CO2UsedPounds, String fileName) {
        List<Tank> tankList = readCO2FromFile(fileName);

        for (Tank tank : tankList) {
            if (tank.getName().equalsIgnoreCase(tankName)) {
                double currentLevel = tank.getLevel();
                double newLevel = Math.max(0, currentLevel - CO2UsedPounds); // Check that level is above 0
                tank.setLevel(newLevel); // Update the remaining tank level
                break;
            }
        }

        writeCO2ToFile(tankList, fileName);
    }

}