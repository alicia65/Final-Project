package com.alicia;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Created by Alicia on 12/2/19.
 */
public class DollGUI extends JFrame {

    private JPanel mainPanel;
    private JTable dollDataTable;
    private JButton endButton;
    private JLabel dollLabel;
    private JTextField dollNameTextField;
    private JButton DollButton;

    private DollDatabase db;

    DollGUI(DollDatabase db) {//constructor called DollGUI contains DollDatabase parameter

        this.db = db; //object this has db variable which contains db value.

        setContentPane(mainPanel);//address about the window's details in mainPanel
        pack();//insert the items to the window
        setTitle("Doll Database Application");//display title as string
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//disconnect the program by shutting off this window
        configureTable();//calling table to modify
        setVisible(true);

        DollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String doll = dollNameTextField.getText();
                dollLabel.setText(doll);
            }
        });

        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int end = JOptionPane.showConfirmDialog(DollGUI.this, "Final call, would you like to end this program?",
                     "End", JOptionPane.OK_CANCEL_OPTION);
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
        dollDataTable.setModel(tableModel);//creating a model to store table model
    }
}
