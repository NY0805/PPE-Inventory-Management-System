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
        SupplierTF.setMaximumSize(size);
        
        return SupplierTF;
    }
    
//    private void ArrangeInRow(String label, JTextField FormTF, Font LabelFont){
//        JPanel FormPanel = new JPanel();
//        FormPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 4));
//        JLabel FormLabel = new JLabel(label);
//        
//        FormLabel.setFont(LabelFont);
//        FormPanel.add(FormLabel);
//        FormPanel.add(FormTF);
//        add(FormPanel);
//    }
    
    public SupplierManagement() {
        Font labelFont = new Font("Arial", Font.PLAIN, 18);
//        UIManager.put("Label.font", labelFont);
        UIManager.put("Button.font", labelFont);
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 30)); // Padding around edges        
        
        Font Sup_TF_Font = new Font("Serif", Font.PLAIN, 18);
        Dimension Sup_TF_Size = new Dimension(600, 30);
        
//        setLayout(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.insets = new Insets(5, 5, 5, 5);
        
//        gbc.gridx = 0; gbc.gridy = 0;
//        add(ArrangeInRow("Supplier Code:", SupplierCode, labelFont), gbc);
//        gbc.gridy++;
        
        
        SupplierCode = TextFieldDesign(Sup_TF_Font, Sup_TF_Size);
        SupplierName = TextFieldDesign(Sup_TF_Font, Sup_TF_Size);
        SupplierContact = TextFieldDesign(Sup_TF_Font, Sup_TF_Size);
        SupplierEmail = TextFieldDesign(Sup_TF_Font, Sup_TF_Size);
        SupplierAddress = TextFieldDesign(Sup_TF_Font, Sup_TF_Size);
        PPESupplies = TextFieldDesign(Sup_TF_Font, Sup_TF_Size);
        
        
//        ArrangeInRow("Supplier Name:", SupplierName, labelFont);
//        ArrangeInRow("Contact Number:", SupplierContact, labelFont);
//        ArrangeInRow("Email:", SupplierEmail, labelFont);
//        ArrangeInRow("Address:", SupplierAddress, labelFont);
//        ArrangeInRow("PPE Supplies:", PPESupplies, labelFont);

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
  
}

