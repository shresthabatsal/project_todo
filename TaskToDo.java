import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;


@SuppressWarnings("all")
public class TaskToDo extends JFrame {

    private PlaceholderTextField titleField;
    private PlaceholderTextArea descriptionArea;
    private JSpinner dueDateSpinner;
    private JComboBox<String> repeatComboBox;

    private DatabaseConnection dbConnection;

    public TaskToDo() {
        setTitle("Add a Task");
        setSize(350, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        dbConnection = new DatabaseConnection();

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 30, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        JLabel titleLabel = new JLabel("Add a Task");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(titleLabel, gbc);

        // Title TextField
        titleField = new PlaceholderTextField("Title");
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(titleField, gbc);

        // Description
        descriptionArea = new PlaceholderTextArea("Description");
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        panel.add(new JScrollPane(descriptionArea), gbc);

        // Due Date
        SpinnerDateModel model = new SpinnerDateModel();
        dueDateSpinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dueDateSpinner, "dd/MM/yyyy");
        dueDateSpinner.setEditor(editor);
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        panel.add(dueDateSpinner, gbc);

        // Repeat
        String[] repeatOptions = {"Repeat", "Never", "Daily", "Weekly", "Monthly", "Yearly"};
        repeatComboBox = new JComboBox<>(repeatOptions);
        repeatComboBox.setRenderer(new PlaceholderComboBoxRenderer("Repeat"));
        repeatComboBox.setSelectedIndex(0); // Set the placeholder as selected
        repeatComboBox.setPreferredSize(new Dimension(200, repeatComboBox.getPreferredSize().height));
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        panel.add(repeatComboBox, gbc);

        // Prevent selecting "Repeat"
        repeatComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (repeatComboBox.getSelectedItem().equals("Repeat")) {
                        repeatComboBox.setSelectedIndex(0); // Deselect "Repeat" and keep it as the placeholder
                    }
                }
            }
        });

        // Add Task Button
        JButton addButton = new JButton("Add Task");
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        panel.add(addButton, gbc);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String description = descriptionArea.getText();
                Date dueDate = (Date) dueDateSpinner.getValue();
                String repeat = (String) repeatComboBox.getSelectedItem();

                if (title.isEmpty() || title.equals("Enter task title...") ||
                    description.isEmpty() || description.equals("Enter task description...") ||
                    dueDate == null || repeat == null || repeat.isEmpty() || repeat.equals("Repeat")) {
                    JOptionPane.showMessageDialog(TaskToDo.this,
                            "Please fill all fields correctly.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else if (dueDate.before(new Date())) {
                    JOptionPane.showMessageDialog(TaskToDo.this,
                            "Due date cannot be in the past.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    saveTask(title, description, dueDate, repeat);
                    JOptionPane.showMessageDialog(TaskToDo.this,
                            "Task added successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    // Optionally, clear fields after saving
                    titleField.setText("");
                    descriptionArea.setText("");
                    dueDateSpinner.setValue(new Date());
                    repeatComboBox.setSelectedIndex(0);
                }
            }
        });

        add(panel);
    }

    private void saveTask(String title, String description, Date dueDate, String repeat) {
        String sql = "INSERT INTO tasks (title, description, due_date, repeat_interval) VALUES (?, ?, ?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setString(3, sdf.format(dueDate));
            pstmt.setString(4, repeat);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save task.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TaskToDo app = new TaskToDo();
            app.setVisible(true);
        });
    }

    // Custom JTextField with placeholder
    class PlaceholderTextField extends JTextField {
        private String placeholder;

        public PlaceholderTextField(String placeholder) {
            this.placeholder = placeholder;
            setText(placeholder);
            setForeground(Color.GRAY);
            addFocusListener(new java.awt.event.FocusAdapter() {
                @Override
                public void focusGained(java.awt.event.FocusEvent evt) {
                    if (getText().equals(placeholder)) {
                        setText("");
                        setForeground(Color.BLACK);
                    }
                }
                @Override
                public void focusLost(java.awt.event.FocusEvent evt) {
                    if (getText().isEmpty()) {
                        setText(placeholder);
                        setForeground(Color.GRAY);
                    }
                }
            });
        }
    }

    // Custom JTextArea with placeholder
    class PlaceholderTextArea extends JTextArea {
        private String placeholder;

        public PlaceholderTextArea(String placeholder) {
            this.placeholder = placeholder;
            setText(placeholder);
            setForeground(Color.GRAY);
            setWrapStyleWord(true);
            setLineWrap(true);
            addFocusListener(new java.awt.event.FocusAdapter() {
                @Override
                public void focusGained(java.awt.event.FocusEvent evt) {
                    if (getText().equals(placeholder)) {
                        setText("");
                        setForeground(Color.BLACK);
                    }
                }
                @Override
                public void focusLost(java.awt.event.FocusEvent evt) {
                    if (getText().isEmpty()) {
                        setText(placeholder);
                        setForeground(Color.GRAY);
                    }
                }
            });
        }
    }

    // Custom ComboBoxRenderer with watermark in gray
    class PlaceholderComboBoxRenderer extends BasicComboBoxRenderer {
        private String watermark;

        public PlaceholderComboBoxRenderer(String watermark) {
            this.watermark = watermark;
        }

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value.toString().equals(watermark)) {
                label.setText(value.toString());
                label.setForeground(Color.GRAY);
            } else {
                label.setText(value.toString());
                label.setForeground(Color.BLACK);
            }
            return label;
        }
    }
}
