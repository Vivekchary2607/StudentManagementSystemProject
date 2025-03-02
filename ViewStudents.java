/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.border.EmptyBorder;
public class ViewStudents extends JFrame {

    public ViewStudents() {
        setTitle("View Students");
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
        panel.setLayout(new BorderLayout());
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        JLabel rollLabel = new JLabel("Roll Number:");
        ComponentStyler.setLabelStyle(rollLabel);
        JTextField rollField = new JTextField();
        ComponentStyler.setTextFieldStyle(rollField);
        panel.add(rollLabel);
        panel.add(rollField);
        JButton viewStudent = new JButton("View Student Details:");
        JButton viewAllStudents = new JButton("View All Students");
        panel.add(viewStudent);
        panel.add(viewAllStudents);

        JButton backButton = new JButton("Back");
        panel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StudentManagementSystemGUI().setVisible(true);
                dispose(); // Close the registration window
            }
        });
        panel.add(new JLabel());
       JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Roll No", "Name", "Branch", "Year"});
        table.setModel(model);
        
        // Style the table
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setSelectionBackground(Color.LIGHT_GRAY);
        table.setSelectionForeground(Color.BLACK);
        
        // Style the table header
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 16));
        tableHeader.setBackground(Color.pink);
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setBorder(new EmptyBorder(0, 0, 0, 0));
        tableHeader.setReorderingAllowed(false);

        // Center align table header text and make it bold
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tableHeader.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        headerRenderer.setFont(new Font("Arial", Font.BOLD, 16));

        // Apply the custom header renderer to each column
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        
         JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        setVisible(true);

        viewStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String roll = rollField.getText();
                viewStudentDetails(roll, model);
            }
        });

        viewAllStudents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchStudentRecords(model);
            }
        });
    }

    public void viewStudentDetails(String roll, DefaultTableModel model) {
        model.setRowCount(0); // Clear previous data
        try (Connection conn = getConnection()) {
            String query = "SELECT rollno, name, branch, year FROM students WHERE rollno = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, roll);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int rollNo = rs.getInt("rollno");
                String name = rs.getString("name");
                String branch = rs.getString("branch");
                String year = rs.getString("year");
                model.addRow(new Object[]{rollNo, name, branch, year});
            } else {
                JOptionPane.showMessageDialog(this, "Roll number not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching student details.");
        }
    }

    public void fetchStudentRecords(DefaultTableModel model) {
        model.setRowCount(0); // Clear previous data
        try (Connection conn = getConnection()) {
            String query = "SELECT rollno, name, branch, year FROM students";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int rollNo = rs.getInt("rollno");
                String name = rs.getString("name");
                String branch = rs.getString("branch");
                String year = rs.getString("year");
                model.addRow(new Object[]{rollNo, name, branch, year});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching student records.");
        }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewStudents().setVisible(true));
    }
}
