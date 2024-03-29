package com.alicia;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Objects;
import java.util.Vector;


/**
 * Created by Alicia on 12/16/19.
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
    JTextField dollNameTextField;
    JLabel nameLabel;
    JTextField typeTextField;
    JLabel typeLabel;
    JTextField resultTextField;
    JButton clearButton;


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

        addButton.addActionListener(new ActionListener() {// add button will listen to chick of keyboard
            @Override
            public void actionPerformed(ActionEvent e) {

                String dollNameInput = JOptionPane.showInputDialog(DollGUI.this,
                        "Enter the doll name", "Add doll name", JOptionPane.INFORMATION_MESSAGE);// Message asks
                //user to enter information

                if (dollNameInput.trim().length()== 0){//display message if user did not response
                        resultLabel.setText("Doll name is");// Prompt user to enter doll name
                        return;//return if user did not answer
                    }

                String dollTypeInput = JOptionPane.showInputDialog(DollGUI.this,
                            "Enter the doll type", "Add doll type", JOptionPane.INFORMATION_MESSAGE);//Requesting user to supply
                // information about adding doll type to data base

                if (dollTypeInput.trim().length()== 0) {
                        resultLabel.setText(" Doll type is");
                        return;
                    }

                addNewDolls(dollNameInput, dollTypeInput);// Method which calling string inputs from user to add new dolls
                displayAllDolls();//print and update dolls
                db.searchDoll();//search query of dolls from data base
            }
        });

        searchButton.addActionListener(new ActionListener() {//listening for search button
            @Override
           public void actionPerformed(ActionEvent e) {

          String searchNInput = JOptionPane.showInputDialog(DollGUI.this, "Enter doll name",
              "Find doll name " , JOptionPane.INFORMATION_MESSAGE) ;//Search for doll names by displaying text using
               //JOptionPane which shows and enters text
          String searchTInput = JOptionPane.showInputDialog(DollGUI.this, "Enter doll type ",
                       "Find doll type", JOptionPane.INFORMATION_MESSAGE);
          List<Doll> same = db.searchDoll();

               if (same.isEmpty()) {//checking for doll name in database
                 resultLabel.setText(" No doll names found" + same);//display no doll names by this name exist
               }else {
                   resultLabel.setText("List of matching doll names found:"); //display found names
                   for (Doll dollName : same) {//checking each name in database
                       resultLabel.setText(String.valueOf(dollName));// looking for doll name
                 }
            }

               String[] alike = new String[0];// create and store values in string of array
               if (Objects.equals(alike, this)) {//searching for match dolls by user requested
                   resultLabel.setText(("List of matching doll types found:"));//Display text to user if dolls are found
                  for (String dollType: alike) {//loop through variable alike to find add doll type
                      resultLabel.setText(dollType);//if dolls are found, sent to resultLabel to display doll types
                  }   searchButton();//call search button to retrieve doll types
              } else {
                   resultLabel.setText("No doll types found" + alike);//if no dolls found, print this message
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
        Doll findType = null;
        String findName = null;
        db.searchDoll();// search in data base for dolls
        searchButton.updateUI();//update search button for new information
    }
}


