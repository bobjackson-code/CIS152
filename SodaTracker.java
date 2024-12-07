import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Final Project: SodaTracker
 * App for managing a home carbonated beverage system
 * 
 * @author Robert Jackson - 12/6/24
 * @version 1.0
 * @since 1.0
 */

public class SodaTracker implements ActionListener {

    private JLabel feedbackLabel;
    private JLabel trackLabel;
    private JLabel trendsLabel;
    private JLabel nameLabel;
    private JLabel primaryFlavorLabel;
    private JLabel secondaryFlavorLabel;
    private JLabel sodaConsumptionLabel;
    private JLabel currentUserPrimaryLabel;
    private JLabel currentUserSecondaryLabel;
    private JLabel currentUserConsumptionLabel;
    private JLabel usageTableLabel;
    private JTextField nameField;
    private JFrame frame;
    private JButton yesButton;
    private JButton noButton;
    private JButton registerButton;
    private JButton resetButton;
    private JButton newUserButton;
    private JButton selectUserButton;
    private JButton deleteUserButton;
    private JButton trackUserButton;
    private JButton trendsUserButton;
    private JButton recordUsageButton;
    private JButton checkCO2Button;
    private JComboBox<String> flavor1Dropdown;
    private JComboBox<String> flavor2Dropdown;
    private JComboBox<String> sodaConsumptionDropdown;
    private JComboBox<String> userDropdown;
    private JTable usageTable;

    private static final String USERS_CONFIG_FILE = "users.csv";

    // define JPanels for app
    private JPanel sidebarPanel, homePanel, usersPanel, trackPanel, trendsPanel, aboutPanel, exitPanel;

    // define section backgrounds
    private Image sidebarImage, homeImage, usersImage, trackImage, trendsImage, aboutImage, exitImage, yesImage,
            noImage;

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

