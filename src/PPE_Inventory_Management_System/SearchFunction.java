/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.table.*;

/**
 *
 * @author user
 */
public class SearchFunction {
    public static void search(JTextField searchTextFiled, JTable searchTable) {
        
        DefaultTableModel model = (DefaultTableModel) searchTable.getModel();
        TableRowSorter<DefaultTableModel> displayResult = new TableRowSorter<>(model);
        searchTable.setRowSorter(displayResult);
            
        String result = searchTextFiled.getText().trim().toLowerCase();
        if (result.isEmpty()) {
            displayResult.setRowFilter(null);
        } else {
            displayResult.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(result)));
        }
    }
}
