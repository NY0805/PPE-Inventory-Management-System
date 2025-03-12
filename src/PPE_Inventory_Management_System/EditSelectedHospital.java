/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class EditSelectedHospital {
    public static void EditHospital (JTable hosTable, JComboBox<String> combobox, 
            JTextField name, JTextField contact, JTextField email, JTextArea address) {
        DefaultTableModel hosModel = (DefaultTableModel) hosTable.getModel();
        combobox.removeAllItems();
        combobox.addItem("Please select");
        
        for (int i = 0; i < hosModel.getRowCount(); i++) {
            String hospitalID = hosModel.getValueAt(i, 0).toString();            
            combobox.addItem(hospitalID);
        }
        combobox.addActionListener(new ActionListener() {            
            @Override
            public void actionPerformed (ActionEvent e) {
                String selected_id = (String) combobox.getSelectedItem();
                
                if (selected_id != null && !selected_id.equals("Please select")) {
                    for (int i = 0; i < hosModel.getRowCount(); i++) {
                        if (hosModel.getValueAt(i, 0).toString().equals(selected_id)) {
                            name.setText(hosModel.getValueAt(i, 1).toString());
                            contact.setText(hosModel.getValueAt(i, 2).toString());
                            email.setText(hosModel.getValueAt(i, 3).toString());
                            address.setText(hosModel.getValueAt(i, 4).toString());
                            
                        }
                    }
                } else{
                    name.setText("");
                    contact.setText("");
                    email.setText("");
                    address.setText("");
                    
                }
            }
        });
    }
}
