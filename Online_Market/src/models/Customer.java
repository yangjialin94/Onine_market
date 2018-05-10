package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.xml.bind.DatatypeConverter;

/**
 * @author Jialin Yang
 * @version 2.1
 * @since 2018-04-09
 * @Lab final-project
 */

/**
 * This class have functions to check if customer exist in customer list by username, add new 
 * customer to customer list and add customer's wish list to database, check if username and 
 * password in correct for login by username and password, get seller's email address by username,
 * get seller's phone number by username, add product to customer's wish list by product, delete 
 * product from customer's wish list by pid.
 */

public class Customer extends User {
	
	// instance field
	public String email;
	public String phone;
	public String username;
	public String password;
	public static String currentUsername;
	
	// constructors
	/**
	 * empty Customer constructor.
	 */
	public Customer() {}
	
	/**
	 * Customer constructor.
	 */
	public Customer(String email, String phone, String username, String password) {
		this.email = email;
		this.phone = phone;
		this.username = username;
		this.password = password;
	}

	// getters
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	
	/**
	 * @return the userName
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	// setters
	/**
	 * @param the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @param the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**
	 * @param the userName to set
	 */
	public void setUsername(String userName) {
		this.username = userName;
	}

	/**
	 * @param the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Method to check if customer exist in customer list by username.
	 * @param username to check
	 * @return true/false for existence
	 */
	public boolean userExist(String username) {
		// Declare DB objects and local variables
		DBConnect conn = new DBConnect();
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<String> usernames = new ArrayList<>();
			
		try {
			// get existed usernames from jyang_customer_tab
			stmt = conn.connect().createStatement();
			String sql = "SELECT username FROM jyang_customers_tab";
			rs = stmt.executeQuery(sql);
			conn.connect().close();
			
			// add usernames into an usernames arraylist
			while (rs.next()) {
				usernames.add(rs.getString(1));
			}
		} catch (SQLException se) {
			// print error message
			se.printStackTrace();
	    }
		
		// check if username exist in usernames arraylist
		if (usernames.contains(username)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Method to add new customer to customer list and add customer's wish list to database.
	 * @param customer to add
	 */
	public static void addToTable(Customer customer) {
		//Declare DB objects and local variables
		DBConnect conn = new DBConnect();
		Statement stmt = null;
		String sql;
		
		try {
			// insert customer into jyang_customer_tab
			System.out.println("Inserting cutomer into jyang_customers_tab");
			stmt = conn.connect().createStatement();
			sql = "INSERT INTO jyang_customers_tab (email, phone, username, password) VALUES ('" + customer.email + "', '" + customer.phone + "', '" + customer.username + "', '" + customer.password + "')";	
			stmt.executeUpdate(sql);
			System.out.println("Finish inserting customer into jyang_customers_tab");
			System.out.println("");
			
			// create username_wish_tab
			String wishTab = customer.username + "_wish_tab";
			System.out.println("Start to Create " + wishTab);
			stmt = conn.connect().createStatement();
			sql = "CREATE TABLE " + wishTab + " (pid INTEGER, " + " username VARCHAR(30), " + " product VARCHAR(30), " + " oldNew VARCHAR(30), " + " price DOUBLE, " + " description VARCHAR(300), " + " PRIMARY KEY (pid))";
			stmt.executeUpdate(sql);
			conn.connect().close();
			System.out.println("Created " + wishTab);
			System.out.println("");
		} catch (SQLException se) {
			// print error message
			se.printStackTrace();
	    }
	}
	
	/**
	 * Method to check if username and password in correct for login by username and password.
	 * @param username and password to check
	 * @return true/false for correctness
	 */
	public boolean logInIsValid(String username, String password) {
		// check if username exist in customer list
		if (!userExist(username)) {
			System.out.println("User does not exsit!");
			return false;
		} else {
			//Declare DB objects 
			DBConnect conn = new DBConnect();
			Statement stmt = null;
			String sql;
				
			try {
				// retrieve password for username from database
				stmt = conn.connect().createStatement();
				sql = "SELECT password FROM jyang_customers_tab WHERE username = '" + username + "'";
				ResultSet rs = stmt.executeQuery(sql);	
				String getPassword = "";
				
				// get encoded password
				if (rs.next()) {
					getPassword = rs.getString(1);
				}
				
				// decode the password
				String decodedPassword = new String(DatatypeConverter.parseBase64Binary(getPassword));
				conn.connect().close();
				
				// check if decodedPassword is same as password
				if (decodedPassword.equals(password)) {
					currentUsername = username;
					return true;
				} else {
					return false;
				}
			} catch (SQLException se) {
				// print error message
				se.printStackTrace();
		    }
		}
		
		return false;
	}
	
	/**
	 * Method to get seller's email address by username.
	 * @param seller's username
	 * @return seller's email
	 */
	public static String findSellerEmail(String username) {
		//Declare DB objects and local variables
		DBConnect conn = new DBConnect();
		Statement stmt = null;
		String sellerEmail = "";
			
		try {
			// retrieve email by username from database
			stmt = conn.connect().createStatement();
			String sql = "SELECT email FROM jyang_sellers_tab WHERE username = '" + username + "'";
			ResultSet rs = stmt.executeQuery(sql);	
			
			// get email
			if (rs.next()) {
				sellerEmail = rs.getString(1);
			}
			
			conn.connect().close();
		} catch (SQLException se) {
			// print error message
			se.printStackTrace();
	    }
		
		// return email
		return sellerEmail;
	}
	
	/**
	 * Method to get seller's phone number by username.
	 * @param seller's username
	 * @return seller's phone number
	 */
	public static String findSellerPhone(String username) {
		//Declare DB objects and local variables
		DBConnect conn = new DBConnect();
		Statement stmt = null;
		String sellerPhone = "";
			
		try {
			// retrieve phone number by username from database
			stmt = conn.connect().createStatement();
			String sql = "SELECT phone FROM jyang_sellers_tab WHERE username = '" + username + "'";
			ResultSet rs = stmt.executeQuery(sql);	
			
			// get phone number
			if (rs.next()) {
				sellerPhone = rs.getString(1);
			}
			
			conn.connect().close();
		} catch (SQLException se) {
			// print error message
			se.printStackTrace();
	    }
		
		// return phone number
		return sellerPhone;
	}
	
	/**
	 * Method to add product to customer's wish list by product.
	 * @param product to add
	 */
	public static void addToWishList(Product product) {
		//Declare DB objects 
		DBConnect conn = new DBConnect();
		Statement stmt = null;
		
		try {
			// add product to wish list 
			System.out.println("Inserting product into " + currentUsername + "_wish_tab");
			stmt = conn.connect().createStatement();
			String sql = "INSERT INTO " + currentUsername + "_wish_tab (pid, username, product, oldNew, price, description) VALUES ('" + product.getPid() + "', '" + product.getUsername() + "', '" + product.getName() + "', '" + product.getCondition() + "', '" + product.getPrice() + "', '" + product.getDescription() + "')";	
			stmt.executeUpdate(sql);
			conn.connect().close();
			System.out.println("Finish inserting product into " + currentUsername + "_wish_tab");
			System.out.println("");
		} catch (SQLException se) {
			// print error message
			se.printStackTrace();
	    }
	}
	
	/**
	 * Method to delete product from customer's wish list by pid.
	 * @param product's pid 
	 */
	public static void deleteFromWishList(int pid) {
		//Declare DB objects 
		DBConnect conn = new DBConnect();
		Statement stmt = null;
			
		try {
			// delete product from wish list
			stmt = conn.connect().createStatement();
			System.out.println("Deleting product from " + currentUsername + "_wish_tab");
			stmt = conn.connect().createStatement();
			String sql = "DELETE FROM " + currentUsername + "_wish_tab WHERE pid=" + pid;	
			stmt.executeUpdate(sql);
			conn.connect().close();
			System.out.println("Finish deleting product from " + currentUsername + "_wish_tab");
			System.out.println("");
		} catch (SQLException se) {
			// print error message
			se.printStackTrace();
	    }
	}
}
