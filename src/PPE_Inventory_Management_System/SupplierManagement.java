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
public class SupplierManagement extends JPanel {
    private JTextField SupplierCode, SupplierName, SupplierContact, SupplierEmail, SupplierAddress, PPESupplies;
    private JButton AddSupplier, ViewSupplier;
    
    private JTextField TextFieldDesign(Font font, Dimension size) {
        JTextField SupplierTF = new JTextField();
        SupplierTF.setFont(font);
        SupplierTF.setPreferredSize(size);
        
        return SupplierTF;
    }
    
    public SupplierManagement() {
        Font LabelFont = new Font("Arial", Font.PLAIN, 18);
        UIManager.put("Label.font", LabelFont);
        UIManager.put("Button.font", LabelFont);
        
//        setLayout(new GridLayout(7, 2, 5, 5));
        
        Font Sup_TF_Font = new Font("Serif", Font.PLAIN, 18);
        Dimension Sup_TF_Size = new Dimension(800, 30);
        
        
        SupplierCode = TextFieldDesign(Sup_TF_Font, Sup_TF_Size);
        SupplierName = TextFieldDesign(Sup_TF_Font, Sup_TF_Size);
        SupplierContact = TextFieldDesign(Sup_TF_Font, Sup_TF_Size);
        SupplierEmail = TextFieldDesign(Sup_TF_Font, Sup_TF_Size);
        SupplierAddress = TextFieldDesign(Sup_TF_Font, Sup_TF_Size);
        PPESupplies = TextFieldDesign(Sup_TF_Font, Sup_TF_Size);
        
        add(new JLabel("Supplier Code:"));        
        add(SupplierCode);

        add(new JLabel("Supplier Name:"));
        add(SupplierName);

        add(new JLabel("Contact Number:"));
        add(SupplierContact);
        
        add(new JLabel("Email:"));
        add(SupplierEmail);
        
        add(new JLabel("Address:"));
        add(SupplierAddress);
        
        add(new JLabel("PPE Supplies:"));
        add(PPESupplies);

        AddSupplier = new JButton("Add Supplier");
        add(AddSupplier);
        
        ViewSupplier = new JButton("View Supplier Details");
        add(ViewSupplier);

    }
    
    public static void main (String[] args) {
        Main supplier_1 = new Main();
//        supplier_1.SupplierManagement = new SupplierManagement();
        
    }
    
}

