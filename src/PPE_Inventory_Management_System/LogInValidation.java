/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import java.awt.CardLayout;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class LogInValidation extends JFrame {
//    private String userId;
//    private String name;
//    private String userType;

    public void Validate(String id, String password, LogIn loginFrame) {
        try {
            FileHandling userFile = new FileHandling();
            ArrayList<String[]> userData = userFile.ReadDataFromFile("user.txt");

            boolean userFound = false;

            for (String[] user : userData) {
                String storedId = user[0];
                String storedPassword = user[2];
                String storedName = user[1];
                String storedContact = user[3];
                String storedUserType = user[4];

                if (storedId.equals(id) && (storedPassword.equals(password))) {
                    userFound = true;
                    saveLoginDetails(id, storedName, password, storedContact, storedUserType);

                    loginFrame.dispose();
                    Main dashboard = new Main(id, storedName, password, storedContact, storedUserType);
                    dashboard.setVisible(true);
                    dashboard.showSideBar(storedUserType);
                    break;
                }
            }

            if (!userFound) {
                JOptionPane.showMessageDialog(null, "Invalid User ID or Password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Login failed, please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error reading user data: " + e.getMessage());
        }
    }

    public void saveLoginDetails(String id, String name, String password, String contact, String userType) throws IOException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String loginTime = ("Log In : " + LocalDateTime.now().format(format));

        FileHandling userFile = new FileHandling();
        String[] headers = {"User ID", "Name", "Password", "Contact", "User Type", "Login Time"};
        String[] data = {id, name, password, contact, userType, loginTime};

        userFile.WriteDataToFile("login.txt", headers, data);
    }

}
