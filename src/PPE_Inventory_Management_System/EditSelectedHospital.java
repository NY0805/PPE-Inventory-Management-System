/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import javax.swing.JCheckBox;
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
    public static void EditHospital (JTable table, JComboBox<String> combobox, JTextField name, JTextField contact, JTextField email, JTextArea address, JCheckBox[] checkBoxItem) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        combobox.removeAllItems();
        combobox.addItem("Please select");
        
        for (int i = 0; i < model.getRowCount(); i++) {
            String hospitalID = model.getValueAt(i, 0).toString();            
            combobox.addItem(hospitalID);
        }
        combobox.addActionListener(new ActionListener() {            
            @Override
            public void actionPerformed (ActionEvent e) {
                String selected_id = (String) combobox.getSelectedItem();
                
                if (selected_id != null && !selected_id.equals("Please select")) {
                    for (int i = 0; i < model.getRowCount(); i++) {
                        if (model.getValueAt(i, 0).toString().equals(selected_id)) {
                            name.setText(model.getValueAt(i, 1).toString());
                            contact.setText(model.getValueAt(i, 2).toString());
                            email.setText(model.getValueAt(i, 3).toString());
                            address.setText(model.getValueAt(i, 4).toString());
                            
                            String items = model.getValueAt(i, 5).toString();
                            
                            String[] itemList = items.split(",\\s*");
                            List<String> itemNames = Arrays.asList(itemList);
                                                       
                            for (JCheckBox checkbox : checkBoxItem) {
                                checkbox.setSelected(itemNames.contains(checkbox.getText()));
                            }
                            return;
                        }
                    }
                } else{
                    name.setText("");
                    contact.setText("");
                    email.setText("");
                    address.setText("");
                    for (JCheckBox checkbox : checkBoxItem) {
                        checkbox.setSelected(false);
                    }
                }
            }
        });
    }
}
