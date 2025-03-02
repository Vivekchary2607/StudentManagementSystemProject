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

public class RegisterWindow extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public RegisterWindow() {
        setTitle("Register");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Allow the window to close without exiting the app
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
        passwordField = new JPasswordField();
        ComponentStyler.setLabelStyle(passwordLabel);
        ComponentStyler.setTextFieldStyle(passwordField);
        JButton backButton = new JButton("Back");
        JButton registerButton = new JButton("Register");

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginWindow().setVisible(true);
                dispose(); // Close the registration window
            }
        });
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(backButton);
        panel.add(registerButton);
        
         setLayout(new BorderLayout());
         add(headerPanel, BorderLayout.NORTH);
         add(panel, BorderLayout.CENTER);
    }

    private void registerUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            // Connect to MySQL database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_db?useSSL=false&serverTimezone=UTC", "root", "root");

            // Prepare SQL query
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // Execute query
            preparedStatement.executeUpdate();

            // Close connections
            preparedStatement.close();
            connection.close();

            JOptionPane.showMessageDialog(this, "Registration successful!");
            new LoginWindow().setVisible(true);
            dispose(); // Close the registration window
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Registration failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RegisterWindow().setVisible(true);
        });
    }
}
