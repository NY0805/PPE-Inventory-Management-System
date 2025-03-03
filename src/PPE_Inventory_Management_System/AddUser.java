/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.io.IOException;
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
    public void saveToFile(JTable table) {
        if (validate()) {
            FileHandling userFile = new FileHandling();
            String filename = "user.txt";
            String[] headers = {"User ID", "Name", "Password", "Contact", "User Type"};
            String[] data = {id, name, password, contact, userType};

            try {
                userFile.WriteDataToFile("user.txt", headers, data);
                JOptionPane.showMessageDialog(null, "User saved successfully!");
                returnToDefault();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.addRow(new Object[]{id, name, password, contact, userType});
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Fail to add user...!");
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
