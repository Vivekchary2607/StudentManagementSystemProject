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
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginWindow extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginWindow() {
        setTitle("Login");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));
        
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
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        ComponentStyler.setLabelStyle(usernameLabel);
        ComponentStyler.setTextFieldStyle(usernameField);
        
        JLabel passwordLabel = new JLabel("Password:");
        ComponentStyler.setLabelStyle(passwordLabel);
        passwordField = new JPasswordField();
        ComponentStyler.setTextFieldStyle(passwordField);
        
        JButton loginButton = new JButton("Login");
        
        JLabel msg=new JLabel("Not Registered then click on register button");
        ComponentStyler.setLabelStyle(msg);
        JButton registerButton = new JButton("Register");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticate();
            }
        });
         registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterWindow().setVisible(true);
                dispose();
            }
        });
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(loginButton);
        panel.add(msg);
        panel.add(registerButton);
        
        
         setLayout(new BorderLayout());
         add(headerPanel, BorderLayout.NORTH);
         add(panel, BorderLayout.CENTER);
    }

    private void authenticate() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            // Connect to MySQL database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_db?useSSL=false&serverTimezone=UTC", "root", "root");

            // Prepare SQL query
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // Execute query
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Successful authentication
                JOptionPane.showMessageDialog(this, "Login successful!");
                dispose(); // Close login window
                new StudentManagementSystemGUI().setVisible(true); // Open the main system window
            } else {
                // Failed authentication
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Close connections
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginWindow().setVisible(true);
        });
    }
}
