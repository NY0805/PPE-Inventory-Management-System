/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.io.IOException;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author User
 */
public class AddUser extends AddEntity {

    private String password;
    private String userType;

    private JTextField tfName;
    private JPasswordField tfPassword;
    private ButtonGroup buttonGroup;
    private ValidateEntity validator = new ValidateEntity();

    public AddUser(String id, String name, JTextField tfName, 
            JPasswordField tfPassword, ButtonGroup buttonGroup) {
        super("", "");
        this.tfName = new JTextField();
        this.tfPassword = new JPasswordField();
        this.buttonGroup = new ButtonGroup();
    }

    public AddUser(String id, String name, String password, String userType,
            JTextField tfName, JPasswordField tfPassword, ButtonGroup buttonGroup) {
        super(id, name);
        this.password = password;
        this.userType = userType;
        this.tfName = tfName;
        this.tfPassword = tfPassword;
        this.buttonGroup = buttonGroup;
    }

    @Override
    public boolean validate() {
        return validator.validateName(tfName.getText())
                && validator.validatePassword(password)
                && validator.validateUserType(userType);
    }

    @Override
    public void saveToFile() {
        if (validate()) {
            FileHandling userFile = new FileHandling();
            String filename = "user.txt";
            String[] headers = {"User ID", "Name", "Password", "User Type"};
            String[] data = {id, name, password, userType};

            try {
                userFile.WriteDataToFile("user.txt", headers, data);
                JOptionPane.showMessageDialog(null, "User saved successfully!");
                returnToDefault();
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
        buttonGroup.clearSelection();
    }
}
