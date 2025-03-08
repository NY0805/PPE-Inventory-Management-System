/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class DeleteEntity {

    public static void deleteEntity(String filename, String[] headers, String selectedId,
            JComboBox<String> combobox, JTable table, JTextField[] textFields, 
            JCheckBox[] checkBox, JRadioButton[] radioButton) throws IOException {
        
        FileHandling fileHandler = new FileHandling();
        ArrayList<String[]> records = fileHandler.ReadDataFromFile(filename);
        ArrayList<String[]> updatedRecords = new ArrayList<>();
        
        boolean found = false;
        
        for (String[] record: records) {
            if (!record[0].equals(selectedId)) {
                updatedRecords.add(record);           
            }else{
                found = true;
            }
        }
        
        if (found) {
            for (JTextField tf : textFields) {
                tf.setText("");
            }
            
            if (checkBox != null) {
                for (JCheckBox cb : checkBox) {
                    cb.setSelected(false);
                }
            }
            
            if (radioButton != null) {
                for (JRadioButton button : radioButton) {
                    button.setSelected(false);
                }
            }
            
            BufferedWriter writeFile = new BufferedWriter(new FileWriter(filename, false));
            
            for (String[] record : updatedRecords) {
                for (int i = 0; i < headers.length; i++) {
                    writeFile.write(headers[i] + ": " + record[i] + "\n");
                }
                writeFile.write("--------------------------------------------------\n");
            }
            writeFile.close();
            
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                if (model.getValueAt(i, 0).toString().equals(selectedId)) {
                    model.removeRow(i);
                    break;
                }
            }
            
            combobox.removeAllItems();
            combobox.addItem("Please select");
            for (String[] record : updatedRecords) {
                combobox.addItem(record[0]); // Add supplier ID
            }       
        } else {
            JOptionPane.showMessageDialog(null, "Failed to delete record!", "Delete Records", JOptionPane.ERROR_MESSAGE);
        } 
    }
    
}