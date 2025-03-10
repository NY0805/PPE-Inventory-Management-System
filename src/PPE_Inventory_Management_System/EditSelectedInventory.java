/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class EditSelectedInventory extends EditEntity {
    
    public EditSelectedInventory(JTable table, JComboBox<String> combobox, JTextField itemName,
            JTextField supplierCode, JTextField quantity, JSpinner unitPrice) {

        super(table, combobox);

        DefaultTableModel model = getModel();

        combobox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected_id = (String) combobox.getSelectedItem();

                if (selected_id != null && !selected_id.equals("Please select")) {
                    for (int i = 0; i < model.getRowCount(); i++) {
                        if (model.getValueAt(i, 0).toString().equals(selected_id)) {
                            itemName.setText(model.getValueAt(i, 1).toString());
                            supplierCode.setText(model.getValueAt(i, 2).toString());                            
                            quantity.setText(model.getValueAt(i, 3).toString());
                            
                            unitPrice.setValue(Double.valueOf(model.getValueAt(i, 4).toString()));
                            JSpinner.NumberEditor format = new JSpinner.NumberEditor(unitPrice, "0.00");
                            unitPrice.setEditor(format);
                                                       
                        }
                    }
                } else {
                    itemName.setText("");
                    supplierCode.setText("");
                    quantity.setText("");
                    unitPrice.setValue(0.00);
                }
            }
        });
    }
}
