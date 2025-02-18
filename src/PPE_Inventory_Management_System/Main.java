/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

//password: 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
//pull > edit > commit > pull > push

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author user
 */
public class Main {
    public static void main (String[] args) {
//      frame setup
        JFrame frame = new JFrame("PPE inventory Management System"); // title
        frame.setVisible(true); // visibility
        frame.setBounds(350, 100, 800, 650); // (x, y, width, height)
        frame.getContentPane().setBackground(new Color(174, 204, 228)); // background color
        frame.setResizable(false); // disable resize
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // enable close window
        
        JTabbedPane TopNavigation = new JTabbedPane(); // Create top bar with multiple tabs
        
        // Create different panels for each tab
        JPanel Dashboard = new JPanel();
        Dashboard.add(new JLabel("Dashboard"));

        JPanel UserManagement = new JPanel();
        UserManagement.add(new JLabel("User Management")); // for admin only

        JPanel SupplierManagement = new JPanel();
        SupplierManagement.add(new JLabel("Supplier Management"));
        
        JPanel HospitalManagement = new JPanel();
        HospitalManagement.add(new JLabel("Hospital Management"));
        
        JPanel Report = new JPanel();
        Report.add(new JLabel("Report"));
        
        JPanel Transaction = new JPanel();
        Transaction.add(new JLabel("Transaction"));
        
        JPanel Logout = new JPanel();
        Logout.add(new JLabel("Logout"));

        // Add tabs with titles
        TopNavigation.addTab("Dashboard", Dashboard);
        TopNavigation.addTab("User Management", UserManagement);
        TopNavigation.addTab("Supplier Management", SupplierManagement);
        TopNavigation.addTab("Hospital Management", HospitalManagement);
        TopNavigation.addTab("Report", Report);
        TopNavigation.addTab("Transaction", Transaction);
        TopNavigation.addTab("Logout", Logout);   
        
        frame.add(TopNavigation); // add all tabs into frame
    }
}
