import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * UnitTests for Final Project: SodaTracker
 * App for managing a home carbonated beverage system
 * 
 * @author Robert Jackson - 12/6/24
 * @version 1.0
 * @since 1.0
 */

class SodaTrackerTest {

    private SodaTracker sodaTracker;
    private JLabel nameLabel;
    private JPanel trendsPanel;

    @BeforeEach
    void setUp() {
        // Initialize an instance of SodaTracker for every test
        sodaTracker = new SodaTracker();
        nameLabel = new JLabel("Enter Your Name:");
        trendsPanel = sodaTracker.getTrendsPanel();
        sodaTracker.getFrame().setVisible(true);
    }
    
    /**
     * Test label text value
     */
    @Test
    void testLabelText() {
        assertEquals("Enter Your Name:", nameLabel.getText(), "New User label text should be 'Enter Your Name:'");
    }
    
    /**
     * Test visibility of trends panel
     */
    @Test
    void testTrendsPanelVisibility() {
    	assertTrue(trendsPanel.isVisible(), "Trends panel should be visible");
    }
    
    
    /**
     * Test user creation
     */
    @Test
    void testUserCreation() {
        Users.User user = new Users.User("Test User", "Mountain Dew", "Pepsi", "2.0");
        assertEquals("Test User", user.getName());
        assertEquals("Mountain Dew", user.getPrimaryFlavor());
        assertEquals("Pepsi", user.getSecondaryFlavor());
        assertEquals("2.0", user.getAvgDailyConsumption());
    }
    
    /**
     * Test the size of the application window
     */
    @Test
    void testFrameTitleAndSize() {
        JFrame frame = sodaTracker.getFrame();
        assertEquals("SodaTracker", frame.getTitle(), "Frame title should be 'SodaTracker'");
        assertEquals(1000, frame.getWidth(), "Frame width should be 1000");
        assertEquals(800, frame.getHeight(), "Frame height should be 800");
    }
    
    /**
     * Test the dropdown menu after a reset
     */
    @Test
    void testResetUserDropdown() {
        sodaTracker.resetUserDropDown();
        int itemCount = sodaTracker.getUserDropdown().getItemCount();
        assertTrue(itemCount > 0, "Users Dropdown should contain 1+ users after reset");
    }
    
    /**
     * Test reading a user from a file
     */
    @Test
    void testReadUsersFromFile() {
        String fileData = "Test User,Starry,Pepsi Zero,2.0";
        @SuppressWarnings("unused")
		BufferedReader reader = new BufferedReader(new StringReader(fileData));
        
        List<Users.User> users = Users.readUsersFromFile("mockFile.csv");
        
        assertEquals(1, users.size(), "Two users should be read from the file");
        assertEquals("Test User", users.get(0).getName(), "User should be 'Test User'");
        assertEquals("Starry", users.get(1).getPrimaryFlavor(), "Primary Flavor should be 'Starry'");
        assertEquals("Pepsi Zero", users.get(2).getSecondaryFlavor(), "Secondary Flavor should be 'Pepsi Zero'");
        assertEquals("2.0", users.get(2).getAvgDailyConsumption(), "AVG Daily Consumption should be '2.0'");
    }

}
