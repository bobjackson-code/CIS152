import java.util.ArrayList;
import java.util.Collections;

/**
 * Final Project: SodaTracker
 * Flavors class to get array of available flavors
 * 
 * @author Robert Jackson - 12/6/24
 * @version 1.0
 * @since 1.0
 */

public class Flavors {
	public static ArrayList<String> getFlavors() {

		// Create an ArrayList of flavors
		ArrayList<String> flavors = new ArrayList<>();

		flavors.add("Diet Mountain Dew");
		flavors.add("Mountain Dew");
		flavors.add("Mountain Dew Zero");
		flavors.add("Diet Pepsi");
		flavors.add("Pepsi");
		flavors.add("Pepsi Zero");
		flavors.add("Root Beer");
		flavors.add("Diet Root Beer");
		flavors.add("Diet Dr Pete");
		flavors.add("Dr Pete");
		flavors.add("Starry");
		flavors.add("Starry Zero");
		flavors.add("Diet Cola");
		flavors.add("Diet Caffeine Free Cola");
		flavors.add("Cola");
		flavors.add("Diet Tonic");
		flavors.add("Ginger Ale");
		flavors.add("Diet Ginger Ale");
		flavors.add("Diet Lemon Lime");
		flavors.add("Lemonade");
		flavors.add("Pink Grapefruit");
		flavors.add("Strawberry Watermelon");
		flavors.add("Cranberry Raspberry");
		flavors.add("Orange Soda");
		flavors.add("Diet Orange Soda");
		flavors.add("Energy");
		Collections.sort(flavors);
		return flavors;

		// Print the array
		// System.out.println("\nFlavors:");
		// for (String flavor : flavors) {
		// 	System.out.println(flavor);
		// }
	}
}