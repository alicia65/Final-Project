package com.alicia;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Vector;


/**
 * Created by Alicia on 12/12/19.
 * Class JAVA 2505.
 *
 */
public class DollGUI extends JFrame {

    private JPanel mainPanel;
    private JButton doneButton;
    private JLabel resultLabel;
    private JButton searchButton;
    private JButton addButton;
    private JTable dollTable;
    private JButton updateButton;
    private JButton deleteButton;
    private DefaultTableModel tableModel;
    private DollDatabase db;

    DollGUI(DollDatabase db) {//constructor called DollGUI contains DollDatabase parameter

        this.db = db; //object this has db variable which contains db value.
        setContentPane(mainPanel);//address about the window's details in mainPanel
        setPreferredSize(new Dimension(1000, 500));
        pack();//insert the items to the window
        setTitle("Doll Database Application");//display title as string
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//disconnect the program by shutting off this window
        configureTable();//calling table to modify

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String dollNameInput = JOptionPane.showInputDialog(DollGUI.this,
                        "Enter the doll name", "Add doll name", JOptionPane.INFORMATION_MESSAGE);

                if (dollNameInput.trim().length()== 0){
                        resultLabel.setText("Doll name is");
                        return;
                    }

                String dollTypeInput = JOptionPane.showInputDialog(DollGUI.this,
                            "Enter the doll type", "Add doll type", JOptionPane.INFORMATION_MESSAGE);

                if (dollTypeInput.trim().length()== 0) {
                        resultLabel.setText(" Doll type is");
                        return;
                    }

                addNewDolls(dollNameInput, dollTypeInput);
                displayAllDolls();
                db.searchDoll(dollNameInput, dollTypeInput);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

             String searchNInput = JOptionPane.showInputDialog(DollGUI.this, "Enter doll name",
                     "Find doll name ", JOptionPane.INFORMATION_MESSAGE);//showing search message to user
                List<String> same = db.search(searchNInput);//search string name and store in same variable

                if (same.isEmpty()) {//checking for doll name in database
                    resultLabel.setText(" No doll names found" + same);//display no doll names by this name exist
                }else {
                    resultLabel.setText("List of matching doll names found:"); //display found names
                    for (String dollName : same) {//checking each name in database
                        resultLabel.setText(dollName);// looking for doll name
                    }
                }

                String searchTInput = JOptionPane.showInputDialog(DollGUI.this, "Enter doll type ",
                     "Find doll type",JOptionPane.INFORMATION_MESSAGE);
                List<String> alike = db.search(searchTInput);

                 if (alike.isEmpty()) {
                     resultLabel.setText("No doll types found" + alike);
                 }else {
                     resultLabel.setText(("List of matching doll types found:"));
                     for (String dollType: alike) {
                         resultLabel.setText(dollType);
                     }
                     searchButton();//call search button to retrieve doll types
                }
            }
        });

        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int end = JOptionPane.showConfirmDialog(DollGUI.this, "Would you like to end this search?",
                        "Done", JOptionPane.OK_CANCEL_OPTION);//Asking user for confirmation before leaving doll database application
                if (end == JOptionPane.OK_OPTION) {//closing program when user selected ok button
                    System.exit(0);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAllDolls();//show and update all dolls
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dollName = null;
                deleteButton(dollName);
                displayAllDolls();
            }
        });
    }

    private void configureTable() {

        Vector columnTerms = db.getColumnTerms();//retrieving column terms from database
        Vector<Vector> features = db.getAllDolls();//save all dolls in vector list

        tableModel = new DefaultTableModel(features, columnTerms);
        dollTable.setModel(tableModel);//creating a model to store table model
    }

    private void displayAllDolls() {
        Vector columnTerms = db.getColumnTerms();
        Vector<Vector> allDolls = db.getAllDolls();
        tableModel.setDataVector(allDolls, columnTerms);
    }

    private void addNewDolls(String dollName, String dollType) {//adding new dolls to database
        db.addNewDolls(dollName, dollType);//Adding new doll information by user
        addButton.updateUI();
    }

    private void updateButton(String dollName, String dollType) {
        db.updateDoll(dollName,dollType);
        updateButton.updateUI();
    }

    private void deleteButton(String dollName) {
        db.delete(dollName);//deleting unwanted doll name in database
        deleteButton.updateUI();
    }

    private void searchButton() {// search for information in database
        db.searchDoll();
        searchButton.updateUI();
    }
}


