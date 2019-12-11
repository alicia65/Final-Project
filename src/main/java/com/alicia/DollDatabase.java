package com.alicia;


/**
 * Created by Alicia on 12/10/2019.
 */

import java.sql.*;
import java.util.Vector;

import static input.InputUtils.stringInput;

public class DollDatabase {

    private static final String DB_CONNECTION_URL = "jdbc:sqlite:dolls.sqlite";//connect to url

    DollDatabase() {

        try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL);
             Statement statement = connection.createStatement()) {

            statement.execute("CREATE TABLE IF NOT EXISTS dolls" +
                    "( name TEXT, type TEXT)");//create and execute doll table with two columns: name and type

        }catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }

    public void addNewDolls(String name, String type) {// add new dolls to database

        String addNewDollSQL = "INSERT INTO dolls VALUES (?, ?)" ;//add new information to doll table

        try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL);
              PreparedStatement addNewDollPs = connection.prepareStatement( addNewDollSQL)) {

            addNewDollPs.setString(1, name);
            addNewDollPs.setString(2, type);
            addNewDollPs.executeUpdate();

        }catch (SQLException sqle) {
            System.err.println("Error adding doll because" + sqle);
        }
    }

    Vector<Vector> getAllDolls() {//fetch all dolls

        try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL);//connecting to database
             Statement statement = connection.createStatement()) {

            ResultSet allDolls = statement.executeQuery(" SELECT rowid, * FROM dolls");//retrieve all rows from doll table

            Vector<Vector> vectors = new Vector<>();//create a list of vector to store rows of dolls

            while (allDolls.next()) {//get to next row
                int id = allDolls.getInt("rowid");//get id and store in allDolls variable
                String dollName = allDolls.getString("name");
                String dollType = allDolls.getString("type");

                Vector v = new Vector();//generate object to store new columns
                v.add(id);// use add() method to add column to variable v
                v.add(dollName);
                v.add(dollType);

                vectors.add(v);//add v to list called vectors
            }
            return vectors;// return vectors

        } catch (SQLException e) {
            throw new RuntimeException(e);//alert for any errors
        }
    }

     Vector getColumnTerms() {

        Vector<String> colTerms = new Vector<>();//creating vector to save three columns

        colTerms.add("ID");
        colTerms.add("name");
        colTerms.add("type");

        return colTerms;
     }

     public void updateDoll(String name, String type) {//update database after making changes

        String sql = "UPDATE dolls SET type = ? WHERE name = ?";//Update changes in database

         try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL);
              PreparedStatement preparedStatement = connection.prepareStatement(sql)) {//protecting database for getting destroy
             //database

             preparedStatement.setString(1, type);//
             preparedStatement.setString(2, name);

             preparedStatement.executeUpdate();

         }catch (SQLException sqle) {
             System.err.println("Error updating doll DB table for doll" + name + type + "because" + sqle);
         }
     }

     public void delete(String name){

        String dsql = "DELETE FROM dolls WHERE name = ?";//sql delete command removes doll name from doll table.

        try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL);
             PreparedStatement dPreparedStatement = connection.prepareStatement(dsql)) {

            dPreparedStatement.setString(1, name);
            dPreparedStatement.execute();

        }catch (SQLException sqle) {
            System.err.println("Error deleting name from dolls table because of" + sqle);
        }

    }//public  void search (String name, String type){

        //String sql = "SELECT * FROM dolls"
    }


