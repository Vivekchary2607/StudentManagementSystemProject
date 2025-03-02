/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementsystem;
import javax.swing.*;
import java.awt.*;

public class ComponentStyler {
    
    // Method to set default styles for JLabel
    public static void setLabelStyle(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setPreferredSize(new Dimension(50, 30)); // Set size
        label.setOpaque(true);
       // label.setBackground(Color.LIGHT_GRAY);
    }

    // Method to set default styles for JTextField
    public static void setTextFieldStyle(JTextField textField) {
        textField.setFont(new Font("Arial", Font.BOLD, 20));
        textField.setPreferredSize(new Dimension(50, 30)); // Set size
        textField.setBackground(Color.LIGHT_GRAY);
    }
}
