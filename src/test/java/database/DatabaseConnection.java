package database;

import java.sql.*;

public class DatabaseConnection {
    public static void main(String[] args) throws SQLException {

        /**
         * In order to connect to database, we need the URL, username, and password
         * NOTE: This can be the interview.
         */

        String url = "jdbc:oracle:thin:@techglobal.cup7q3kvh5as.us-east-2.rds.amazonaws.com:1521/TGDEVQA";
        String username = "TECHGLOBALQA";
        String password = "TechGlobal123$!";
        String query = "SELECT * FROM employees";

        /** Create a connection to the database with the parameters we stored
         */
        Connection connection = DriverManager.getConnection(url, username, password);

        /**
         * Statement keeps the connection between DB and Automation framework to send queries.
         */
        Statement statement = connection.createStatement();

        /** ResultSet is sending the query to the database and get  the result.
         */
        ResultSet resultSet = statement.executeQuery(query);

        /**
         * ResultSetMetaData gives the meta information about the table ex.(Column size, names, column type etc.)
         */

        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        System.out.println("Number of columns: " + resultSetMetaData.getColumnCount());
        System.out.println("Column name :" + resultSetMetaData.getColumnName(2));

        while(resultSet.next()) {
            System.out.println(resultSet.getString("FIRST_NAME"));
            System.out.println(resultSet.getString(8));
        }
    }
}
