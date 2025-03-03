/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import javax.swing.JTable;

/**
 *
 * @author User
 */
public abstract class AddEntity {
        
    protected String id;
    protected String name;
        
    public AddEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }
    
    // assume the data will and must be save into file after call add method
    public abstract boolean validate();
    public abstract void saveToFile(JTable table);
    public abstract void returnToDefault();
}
