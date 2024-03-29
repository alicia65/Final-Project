package com.alicia;


/**
 * Created by Alicia on 12/12/2019.
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static input.InputUtils.stringInput;

public class DollDatabase {

    private static final String DB_CONNECTION_URL = "jdbc:sqlite:dolls.sqlite";//connect to url

    DollDatabase(Object doll, Object list) {

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

    Vector<Vector> getAllDolls() {//search all dolls

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

    public List<Doll> searchDoll() {//Using search button to search for dolls in database

        String findSql = "SELECT * FROM dolls WHERE equalsIgnorcase(name, type) like equalsIgnorecase(? ,?)";

        try(Connection connection = DriverManager.getConnection(DB_CONNECTION_URL);
            PreparedStatement preparedStatement = connection.prepareStatement(findSql)) {

            String findName = "";
            preparedStatement.setString(1, "%" + findName + "%");
            String findType = "";
            preparedStatement.setString(2, "%" + findType + "%");
            ResultSet dollsRs = preparedStatement.executeQuery();

            List<Doll> dolls = new ArrayList<>();//Creating array list to save doll names and types

            while (dollsRs.next()) {//getting next doll while true
                String name = dollsRs.getString("name"); //Getting string name and sending via result set
                String type = dollsRs.getString("type");
                Doll doll = new Doll(name, type);
                dolls.add(doll);//adding found dolls to doll list
            }

            return dolls;//return all dolls

        }catch(SQLException e){
            throw  new RuntimeException(e);
        }
    }

     Vector getColumnTerms() {

        Vector<String> colTerms = new Vector<>();//creating vector to save three columns

        colTerms.add("ID");//add id to vector
        colTerms.add("name");//add name to vector
        colTerms.add("type");

        return colTerms;//return variable called colTerms
     }

     public void updateDoll(String name, String type) {//update database after making changes

        String sql = "UPDATE dolls SET type = ? WHERE name = ?";//Update changes in database

         try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL);// connect to URL
              PreparedStatement preparedStatement = connection.prepareStatement(sql)) {//protecting database for getting destroy
             //database

             preparedStatement.setString(1, type);//protect type for unwanted visitors and assign to location # 1
             preparedStatement.setString(2, name);

             preparedStatement.executeUpdate();//go get doll name and type

         }catch (SQLException sqle) {//checking for sqle
             System.err.println("Error updating doll DB table for doll" + name + type + "because" + sqle);
         }
     }

     public void delete(String name) {// delete method delete doll name

         String dsql = "DELETE FROM dolls WHERE name = ?";//sql delete command removes doll name from doll table.

         try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL);
              PreparedStatement dPreparedStatement = connection.prepareStatement(dsql)) {

             dPreparedStatement.setString(1, name);
             dPreparedStatement.execute();

         } catch (SQLException sqle) {
             System.err.println("Error deleting name from dolls table because of" + sqle);
         }
     }

}


