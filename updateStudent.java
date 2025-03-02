/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class updateStudent extends JFrame {
    private JComboBox<String> fieldComboBox;
    private JTextField idField, valueField;
    private JButton updateButton;

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:mysql://localhost:3306/student_db?useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "root";
        return DriverManager.getConnection(url, username, password);
    }

    public updateStudent() {
        setTitle("Update Student");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.pink);
        headerPanel.setPreferredSize(new Dimension(800, 50)); // Match frame width
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center components

        JLabel headerLabel = new JLabel("Student Management System");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(headerLabel);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        JLabel updateid = new JLabel("Enter The Student RollNo To Update:");
        ComponentStyler.setLabelStyle(updateid);
        panel.add(updateid);
        idField = new JTextField();
        panel.add(idField);
        ComponentStyler.setTextFieldStyle(idField);
        JLabel updateLabel = new JLabel("Field to Update:");
        updateLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(updateLabel);
        String[] s = new String[]{"Name", "Branch", "Year"};
        fieldComboBox = new JComboBox<>(s);

        // Set custom renderer to change font size
        fieldComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setFont(new Font("Arial", Font.PLAIN, 20)); // Change the font size here
                return label;
            }
        });
        panel.add(fieldComboBox);
        JLabel newLabel = new JLabel("New Value:");
        newLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(newLabel);
        valueField = new JTextField();
        panel.add(valueField);
        ComponentStyler.setTextFieldStyle(valueField);
        JButton backButton = new JButton("Back");
        panel.add(backButton);
        updateButton = new JButton("Update");
        panel.add(updateButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StudentManagementSystemGUI().setVisible(true);
                dispose(); // Close the registration window
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStudentRecord();
            }
        });

        add(headerPanel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
    }

    private void updateStudentRecord() {
        int id = Integer.parseInt(idField.getText());
        String selectedField = fieldComboBox.getSelectedItem().toString().toLowerCase();
        String newValue = valueField.getText();

        try (Connection conn = getConnection()) {
            String query = "UPDATE students SET " + selectedField + " = ? WHERE rollno = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, newValue);
            pstmt.setInt(2, id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Student record updated successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Student ID not found.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new updateStudent().setVisible(true);
            }
        });
    }
}
