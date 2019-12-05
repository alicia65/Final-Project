package com.alicia;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import static input.InputUtils.stringInput;

/**
 * Created by Alicia on 12/2/19.
 */
public class DollGUI extends JFrame {

    private JPanel mainPanel;
    private JButton doneButton;
    private JLabel resultLabel;
    private JTextField dollNameTextField;
    private JButton searchButton;
    private JButton clearButton;
    private JLabel nameLable;
    private JLabel typeLable;
    private JTextField typeTextField;
    private JTextField resultTextField;
    private JButton addButton;
    private JTable dollTable;
    private DefaultTableModel tableModel;


    private DollDatabase db;

    DollGUI(DollDatabase db) {//constructor called DollGUI contains DollDatabase parameter

        this.db = db; //object this has db variable which contains db value.

        setContentPane(mainPanel);//address about the window's details in mainPanel
        pack();//insert the items to the window
        setTitle("Doll Database Application");//display title as string
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//disconnect the program by shutting off this window
        configureTable();//calling table to modify
        setVisible(true);

        //addButton = new JButton("add");//initialized add Button
        addButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {

                 String name = resultTextField.getText();

                 if(name.isEmpty()){
                     resultLabel.setText("Doll name");
                     return;
                 }

                 String type = resultTextField.getText();
                 if(type.isEmpty()){
                     resultLabel.setText("Doll type");
                     return;
                 }

                 addNewDolls();
                 displayAllDolls();
                 clearButton();
                 searchButton();
             }
         });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            searchButton();
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
    }

    private void configureTable(){

        Vector columnTerms = db.getColumnTerms();//retrieving column terms from database
        Vector<Vector> features = db.getAllDolls();//save all dolls in vector list

        DefaultTableModel tableModel = new DefaultTableModel(features, columnTerms);
        dollTable.setModel(tableModel);//creating a model to store table model

    }

    public void clearButton() {

        dollNameTextField.setText("");
        typeTextField.setText("");
        resultTextField.setText("");
    }

    private void addNewDolls() {

            String dollName = dollNameTextField.getText();
            String dollType = typeTextField.getText();
           db.addNewDolls(dollName, dollType);
    }

    private void displayAllDolls() {

        Vector columnTerms = db.getColumnTerms();
        Vector<Vector> allDolls = db.getAllDolls();
        tableModel.setDataVector(allDolls, columnTerms);

        }

     private void searchButton(){// search for information in database for user
       for (Vector row: dollTable.getTableModel()) {// will read row by row and store in doll table
            String name = (String) row.get(0);//First row assigned to 0 for name and cast to string
           String type = (String) row.get(1);//Second row indicated as 1 for type and cast to string
           System.out.println(name + "is a doll with type" + type);
       }
     }
}


