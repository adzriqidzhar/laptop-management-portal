import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class SettingsPage extends JFrame {

    private JRadioButton lightThemeRadio, darkThemeRadio;
    private JCheckBox notificationsCheckBox;
    private JComboBox<String> languageComboBox;
    private JButton saveButton, cancelButton;

    public SettingsPage() {
        super("Settings - Laptop Management Portal");

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Theme Panel
        JPanel themePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        themePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Theme",
                TitledBorder.LEFT, TitledBorder.TOP));
        lightThemeRadio = new JRadioButton("Light");
        darkThemeRadio = new JRadioButton("Dark");
        ButtonGroup themeGroup = new ButtonGroup();
        themeGroup.add(lightThemeRadio);
        themeGroup.add(darkThemeRadio);
        lightThemeRadio.setSelected(true);  // Default
        themePanel.add(lightThemeRadio);
        themePanel.add(darkThemeRadio);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(themePanel, gbc);

        // Notifications Panel
        JPanel notificationsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        notificationsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Notifications",
                TitledBorder.LEFT, TitledBorder.TOP));
        notificationsCheckBox = new JCheckBox("Enable Notifications", true);
        notificationsPanel.add(notificationsCheckBox);

        gbc.gridy = 1;
        add(notificationsPanel, gbc);

        // Language Panel
        JPanel languagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        languagePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Language",
                TitledBorder.LEFT, TitledBorder.TOP));
        String[] languages = {"English", "Bahasa Malaysia", "Chinese", "Tamil"};
        languageComboBox = new JComboBox<>(languages);
        languagePanel.add(languageComboBox);

        gbc.gridy = 2;
        add(languagePanel, gbc);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);

        gbc.gridy = 3;
        add(buttonsPanel, gbc);

        // Action listeners
        saveButton.addActionListener(e -> {
            String theme = lightThemeRadio.isSelected() ? "Light" : "Dark";
            boolean notifications = notificationsCheckBox.isSelected();
            String language = (String) languageComboBox.getSelectedItem();

            // For now, just display the settings
            JOptionPane.showMessageDialog(this, 
                "Settings Saved:\nTheme: " + theme + "\nNotifications: " + (notifications ? "Enabled" : "Disabled") + "\nLanguage: " + language,
                "Settings", JOptionPane.INFORMATION_MESSAGE);
        });

        cancelButton.addActionListener(e -> {
            // Close the settings window without saving
            dispose();
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // For testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SettingsPage());
    }
}
