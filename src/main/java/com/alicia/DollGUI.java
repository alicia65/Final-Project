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
    private JButton updateButton;
    private JButton deleteButton;
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

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name = dollNameTextField.getText();

                if (name.isEmpty()) {
                    resultLabel.setText(name);
                    return;
                }

                String type = typeTextField.getText();
                if (type.isEmpty()) {
                    resultLabel.setText(type);
                    return;
                }

                resultTextField.setText(name + "" + type);
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

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateButton();
                displayAllDolls();
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

    private void searchButton() {// search for information in database

        String dollName = "";
        String dollType = "";

        if (dollName == dollType){
            resultTextField.getText();
            db.getColumnTerms();

        }else {
            resultTextField.getText();
        }

    }
}



