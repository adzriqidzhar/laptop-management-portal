import java.awt.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class LaptopDashboard extends JFrame {

    // Input fields
    private JTextField brandField, modelField, processorField, ramField, storageField, batteryLifeField;
    private JTextField gpuModelField, businessSoftwareField;
    private JRadioButton gamingRadio, businessRadio;
    private JTextArea outputArea;
    private JButton submitButton, logoutButton, settingsButton, manageLaptopsButton;

    // Data structure to hold laptop data in table-friendly format
    private Vector<Vector<Object>> laptopData = new Vector<>();

    public LaptopDashboard() {
        super("Laptop Management Portal");

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Panel for common laptop info
        JPanel commonPanel = new JPanel(new GridBagLayout());
        commonPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Common Laptop Information",
                TitledBorder.LEFT, TitledBorder.TOP));
        GridBagConstraints cCommon = new GridBagConstraints();
        cCommon.insets = new Insets(4,4,4,4);
        cCommon.fill = GridBagConstraints.HORIZONTAL;
        cCommon.gridx = 0; cCommon.gridy = 0;

        brandField = new JTextField(15);
        modelField = new JTextField(15);
        processorField = new JTextField(15);
        ramField = new JTextField(5);
        storageField = new JTextField(10);
        batteryLifeField = new JTextField(5);

        commonPanel.add(new JLabel("Brand:"), cCommon);
        cCommon.gridx = 1; commonPanel.add(brandField, cCommon);
        cCommon.gridx = 0; cCommon.gridy++;
        commonPanel.add(new JLabel("Model:"), cCommon);
        cCommon.gridx = 1; commonPanel.add(modelField, cCommon);
        cCommon.gridx = 0; cCommon.gridy++;
        commonPanel.add(new JLabel("Processor:"), cCommon);
        cCommon.gridx = 1; commonPanel.add(processorField, cCommon);
        cCommon.gridx = 0; cCommon.gridy++;
        commonPanel.add(new JLabel("RAM (GB):"), cCommon);
        cCommon.gridx = 1; commonPanel.add(ramField, cCommon);
        cCommon.gridx = 0; cCommon.gridy++;
        commonPanel.add(new JLabel("Storage (GB):"), cCommon);
        cCommon.gridx = 1; commonPanel.add(storageField, cCommon);
        cCommon.gridx = 0; cCommon.gridy++;
        commonPanel.add(new JLabel("Battery Life (hours):"), cCommon);
        cCommon.gridx = 1; commonPanel.add(batteryLifeField, cCommon);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(commonPanel, gbc);

        // Panel for laptop type selection and specific fields
        JPanel typePanel = new JPanel(new GridBagLayout());
        typePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Laptop Type",
                TitledBorder.LEFT, TitledBorder.TOP));
        GridBagConstraints cType = new GridBagConstraints();
        cType.insets = new Insets(4,4,4,4);
        cType.fill = GridBagConstraints.HORIZONTAL;
        cType.gridx = 0; cType.gridy = 0;

        gamingRadio = new JRadioButton("Gaming Laptop");
        businessRadio = new JRadioButton("Business Laptop");
        ButtonGroup group = new ButtonGroup();
        group.add(gamingRadio);
        group.add(businessRadio);

        gpuModelField = new JTextField(15);
        businessSoftwareField = new JTextField(15);
        gpuModelField.setEnabled(false);
        businessSoftwareField.setEnabled(false);

        typePanel.add(gamingRadio, cType);
        cType.gridx = 1; typePanel.add(new JLabel("GPU Model:"), cType);
        cType.gridx = 2; typePanel.add(gpuModelField, cType);

        cType.gridx = 0; cType.gridy++;
        typePanel.add(businessRadio, cType);
        cType.gridx = 1; typePanel.add(new JLabel("Business Software:"), cType);
        cType.gridx = 2; typePanel.add(businessSoftwareField, cType);

        gbc.gridy = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(typePanel, gbc);

        // Output area panel
        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Output"));

        gbc.gridy = 2; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.BOTH; gbc.weightx=1; gbc.weighty=1;
        add(scrollPane, gbc);

        // Buttons panel
        submitButton = new JButton("Submit");
        logoutButton = new JButton("Logout");
        settingsButton = new JButton("Settings");
        manageLaptopsButton = new JButton("Manage Laptops");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        buttonPanel.add(manageLaptopsButton);
        buttonPanel.add(settingsButton);
        buttonPanel.add(logoutButton);

        gbc.gridy = 3; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx=0; gbc.weighty=0;
        add(buttonPanel, gbc);

        // Radio button actions
        gamingRadio.addActionListener(e -> {
            gpuModelField.setEnabled(true);
            businessSoftwareField.setEnabled(false);
            businessSoftwareField.setText("");
        });

        businessRadio.addActionListener(e -> {
            gpuModelField.setEnabled(false);
            gpuModelField.setText("");
            businessSoftwareField.setEnabled(true);
        });

        // Submit action
        submitButton.addActionListener(e -> {
            try {
                String brand = brandField.getText().trim();
                String model = modelField.getText().trim();
                String processor = processorField.getText().trim();
                int ram = Integer.parseInt(ramField.getText().trim());
                int storage = Integer.parseInt(storageField.getText().trim());
                int batteryLife = Integer.parseInt(batteryLifeField.getText().trim());

                Vector<Object> row = new Vector<>();
                row.add(brand);
                row.add(model);
                row.add(processor);
                row.add(ram);
                row.add(storage);
                row.add(batteryLife);

                if (gamingRadio.isSelected()) {
                    if (gpuModelField.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please enter GPU Model for Gaming Laptop.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    row.add("Gaming");
                    row.add(gpuModelField.getText().trim());
                    row.add("");
                } else if (businessRadio.isSelected()) {
                    if (businessSoftwareField.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please enter Business Software for Business Laptop.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    row.add("Business");
                    row.add("");
                    row.add(businessSoftwareField.getText().trim());
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a laptop type.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Add laptop data to the vector
                laptopData.add(row);

                outputArea.append("Added Laptop: " + brand + " " + model + "\n");

                clearInputs();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric values for RAM, Storage, and Battery Life.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Logout action
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose(); // Close dashboard
                SwingUtilities.invokeLater(() -> new LoginPage()); // Show login page again
            }
        });

        // Settings action
        settingsButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new SettingsPage());
        });

        // Manage laptops action - pass the current data
        manageLaptopsButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new LaptopManagementPage(laptopData));
        });

        // Frame settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void clearInputs() {
        brandField.setText("");
        modelField.setText("");
        processorField.setText("");
        ramField.setText("");
        storageField.setText("");
        batteryLifeField.setText("");
        gpuModelField.setText("");
        businessSoftwareField.setText("");
        gpuModelField.setEnabled(false);
        businessSoftwareField.setEnabled(false);
        gamingRadio.setSelected(false);
        businessRadio.setSelected(false);
    }

    // Main method to start dashboard
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LaptopDashboard::new);
    }
}
