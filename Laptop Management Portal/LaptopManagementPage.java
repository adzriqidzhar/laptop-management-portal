import java.awt.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LaptopManagementPage extends JFrame {

    private JTable laptopTable;
    private DefaultTableModel tableModel;

    // Form fields
    private JTextField brandField, modelField, processorField, ramField, storageField, batteryLifeField;
    private JTextField gpuModelField, businessSoftwareField;
    private JRadioButton gamingRadio, businessRadio;

    // Buttons
    private JButton addButton, updateButton, deleteButton, clearButton;

    // Data source for the table
    private Vector<Vector<Object>> laptopData;

    public LaptopManagementPage(Vector<Vector<Object>> laptopData) {
        super("Laptop Management Portal - Data Entry");

        this.laptopData = laptopData;

        setLayout(new BorderLayout(10, 10));

        // Table setup
        String[] columns = {"Brand", "Model", "Processor", "RAM (GB)", "Storage (GB)", "Battery Life", "Type", "GPU Model", "Business Software"};
        tableModel = new DefaultTableModel(columns, 0) {
            // Make cells non-editable directly in table
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        laptopTable = new JTable(tableModel);
        laptopTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Load initial data into the table
        for (Vector<Object> row : laptopData) {
            tableModel.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(laptopTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Laptops List"));
        add(scrollPane, BorderLayout.CENTER);

        // Form panel setup
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Add / Edit Laptop"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Brand:"), gbc);
        gbc.gridx = 1;
        brandField = new JTextField(15);
        formPanel.add(brandField, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Model:"), gbc);
        gbc.gridx = 3;
        modelField = new JTextField(15);
        formPanel.add(modelField, gbc);

        // Row 1
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Processor:"), gbc);
        gbc.gridx = 1;
        processorField = new JTextField(15);
        formPanel.add(processorField, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("RAM (GB):"), gbc);
        gbc.gridx = 3;
        ramField = new JTextField(5);
        formPanel.add(ramField, gbc);

        // Row 2
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Storage (GB):"), gbc);
        gbc.gridx = 1;
        storageField = new JTextField(10);
        formPanel.add(storageField, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Battery Life (hours):"), gbc);
        gbc.gridx = 3;
        batteryLifeField = new JTextField(5);
        formPanel.add(batteryLifeField, gbc);

        // Row 3 - Laptop Type radios
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Laptop Type:"), gbc);
        gbc.gridx = 1;
        gamingRadio = new JRadioButton("Gaming");
        businessRadio = new JRadioButton("Business");
        ButtonGroup typeGroup = new ButtonGroup();
        typeGroup.add(gamingRadio);
        typeGroup.add(businessRadio);
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        radioPanel.add(gamingRadio);
        radioPanel.add(businessRadio);
        formPanel.add(radioPanel, gbc);

        // Row 4
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("GPU Model:"), gbc);
        gbc.gridx = 1;
        gpuModelField = new JTextField(15);
        formPanel.add(gpuModelField, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Business Software:"), gbc);
        gbc.gridx = 3;
        businessSoftwareField = new JTextField(15);
        formPanel.add(businessSoftwareField, gbc);

        // Disable specific fields initially
        gpuModelField.setEnabled(false);
        businessSoftwareField.setEnabled(false);

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

        add(formPanel, BorderLayout.NORTH);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear");

        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Table selection listener loads data into form fields
        laptopTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = laptopTable.getSelectedRow();
                if (selectedRow >= 0) {
                    loadLaptopToForm(selectedRow);
                    addButton.setEnabled(false);
                    updateButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                }
            }
        });

        // Button listeners
        addButton.addActionListener(e -> addLaptop());
        updateButton.addActionListener(e -> updateLaptop());
        deleteButton.addActionListener(e -> deleteLaptop());
        clearButton.addActionListener(e -> clearForm());

        // Frame settings
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadLaptopToForm(int row) {
        brandField.setText(tableModel.getValueAt(row, 0).toString());
        modelField.setText(tableModel.getValueAt(row, 1).toString());
        processorField.setText(tableModel.getValueAt(row, 2).toString());
        ramField.setText(tableModel.getValueAt(row, 3).toString());
        storageField.setText(tableModel.getValueAt(row, 4).toString());
        batteryLifeField.setText(tableModel.getValueAt(row, 5).toString());

        String type = tableModel.getValueAt(row, 6).toString();
        if (type.equals("Gaming")) {
            gamingRadio.setSelected(true);
            gpuModelField.setEnabled(true);
            businessSoftwareField.setEnabled(false);
            businessSoftwareField.setText("");
            gpuModelField.setText(tableModel.getValueAt(row, 7).toString());
        } else {
            businessRadio.setSelected(true);
            businessSoftwareField.setEnabled(true);
            gpuModelField.setEnabled(false);
            gpuModelField.setText("");
            businessSoftwareField.setText(tableModel.getValueAt(row, 8).toString());
        }
    }

    private void addLaptop() {
        if (!validateInputs()) return;

        Vector<Object> row = new Vector<>();
        row.add(brandField.getText().trim());
        row.add(modelField.getText().trim());
        row.add(processorField.getText().trim());
        row.add(Integer.parseInt(ramField.getText().trim()));
        row.add(Integer.parseInt(storageField.getText().trim()));
        row.add(Integer.parseInt(batteryLifeField.getText().trim()));

        if (gamingRadio.isSelected()) {
            row.add("Gaming");
            row.add(gpuModelField.getText().trim());
            row.add("");
        } else if (businessRadio.isSelected()) {
            row.add("Business");
            row.add("");
            row.add(businessSoftwareField.getText().trim());
        } else {
            JOptionPane.showMessageDialog(this, "Please select a laptop type.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        tableModel.addRow(row);
        laptopData.add(row);  // Keep the underlying data consistent
        clearForm();
    }

    private void updateLaptop() {
        int selectedRow = laptopTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select a laptop to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!validateInputs()) return;

        tableModel.setValueAt(brandField.getText().trim(), selectedRow, 0);
        tableModel.setValueAt(modelField.getText().trim(), selectedRow, 1);
        tableModel.setValueAt(processorField.getText().trim(), selectedRow, 2);
        tableModel.setValueAt(Integer.parseInt(ramField.getText().trim()), selectedRow, 3);
        tableModel.setValueAt(Integer.parseInt(storageField.getText().trim()), selectedRow, 4);
        tableModel.setValueAt(Integer.parseInt(batteryLifeField.getText().trim()), selectedRow, 5);

        if (gamingRadio.isSelected()) {
            tableModel.setValueAt("Gaming", selectedRow, 6);
            tableModel.setValueAt(gpuModelField.getText().trim(), selectedRow, 7);
            tableModel.setValueAt("", selectedRow, 8);
        } else {
            tableModel.setValueAt("Business", selectedRow, 6);
            tableModel.setValueAt("", selectedRow, 7);
            tableModel.setValueAt(businessSoftwareField.getText().trim(), selectedRow, 8);
        }

        // Update the underlying data as well
        Vector<Object> updatedRow = new Vector<>();
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            updatedRow.add(tableModel.getValueAt(selectedRow, i));
        }
        laptopData.set(selectedRow, updatedRow);

        clearForm();
        laptopTable.clearSelection();
        addButton.setEnabled(true);
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }

    private void deleteLaptop() {
        int selectedRow = laptopTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select a laptop to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure to delete the selected laptop?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
            laptopData.remove(selectedRow);  // Keep data consistent
            clearForm();
            addButton.setEnabled(true);
            updateButton.setEnabled(false);
            deleteButton.setEnabled(false);
        }
    }

    private void clearForm() {
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
        laptopTable.clearSelection();
        addButton.setEnabled(true);
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }

    private boolean validateInputs() {
        if (brandField.getText().trim().isEmpty() || modelField.getText().trim().isEmpty() ||
            processorField.getText().trim().isEmpty() || ramField.getText().trim().isEmpty() ||
            storageField.getText().trim().isEmpty() || batteryLifeField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all common laptop fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            int ram = Integer.parseInt(ramField.getText().trim());
            int storage = Integer.parseInt(storageField.getText().trim());
            int battery = Integer.parseInt(batteryLifeField.getText().trim());

            if (ram <= 0 || storage <= 0 || battery <= 0) {
                JOptionPane.showMessageDialog(this, "RAM, Storage and Battery Life must be positive numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "RAM, Storage and Battery Life must be numeric.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!gamingRadio.isSelected() && !businessRadio.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select a laptop type.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (gamingRadio.isSelected() && gpuModelField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter GPU Model for Gaming Laptop.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (businessRadio.isSelected() && businessSoftwareField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Business Software for Business Laptop.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LaptopManagementPage(new Vector<>()));
    }
}