    public SodaTracker() {
        currentSection = "home";
        // load background images
        sidebarImage = new ImageIcon("assets/section_sidebar.png").getImage();
        homeImage = new ImageIcon("assets/section_home.png").getImage();
        usersImage = new ImageIcon("assets/section_users.png").getImage();
        trackImage = new ImageIcon("assets/section_track.png").getImage();
        trendsImage = new ImageIcon("assets/section_trends.png").getImage();
        aboutImage = new ImageIcon("assets/section_about.png").getImage();
        exitImage = new ImageIcon("assets/section_exit.png").getImage();
        yesImage = new ImageIcon("assets/button_yes.png").getImage();
        noImage = new ImageIcon("assets/button_no.png").getImage();

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

        frame.setTitle("SodaTracker");
        frame.setSize(1000, 800);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the custom panel
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

        // Setup the components for the usersPanel
        // //////////////////////////////////////
        Dimension panelSize = usersPanel.getPreferredSize();
        int panelWidth = panelSize.width;
        int buttonWidth = 160;
        int buttonHeight = 48;

        int buttonXOffset = 100;
        int buttonY = 220;

        int loadButtonX = (panelWidth / 2) - (buttonWidth / 2) + buttonXOffset;
        int newButtonX = (panelWidth / 2) - (buttonWidth / 2) - buttonXOffset;

        newUserButton = new JButton("New User");
        newUserButton.setBounds(newButtonX, buttonY, buttonWidth, buttonHeight);
        newUserButton.addActionListener(this);

        selectUserButton = new JButton("Select a User");
        selectUserButton.setBounds(loadButtonX, buttonY, buttonWidth, buttonHeight);
        selectUserButton.addActionListener(this);

        int buttonYOffset = 295;
        int buttonYIncrement = 25;
        nameLabel = new JLabel("Enter Your Name:");
        nameField = new JTextField(20);
        nameLabel.setBounds(56, buttonYOffset, 200, 25);
        buttonYOffset += buttonYIncrement;
        nameField.setBounds(52, buttonYOffset, 250, 25);
        buttonYOffset += (buttonYIncrement + 20);
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

        primaryFlavorLabel.setBounds(56, buttonYOffset, 200, 25);
        buttonYOffset += buttonYIncrement;
        flavor1Dropdown.setBounds(52, buttonYOffset, 250, 30);
        buttonYOffset += (buttonYIncrement + 20);

        secondaryFlavorLabel.setBounds(56, buttonYOffset, 200, 25);
        buttonYOffset += buttonYIncrement;
        flavor2Dropdown.setBounds(52, buttonYOffset, 250, 30);
        buttonYOffset += (buttonYIncrement + 20);

        sodaConsumptionLabel = new JLabel("Select Your Soda Consumption Per Day:");
        sodaConsumptionDropdown = new JComboBox<>(new String[] {
                "0.5 liters / 17oz", "1 liter / 34oz", "1.5 liters / 51oz", "2 liters / 68oz", "2.5 liters / 85oz",
                "3 liters / 101oz"
        });
        sodaConsumptionDropdown.setSelectedIndex(-1);
        sodaConsumptionDropdown.setMaximumRowCount(6);
        sodaConsumptionLabel.setBounds(56, buttonYOffset, 400, 25);
        buttonYOffset += buttonYIncrement;
        sodaConsumptionDropdown.setBounds(52, buttonYOffset, 250, 30);
        buttonYOffset += (buttonYIncrement + 30);
        sodaConsumptionLabel.setVisible(false);
        sodaConsumptionDropdown.setVisible(false);

        // Buttons
        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        resetButton = new JButton("Reset");
        resetButton.addActionListener(this);
        registerButton.setBounds(70, buttonYOffset, 100, 25);
        resetButton.setBounds(170, buttonYOffset, 100, 25);
        buttonYOffset += (buttonYIncrement + 20);
        registerButton.setVisible(false);
        resetButton.setVisible(false);

        feedbackLabel = new JLabel("");
        feedbackLabel.setBounds(78, buttonYOffset, 550, 25);
        usersPanel.add(feedbackLabel);

        // ************* Select a User **********************************
        // Dropdown for user list
        userDropdown = new JComboBox<>();
        userDropdown.setBounds(56, 300, 510, 30);
        userDropdown.setVisible(false);
        usersPanel.add(userDropdown);

        // Delete button
        deleteUserButton = new JButton("Delete User");
        deleteUserButton.setBounds(580, 300, 110, 30);
        deleteUserButton.setVisible(false);
        deleteUserButton.addActionListener(this);
        usersPanel.add(deleteUserButton);

        // Track button
        trackUserButton = new JButton("User Track");
        trackUserButton.setBounds(580, 340, 110, 30);
        trackUserButton.setVisible(false);
        trackUserButton.addActionListener(this);
        usersPanel.add(trackUserButton);

        // Trends button
        trendsUserButton = new JButton("User Trends");
        trendsUserButton.setBounds(580, 380, 110, 30);
        trendsUserButton.setVisible(false);
        trendsUserButton.addActionListener(this);
        usersPanel.add(trendsUserButton);

        usersPanel.add(nameLabel);
        usersPanel.add(nameField);
        usersPanel.add(primaryFlavorLabel);
        usersPanel.add(flavor1Dropdown);
        usersPanel.add(secondaryFlavorLabel);
        usersPanel.add(flavor2Dropdown);
        usersPanel.add(registerButton);
        usersPanel.add(resetButton);
        usersPanel.add(sodaConsumptionLabel);
        usersPanel.add(sodaConsumptionDropdown);
        usersPanel.add(newUserButton);
        usersPanel.add(selectUserButton);

        // ************* End Select a User ***********************

        // ******* Components for the trackPanel *****************

        int trackYOffset = 180;
        int trackYIncrement = 25;

        trackLabel = new JLabel("");
        Font font = new Font("Arial", Font.PLAIN, 16);
        trackLabel.setForeground(new Color(61, 61, 61));
        trackLabel.setFont(font);
        trackLabel.setVerticalAlignment(SwingConstants.TOP);
        trackLabel.setHorizontalAlignment(SwingConstants.LEFT);
        trackLabel.setBounds(58, trackYOffset, 600, 60);
        trackYOffset += (trackYIncrement+ 10);
        trackPanel.add(trackLabel);

        recordUsageButton = new JButton("Record Usage");
        recordUsageButton.setBounds(58, trackYOffset, 160, 48);
        recordUsageButton.setVisible(false);
        recordUsageButton.addActionListener(this);
        trackPanel.add(recordUsageButton);

        checkCO2Button = new JButton("Check CO2");
        checkCO2Button.setBounds(243, trackYOffset, 160, 48);
        trackYOffset += trackYIncrement;
        checkCO2Button.setVisible(false);
        checkCO2Button.addActionListener(this);
        trackPanel.add(checkCO2Button);

        // System.out.println("The current user is: " + currentUser);
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
        trendsPanel.add(trendsLabel);
        // System.out.println("The current user is: " + currentUser);

        currentUserPrimaryLabel = new JLabel("");
        currentUserPrimaryLabel.setFont(font);
        currentUserPrimaryLabel.setVerticalAlignment(SwingConstants.TOP);
        currentUserPrimaryLabel.setHorizontalAlignment(SwingConstants.LEFT);
        currentUserPrimaryLabel.setBounds(58, trendsYOffset, 600, 60);
        trendsYOffset += trendsYIncrement;
        trendsPanel.add(currentUserPrimaryLabel);

        currentUserSecondaryLabel = new JLabel("");
        currentUserSecondaryLabel.setFont(font);
        currentUserSecondaryLabel.setVerticalAlignment(SwingConstants.TOP);
        currentUserSecondaryLabel.setHorizontalAlignment(SwingConstants.LEFT);
        currentUserSecondaryLabel.setBounds(58, trendsYOffset, 600, 60);
        trendsYOffset += trendsYIncrement;
        trendsPanel.add(currentUserSecondaryLabel);

        currentUserConsumptionLabel = new JLabel("");
        currentUserConsumptionLabel.setFont(font);
        currentUserConsumptionLabel.setVerticalAlignment(SwingConstants.TOP);
        currentUserConsumptionLabel.setHorizontalAlignment(SwingConstants.LEFT);
        currentUserConsumptionLabel.setBounds(58, trendsYOffset, 600, 60);
        trendsYOffset += (trendsYIncrement + 10);
        trendsPanel.add(currentUserConsumptionLabel);

        usageTableLabel = new JLabel("");
        usageTableLabel.setFont(font);
        usageTableLabel.setVerticalAlignment(SwingConstants.TOP);
        usageTableLabel.setHorizontalAlignment(SwingConstants.LEFT);
        usageTableLabel.setBounds(58, trendsYOffset, 600, 60);
        trendsYOffset += trendsYIncrement;
        trendsPanel.add(usageTableLabel);

        String[] columnNames = { "User", "Date", "Action", "Details", "Flavor" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0); // 0 rows initially
        usageTable = new JTable(tableModel);

        // ********* Usage Table
        // *****************************************************************
        JScrollPane scrollPane = new JScrollPane(usageTable);
        scrollPane.setBounds(58, trendsYOffset, 640, 420); // Adjust size as needed
        trendsPanel.add(scrollPane);

        trendsPanel.setLayout(null);

        // usageTable.setBounds(58, trendsYOffset, 600, 60);
        usageTable.setRowHeight(30);
        usageTable.setFont(new Font("Arial", Font.PLAIN, 16));

        // trendsPanel.add(new JScrollPane(usageTable));

        trendsPanel.revalidate();
        trendsPanel.repaint();

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

    private void homeMenu() {
        currentSection = "home";
        // currentUser = null;
        removePanels();
        addPanel(currentSection);
        refreshSidebar();

        homePanel.setLayout(null); // reset panel layout
        // sidebarPanel.setLayout(null);

        // frame.getContentPane().removeAll();
        // frame.add(sidebarPanel, BorderLayout.WEST);

        homePanel.revalidate();
        homePanel.repaint();
        frame.setVisible(true);

    }

    private void usersMenu() {
        currentSection = "users";
        removePanels();
        addPanel(currentSection);
        refreshSidebar();

        usersPanel.setLayout(null); // reset panel layout

        if (currentUser == null) {
            trackLabel.setText(
                    "<html><span style='color:red;'>No user selected!</span><br><br>Please select a user from Users >> Select a User</html>");
            System.out.println("No user selected!");
            recordUsageButton.setVisible(false);
            checkCO2Button.setVisible(false);
        } else {
            recordUsageButton.setVisible(true);
            checkCO2Button.setVisible(true);
        }

        nameField.setText("");
        flavor1Dropdown.setSelectedIndex(-1);
        flavor2Dropdown.setSelectedIndex(-1);

        // refresh GUI
        usersPanel.revalidate();
        usersPanel.repaint();
    }

    private void trackMenu() {
        currentSection = "track";
        removePanels();
        addPanel(currentSection);
        refreshSidebar();
        trackPanel.setLayout(null); // reset panel layout

        if (currentUser == null) {
            trackLabel.setText(
                    "<html><span style='color:red;'>No user selected!</span><br><br>Please select a user from Users >> Select a User</html>");
            System.out.println("No user selected!");
            currentUserPrimaryLabel.setText("");
            currentUserSecondaryLabel.setText("");
            currentUserConsumptionLabel.setText("");
        } else {
            System.out.println("The current user is: " + currentUser);
            trackLabel.setText("<html><strong>User:</strong> " + currentUser + "</html>");
            currentUserPrimaryLabel.setText("Primary Flavor: " + currentUserPrimary);
            currentUserSecondaryLabel.setText("Secondary Flavor: " + currentUserSecondary);
            currentUserConsumptionLabel.setText("Average Cosumption per Day: " + currentUserConsumption);
        }

        trackPanel.revalidate();
        trackPanel.repaint();
    }

    private void trendsMenu() {
        currentSection = "trends";
        removePanels();
        addPanel(currentSection);
        refreshSidebar();
        trendsPanel.setLayout(null); // reset panel layout

        if (currentUser == null) {
            trendsLabel
                    .setText(
                            "<html><span style='color:red;'>No user selected!</span><br><br>Please select a user from Users >> Select a User</html>");
            System.out.println("No user selected!");
            currentUserPrimaryLabel.setText("");
            currentUserSecondaryLabel.setText("");
            currentUserConsumptionLabel.setText("");
        } else {
            System.out.println("The current user is: " + currentUser);
            trendsLabel.setText("<html><strong>User:</strong> " + currentUser + "</html>");
            currentUserPrimaryLabel
                    .setText("<html><strong>Primary Flavor:</strong>  " + currentUserPrimary + "</html>");
            currentUserSecondaryLabel
                    .setText("<html><strong>Secondary Flavor:</strong> " + currentUserSecondary + "</html>");
            currentUserConsumptionLabel
                    .setText("<html><strong>Average Daily Consumption:</strong> " + currentUserConsumption + "</html>");
        }
        Users.displayUserUsage("usage.csv", currentUser, trendsPanel, usageTable, usageTableLabel);

        trendsPanel.revalidate();
        trendsPanel.repaint();
    }

    private void aboutMenu() {
        currentSection = "about";
        removePanels();
        addPanel(currentSection);
        refreshSidebar();
        aboutPanel.setLayout(null); // reset panel layout

        aboutPanel.revalidate();
        aboutPanel.repaint();
    }

    private void exitMenu() {
        currentSection = "exit";
        removePanels();
        addPanel(currentSection);
        refreshSidebar();
        exitPanel.setLayout(null); // reset panel layout

        exitPanel.revalidate();
        exitPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == selectUserButton) {
            System.out.println("select a user clicked!");

            // Read users from the file and display them
            List<Users.User> users = Users.readUsersFromFile(USERS_CONFIG_FILE);

            userDropdown.removeAllItems();
            if (users.isEmpty()) {
                userDropdown.addItem("No users found");
            } else {
                for (Users.User user : users) {
                    userDropdown.addItem(
                            user.getName() + " - " + user.getPrimaryFlavor() + " - " + user.getSecondaryFlavor() + " - "
                                    + user.getAvgDailyConsumption() + " Liters");
                }
            }
            nameField.setText("");
            flavor1Dropdown.setSelectedIndex(-1);
            flavor2Dropdown.setSelectedIndex(-1);
            nameLabel.setVisible(false);
            nameField.setVisible(false);
            primaryFlavorLabel.setVisible(false);
            secondaryFlavorLabel.setVisible(false);
            flavor1Dropdown.setVisible(false);
            flavor2Dropdown.setVisible(false);
            registerButton.setVisible(false);
            resetButton.setVisible(false);
            feedbackLabel.setVisible(false);
            sodaConsumptionLabel.setVisible(false);
            sodaConsumptionDropdown.setVisible(false);

            userDropdown.setVisible(true);
            deleteUserButton.setVisible(true);
            trackUserButton.setVisible(true);
            trendsUserButton.setVisible(true);
            recordUsageButton.setVisible(true);
            checkCO2Button.setVisible(true);

            usersPanel.revalidate();
            usersPanel.repaint();
        }

        if (e.getSource() == checkCO2Button) {
            System.out.println("Check CO2 clicked!");

            CO2Tracker tracker = new CO2Tracker();
            if (tracker.tankQueue.isEmpty()) {
                tracker.addTank("Primary", 5.0);
                tracker.addTank("Backup", 5.0); 
            }
            StringBuilder message = new StringBuilder();
            if (tracker.tankQueue.isEmpty()) {
                message.append("Sorry, no tanks available. Add a CO2 tank.");
            } else {
                message.append("CO2 Tank Status:\n");
                for (CO2Tracker.Tank tank : tracker.tankQueue) {
                    message.append(tank.toString()).append("\n");
                }
            }

            trackPanel.revalidate();
            trackPanel.repaint();
        }

        if (e.getSource() == newUserButton) {
            System.out.println("new user clicked!");
            userDropdown.setVisible(false);
            deleteUserButton.setVisible(false);
            trackUserButton.setVisible(false);
            trendsUserButton.setVisible(false);

            nameLabel.setVisible(true);
            nameField.setVisible(true);
            primaryFlavorLabel.setVisible(true);
            secondaryFlavorLabel.setVisible(true);
            flavor1Dropdown.setVisible(true);
            flavor2Dropdown.setVisible(true);
            registerButton.setVisible(true);
            resetButton.setVisible(true);
            feedbackLabel.setVisible(true);
            sodaConsumptionLabel.setVisible(true);
            sodaConsumptionDropdown.setVisible(true);

            usersPanel.revalidate();
            usersPanel.repaint();
        }

        if (e.getSource() == resetButton) {
            System.out.println("reset clicked!");
            nameField.setText("");
            flavor1Dropdown.setSelectedIndex(-1);
            flavor2Dropdown.setSelectedIndex(-1);
        }

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
                trackMenu();
            } else {
                System.out.println("No user selected.");
            }

        }

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

        if (e.getSource() == deleteUserButton) {
            System.out.println("delete clicked!");

            String selectedUser = (String) userDropdown.getSelectedItem();

            if (selectedUser != null) {

                String userToDelete = selectedUser.split(" - ")[0];
                List<Users.User> users = Users.readUsersFromFile(USERS_CONFIG_FILE);

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

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_CONFIG_FILE))) {
                    for (Users.User user : updatedUsers) {
                        writer.write(user.getName() + "," + user.getPrimaryFlavor() + "," + user.getSecondaryFlavor()
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
                    deleteUserButton.setVisible(false);
                }

            }
        }

        if (e.getSource() == registerButton) {
            System.out.println("register clicked!");
            String feedbackText;
            if (nameField.getText().trim().isEmpty()) {
                feedbackText = "Name cannot be blank. Please enter a name.";
                System.out.println(feedbackText);
                feedbackLabel.setText(feedbackText);
            } else if (flavor1Dropdown.getSelectedIndex() == flavor2Dropdown.getSelectedIndex()) {
                feedbackText = "Primary and secondary flavor match. Please choose unique values.";
                System.out.println(feedbackText);
                feedbackLabel.setText(feedbackText);
            } else if (flavor1Dropdown.getSelectedIndex() == -1 || flavor2Dropdown.getSelectedIndex() == -1) {
                feedbackText = "Select options for both primary and secondary flavors.";
                System.out.println(feedbackText);
                feedbackLabel.setText(feedbackText);
            } else if (sodaConsumptionDropdown.getSelectedIndex() == -1) {
                feedbackText = "Select soda consumption per day.";
                System.out.println(feedbackText);
                feedbackLabel.setText(feedbackText);
            } else {
                feedbackText = "Registration successful!";
                System.out.println(feedbackText);
                feedbackLabel.setText(feedbackText);

                String user = nameField.getText().trim();
                String primaryFlavor = (String) flavor1Dropdown.getSelectedItem();
                String secondaryFlavor = (String) flavor2Dropdown.getSelectedItem();
                String sodaConsumption = (String) sodaConsumptionDropdown.getSelectedItem();

                // convert cosumption from OZ friendly measurements to only liters
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
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_CONFIG_FILE, true))) {
                    writer.write(user + "," + primaryFlavor + "," + secondaryFlavor + "," + litersValue);
                    writer.newLine();
                    System.out.println("User data saved to file: " + USERS_CONFIG_FILE);
                } catch (Exception ex) {
                    feedbackText = "Error writing to file: " + ex.getMessage();
                    System.err.println(feedbackText);
                }
            }
        }
        if (e.getSource() == yesButton) {
            System.out.println("yes clicked!");
            System.exit(0);
        }
        if (e.getSource() == noButton) {
            System.out.println("no clicked!");
            homeMenu();

        }
    }

    private void removePanels() {
        // frame.getContentPane().removeAll();
        frame.getContentPane().remove(1);
    }

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
            newPanel.setLayout(new BorderLayout());
            frame.revalidate();
            frame.repaint();
        }
    }

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

    public static void main(String[] args) {
        new SodaTracker();
        System.out.println("SodaTracker called");

    }

}
