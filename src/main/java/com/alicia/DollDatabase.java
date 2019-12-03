package com.alicia;

import java.sql.*;
import java.util.Vector;

public class DollDatabase {

    private static final String DB_CONNECTION_URL = "jdbc:sqlite:dolls:sqlite";

    DollDatabase() {

        try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS dolls" +
                    "(id INTEGER PRIMARY KEY , title TEXT, type TEXT)");

            statement.executeUpdate("INSERT INTO dolls (title, type ) VALUE ('Molly McIntire', 'American Girl')");
            statement.executeUpdate("INSERT INTO dolls (title, type ) VALUE ('Barbie', 'Fashion doll')");
            statement.executeUpdate("INSERT INTO dolls (title, type ) VALUE ('Britney Spears', 'Pop singer')");

        }catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }

    public void addNewDoll(String title, String type) {

        try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL);
              PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO dolls ('title', 'type') values (?, ?) ")) {

            preparedStatement.setString(1,title);
            preparedStatement.setString(2, type);
            preparedStatement.executeUpdate();

        }catch (SQLException sqle) {
            System.err.println("Error adding doll because" + sqle);
        }
    }

    Vector<Vector> getAllDolls() {

        try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL);
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery(" SELECT * FROM dolls ORDER BY title");

            Vector<Vector> vectors = new Vector<>();

            while (rs.next()) {

                int id = rs.getInt("id");
                String title = rs.getString("title");
                String type = rs.getString("type");

                Vector v = new Vector();
                v.add(id);
                v.add(title);
                v.add(type);

                vectors.add(v);
            }
            return vectors;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

     Vector getColumnTerms() {

        Vector<String> colTerms = new Vector<>();

        colTerms.add("ID");
        colTerms.add("title");
        colTerms.add("type");

        return colTerms;
     }

     public void updateDoll(String title, String type) {

        String sql = "UPDATE dolls SET title = ?, WHERE type = ?";

         try (Connection connection = DriverManager.getConnection(DB_CONNECTION_URL);
              PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

             preparedStatement.setString(1, title);
             preparedStatement.setString(2, type);

             preparedStatement.executeUpdate();

         }catch (SQLException sqle) {
             System.err.println("Error updating doll DB table for doll" + title + type + "because" + sqle);
         }
     }


 }

