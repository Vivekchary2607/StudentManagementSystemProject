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
import java.sql.SQLException;
import java.sql.*;

public class AddStudentWindow extends JFrame {
    public void addStudent(String name, String roll,String year,String branch) {
        try (Connection connection = getConnection()) {
            String query = "INSERT INTO students (name, rollno,year,branch) VALUES (?, ?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, roll);
            statement.setString(3,year);
            statement.setString(4,branch);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
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

    public AddStudentWindow() {
        setTitle("Add Student");
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
    panel.setLayout(new GridLayout(5, 2, 10, 10));
    
    JLabel nameLabel = new JLabel("Name:");
    ComponentStyler.setLabelStyle(nameLabel);
    JTextField nameField = new JTextField();
    ComponentStyler.setTextFieldStyle(nameField);
        
    JLabel rollLabel = new JLabel("Roll Number:");
    ComponentStyler.setLabelStyle(rollLabel);
    JTextField rollField = new JTextField();
    ComponentStyler.setTextFieldStyle(rollField);
    
    JLabel yearLabel =new JLabel("Year:");
    ComponentStyler.setLabelStyle(yearLabel);
    JTextField yearField =new JTextField();
    ComponentStyler.setTextFieldStyle(yearField);
    
    JLabel branchLabel =new JLabel("Branch:");
    ComponentStyler.setLabelStyle(branchLabel);
    JTextField branchField =new JTextField();
    ComponentStyler.setTextFieldStyle(branchField);
    JButton backButton = new JButton("Back");
    JButton addButton = new JButton("Add Student");
    addButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Add student to the database
                String name = nameField.getText();
                String roll = rollField.getText();
                String year = yearField.getText();
                String branch= branchField.getText();
                addStudent(name, roll,year,branch); // Call addStudent method
                JOptionPane.showMessageDialog(null, "Student added successfully!");
        }
    });
    backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StudentManagementSystemGUI().setVisible(true);
                dispose(); // Close the registration window
            }
        });
    // Add components to panel
    headerPanel.add(headerLabel);
    panel.add(nameLabel);
    panel.add(nameField);
    panel.add(rollLabel);
    panel.add(rollField);
    panel.add(yearLabel);
    panel.add(yearField);
    panel.add(branchLabel);
    panel.add(branchField);
    panel.add(backButton);
    panel.add(addButton);
    
    // Adding header and main panel to the frame
    setLayout(new BorderLayout());
    add(headerPanel, BorderLayout.NORTH);
    add(panel, BorderLayout.CENTER);
    setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AddStudentWindow().setVisible(true);
        });
    }
}
