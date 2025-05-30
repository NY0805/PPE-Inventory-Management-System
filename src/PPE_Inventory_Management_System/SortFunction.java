/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class SortFunction {
    // used to store original data before sort
    private static ArrayList<String[]> defaultData = new ArrayList<>();
    
    public static void sortDefault(JTable table) {
        
        if (!defaultData.isEmpty()) {
            return;
        }
        // save original data before sort into defaultData
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String[] row = new String[model.getColumnCount()];
            for (int j = 0; j < model.getColumnCount(); j++) {
                row[j] = model.getValueAt(i, j).toString();
            }
            defaultData.add(row);
        }
    }
    
    public static void restoreDefaultData(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        for (String[] row : defaultData) {
            model.addRow(row);
        }
    }
    
    public static void sort(JComboBox<String> comboboxSort, JTable table, int columnIndex) {
        String order = comboboxSort.getSelectedItem().toString();
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        ArrayList<String[]> tableData = new ArrayList<>();

        sortDefault(table);
        
        if (order.equals("Sorted by")) {
            restoreDefaultData(table);
            return;
        }
        
        for (int i = 0; i < model.getRowCount(); i++) {
            String[] row = new String[model.getColumnCount()];
            for (int j = 0; j < model.getColumnCount(); j++) {
                row[j] = model.getValueAt(i, j).toString();
            }
            tableData.add(row);
        }
        
        tableData.sort((a, b) -> {
            String data1 = (a[columnIndex] != null) ? a[columnIndex] : "";
            String data2 = (b[columnIndex] != null) ? b[columnIndex] : "";
            
            if (order.equals("Ascending")) {
                return data1.compareTo(data2);
            } else if (order.equals("Descending")) {
                return data2.compareTo(data1);
            }else {
                return 0;
            }
        });
        

        model.setRowCount(0);
        for (String[] row : tableData) {
            model.addRow(row);
        }
    }
}
