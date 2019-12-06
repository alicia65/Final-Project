package com.alicia;

import java.sql.*;
import java.util.Vector;

import static input.InputUtils.stringInput;

public class DollDatabase {

    private static final String DB_CONNECTION_URL = "jdbc:sqlite:dolls.sqlite";

    DollDatabase() {

        try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL);
             Statement statement = connection.createStatement()) {

            statement.execute("CREATE TABLE IF NOT EXISTS dolls" +
                    "( name TEXT, type TEXT)");

        }catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }

    public void addNewDolls(String name, String type) {

        String addNewDollSQL = "INSERT INTO dolls VALUES (?, ?)" ;

        try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL);
              PreparedStatement addNewDollPs = connection.prepareStatement( addNewDollSQL)) {

            addNewDollPs.setString(1, name);
            addNewDollPs.setString(2, type);
            addNewDollPs.executeUpdate();

        }catch (SQLException sqle) {
            System.err.println("Error adding doll because" + sqle);
        }
    }

    Vector<Vector> getAllDolls() {

        try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL);
             Statement statement = connection.createStatement()) {

            ResultSet allDolls = statement.executeQuery(" SELECT rowid, * FROM dolls");//retrieve all dolls with names starting with "CH"

            Vector<Vector> vectors = new Vector<>();

            while (allDolls.next()) {
                int id = allDolls.getInt("rowid");
                String dollName = allDolls.getString("name");
                String dollType = allDolls.getString("type");

                Vector v = new Vector();
                v.add(id);
                v.add(dollName);
                v.add(dollType);

                vectors.add(v);
            }
            return vectors;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

     Vector getColumnTerms() {

        Vector<String> colTerms = new Vector<>();//creating vector to save three columns

        colTerms.add("ID");
        colTerms.add("name");
        colTerms.add("type");

        return colTerms;
     }

     private void updateDoll(String name, String type) {//update database after making changes

        String sql = "UPDATE dolls SET name = ?, WHERE type = ?";//Update changes in database

         try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL);
              PreparedStatement preparedStatement = connection.prepareStatement(sql)) {//protecting database for getting destroy
             //database

             preparedStatement.setString(1, name);//
             preparedStatement.setString(2, type);

             preparedStatement.executeUpdate();

         }catch (SQLException sqle) {
             System.err.println("Error updating doll DB table for doll" + name + type + "because" + sqle);
         }
     }

     public void delete(String name){

        String dsql = "DELETE FROM dolls WHERE name = ?";

        try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL);
             PreparedStatement dpreparedStatement = connection.prepareStatement(dsql)) {

            dpreparedStatement.setString(1, name);
            dpreparedStatement.execute();

        }catch (SQLException sqle) {
            System.err.println("Error deleting name from dolls table because of" + sqle);
        }

    }

 }

