/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class EditUser extends EditEntity {

    private ValidateEntity validator = new ValidateEntity();

    public EditUser(JTable table, JComboBox<String> combobox, JTextField name,
            JPasswordField password, JTextField contact, ButtonGroup buttonGroup,
            JRadioButton rbEditAdmin, JRadioButton rbEditStaff) {

        super(table, combobox);

        DefaultTableModel model = getModel();

        combobox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected_id = (String) combobox.getSelectedItem();

                if (selected_id != null && !selected_id.equals("Please select")) {
                    for (int i = 0; i < model.getRowCount(); i++) {
                        if (model.getValueAt(i, 0).toString().equals(selected_id)) {
                            name.setText(model.getValueAt(i, 1).toString());
                            password.setText(model.getValueAt(i, 2).toString());
                            contact.setText(model.getValueAt(i, 3).toString());
                            String userType = model.getValueAt(i, 4).toString();

                            if (userType.equals("Admin")) {
                                rbEditAdmin.setSelected(true);
                            } else if (userType.equals("Staff")) {
                                rbEditStaff.setSelected(true);
                            }
                        }
                    }
                } else {
                    name.setText("");
                    password.setText("");
                    contact.setText("");
                    buttonGroup.clearSelection();
                }
            }
        });
    }

    public void saveEditData(String id, String name, String password, String contact, String userType) {

        if (validator.validateName(name)
                && validator.validatePassword(password)
                && validator.validateContact(contact)
                && validator.validateUserType(userType)) {

            FileHandling userFile = new FileHandling();
            String filename = "user.txt";
            String[] headers = {"User ID", "Name", "Password", "Contact", "User Type"};
            String[] data = {id, name, password, contact, userType};

            try {
                List<String[]> userList = userFile.ReadDataFromFile(filename);
                List<String[]> updatedData = new ArrayList<>();

                for (String[] user : userList) {
                    if (!user[0].equals(id)) {
                        updatedData.add(user);
                    }
                }

                updatedData.add(new String[]{id, name, password, contact, userType});

                for (String[] user : updatedData) {
                    userFile.WriteDataToFile(filename, headers, user);
                }

                JOptionPane.showMessageDialog(null, "User edited successfully!");

                DefaultTableModel model = (DefaultTableModel) table.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    if (model.getValueAt(i, 0).toString().equals(id)) {
                        model.removeRow(i);
                        break;
                    }
                }
                model.addRow(new Object[]{id, name, password, contact, userType});

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Fail to edit user...!");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Validation error");
        }
    }
}
