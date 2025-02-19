/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.Font;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author User
 */
public class signUpPage extends JFrame implements ActionListener {

    private JTextField username;
    private JTextField name;
    private JPasswordField password;
    private JButton signUpButton;
    private JLabel statusMassage;
    private JLabel signUpTitle;

    public signUpPage() {
        // set up the frame
        setTitle("PPE Inventory Register");
        setBounds(350, 110, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // set up the Panel
        JPanel signUpContainer = new JPanel();
        signUpContainer.setPreferredSize(new Dimension(400, 600));
        signUpContainer.setBackground(new Color(0xFFFFFF));
        signUpContainer.setLayout(new BorderLayout());
        signUpContainer.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        // add the register title
        signUpTitle = new JLabel("SIGN UP");
        signUpTitle.setHorizontalAlignment(JLabel.CENTER);
        signUpTitle.setVerticalAlignment(JLabel.TOP);
        signUpTitle.setFont(new Font("MV Boli", Font.BOLD, 30));
        signUpTitle.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // set up the content container
        JPanel contentContainer = new JPanel();
        contentContainer.setPreferredSize(new Dimension(340, 500));
        contentContainer.setBackground(new Color(0xF5F5DC));
        contentContainer.setLayout(null);

        // username title
        JLabel usernameTitle = new JLabel("Username");
        usernameTitle.setFont(new Font("MV Boli", Font.PLAIN, 16));
        usernameTitle.setBounds(20, 20, 200, 30);
//        usernameTitle.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // username textfield
        username = new JTextField();
        username.setBounds(20, 55, 340, 30);
        username.setBackground(Color.white);
        username.setCaretColor(Color.blue);
        username.setBorder(BorderFactory.createLineBorder(new Color(0x486A47), 2));

        // full name title
        JLabel fullNameTitle = new JLabel("Full Name");
        fullNameTitle.setFont(new Font("MV Boli", Font.PLAIN, 16));
        fullNameTitle.setBounds(20, 110, 200, 30);
//        usernameTitle.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // full name textfield
        JTextField fullName = new JTextField();
        fullName.setBounds(20, 145, 340, 30);
        fullName.setBackground(Color.white);
        fullName.setCaretColor(Color.blue);
        fullName.setBorder(BorderFactory.createLineBorder(new Color(0x486A47), 2));

        // password title
        JLabel passwordTitle = new JLabel("Password");
        passwordTitle.setFont(new Font("MV Boli", Font.PLAIN, 16));
        passwordTitle.setBounds(20, 200, 200, 30);
//        usernameTitle.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // full name textfield
        password = new JPasswordField();
        password.setBounds(20, 235, 340, 30);
        password.setBackground(Color.white);
        password.setCaretColor(Color.blue);
        password.setBorder(BorderFactory.createLineBorder(new Color(0x486A47), 2));

        // user type title
        JLabel userTypeTitle = new JLabel("User Type");
        userTypeTitle.setFont(new Font("MV Boli", Font.PLAIN, 16));
        userTypeTitle.setBounds(20, 290, 200, 30);
//        usernameTitle.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // user type selection
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        radioPanel.setBounds(20, 325, 340, 30);

        JRadioButton admin = new JRadioButton("Admin");
        JRadioButton staff = new JRadioButton("Staff");
        admin.setFocusable(false);
        staff.setFocusable(false);
        
        ButtonGroup userTypeGroup = new ButtonGroup();
        userTypeGroup.add(admin);
        userTypeGroup.add(staff);

        radioPanel.add(admin);
        radioPanel.add(staff);

        contentContainer.add(usernameTitle);
        contentContainer.add(username);
        contentContainer.add(fullNameTitle);
        contentContainer.add(fullName);
        contentContainer.add(passwordTitle);
        contentContainer.add(password);
        contentContainer.add(userTypeTitle);
        contentContainer.add(radioPanel);

        signUpContainer.add(signUpTitle, BorderLayout.NORTH);
        signUpContainer.add(contentContainer, BorderLayout.SOUTH);

        add(signUpContainer, BorderLayout.EAST);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        new signUpPage();
    }
}
