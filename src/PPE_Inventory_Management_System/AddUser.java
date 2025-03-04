/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class AddUser extends AddEntity {

    private String password;
    private String contact;
    private String userType;

    private JTextField tfName;
    private JPasswordField tfPassword;
    private JTextField tfContact;
    private ButtonGroup buttonGroup;
    private ValidateEntity validator = new ValidateEntity();

    public AddUser() {
        super("", "");
        this.password = "";
        this.contact = "";
        this.userType = "";
    }

    public AddUser(String id, String name,
            JTextField tfName, JPasswordField tfPassword, JTextField tfContact, ButtonGroup buttonGroup) {
        super(id, name);
        this.password = new String(tfPassword.getPassword());
        this.contact = tfContact.getText();
        ButtonModel selectedButton = buttonGroup.getSelection();
        this.userType = (selectedButton != null) ? selectedButton.getActionCommand() : "No Selection";
        this.tfName = tfName;
        this.tfPassword = tfPassword;
        this.tfContact = tfContact;
        this.buttonGroup = buttonGroup;
    }

    @Override
    public boolean validate() {
        return validator.validateName(tfName.getText())
                && validator.validatePassword(password)
                && validator.validateContact(contact)
                && validator.validateUserType(userType);
    }

    @Override
    public void saveToFile(boolean isEdit, JTable table) throws IOException {
        if (validate()) {
            FileHandling userFile = new FileHandling();
            String filename = "user.txt";
            String[] headers = {"User ID", "Name", "Password", "Contact", "User Type"};
            String[] data = {id, name, password, contact, userType};

            ArrayList<String[]> userData = userFile.ReadDataFromFile("user.txt");
            System.out.println("read data");
            if (isEdit) {
                for (int i = 0; i < userData.size(); i++) {
                    if (userData.get(i)[0].equals(id)) {
                        userData.set(i, data);
                        break;
                    }
                }
            } else {
                userData.add(data);
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter("user.txt"));
            writer.close();
            for (String[] user : userData) {
                userFile.WriteDataToFile("user.txt", headers, user);
            }
            System.out.println("write data");

            JOptionPane.showMessageDialog(null, isEdit ? "User updated successfully!" : "User added successfully!");
            returnToDefault();
            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);
            model.setColumnIdentifiers(headers);
            model.setRowCount(0);
            for (String[] rowData : userData) {
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
        tfName.setText("");
        tfPassword.setText("");
        tfContact.setText("");
        buttonGroup.clearSelection();
    }
}
