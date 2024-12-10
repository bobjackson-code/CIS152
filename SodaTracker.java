import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 * Final Project: SodaTracker
 * App for managing a home carbonated beverage system
 * 
 * @author Robert Jackson - 12/6/24
 * @version 1.0
 * @since 1.0
 */

public class SodaTracker implements ActionListener {

    // Define various Java GUI elements
    private JLabel userFeedbackLabel;
    private JLabel trackFeedbackLabel;
    private JLabel trackLabel;
    private JLabel trendsLabel;
    private JLabel nameLabel;
    private JLabel primaryFlavorLabel;
    private JLabel secondaryFlavorLabel;
    private JLabel sodaConsumptionLabel;
    private JLabel currentUserPrimaryLabel;
    private JLabel currentUserSecondaryLabel;
    private JLabel currentUserConsumptionLabel;
    private JLabel trackTableLabel;
    private JLabel trendsTableLabel;
    private JTextField nameField;
    private JFrame frame;
    private JButton yesButton;
    private JButton noButton;
    private JButton registerButton;
    private JButton resetInputButton;
    private JButton newUserButton;
    private JButton selectUserButton;
    private JButton deleteUserButton;
    private JButton resetUsersButton;
    private JButton trackUserButton;
    private JButton trendsUserButton;
    private JButton recordUsageButton;
    private JButton loadCO2Button;
    private JButton useCO2DailyButton;
    private JButton useCO2WeeklyButton;
    private JComboBox<String> flavor1Dropdown;
    private JComboBox<String> flavor2Dropdown;
    private JComboBox<String> sodaConsumptionDropdown;
    private JComboBox<String> userDropdown;
    private JTable trackTable;
    private JTable trendsTable;

    private Users.User currentUserObject;

    private MyQueue tankQueue = new MyQueue();

    private static final String USERS_FILE = "users.csv";
    private static final String USAGE_FILE = "usage.csv";
    private static final String CO2_FILE = "CO2.csv";

    // define JPanels for app
    private JPanel sidebarPanel, homePanel, usersPanel, trackPanel, trendsPanel, aboutPanel, exitPanel;

    // define section backgrounds
    private Image sidebarImage;
    private Image homeImage;
	private Image usersImage;
	private Image trackImage;
	private Image trendsImage;
	private Image aboutImage;
	private Image exitImage;
	private Image yesImage;
	private Image noImage;

    // define navigation images
    private Image headerNav;
    private Image homeNav, homeNavHighlight;
    private Image usersNav, usersNavHighlight;
    private Image trackNav, trackNavHighlight;
    private Image trendsNav, trendsNavHighlight;
    private Image aboutNav, aboutNavHighlight;
    private Image exitNav, exitNavHighlight;

    private String currentSection;
    private String currentUser;
    private String currentUserPrimary;
    private String currentUserSecondary;
    private String currentUserConsumption;

