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
public class EditSelectedInventory {
    
    public static void EditSelectedInventory(JTable table, JComboBox<String> comboItemCode, JTextField itemName,
            JComboBox<String> supplierCode, JTextField quantity, JSpinner unitPrice) {


        DefaultTableModel model = (DefaultTableModel) table.getModel();
        comboItemCode.removeAllItems();
        comboItemCode.addItem("Please select");
        for (int i = 0; i < model.getRowCount(); i++) {
            String itemID = model.getValueAt(i, 0).toString();            
            comboItemCode.addItem(itemID);
        }

        comboItemCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected_id = (String) comboItemCode.getSelectedItem();

                if (selected_id != null && !selected_id.equals("Please select")) {
                    for (int i = 0; i < model.getRowCount(); i++) {
                        if (model.getValueAt(i, 0).toString().equals(selected_id)) {
                            itemName.setText(model.getValueAt(i, 1).toString());
//                            supplierCode.setText(model.getValueAt(i, 2).toString());                            
                            quantity.setText(model.getValueAt(i, 3).toString());
                            
                            unitPrice.setValue(Double.valueOf(model.getValueAt(i, 4).toString()));
                            JSpinner.NumberEditor format = new JSpinner.NumberEditor(unitPrice, "0.00");
                            unitPrice.setEditor(format);
                                                       
                        }
                    }
                } else {
                    itemName.setText("");
//                    supplierCode.setText("");
                    quantity.setText("");
                    unitPrice.setValue(0.00);
                }
            }
        });
    }
    
//    public static void saveEditInventory(boolean isEdit, JComboBox<String> comboItemCode, JTextField tfItemName,
//            JTextField tfsupplierCode, JTextField tfquantity, JSpinner spinnerUnitPrice, JTable table) throws IOException {
//        
//        String itemCode, itemName, supplierCode, quantity, unitPrice;
//        
//        itemCode = (String)comboItemCode.getSelectedItem();
//        itemName = tfItemName.getText();
//        supplierCode = tfsupplierCode.getText();
//        quantity = tfquantity.getText();
//        unitPrice = (String)spinnerUnitPrice.getValue();
//
//        String[] headers = {"Item Code", "Item Name", "Supplier Code", "Quantity(boxes)", "Price per box(RM)"};
//        String[] data = {itemCode, itemName, supplierCode, quantity, unitPrice};
// 
//        ValidateEntity validate = new ValidateEntity();
//        FileHandling itemFile = new FileHandling();
//        
//        if (itemCode.isEmpty() || itemName.isEmpty() || supplierCode.isEmpty() || quantity.isEmpty() || 
//            unitPrice.isEmpty()) {
//            JOptionPane.showMessageDialog(null, "Please fill out all fields!", "Warning", JOptionPane.WARNING_MESSAGE);            
//            return;
//        }
//        
//        if (!validate.validateName(itemName) || !validate.validatePrice(unitPrice) || !validate.validateQuantity(quantity)) {
//            JOptionPane.showMessageDialog(null, "Please enter valid information!", "Warning", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//
//         
//        ArrayList<String[]> itemData = itemFile.ReadDataFromFile("ppe.txt");
//        if (isEdit) {
//            for (int i = 0; i < itemData.size(); i++) {
//                if (itemData.get(i)[0].equals(itemCode)) {
//                    itemData.set(i, data);
//                    break;
//                }
//            }
//        }else{
//            itemData.add(data);
//        }
//
//        BufferedWriter writer = new BufferedWriter(new FileWriter("ppe.txt"));
//        writer.close();
//        for (String[]item: itemData) {
//            itemFile.WriteDataToFile("ppe.txt", headers, item);
//
//        }
//
//        JOptionPane.showMessageDialog(null, isEdit ? "PPE updated successfully!" : "PPE added successfully!");
//        
//        tfItemName.setText("");
//        tfsupplierCode.setText("");
//        tfquantity.setText("");
//        spinnerUnitPrice.setValue(0.00);
//
//        DefaultTableModel model = new DefaultTableModel();
//        table.setModel(model);
//        model.setColumnIdentifiers(headers);
//        model.setRowCount(0);
//        for (String[] rowData: itemData) {
//            if (rowData.length == 5) {
//                System.out.println(Arrays.toString(rowData));
//                model.addRow(rowData);
//            }else {
//                System.err.println("skipping record: " + Arrays.toString(rowData));
//            }            
//        } 
//    }
}
