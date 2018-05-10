package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Jialin Yang
 * @version 2.1
 * @since 2018-04-09
 * @Lab final-project
 */

/**
 * This class allows an object to connect / close a database connection.
 */
  
public class DBConnect {
 
	// Code database URL
	static final String DB_URL = "jdbc:mysql://www.papademas.net:3306/fp510?autoReconnect=true&useSSL=false";
	// Database credentials
	static final String USER = "fpuser", PASS = "510";
	
	/**
	 * Method to connect to database.
	 */
	public Connection connect() throws SQLException {
		return DriverManager.getConnection(DB_URL, USER, PASS);
	}
}
