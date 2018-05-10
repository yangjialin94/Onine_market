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
 * This class have functions to check if seller exist in seller list by username, add new seller 
 * to seller list and add seller's sell list to database, check if username and password in correct 
 * for login by username and password, add product to product list and sell list, delete product 
 * from product list and sell list by pid.
 */

public class Seller extends User implements Post {
	
	// instance field
	private String email;
	private String phone;
	public String username;
	private String password;
	public static String currentUsername;
	
	// constructors
	/**
	 * empty Seller constructor.
	 */
	public Seller() {}

	/**
	 * Seller constructor.
	 */
	public Seller(String email, String phone, String username, String password) {
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
	 * Method to check if seller exist in seller list by username.
	 * @param username to check
	 * @return true/false for existence
	 */
	@Override
	public boolean userExist(String username) {
		// Declare DB objects and local variables
		DBConnect conn = new DBConnect();
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<String> usernames = new ArrayList<>();
			
		try {
			// get existed usernames from jyang_sellers_tab
			stmt = conn.connect().createStatement();
			String sql = "SELECT username FROM jyang_sellers_tab";
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
	 * Method to add new seller to seller list and add seller's sell list to database.
	 * @param seller to add
	 */
	public static void addToTable(Seller seller) {
		//Declare DB objects and local variables
		DBConnect conn = new DBConnect();
		Statement stmt = null;
		String sql;
		
		try {
			// insert seller into jyang_customer_tab
			System.out.println("Inserting seller into jyang_sellers_tab");
			stmt = conn.connect().createStatement();
			sql = "INSERT INTO jyang_sellers_tab (email, phone, username, password) VALUES ('" + seller.email + "', '" + seller.phone + "', '" + seller.username + "', '" + seller.password + "')";	
			stmt.executeUpdate(sql);
			System.out.println("Finish inserting seller into jyang_sellers_tab");
			System.out.println("");
			
			// create username_sell_tab
			String sellTab = seller.username + "_sell_tab";
			System.out.println("Start to Create " + sellTab);
			stmt = conn.connect().createStatement();
			sql = "CREATE TABLE " + sellTab + " (pid INTEGER, " + " username VARCHAR(30), " + " product VARCHAR(30), " + " oldNew VARCHAR(30), " + " price DOUBLE, " + " description VARCHAR(300), " + " PRIMARY KEY (pid))";
			stmt.executeUpdate(sql);
			conn.connect().close();
			System.out.println("Created " + sellTab);
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
	@Override
	public boolean logInIsValid(String username, String password) {
		// check if username exist in seller list
		if (!userExist(username)) {
			System.out.println("User does not exsit!");
			return false;
		} else {
			//Declare DB objects and local variables
			DBConnect conn = new DBConnect();
			Statement stmt = null;
			String sql;
				
			try {
				// retrieve password for username from database
				stmt = conn.connect().createStatement();
				sql = "SELECT password FROM jyang_sellers_tab WHERE username = '" + username + "'";
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
	 * Method to add product to product list and sell list.
	 * @param product to add
	 */
	public void postNewProduct(Product product) {
		//Declare DB objects and local variables
		DBConnect conn = new DBConnect();
		Statement stmt = null;
		String sql;
		
		try {
			// insert product info into jyang_product_tab
			System.out.println("Inserting info into jyang_product_tab");
			stmt = conn.connect().createStatement();
			sql = "INSERT INTO jyang_product_tab (pid, username, product, oldNew, price, description) VALUES ('" + product.getPid() + "', '" + product.getUsername() + "', '" + product.getName() + "', '" + product.getCondition() + "', '" + product.getPrice() + "', '" + product.getDescription() + "')";	
			stmt.executeUpdate(sql);
			System.out.println("Finish inserting info into jyang_product_tab");
			System.out.println("");
			
			// insert product info into username_sell_tab
			String tabName = product.getUsername() + "_sell_tab";
			System.out.println("Inserting info into " + tabName);
			stmt = conn.connect().createStatement();
			sql = "INSERT INTO " + tabName + " (pid, username, product, oldNew, price, description) VALUES ('" + product.getPid() + "', '" + product.getUsername() + "', '" + product.getName() + "', '" + product.getCondition() + "', '" + product.getPrice() + "', '" + product.getDescription() + "')";	
			stmt.executeUpdate(sql);
			conn.connect().close();
			System.out.println("Finish inserting info into " + tabName);
			System.out.println("");
		} catch (SQLException se) {
			// print error message
			se.printStackTrace();
	    }
	}
	
	/**
	 * Method to delete product from product list and sell list by pid.
	 * @param product's pid
	 */
	public static void delete(int pid) {
		//Declare DB objects and local variables
		DBConnect conn = new DBConnect();
		Statement stmt = null;
		String sql;
			
		try {
			// delete product from jyang_product_tab
			System.out.println("Deleting product from jyang_product_tab");
			stmt = conn.connect().createStatement();
			sql = "DELETE FROM jyang_product_tab WHERE pid=" + pid;	
			stmt.executeUpdate(sql);
			System.out.println("Finish deleting product from jyang_product_tab");
			System.out.println("");
			
			// delete product from username_sell_tab
			String tabName = currentUsername + "_sell_tab";
			System.out.println("Deleting product from " + tabName);
			stmt = conn.connect().createStatement();
			sql = "DELETE FROM " + tabName + " WHERE pid=" + pid;
			stmt.executeUpdate(sql);
			conn.connect().close();
			System.out.println("Finish deleting product from " + tabName);
			System.out.println("");
		} catch (SQLException se) {
			// print error message
			se.printStackTrace();
	    }
	}
}
