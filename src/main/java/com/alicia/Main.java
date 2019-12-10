package com.alicia;

import java.util.Vector;

import static input.InputUtils.stringInput;
public class Main {

    public static void main(String[] args) {
        // write your code here
        DollDatabase db = new DollDatabase();
        DollGUI gui = new DollGUI(db);
    }
}

