/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class AddInventory extends AddEntity {

    private String supplierCode;
    private String price;
    private String quantity;

    private JTextField tfPPECode;
    private JTextField tfPPEName;
    private JTextField tfSupplierCode;
    private JTextField tfPPEQuantity;
    private JSpinner spinnerPrice;
    private ValidateEntity validator = new ValidateEntity();

    public AddInventory(String id, String name, JTextField tfPPECode, JTextField tfPPEName, 
            JTextField tfPPESupplierCode, JTextField tfPPEQuantity, JSpinner spinnerPrice) {

        super(id, name);
        this.supplierCode = tfPPESupplierCode.getText();
        Object value = spinnerPrice.getValue();
        double number = ((Number) value).doubleValue();
        this.price = new DecimalFormat("0.00").format(number);
        this.quantity = tfPPEQuantity.getText();
        this.tfPPECode = tfPPECode;
        this.tfPPEName = tfPPEName;
        this.tfPPEQuantity = tfPPEQuantity;
        this.spinnerPrice = spinnerPrice;
    }

    @Override
    public boolean validate() {
        return validator.validateID(id, "ppe.txt")
                && validator.validateName(tfPPEName.getText())
                && validator.validateName(supplierCode)
                && validator.validatePrice(price)
                && validator.validateQuantity(quantity);
    }

    @Override
    public void saveToFile(boolean isEdit, JTable table) throws IOException {
        if (validate()) {
            FileHandling ppeFile = new FileHandling();
            String[] headers = {"Item Code", "Name", "Supplier Code", "Quantity", "Price(RM)"};
            String[] data = {id, name, supplierCode, quantity, price};

            ArrayList<String[]> ppeData = ppeFile.ReadDataFromFile("ppe.txt");
            System.out.println("read data");
            if (isEdit) {
                for (int i = 0; i < ppeData.size(); i++) {
                    if (ppeData.get(i)[0].equals(id)) {
                        ppeData.set(i, data);
                        break;
                    }
                }
            } else {
                ppeData.add(data);
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter("ppe.txt"));
            writer.close();
            for (String[] user : ppeData) {
                ppeFile.WriteDataToFile("ppe.txt", headers, user);
            }
            System.out.println("write data");

            JOptionPane.showMessageDialog(null, isEdit ? "PPE item updated successfully!" : "PPE item added successfully!");
            returnToDefault();
            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);
            model.setColumnIdentifiers(headers);
            model.setRowCount(0);
            for (String[] rowData : ppeData) {
                if (rowData.length == 5) {
                    System.out.println(Arrays.toString(rowData));
                    model.addRow(rowData);
                } else {
                    System.err.println("skipping record: " + Arrays.toString(rowData));
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Validate error");
        }
    }

    @Override
    public void returnToDefault() {
        tfPPECode.setText("");
        tfPPEName.setText("");
//        tfPPEQuantity.setText("");
        spinnerPrice.setModel(new SpinnerNumberModel(1, 1, 100, 1));
    }

}
