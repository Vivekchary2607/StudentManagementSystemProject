/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentUpdateApp extends JFrame {
    private JTextField idField;
    private JTextField nameField;
    private JTextField branchField;
    private JTextField yearField;

    public StudentUpdateApp() {
        setTitle("Student Update App");
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
        panel.setLayout(new GridLayout(5, 2,10,10));
        JLabel id=new JLabel("Student ID:");
        ComponentStyler.setLabelStyle(id);
        panel.add(id);
        idField = new JTextField();
        ComponentStyler.setTextFieldStyle(idField);
        panel.add(idField);
        
        JLabel name=new JLabel("Name:");
        ComponentStyler.setLabelStyle(name);
        panel.add(name);
        nameField = new JTextField();
        ComponentStyler.setTextFieldStyle(nameField);
        panel.add(nameField);
        
        JLabel branch=new JLabel("Branch:");
        ComponentStyler.setLabelStyle(branch);
        panel.add(branch);
        branchField = new JTextField();
        ComponentStyler.setTextFieldStyle(branchField);
        panel.add(branchField);
        
        JLabel year=new JLabel("Year:");
        ComponentStyler.setLabelStyle(year);
        panel.add(year);
        yearField = new JTextField();
        ComponentStyler.setTextFieldStyle(yearField);
        panel.add(yearField);
        JButton backButton = new JButton("Back");
        panel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StudentManagementSystemGUI().setVisible(true);
                dispose(); // Close the registration window
            }
        });
        JButton updateButton = new JButton("Update");
        panel.add(updateButton);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateStudentDetails();
            }
        });
        
        setVisible(true);
        setLayout(new BorderLayout());
         add(headerPanel, BorderLayout.NORTH);
         add(panel, BorderLayout.CENTER);
    }

    private void updateStudentDetails() {
        String id = idField.getText();
        String name = nameField.getText();
        String branch = branchField.getText();
        String year = yearField.getText();

        String url = "jdbc:mysql://localhost:3306/student_db?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "root";

        StringBuilder sqlBuilder = new StringBuilder("UPDATE students SET ");
        boolean first = true;

        if (!name.isEmpty()) {
            sqlBuilder.append("name = ?");
            first = false;
        }
        if (!branch.isEmpty()) {
            if (!first) sqlBuilder.append(", ");
            sqlBuilder.append("branch = ?");
            first = false;
        }
        if (!year.isEmpty()) {
            if (!first) sqlBuilder.append(", ");
            sqlBuilder.append("year = ?");
        }

        sqlBuilder.append(" WHERE rollno = ?");

        try (Connection conn = DriverManager.getConnection(url, user, password)) 
            {
            // Check if the ID exists in the database
            String checkIdQuery = "SELECT COUNT(*) FROM students WHERE rollno = ?";
            PreparedStatement checkIdStmt = conn.prepareStatement(checkIdQuery);
            checkIdStmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = checkIdStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count == 0) {
                JOptionPane.showMessageDialog(this, "Invalid ID. Student not found.");
                return;
            }

            PreparedStatement pstmt = conn.prepareStatement(sqlBuilder.toString());
            int paramIndex = 1;

            if (!name.isEmpty()) {
                pstmt.setString(paramIndex++, name);
            }
            if (!branch.isEmpty()) {
                pstmt.setString(paramIndex++, branch);
            }
            if (!year.isEmpty()) {
                pstmt.setString(paramIndex++, year);
            }
            pstmt.setInt(paramIndex, Integer.parseInt(id));
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Student details updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating student details.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentUpdateApp());
    }
}