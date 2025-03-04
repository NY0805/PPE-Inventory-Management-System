/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public abstract class EditEntity {

    protected JTable table;
    protected JComboBox<String> combobox;

    public EditEntity(JTable table, JComboBox<String> combobox) {
        this.table = table;
        this.combobox = combobox;
        populateComboBox();
    }

    private void populateComboBox() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        combobox.removeAllItems();
        combobox.addItem("Please select");

        for (int i = 0; i < model.getRowCount(); i++) {
            String id = model.getValueAt(i, 0).toString();
            combobox.addItem(id);
        }
    }

    public DefaultTableModel getModel() {
        return (DefaultTableModel) table.getModel();
    }
}
