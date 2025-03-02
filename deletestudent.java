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

public class deletestudent extends JFrame {
    private JTextField idField;
    private JButton deleteButton;

    public deletestudent() {
        setTitle("Delete Student");
        setSize(800, 600);
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
        panel.setLayout(new GridLayout(3, 2, 10, 10)); // Adjusted layout for fewer components

        JLabel idLabel = new JLabel("Student ID:");
        ComponentStyler.setLabelStyle(idLabel);
        panel.add(idLabel);

        idField = new JTextField();
        ComponentStyler.setTextFieldStyle(idField);
        panel.add(idField);
        JButton backButton = new JButton("Back");
        panel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StudentManagementSystemGUI().setVisible(true);
                dispose(); // Close the registration window
            }
        });
        deleteButton = new JButton("Delete");
        panel.add(deleteButton);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudentRecord();
            }
        });

        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    private Connection getConnection() throws SQLException {
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

    private void deleteStudentRecord() {
        int id = Integer.parseInt(idField.getText());

        try (Connection conn = getConnection()) {
            // Check if the ID exists in the database
            String checkIdQuery = "SELECT COUNT(*) FROM students WHERE rollno = ?";
            PreparedStatement checkIdStmt = conn.prepareStatement(checkIdQuery);
            checkIdStmt.setInt(1, id);
            ResultSet rs = checkIdStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count == 0) {
                JOptionPane.showMessageDialog(this, "Invalid ID. Student not found.");
                return;
            }

            // Proceed with deletion if the ID exists
            String deleteQuery = "DELETE FROM students WHERE rollno = ?";
            PreparedStatement pstmt = conn.prepareStatement(deleteQuery);
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Student record deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Error deleting student record.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting student record.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new deletestudent().setVisible(true);
        });
    }
}
