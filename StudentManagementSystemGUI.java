/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 *
 * @author G Supriya
 */

public class StudentManagementSystemGUI extends JFrame{
    
     public StudentManagementSystemGUI() {
        setTitle("Student Management System");
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

        JButton addButton = new JButton("Add Student");
        JButton updateButton = new JButton("Update Student's single field");
        JButton updateButtonAll = new JButton("Update Student's All Details");
        JButton deleteButton = new JButton("Delete Student");
        JButton viewButton = new JButton("View Students");
        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddStudentWindow().setVisible(true);
                dispose();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new updateStudent().setVisible(true);
               dispose();
            }
        });
        updateButtonAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new StudentUpdateApp().setVisible(true);
               dispose();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new deletestudent().setVisible(true);
               dispose();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              new ViewStudents().setVisible(true);
              dispose();
            }
        });

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(updateButtonAll);
        panel.add(deleteButton);
        panel.add(viewButton);

        add(panel, BorderLayout.SOUTH);
        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
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
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentManagementSystemGUI().setVisible(true);
        });
    }
}
