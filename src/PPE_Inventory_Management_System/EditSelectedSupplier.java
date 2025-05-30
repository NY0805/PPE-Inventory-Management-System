/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class EditSelectedSupplier {
    public static void EditSupplier (JTable supTable, JComboBox<String> combobox, 
            JTextField name, JTextField contact, JTextField email, 
            JTextArea address, JTextField ppeSupplies) {
     
        DefaultTableModel supModel = (DefaultTableModel) supTable.getModel();
        combobox.removeAllItems();
        combobox.addItem("Please select");
        
        for (int i = 0; i < supModel.getRowCount(); i++) {
            String supplierID = supModel.getValueAt(i, 0).toString();            
            combobox.addItem(supplierID);
        }
        combobox.addActionListener(new ActionListener() {            
            @Override
            public void actionPerformed (ActionEvent e) {
                String selected_id = (String) combobox.getSelectedItem();
                
                if (selected_id != null && !selected_id.equals("Please select")) {
                    for (int i = 0; i < supModel.getRowCount(); i++) {
                        if (supModel.getValueAt(i, 0).toString().equals(selected_id)) {
                            name.setText(supModel.getValueAt(i, 1).toString());
                            contact.setText(supModel.getValueAt(i, 2).toString());
                            email.setText(supModel.getValueAt(i, 3).toString());
                            address.setText(supModel.getValueAt(i, 4).toString());                            
                            ppeSupplies.setText(supModel.getValueAt(i, 5).toString());
                            
                                                       
                            
                        }
                    }
                } else{
                    name.setText("");
                    contact.setText("");
                    email.setText("");
                    address.setText("");
                    ppeSupplies.setText("");
                }
            }
        });
        
    }
}
