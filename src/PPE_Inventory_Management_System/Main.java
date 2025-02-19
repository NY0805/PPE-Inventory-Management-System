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
public class Main extends JFrame{
    
    public Main() {
        
        Font TopNavFont = new Font("Arial", Font.BOLD, 14);
        UIManager.put("TabbedPane.font", TopNavFont);
        UIManager.put("TabbedPane.tabInsets", new Insets(5, 12, 5, 12)); // Padding inside tabs
        UIManager.put("TabbedPane.contentBorderInsets", new Insets(5, 5, 5, 5)); // margin of content below tabs
        UIManager.put("TabbedPane.tabAreaInsets", new Insets(5, 5, 5, 5)); // Adjust spacing around tab area         
        UIManager.put("TabbedPane.light", Color.BLUE);  // Light effect on borders
        
        
        // frame setup        
        setBounds(300, 80, 900, 650);
        getContentPane().setBackground(new Color(174, 204, 228));
        setResizable(false); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("PPE Inventory Management System");
        
        // Top Navigation Tab and Panel
        JTabbedPane TopNav = new JTabbedPane(); 
//        TopNav.setTabPlacement(JTabbedPane.TOP);
        
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
//        TopNav.addTab("Dashboard", new Dashboard());
//        TopNav.addTab("User Management", new UserManagement());
        TopNav.addTab("Supplier Management", new SupplierManagement());
        TopNav.addTab("Hospital Management", new HospitalManagement());
//        TopNav.addTab("Report", new Report());
//        TopNav.addTab("Transaction", new Transaction());
//        TopNav.addTab("Logout", new Logout());           
        add(TopNav);
        
        setVisible(true);
        
    }
    
    public static void main (String[] args) {
        new Main();
    }

}
