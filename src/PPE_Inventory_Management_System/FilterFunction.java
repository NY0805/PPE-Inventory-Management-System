/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PPE_Inventory_Management_System;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author user
 */
public class FilterFunction {
    public static void filterBtnChange(JButton filter, JTable table, JMenuItem... menuItems) {
        
        for (JMenuItem item : menuItems) {
            item.addActionListener(e -> {
                JMenuItem menuItem = (JMenuItem) e.getSource();
                filter.setText(menuItem.getText());
                
                TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) table.getRowSorter();
                if (sorter == null) {
                    sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
                    table.setRowSorter(sorter);
                }

                filterRow(sorter, menuItem.getText());
            });
        } 
    }
    
    private static void filterRow(TableRowSorter<DefaultTableModel> sorter, String filterOption) {
        // no filter applied initially
        RowFilter<DefaultTableModel, Object> filter = null;
        
        switch (filterOption) {
            case "Below 25 boxes": {
                filter = new RowFilter<DefaultTableModel, Object>() {
                    @Override
                    // entry object represent a row, used to decide a row is hidden or shown
                    public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                        try {
                            int quantity = Integer.parseInt(entry.getStringValue(3)); // get value from column 4
                            return quantity < 25;
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    }
                };
                break;
            }
            case "Above 25 boxes": {
                filter = new RowFilter<DefaultTableModel, Object>() {
                    @Override
                    // <?> is a wildcard, it can hold any types
                    public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                        try {
                            int quantity = Integer.parseInt(entry.getStringValue(3));
                            return quantity > 25;
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    }
                };
                break;
            }
            case "Out Of Stock": {
                filter = new RowFilter<DefaultTableModel, Object>() {
                    @Override
                    public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                        return entry.getStringValue(3).equals("0");
                    }
                };
                break;
            }
            case "RM 5 - 10": {
                filter = new RowFilter<DefaultTableModel, Object>() {
                    @Override
                    public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                        try {
                            double UnitPrice = Double.parseDouble(entry.getStringValue(4));
                            return UnitPrice >= 5 && UnitPrice <= 10;
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    }
                };
                break;
            }
            case "RM 11 - 15": {
                filter = new RowFilter<DefaultTableModel, Object>() {
                    @Override
                    public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                        try {
                            double UnitPrice = Double.parseDouble(entry.getStringValue(4));
                            return UnitPrice >= 11 && UnitPrice <= 15;
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    }
                };
                break;
            }
            case "RM 16 - 20": {
                filter = new RowFilter<DefaultTableModel, Object>() {
                    @Override
                    public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                        try {
                            double UnitPrice = Double.parseDouble(entry.getStringValue(4));
                            return UnitPrice >= 16 && UnitPrice <= 20;
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    }
                };
                break;
            }
            case "Above RM 20": {
                filter = new RowFilter<DefaultTableModel, Object>() {
                    @Override
                    public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                        try {
                            double UnitPrice = Double.parseDouble(entry.getStringValue(4));
                            return UnitPrice > 20;
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    }
                };
                break;
            }
            
    //      ===================================================================================================
            
            case "Below 25": {
                filter = new RowFilter<DefaultTableModel, Object>() {
                    @Override
                    public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Object> entry) {
                        try {
                            int Qty = Integer.parseInt(entry.getStringValue(6));
                            return Qty < 25;
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    }
                };
                break;
            }
            case "25 - 100": {
                filter = new RowFilter<DefaultTableModel, Object>() {
                    @Override
                    public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Object> entry) {
                        try {
                            int Qty = Integer.parseInt(entry.getStringValue(6));
                            return Qty >= 25 && Qty <= 100;
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    }
                };
                break;
            }
            case "101 - 200": {
                filter = new RowFilter<DefaultTableModel, Object>() {
                    @Override
                    public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Object> entry) {
                        try {
                            int Qty = Integer.parseInt(entry.getStringValue(6));
                            return Qty >= 101 && Qty <= 200;
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    }
                };
                break;
            }
            case "Above 200": {
                filter = new RowFilter<DefaultTableModel, Object>() {
                    @Override
                    public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Object> entry) {
                        try {
                            int Qty = Integer.parseInt(entry.getStringValue(6));
                            return Qty > 200;
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    }
                };
                break;
            }
            case "Below RM 100": {
                filter = new RowFilter<DefaultTableModel, Object>() {
                    @Override
                    public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Object> entry) {
                        try {
                            double transaction = Double.parseDouble(entry.getStringValue(7));
                            return transaction < 100;
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    }
                };
                break;
            }
            case "RM 100 - 200": {
                filter = new RowFilter<DefaultTableModel, Object>() {
                    @Override
                    public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Object> entry) {
                        try {
                            double transaction = Double.parseDouble(entry.getStringValue(7));
                            return transaction >= 100 && transaction <= 200;
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    }
                };
                break;
            }
            case "RM 201 - 300": {
                filter = new RowFilter<DefaultTableModel, Object>() {
                    @Override
                    public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Object> entry) {
                        try {
                            double transaction = Double.parseDouble(entry.getStringValue(7));
                            return transaction >= 201 && transaction <= 300;
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    }
                };
                break;
            }
            case "Above RM 300": {
                filter = new RowFilter<DefaultTableModel, Object>() {
                    @Override
                    public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Object> entry) {
                        try {
                            double transaction = Double.parseDouble(entry.getStringValue(7));
                            return transaction > 300;
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    }
                };
                break;
            }
            default: {
                sorter.setRowFilter(null);
                return;
            }
        }
        
        sorter.setRowFilter(filter);
    }
}
