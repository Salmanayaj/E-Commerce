package com.example.ecommerce;

import java.sql.*;


public class dbConnection {
    private final String dbUrl = "jdbc:mysql://localhost:3306/ecommerce";
    private final String userName = "root";
    private final String password = "salman12";

    private Statement getStatement() {
        try {
            Connection connection = DriverManager.getConnection(dbUrl,userName,password);
            return connection.createStatement();
        } catch (Exception e) {
             e.printStackTrace();
        }
        return null;
    }
    public int updateDatabase(String query){
        try {
            Statement statement = getStatement();
            return statement.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public ResultSet getqueryTable(String query){
        try {
            Statement statement = getStatement();
            return statement.executeQuery(query);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        dbConnection conn = new dbConnection();
        ResultSet rs = conn.getqueryTable("Select * from customer");
        if(rs != null){
            System.out.println("COnnection Successful");
        } else {
            System.out.println("Connection fail");
        }
    }
}
