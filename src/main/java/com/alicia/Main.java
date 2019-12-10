package com.alicia;

/**
 * Created by Alicia on 12/10/2019.
 * Doll database application is an OneStop doll information place.
 * It is user-friendly app where doll lovers can explore and learn more about their interested dolls.
 * Furthermore, the application has special buttons: search, add, update, clear, delete, and done. These buttons help users
 * to navigate while using the application.
  */

import java.util.Vector;

import static input.InputUtils.stringInput;
public class Main {

    public static void main(String[] args) {
        // write your code here
        DollDatabase db = new DollDatabase();
        DollGUI gui = new DollGUI(db);
    }
}

