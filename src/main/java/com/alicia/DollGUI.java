package com.alicia;

import javax.accessibility.AccessibleTableModelChange;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;


/**
 * Created by Alicia on 12/10/19.
 * Class JAVA 2505.
 *
 */
public class DollGUI extends JFrame {

    private final String title = new String();
    private JPanel mainPanel;
    private JButton doneButton;
    private JLabel resultLabel;
    private JTextField dollNameTextField;
    private JButton searchButton;
    private JButton clearButton;
    private JLabel nameLabel;
    private JLabel typeLabel;
    private JTextField typeTextField;
    private JTextField resultTextField;
    private JButton addButton;
    private JTable dollTable;
    private JButton updateButton;
    private JButton deleteButton;
    private DefaultTableModel tableModel;
    private DollDatabase db;

    DollGUI(DollDatabase db) {//constructor called DollGUI contains DollDatabase parameter

        this.db = db; //object this has db variable which contains db value.
        setContentPane(mainPanel);//address about the window's details in mainPanel
        setPreferredSize(new Dimension(500, 500));
        pack();//insert the items to the window
        setTitle("Doll Database Application");//display title as string
        setLocationRelativeTo(null);
        setTitle(title);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//disconnect the program by shutting off this window
        configureTable();//calling table to modify

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String input = JOptionPane.showInputDialog(DollGUI.this,
                        "Enter the doll name", "Title", JOptionPane.INFORMATION_MESSAGE);
                String name = dollNameTextField.getText();

                try {
                    if (name.trim().length()== 0){
                        resultLabel.setText("Doll name is");
                        return;
                    }

                    String input = JOptionPane.showInputDialog(DollGUI.this,
                            "Enter the doll type", "Title", JOptionPane.INFORMATION_MESSAGE);
                    String type = typeTextField.getText();
                    if (type.trim().length()== 0) {
                        resultLabel.setText(" Doll type is");
                        return;
                    }
                    resultTextField.setText(name + "" + type);

                }catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(DollGUI.this,
                     "Please enter only letters(no numbers");// set up validation by requesting users to enter letters only
                }
                addNewDolls();
                displayAllDolls();
                clearButton();
                searchButton();
                //setTitle( name, type);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (typeTextField.getText().endsWith("")) {
                    resultTextField.getText();
                    searchButton();
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearButton();
            }
        });

        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int end = JOptionPane.showConfirmDialog(DollGUI.this, "Would you like to end this search?",
                        "Done", JOptionPane.OK_CANCEL_OPTION);
                if (end == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateButton();
                displayAllDolls();
                //makeChangeToTable(e);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteButton();
                displayAllDolls();
            }
        });
    }

    //private void setTitle(String name, String type) {//creating title for columns
        //JTable table = new JTable(vectors, colTerms);
        //JScrollPane scrollPane = new JScrollPane(table);
        //table.setFillsViewportHeight(true);
       //}


    private void configureTable() {

        Vector columnTerms = db.getColumnTerms();//retrieving column terms from database
        Vector<Vector> features = db.getAllDolls();//save all dolls in vector list

        tableModel = new DefaultTableModel(features, columnTerms);
        dollTable.setModel(tableModel);//creating a model to store table model
    }

    public void clearButton() {
        dollNameTextField.setText("");
        typeTextField.setText("");
        resultTextField.setText("");
    }

    private void displayAllDolls() {
        Vector columnTerms = db.getColumnTerms();
        Vector<Vector> allDolls = db.getAllDolls();
        tableModel.setDataVector(allDolls, columnTerms);
    }

    private void addNewDolls() {
        String dollName = dollNameTextField.getText();
        String dollType = typeTextField.getText();
        db.addNewDolls(dollName, dollType);
        addButton.updateUI();
    }

    private void updateButton() {
        String dollName = dollNameTextField.getText();
        String dollType = typeTextField.getText();
        db.updateDoll(dollName,dollType);
        updateButton.updateUI();
    }

    private void deleteButton() {
        String dollName = dollNameTextField.getText();
        db.delete(dollName);
    }

    private void makeChangeToTable(AccessibleTableModelChange e) {

        int rowId = e.getFirstRow();
        //TableColumnModel  columnModel = (TableColumnModel) e.getFirstColumn();
       // String columnName = columnModel.
        //String columnType =
    }
    private void searchButton() {// search for information in database


            searchButton.updateUI();
    }
}