    /**
     * Main method to define and run the GUI
     * 
     */
    public SodaTracker() {
        currentSection = "home"; // set initial section to home
        // define section background image paths
        sidebarImage = new ImageIcon("assets/section_sidebar.png").getImage();
        homeImage = new ImageIcon("assets/section_home.png").getImage();
        usersImage = new ImageIcon("assets/section_users.png").getImage();
        trackImage = new ImageIcon("assets/section_track.png").getImage();
        trendsImage = new ImageIcon("assets/section_trends.png").getImage();
        aboutImage = new ImageIcon("assets/section_about.png").getImage();
        exitImage = new ImageIcon("assets/section_exit.png").getImage();
        yesImage = new ImageIcon("assets/button_yes.png").getImage();
        noImage = new ImageIcon("assets/button_no.png").getImage();

        // define navigation image paths
        headerNav = new ImageIcon("assets/menu_header.png").getImage();
        homeNav = new ImageIcon("assets/menu_home.png").getImage();
        homeNavHighlight = new ImageIcon("assets/menu_home_highlight.png").getImage();
        usersNav = new ImageIcon("assets/menu_users.png").getImage();
        usersNavHighlight = new ImageIcon("assets/menu_users_highlight.png").getImage();
        trackNav = new ImageIcon("assets/menu_track.png").getImage();
        trackNavHighlight = new ImageIcon("assets/menu_track_highlight.png").getImage();
        trendsNav = new ImageIcon("assets/menu_trends.png").getImage();
        trendsNavHighlight = new ImageIcon("assets/menu_trends_highlight.png").getImage();
        aboutNav = new ImageIcon("assets/menu_about.png").getImage();
        aboutNavHighlight = new ImageIcon("assets/menu_about_highlight.png").getImage();
        exitNav = new ImageIcon("assets/menu_exit.png").getImage();
        exitNavHighlight = new ImageIcon("assets/menu_exit_highlight.png").getImage();

        frame = new JFrame();

        frame.setTitle("SodaTracker"); // set application name in title bar
        frame.setSize(1000, 800); // define the application dimensions
        frame.setResizable(false); // disable window from being resizeable
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the custom panel for the navivation menu
        sidebarPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(sidebarImage, 0, 0, sidebarImage.getWidth(null), sidebarImage.getHeight(null), this);
                g.drawImage(headerNav, 8, 95, headerNav.getWidth(null), headerNav.getHeight(null), this);

            }
        };

        sidebarPanel.setPreferredSize(new Dimension(255, 800));

        // Define JPanels
        homePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(homeImage, 0, 0, homeImage.getWidth(null), homeImage.getHeight(null), this);
            }
        };

        usersPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(usersImage, 0, 0, usersImage.getWidth(null),
                        usersImage.getHeight(null), this);
            }
        };
        // Set preferred size for usersPanel
        usersPanel.setPreferredSize(new Dimension(744, 800)); // Adjust dimensions as needed
        usersPanel.setLayout(null); // Use null layout for absolute positioning

        trackPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(trackImage, 0, 0, trackImage.getWidth(null), trackImage.getHeight(null), this);
            }
        };

        trendsPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(trendsImage, 0, 0, trendsImage.getWidth(null), trendsImage.getHeight(null), this);
            }
        };

        aboutPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(aboutImage, 0, 0, aboutImage.getWidth(null), aboutImage.getHeight(null), this);
            }
        };

        exitPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(exitImage, 0, 0, exitImage.getWidth(null), exitImage.getHeight(null), this);
            }
        };

        frame.setLayout(new BorderLayout());
        frame.add(sidebarPanel, BorderLayout.WEST);
        frame.add(homePanel, BorderLayout.CENTER);

        // Center frame on screen
        frame.setLocationRelativeTo(null);

        // Optionally move the window to a specific monitor
        moveToSecondMonitor();

        // ********** Setup the components for the usersPanel
        // ******************************
        // Dimension panelSize = usersPanel.getPreferredSize();
        // int panelWidth = panelSize.width;
        int buttonWidth = 150;
        int buttonHeight = 36;

        // int buttonXOffset = 100;
        // int buttonY = 220;

        // int newButtonX = (panelWidth / 3) - (buttonWidth / 2) - buttonXOffset;
        // int loadButtonX = (panelWidth / 3) - (buttonWidth / 2) + buttonXOffset;

        int usersXOffset = 56;
        int usersXIncrement = 170;
        int usersYOffset = 180;
        int usersYIncrement = 25;

        // New User button
        newUserButton = new JButton("New User");
        newUserButton.setBounds(usersXOffset, usersYOffset, buttonWidth, buttonHeight);
        // System.out.println("New User Button X: " + usersXOffset + ", Y: " +
        // usersYOffset + " Size: " + buttonWidth + ", " + buttonHeight);
        usersXOffset += usersXIncrement;
        newUserButton.addActionListener(this);

        // Select a User button
        selectUserButton = new JButton("Select a User");
        selectUserButton.setBounds(usersXOffset, usersYOffset, buttonWidth, buttonHeight);
        // System.out.println("Select Button X: " + usersXOffset + ", Y: " +
        // usersYOffset + " Size: " + buttonWidth + ", " + buttonHeight);
        usersXOffset += usersXIncrement;
        selectUserButton.addActionListener(this);

        // Delete button
        deleteUserButton = new JButton("Delete User");
        deleteUserButton.setBounds(usersXOffset, usersYOffset, buttonWidth, buttonHeight);
        // System.out.println("Delete Button X: " + usersXOffset + ", Y: " +
        // usersYOffset + " Size: " + buttonWidth + ", " + buttonHeight);
        usersXOffset += usersXIncrement;
        deleteUserButton.setEnabled(false);
        deleteUserButton.addActionListener(this);

        // Reset Users button
        resetUsersButton = new JButton("Reset Users");
        resetUsersButton.setBounds(usersXOffset, usersYOffset, buttonWidth, buttonHeight);
        // System.out.println("Reset Users Button X: " + usersXOffset + ", Y: " +
        // usersYOffset + " Size: " + buttonWidth + ", " + buttonHeight);
        usersYOffset += (usersYIncrement + 40);
        resetUsersButton.setEnabled(false);
        resetUsersButton.addActionListener(this);

        nameLabel = new JLabel("Enter Your Name:");
        nameField = new JTextField(20);
        nameLabel.setBounds(56, usersYOffset, 200, 25);
        // System.out.println("Name Label X: " + usersXOffset + ", Y: " + usersYOffset);
        usersYOffset += usersYIncrement;
        nameField.setBounds(52, usersYOffset, 250, 25);
        usersYOffset += (usersYIncrement + 20);
        nameLabel.setVisible(false);
        nameField.setVisible(false);

        // Get sorted flavors from the Flavors class
        ArrayList<String> flavors = Flavors.getFlavors();
        primaryFlavorLabel = new JLabel("Select Your Primary Flavor:");
        flavor1Dropdown = new JComboBox<>(flavors.toArray(new String[0]));
        flavor1Dropdown.setSelectedIndex(-1);
        flavor1Dropdown.setMaximumRowCount(14);
        primaryFlavorLabel.setVisible(false);
        flavor1Dropdown.setVisible(false);

        secondaryFlavorLabel = new JLabel("Select Your Secondary Flavor:");
        flavor2Dropdown = new JComboBox<>(flavors.toArray(new String[0]));
        flavor2Dropdown.setSelectedIndex(-1);
        flavor2Dropdown.setMaximumRowCount(14);
        secondaryFlavorLabel.setVisible(false);
        flavor2Dropdown.setVisible(false);

        primaryFlavorLabel.setBounds(56, usersYOffset, 200, 25);
        usersYOffset += usersYIncrement;
        flavor1Dropdown.setBounds(52, usersYOffset, 250, 30);
        usersYOffset += (usersYIncrement + 20);

        secondaryFlavorLabel.setBounds(56, usersYOffset, 200, 25);
        usersYOffset += usersYIncrement;
        flavor2Dropdown.setBounds(52, usersYOffset, 250, 30);
        usersYOffset += (usersYIncrement + 20);

        sodaConsumptionLabel = new JLabel("Select Your Soda Consumption Per Day:");
        sodaConsumptionDropdown = new JComboBox<>(new String[] {
                "0.5 liters / 17oz", "1 liter / 34oz", "1.5 liters / 51oz", "2 liters / 68oz", "2.5 liters / 85oz",
                "3 liters / 101oz"
        });
        sodaConsumptionDropdown.setSelectedIndex(-1);
        sodaConsumptionDropdown.setMaximumRowCount(6);
        sodaConsumptionLabel.setBounds(56, usersYOffset, 400, 25);
        usersYOffset += usersYIncrement;
        sodaConsumptionDropdown.setBounds(52, usersYOffset, 250, 30);
        usersYOffset += (usersYIncrement + 30);
        sodaConsumptionLabel.setVisible(false);
        sodaConsumptionDropdown.setVisible(false);

        // Buttons
        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        resetInputButton = new JButton("Reset");
        resetInputButton.addActionListener(this);
        registerButton.setBounds(70, usersYOffset, 100, 25);
        resetInputButton.setBounds(170, usersYOffset, 100, 25);
        usersYOffset += (usersYIncrement + 20);
        registerButton.setVisible(false);
        resetInputButton.setVisible(false);

        // Feedback for register button
        userFeedbackLabel = new JLabel("");
        userFeedbackLabel.setBounds(78, usersYOffset, 550, 25);
        userFeedbackLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        // ************* Select a User **********************************
        // Dropdown for user list

        usersXOffset = 566;
        usersYOffset = 245; // 255
        usersYIncrement = 40;
        userDropdown = new JComboBox<>();
        userDropdown.setBounds(56, usersYOffset, 510, 36);
        userDropdown.setFont(new Font("Arial", Font.PLAIN, 15));
        // System.out.println("JComboBox X: " + 56 + ", Y: " + usersYOffset);
        userDropdown.setVisible(false);

        // Track button
        trackUserButton = new JButton("User Track");
        trackUserButton.setBounds(usersXOffset, usersYOffset, buttonWidth, buttonHeight);
        // System.out.println("User Track Button X: " + usersXOffset + ", Y: " +
        // usersYOffset);
        usersYOffset += usersYIncrement;
        trackUserButton.setEnabled(false);
        trackUserButton.addActionListener(this);

        // Trends button
        trendsUserButton = new JButton("User Trends");
        trendsUserButton.setBounds(usersXOffset, usersYOffset, buttonWidth, buttonHeight);
        usersYOffset += usersYIncrement;
        // System.out.println("User Trends Button X: " + usersXOffset + ", Y: " +
        // usersYOffset);
        trendsUserButton.setEnabled(false);
        trendsUserButton.addActionListener(this);

        usersPanel.add(userFeedbackLabel);
        usersPanel.add(nameLabel);
        usersPanel.add(primaryFlavorLabel);
        usersPanel.add(secondaryFlavorLabel);
        usersPanel.add(sodaConsumptionLabel);

        usersPanel.add(userDropdown);
        usersPanel.add(flavor1Dropdown);
        usersPanel.add(flavor2Dropdown);
        usersPanel.add(sodaConsumptionDropdown);

        usersPanel.add(nameField);

        usersPanel.add(registerButton);
        usersPanel.add(resetInputButton);

        usersPanel.add(newUserButton);
        usersPanel.add(selectUserButton);
        usersPanel.add(deleteUserButton);
        usersPanel.add(resetUsersButton);
        usersPanel.add(trackUserButton);
        usersPanel.add(trendsUserButton);

        // ******* End Select a User *****************************

        // ******* Components for the trackPanel *****************

        int trackYOffset = 180;
        int trackYIncrement = 25;

        int trackXOffset = 56; // starting X for 1st button
        int trackXIncrement = 15; // space between 4 buttons
        int trackButtonWidth = 150; // button width
        int trackButtonHeight = 48; // button height

        trackLabel = new JLabel("");
        Font font = new Font("Arial", Font.PLAIN, 16);
        trackLabel.setForeground(new Color(61, 61, 61));
        trackLabel.setFont(font);
        trackLabel.setVerticalAlignment(SwingConstants.TOP);
        trackLabel.setHorizontalAlignment(SwingConstants.LEFT);
        trackLabel.setBounds(58, trackYOffset, 600, 60);
        trackYOffset += (trackYIncrement + 10);

        recordUsageButton = new JButton("Record Usage");
        recordUsageButton.setBounds(trackXOffset, trackYOffset, trackButtonWidth, trackButtonHeight);
        recordUsageButton.addActionListener(this);
        recordUsageButton.setEnabled(false);
        trackXOffset += trackButtonWidth + trackXIncrement;

        loadCO2Button = new JButton("Load CO2 Data");
        loadCO2Button.setBounds(trackXOffset, trackYOffset, trackButtonWidth, trackButtonHeight);
        loadCO2Button.addActionListener(this);
        trackXOffset += trackButtonWidth + trackXIncrement;

        useCO2DailyButton = new JButton("Use CO2 (Daily)");
        useCO2DailyButton.setBounds(trackXOffset, trackYOffset, trackButtonWidth, trackButtonHeight);
        useCO2DailyButton.addActionListener(this);
        trackXOffset += trackButtonWidth + trackXIncrement;

        useCO2WeeklyButton = new JButton("Use CO2 (Weekly)");
        useCO2WeeklyButton.setBounds(trackXOffset, trackYOffset, trackButtonWidth, trackButtonHeight);
        trackYOffset += (trackYIncrement + 35);
        useCO2WeeklyButton.addActionListener(this);

        // Feedback for CO2 buttons
        trackFeedbackLabel = new JLabel("");
        trackFeedbackLabel.setBounds(58, trackYOffset, 640, 25);
        trackFeedbackLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        trackFeedbackLabel.setForeground(Color.RED);
        // trackFeedbackLabel.setBorder(BorderFactory.createLineBorder(Color.RED));

        // Track Table ***************************************************
        trackYOffset = 310;
        trackTableLabel = new JLabel("Cosumables Tracking");
        trackTableLabel.setFont(font);
        trackTableLabel.setVerticalAlignment(SwingConstants.TOP);
        trackTableLabel.setHorizontalAlignment(SwingConstants.LEFT);
        trackTableLabel.setBounds(58, trackYOffset, 600, 60);
        trackYOffset += trackYIncrement;

        String[] trackColumnNames = { "Tank Name", "Date Filled", "Size (OZ)", "Remaining (OZ)" };
        DefaultTableModel trackTableModel = new DefaultTableModel(trackColumnNames, 0); // 0 rows initially
        trackTable = new JTable(trackTableModel);
        trackTable.setRowHeight(30);
        trackTable.setFont(new Font("Arial", Font.PLAIN, 16));
        // trackTable.setTableHeader(null);

        JScrollPane trackScrollPane = new JScrollPane(trackTable);
        trackScrollPane.setBounds(58, 335, 640, 400); // Adjust size as needed
        // System.out.println("trackYOffset = " + trackYOffset);
        // trackScrollPane.setBorder(BorderFactory.createLineBorder(Color.RED));

        trackPanel.add(trackLabel);
        trackPanel.add(trackFeedbackLabel);
        trackPanel.add(recordUsageButton);
        trackPanel.add(loadCO2Button);
        trackPanel.add(useCO2DailyButton);
        trackPanel.add(useCO2WeeklyButton);
        trackPanel.add(trackTableLabel);
        trackPanel.add(trackScrollPane);

        // ******* End of trackPanel ****************************

        // ******* Components for the trendsPanel ***************

        int trendsYOffset = 180;
        int trendsYIncrement = 25;

        trendsLabel = new JLabel("");
        trendsLabel.setFont(font);
        trendsLabel.setVerticalAlignment(SwingConstants.TOP);
        trendsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        trendsLabel.setBounds(58, trendsYOffset, 600, 60);
        trendsYOffset += trendsYIncrement;

        currentUserPrimaryLabel = new JLabel("");
        currentUserPrimaryLabel.setFont(font);
        currentUserPrimaryLabel.setVerticalAlignment(SwingConstants.TOP);
        currentUserPrimaryLabel.setHorizontalAlignment(SwingConstants.LEFT);
        currentUserPrimaryLabel.setBounds(58, trendsYOffset, 600, 60);
        trendsYOffset += trendsYIncrement;

        currentUserSecondaryLabel = new JLabel("");
        currentUserSecondaryLabel.setFont(font);
        currentUserSecondaryLabel.setVerticalAlignment(SwingConstants.TOP);
        currentUserSecondaryLabel.setHorizontalAlignment(SwingConstants.LEFT);
        currentUserSecondaryLabel.setBounds(58, trendsYOffset, 600, 60);
        trendsYOffset += trendsYIncrement;

        currentUserConsumptionLabel = new JLabel("");
        currentUserConsumptionLabel.setFont(font);
        currentUserConsumptionLabel.setVerticalAlignment(SwingConstants.TOP);
        currentUserConsumptionLabel.setHorizontalAlignment(SwingConstants.LEFT);
        currentUserConsumptionLabel.setBounds(58, trendsYOffset, 600, 60);
        trendsYOffset += (trendsYIncrement + 10);

        // *** Trends Table ***************************************************
        trendsYOffset = 310;
        trendsTableLabel = new JLabel("User Trends");
        trendsTableLabel.setFont(font);
        trendsTableLabel.setVerticalAlignment(SwingConstants.TOP);
        trendsTableLabel.setHorizontalAlignment(SwingConstants.LEFT);
        trendsTableLabel.setBounds(58, trendsYOffset, 600, 60);
        // System.out.println("trends table Label X: 58" + ", Y: " + trendsYOffset);
        trendsYOffset += trendsYIncrement;

        String[] trendsColumnNames = { "User", "Date", "Action", "Details", "Flavor" };
        DefaultTableModel trendsTableModel = new DefaultTableModel(trendsColumnNames, 0);
        trendsTable = new JTable(trendsTableModel);
        trendsTable.setRowHeight(30);
        trendsTable.setFont(new Font("Arial", Font.PLAIN, 16));

        JScrollPane trendsScrollPane = new JScrollPane(trendsTable);
        trendsScrollPane.setBounds(58, 335, 640, 400); // Adjust size as needed
        // System.out.println("trendsYOffset = " + trendsYOffset);
        // trendsScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        // *** End of Trends Table ***************************************************

        trendsPanel.add(trendsLabel);
        trendsPanel.add(currentUserPrimaryLabel);
        trendsPanel.add(currentUserSecondaryLabel);
        trendsPanel.add(currentUserConsumptionLabel);
        trendsPanel.add(trendsTableLabel);
        trendsPanel.add(trendsScrollPane);
        // ******* End of trendsPanel ***************************

        // ******* Components for the exitPanel *****************

        yesButton = new JButton(new ImageIcon(yesImage));
        yesButton.setBounds(520, 410, 52, 40);
        yesButton.setBorderPainted(false);
        yesButton.setContentAreaFilled(false);
        yesButton.setFocusPainted(false);
        yesButton.addActionListener(this);

        noButton = new JButton(new ImageIcon(noImage));
        noButton.setBounds(460, 410, 52, 40);
        noButton.setBorderPainted(false);
        noButton.setContentAreaFilled(false);
        noButton.setFocusPainted(false);
        noButton.addActionListener(this);

        exitPanel.add(yesButton);
        exitPanel.add(noButton);
        // ******* End of exitPanel ****************************

        // Load home menu
        homeMenu();
    }

    /**
     * Setup GUI elements for Home
     */
    private void homeMenu() {
        currentSection = "home";
        currentUser = "none";
        removePanels();
        homePanel.setLayout(null); // reset panel layout
        addPanel(currentSection);
        refreshSidebar();
        frame.setVisible(true);
    }

    /**
     * Setup GUI elements for Users
     */
    private void usersMenu() {
        currentSection = "users";
        removePanels();
        usersPanel.setLayout(null); // reset panel layout
        addPanel(currentSection);
        refreshSidebar();

        if (currentUser == "none") {
            trackLabel.setText(
                    "<html><span style='color:red;'>No user selected!</span><br><br>Please select a user from Users >> Select a User</html>");
            System.out.println("No user selected!");
        }
        nameField.setText("");
        flavor1Dropdown.setSelectedIndex(-1);
        flavor2Dropdown.setSelectedIndex(-1);
    }

    /**
     * Setup GUI elements for Track
     */
    private void trackMenu() {
        currentSection = "track";
        removePanels();
        trackPanel.setLayout(null); // reset panel layout
        addPanel(currentSection);
        refreshSidebar();

        if (currentUser == "none") {
            trackLabel.setText(
                    "<html><span style='color:red;'>No user selected!</span><br><br>Please select a user from Users >> Select a User</html>");
            System.out.println("No user selected!");
            trackFeedbackLabel.setText("");
            currentUserPrimaryLabel.setText("");
            currentUserSecondaryLabel.setText("");
            currentUserConsumptionLabel.setText("");
            recordUsageButton.setVisible(false);
            loadCO2Button.setVisible(false);
            trackFeedbackLabel.setVisible(false);
            useCO2DailyButton.setVisible(false);
            useCO2WeeklyButton.setVisible(false);

            // Clear the trackTable
            String[] columnNames = { "Tank Name", "Date Filled", "Size (OZ)", "Remaining (OZ)" };
            DefaultTableModel emptyModel = new DefaultTableModel(columnNames, 0); // No rows
            trackTable.setModel(emptyModel);

        } else {
            System.out.println("****** The current user is: " + currentUser);
            trackLabel.setText("<html><strong>User:</strong> " + currentUser + "</html>");
            trackFeedbackLabel.setText("<html>Please click Load CO2 Tank Data</html>");
            currentUserPrimaryLabel.setText("Primary Flavor: " + currentUserPrimary);
            currentUserSecondaryLabel.setText("Secondary Flavor: " + currentUserSecondary);
            currentUserConsumptionLabel.setText("Average Cosumption per Day: " + currentUserConsumption);
            recordUsageButton.setVisible(true);
            loadCO2Button.setVisible(true);
            trackFeedbackLabel.setVisible(true);
            useCO2DailyButton.setVisible(true);
            useCO2WeeklyButton.setVisible(true);

        }
    }

    /**
     * Setup GUI elements for Trends
     */
    private void trendsMenu() {
        currentSection = "trends";
        removePanels();
        trendsPanel.setLayout(null); // reset panel layout
        addPanel(currentSection);
        refreshSidebar();

        File usageFile = new File(USAGE_FILE);
        if (!usageFile.exists()) {
            JOptionPane.showMessageDialog(
                    frame,
                    "The file '" + USAGE_FILE
                            + "'' does not exist in the application directory!\nThis application will now close.",
                    "File Not Found",
                    JOptionPane.WARNING_MESSAGE);
            System.exit(0);
            return;
        }

        if (currentUser == "none") {
            trendsLabel
                    .setText(
                            "<html><span style='color:red;'>No user selected!</span><br><br>Please select a user from Users >> Select a User</html>");
            System.out.println("No user selected!");
            currentUserPrimaryLabel.setText("");
            currentUserSecondaryLabel.setText("");
            currentUserConsumptionLabel.setText("");

            // Clear the trendsTable
            String[] columnNames = { "User", "Date", "Action", "Details", "Flavor" };
            DefaultTableModel emptyModel = new DefaultTableModel(columnNames, 0); // No rows
            trendsTable.setModel(emptyModel);
        } else {
            System.out.println("The current user is: " + currentUser);
            trendsLabel.setText("<html><strong>User:</strong> " + currentUser + "</html>");
            currentUserPrimaryLabel
                    .setText("<html><strong>Primary Flavor:</strong>  " + currentUserPrimary + "</html>");
            currentUserSecondaryLabel
                    .setText("<html><strong>Secondary Flavor:</strong> " + currentUserSecondary + "</html>");
            currentUserConsumptionLabel
                    .setText("<html><strong>Average Daily Consumption:</strong> " + currentUserConsumption + "</html>");
            Users.displayUserUsage(USAGE_FILE, currentUser, trendsPanel, trendsTable);
        }
    }

    /**
     * Setup GUI elements for About
     */
    private void aboutMenu() {
        currentSection = "about";
        removePanels();
        aboutPanel.setLayout(null); // reset panel layout
        addPanel(currentSection);
        refreshSidebar();
    }

    /**
     * Setup GUI elements for Exit
     */
    private void exitMenu() {
        currentSection = "exit";
        removePanels();
        exitPanel.setLayout(null); // reset panel layout
        addPanel(currentSection);
        refreshSidebar();
        // exitPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
    }

    /**
     * Handles various button events
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == selectUserButton) {
            System.out.println("select a user clicked!");
            userFeedbackLabel.setText("");

            nameField.setText("");
            flavor1Dropdown.setSelectedIndex(-1);
            flavor2Dropdown.setSelectedIndex(-1);
            sodaConsumptionDropdown.setSelectedIndex(-1);
            nameLabel.setVisible(false);
            nameField.setVisible(false);
            primaryFlavorLabel.setVisible(false);
            secondaryFlavorLabel.setVisible(false);
            flavor1Dropdown.setVisible(false);
            flavor2Dropdown.setVisible(false);
            registerButton.setVisible(false);
            resetInputButton.setVisible(false);
            userFeedbackLabel.setVisible(false);
            sodaConsumptionLabel.setVisible(false);
            sodaConsumptionDropdown.setVisible(false);

            deleteUserButton.setEnabled(true);
            resetUsersButton.setEnabled(true);
            trackUserButton.setEnabled(true);
            trendsUserButton.setEnabled(true);

            userDropdown.setVisible(true);
            recordUsageButton.setVisible(true);
            loadCO2Button.setVisible(true);

            resetUserDropDown();

            usersPanel.revalidate();
            usersPanel.repaint();
        }

        // Handles the New User button in Users
        if (e.getSource() == newUserButton) {
            System.out.println("new user clicked!");
            userDropdown.setVisible(false);

            deleteUserButton.setEnabled(false);
            resetUsersButton.setEnabled(false);
            trackUserButton.setEnabled(false);
            trendsUserButton.setEnabled(false);

            nameLabel.setVisible(true);
            nameField.setVisible(true);
            primaryFlavorLabel.setVisible(true);
            secondaryFlavorLabel.setVisible(true);
            flavor1Dropdown.setVisible(true);
            flavor2Dropdown.setVisible(true);
            registerButton.setVisible(true);
            resetInputButton.setVisible(true);
            userFeedbackLabel.setVisible(true);
            sodaConsumptionLabel.setVisible(true);
            sodaConsumptionDropdown.setVisible(true);

            usersPanel.revalidate();
            usersPanel.repaint();
        }

        // Handles the Reset button in Users
        if (e.getSource() == resetInputButton) {
            System.out.println("reset input clicked!");
            nameField.setText("");
            flavor1Dropdown.setSelectedIndex(-1);
            flavor2Dropdown.setSelectedIndex(-1);
            sodaConsumptionDropdown.setSelectedIndex(-1);
        }

        // Handles the User Track button in Users
        if (e.getSource() == trackUserButton) {
            System.out.println("user track clicked!");
            String selectedUser = (String) userDropdown.getSelectedItem();

            if (selectedUser != null) {
                // Split the string and get the name part (first portion)
                String userOnly = selectedUser.split(" - ")[0];
                String primaryFlavorOnly = selectedUser.split(" - ")[1];
                String secondaryFlavorOnly = selectedUser.split(" - ")[2];
                String sodaConsumptionOnly = selectedUser.split(" - ")[3];
                System.out.println("Viewing: " + userOnly);

                currentUser = userOnly;
                currentUserPrimary = primaryFlavorOnly;
                currentUserSecondary = secondaryFlavorOnly;
                currentUserConsumption = sodaConsumptionOnly;

                for (Users.User user : Users.userList) {
                    if (user.getName().equalsIgnoreCase(currentUser)) {
                        currentUserObject = user; // Set global currentUserObject
                        break;
                    }
                }

                trackMenu();
            } else {
                System.out.println("No user selected.");
            }

        }

        // Handles the User Trends button in Users
        if (e.getSource() == trendsUserButton) {
            System.out.println("user trends clicked!");
            String selectedUser = (String) userDropdown.getSelectedItem();

            if (selectedUser != null) {
                // Split the string and get the name part (first portion)
                String userOnly = selectedUser.split(" - ")[0];
                String primaryFlavorOnly = selectedUser.split(" - ")[1];
                String secondaryFlavorOnly = selectedUser.split(" - ")[2];
                String sodaConsumptionOnly = selectedUser.split(" - ")[3];
                System.out.println("Viewing: " + userOnly);

                currentUser = userOnly;
                currentUserPrimary = primaryFlavorOnly;
                currentUserSecondary = secondaryFlavorOnly;
                currentUserConsumption = sodaConsumptionOnly;
                trendsMenu();
            } else {
                System.out.println("No user selected.");
            }

        }

        // Handles the Delete user button in Users
        if (e.getSource() == deleteUserButton) {
            System.out.println("delete clicked!");

            String selectedUser = (String) userDropdown.getSelectedItem();

            if (selectedUser != null) {
                // Show confirmation dialog box
                int deleteConfirmation = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to delete user: " + selectedUser.split(" - ")[0] + "?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (deleteConfirmation == JOptionPane.YES_OPTION) {

                    String userToDelete = selectedUser.split(" - ")[0];
                    List<Users.User> users = Users.readUsersFromFile(USERS_FILE);

                    List<Users.User> updatedUsers = new ArrayList<>();
                    boolean userFound = false;

                    for (Users.User user : users) {
                        if (user.getName().equals(userToDelete)) {
                            userFound = true;
                        } else {
                            updatedUsers.add(user);
                        }
                    }
                    if (!userFound) {
                        System.out.println("User not found in file.");
                        return;
                    }

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
                        for (Users.User user : updatedUsers) {
                            writer.write(
                                    user.getName() + "," + user.getPrimaryFlavor() + "," + user.getSecondaryFlavor()
                                            + "," + user.getAvgDailyConsumption());
                            writer.newLine();
                        }
                    } catch (IOException ex) {
                        System.err.println("Error writing file: " + ex.getMessage());
                        return;
                    }

                    userDropdown.removeItem(selectedUser);
                    if (userDropdown.getItemCount() == 0) {
                        userDropdown.addItem("No users found");
                        selectUserButton.setEnabled(false);
                        deleteUserButton.setEnabled(false);
                        trackUserButton.setEnabled(false);
                        trendsUserButton.setEnabled(false);
                        resetUsersButton.setEnabled(true);
                    }
                }

            }
        }

        // Handles the Reset Users button in Users
        if (e.getSource() == resetUsersButton) {
            System.out.println("reset users clicked!");

            try {
                // Show confirmation dialog box
                int deleteConfirmation = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to reset users to default values?",
                        "Confirm Reset",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (deleteConfirmation == JOptionPane.YES_OPTION) {
                    UsersCreate.resetUsers();
                    resetUserDropDown();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        // Handles the register button in Users >> New User
        if (e.getSource() == registerButton) {
            System.out.println("register clicked!");
            String feedbackText;
            if (nameField.getText().trim().isEmpty()) {
                feedbackText = "Name cannot be blank. Please enter a name.";
                System.out.println(feedbackText);
                userFeedbackLabel.setText(feedbackText);
            } else if (flavor1Dropdown.getSelectedIndex() == flavor2Dropdown.getSelectedIndex()) {
                feedbackText = "Primary and secondary flavor match. Please choose unique values.";
                System.out.println(feedbackText);
                userFeedbackLabel.setText(feedbackText);
            } else if (flavor1Dropdown.getSelectedIndex() == -1 || flavor2Dropdown.getSelectedIndex() == -1) {
                feedbackText = "Select options for both primary and secondary flavors.";
                System.out.println(feedbackText);
                userFeedbackLabel.setText(feedbackText);
            } else if (sodaConsumptionDropdown.getSelectedIndex() == -1) {
                feedbackText = "Select soda consumption per day.";
                System.out.println(feedbackText);
                userFeedbackLabel.setText(feedbackText);
            } else {
                feedbackText = "<html>Registration successful! Please click <strong>Select a User</strong> to continue.</html>";
                System.out.println(feedbackText);
                userFeedbackLabel.setText(feedbackText);

                String user = nameField.getText().trim();
                String primaryFlavor = (String) flavor1Dropdown.getSelectedItem();
                String secondaryFlavor = (String) flavor2Dropdown.getSelectedItem();
                String sodaConsumption = (String) sodaConsumptionDropdown.getSelectedItem();

                // Convert cosumption from Oz friendly measurements to only liters
                String litersValue = switch (sodaConsumption) {
                    case "0.5 liters / 17oz" -> "0.5";
                    case "1 liter / 34oz" -> "1";
                    case "1.5 liters / 51oz" -> "1.5";
                    case "2 liters / 68oz" -> "2";
                    case "2.5 liters / 85oz" -> "2.5";
                    case "3 liters / 101oz" -> "3";
                    default -> ""; // Handle invalid or no selection
                };

                // Write the data to the file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
                    writer.write(user + "," + primaryFlavor + "," + secondaryFlavor + "," + litersValue);
                    writer.newLine();
                    System.out.println("User data saved to file: " + USERS_FILE);
                } catch (Exception ex) {
                    feedbackText = "Error writing to file: " + ex.getMessage();
                    System.err.println(feedbackText);
                }
                selectUserButton.setEnabled(true);
                resetUserDropDown();
            }

        }
        // Handles the Exit dialog buttons
        if (e.getSource() == yesButton) {
            System.out.println("yes clicked!");
            System.exit(0);
        }
        if (e.getSource() == noButton) {
            System.out.println("no clicked!");
            homeMenu();
        }

        // Handles the Check CO2 button in Track
        if (e.getSource() == loadCO2Button) {
            System.out.println("Check CO2 clicked!");
            trackFeedbackLabel.setText("");
            tankQueue.clear();

            // Read tank data from file
            List<CO2.Tank> tanks = CO2.readCO2FromFile(CO2_FILE);
            System.out.println("******* Tanks loaded from file: " + CO2_FILE + ". Total tanks loaded: " + tanks.size());

            // Enqueue tanks into the queue
            for (CO2.Tank tank : tanks) {
                System.out.println("******* Adding tank to queue: " + tank);
                tankQueue.enQueue(tank);
            }

            String[] columnNames = { "Tank Name", "Date Filled", "Size (OZ)", "Remaining (OZ)" };
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            for (CO2.Tank tank : tankQueue.toList()) {
                model.addRow(new Object[] {
                        tank.getName(),
                        tank.getDate(),
                        tank.getSize(),
                        tank.getLevel()
                });
                System.out.println("Tank added to track table: " + tank);
            }

            trackTable.setModel(model);
        }

        /*
         * Handles the Use CO2 DAILY button in Track
         * Stats:
         * A 14.46oz SodaStream CO2 tank can produce 60 liters of carbonated water.
         * So an 80oz (5LBS) CO2 tanks can produce approximately 332 liters of
         * carbonated water.
         * 1 Liter = 33.81 fl oz
         */
        if (e.getSource() == useCO2DailyButton) {
            System.out.println("Use CO2 Daily clicked!");

            if (tankQueue.isEmpty()) {
                System.out.println("Queue is empty");
                return;
            }

            try {
                double userAVGdailyConsumption = Double.parseDouble(currentUserObject.getAvgDailyConsumption());

                // Calculate the user's daily CO2 usage based on their set AVGdailyConsumption
                double CO2OuncesPerLiter = 14.46 / 60;
                double CO2UsedOunces = Math.round(userAVGdailyConsumption * CO2OuncesPerLiter * 100.0) / 100.0;

                System.out.println("Will subtract user's daily CO2 Usage of " + CO2UsedOunces + " ounces");
                useCO2(CO2UsedOunces, "daily");

            } catch (NumberFormatException nfe) {
                System.err.println("Error parsing average daily consumption: " + nfe.getMessage());
            }

        }

        /*
         * Handles the Use CO2 WEEKLY button in Track
         */
        if (e.getSource() == useCO2WeeklyButton) {
            System.out.println("Use CO2 Weekly clicked!");

            if (tankQueue.isEmpty()) {
                System.out.println("Queue is empty");
                return;
            }

            try {
                double userAVGdailyConsumption = Double.parseDouble(currentUserObject.getAvgDailyConsumption());
                double weeklyConsumption = userAVGdailyConsumption * 7;

                // Calculate the user's daily CO2 usage based on their set AVGdailyConsumption
                double CO2OuncesPerLiter = 14.46 / 60;
                double CO2UsedOunces = Math.round(weeklyConsumption * CO2OuncesPerLiter * 100.0) / 100.0;

                System.out.println("Will subtract user's weekly CO2 Usage of " + CO2UsedOunces + " ounces");
                useCO2(CO2UsedOunces, "weekly");

            } catch (NumberFormatException nfe) {
                System.err.println("Error parsing average weekly consumption: " + nfe.getMessage());
            }

        }

        trackPanel.revalidate();
        trackPanel.repaint();
    }

    private void useCO2(double userCO2UsedOunces, String timeFrame) {

        if (tankQueue == null || tankQueue.isEmpty()) {
            System.out.println("No CO2 tanks available in the queue.");
            return;
        }
        if (!tankQueue.isEmpty()) {
            System.out.println("Queue is not empty");
        }

        // Retrieve the first tank in the Queue
        CO2.Tank tank = tankQueue.front();

        // CO2.Tank tank = new CO2.Tank("Tank1", "2024-12-06", "100", "50");

        System.out.println("Name: " + tank.getName());

        System.out.println("Date: " + tank.getDate());

        System.out.println("Size: " + tank.getDate());

        System.out.println("Level: " + tank.getDate());

        if (tank.getLevel() >= userCO2UsedOunces) {
            double levelUpdate = tank.getLevel() - userCO2UsedOunces;
            double formattedevelUpdate = Math.round(levelUpdate * 100.0) / 100.0;
            tank.setLevel(formattedevelUpdate);

            System.out.println("Subtracted user's " + timeFrame + " CO2 Usage of " + userCO2UsedOunces + " ounces");
            trackFeedbackLabel.setText(
                    currentUser + "'s " + timeFrame + " CO2 usage of " + userCO2UsedOunces
                            + " ounces has been subtracted from the tank queue.");

            // updateTableRow(tank);

            // Update the Table row
            DefaultTableModel model = (DefaultTableModel) trackTable.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                String tankName = (String) model.getValueAt(i, 0);
                if (tankName.equals(tank.getName())) {
                    model.setValueAt(tank.getLevel(), i, 3); // Remaining (OZ)
                    System.out.println("Updated tank: " + tank.getName() + " to new level: " + tank.getLevel());
                    break;
                }
            }

        } else {
            tankQueue.deQueue(); // Remove tank since it's empty
            System.out.println("Not enough CO2 remaining in the tank. Dequeuing.");
            trackFeedbackLabel.setText("Not enough CO2 remaing in the tank. Dequeuing.");
        }
        tankQueue.printQueue();

    }

    /**
     * Method to remove panels from GUI
     */
    private void removePanels() {
        frame.getContentPane().remove(1);
    }

    /**
     * Method to add panel to GUI based on currentSection
     * 
     * @param currentSection
     */
    private void addPanel(String currentSection) {

        // add panel that corresponds to currentSection
        JPanel newPanel = switch (currentSection) {
            case "home" -> homePanel;
            case "users" -> usersPanel;
            case "track" -> trackPanel;
            case "trends" -> trendsPanel;
            case "about" -> aboutPanel;
            case "exit" -> exitPanel;
            default -> null;
        };

        if (newPanel != null) {
            frame.add(newPanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
        } else {
            System.err.println("Error loading panel for section: " + currentSection);
        }
    }

    void resetUserDropDown() { // change from private for unit testing
        // Read users from the file and display them in the dropdown
        List<Users.User> users = Users.readUsersFromFile(USERS_FILE);
        userDropdown.removeAllItems();
        if (users.isEmpty()) {
            userDropdown.addItem("No users found");
        } else {
            for (Users.User user : users) {
                userDropdown.addItem(
                        user.getName() + " - " + user.getPrimaryFlavor() + " - " + user.getSecondaryFlavor()
                                + " - "
                                + user.getAvgDailyConsumption() + " Liters");
            }
        }
    }

    /**
     * Method to refresh the sidebar navigation menu
     */
    private void refreshSidebar() {
        sidebarPanel.removeAll();
        sidebarPanel.setLayout(null);

        // new nav menu code
        int nav_x = 8;
        int nav_y = 139;
        int nav_spacing = 40;

        JButton homeButton = createNavButton("home", homeNav, homeNavHighlight, nav_x, nav_y);
        sidebarPanel.add(homeButton);
        nav_y += nav_spacing;

        JButton usersButton = createNavButton("users", usersNav, usersNavHighlight, nav_x, nav_y);
        sidebarPanel.add(usersButton);
        nav_y += nav_spacing;

        JButton trackButton = createNavButton("track", trackNav, trackNavHighlight, nav_x, nav_y);
        sidebarPanel.add(trackButton);
        nav_y += nav_spacing;

        JButton trendsButton = createNavButton("trends", trendsNav, trendsNavHighlight, nav_x, nav_y);
        sidebarPanel.add(trendsButton);
        nav_y += nav_spacing;

        JButton aboutButton = createNavButton("about", aboutNav, aboutNavHighlight, nav_x, nav_y);
        sidebarPanel.add(aboutButton);
        nav_y += nav_spacing;

        JButton exitButton = createNavButton("exit", exitNav, exitNavHighlight, nav_x, nav_y);
        sidebarPanel.add(exitButton);
    }

    /**
     * Method to control the navigation menu mouseover states
     * 
     * @param section
     * @param defaultImage
     * @param highlightImage
     * @param x
     * @param y
     * @return
     */
    private JButton createNavButton(String section, Image defaultImage, Image highlightImage, int x, int y) {

        Image menuState = section.equals(currentSection) ? highlightImage : defaultImage;
        JButton button = new JButton(new ImageIcon(menuState));

        button.setRolloverIcon(new ImageIcon(highlightImage));
        button.setBounds(x, y, defaultImage.getWidth(null), defaultImage.getHeight(null));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);

        // Add ActionListener for navigation
        button.addActionListener(e -> handleNavigation(section));
        return button;
    }

    /**
     * Method to handle navigation button clicks
     * 
     * @param section
     */
    private void handleNavigation(String section) {
        currentSection = section;
        if (section.contains("home")) {
            System.out.println("Home clicked!");
            homeMenu();
        } else if (section.contains("users")) {
            System.out.println("Users clicked!");
            usersMenu();
        } else if (section.contains("track")) {
            System.out.println("Track clicked!");
            trackMenu();
        } else if (section.contains("trends")) {
            System.out.println("Trends clicked!");
            trendsMenu();
        } else if (section.contains("about")) {
            System.out.println("About clicked!");
            aboutMenu();
        } else if (section.contains("exit")) {
            System.out.println("Exit clicked!");
            exitMenu();
        }
    }

    /**
     * Method to center app on screen if only 1 monitor present or center on a 2nd
     * screen if multiple are present
     */
    private void moveToSecondMonitor() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screens = ge.getScreenDevices();

        if (screens.length > 1) { // Check if there is multiple monitors
            // Get screen size of the second monitor
            int screenWidth = screens[1].getDefaultConfiguration().getBounds().width;
            int screenHeight = screens[1].getDefaultConfiguration().getBounds().height;
            int screenX = screens[1].getDefaultConfiguration().getBounds().x;
            int screenY = screens[1].getDefaultConfiguration().getBounds().y;

            // Calculate the centered position for the second monitor
            int x = screenX + (screenWidth - frame.getWidth()) / 2;
            int y = screenY + (screenHeight - frame.getHeight()) / 2;

            // Set the frame location to the centered position of the second monitor
            frame.setLocation(x, y);

        } else {
            // Center the frame on the primary screen if only 1 monitor
            frame.setLocationRelativeTo(null);
        }
    }
    
 // Added for Unit Tests
    public JPanel getTrendsPanel() {
        return trendsPanel;
    }
    

    // Added for Unit Tests
    public JFrame getFrame() {
        return frame;
    }
    
 // Added for Unit Tests
    public JComboBox<String> getUserDropdown() {
        return userDropdown;
    }

    /**
     * Starts application by calling primary method
     * 
     * @param args
     */
    public static void main(String[] args) {
        new SodaTracker();
        System.out.println("Welcome to SodaTracker!");

    }


}