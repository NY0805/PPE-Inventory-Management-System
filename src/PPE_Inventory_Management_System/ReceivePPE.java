/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class ReceivePPE {
    public static void ReceivePPE(JComboBox combobox, JTable table) {
        
        combobox.removeAllItems();
        combobox.addItem("Please select");
        
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        int rowcount = model.getRowCount();
        for (int i = 0; i < rowcount; i++) {
            String itemCode = model.getValueAt(i, 0).toString();
            combobox.addItem(itemCode);
        }
    }
    
    public static void ReceiveDate(JLabel lbDate) {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        lbDate.setText(currentDate);
        
//        dateChooser.setDate(new Date());
        
    }
    
    public static void SaveReceivedPPE(JComboBox itemID, JComboBox supplierID, int quantity, Date receivedDate) {
        
    }
}
