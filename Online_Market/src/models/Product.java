package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Jialin Yang
 * @version 2.1
 * @since 2018-04-09
 * @Lab final-project
 */

/**
 * This class have functions to check if product already exist in seller's sell list by name,
 * check if product with pid exist in seller's sell list, check if product with pid exist in 
 * product list, check if product with pid exist in customer's wish list, get pid for new product,
 * get product from product list by pid.
 */

public class Product {
	
	// instance field
	private int pid;
	private String username;
	private String name;
	private String condition;
	private double price;
	private String description;
	
	// constructors
	/**
	 * Product constructor
	 */
	public Product(int pid, String username, String name, String condition, double price, String description) {
		this.pid = pid;
		this.username = username;
		this.name = name;
		this.condition = condition;
		this.price = price;
		this.description = description;
	}

	// getters
	/**
	 * @return the pid to get
	 */
	public int getPid() {
		return this.pid;
	}
	
	/**
	 * @return the username to get
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * @return the name to get
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return the condition to get
	 */
	public String getCondition() {
		return this.condition;
	}
	
	/**
	 * @return the price to get
	 */
	public double getPrice() {
		return this.price;
	}

	/**
	 * @return the description to get
	 */
	public String getDescription() {
		return this.description;
	}

	// setters
	/**
	 * @param the pid to set
	 */
	public void setPid(int pid) {
		this.pid = pid;
	}
	
	/**
	 * @param the username to set
	 */
	public void setUserName(String username) {
		this.username = username;
	}
	
	/**
	 * @param the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param the condition to set
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * @param the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @param the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Method to check if product already exist in seller's sell list by name.
	 * @param productName to check
	 * @return true/false for existence
	 */
	public static boolean productExist(String productName) {
		//Declare DB objects and local variables
		DBConnect conn = new DBConnect();
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<String> products = new ArrayList<>();
			
		try {
			// retrieve name from database
			stmt = conn.connect().createStatement();
			String sql = "SELECT product FROM " + Seller.currentUsername + "_sell_tab";
			rs = stmt.executeQuery(sql);
			conn.connect().close();
			
			// get name
			while (rs.next()) {
				products.add(rs.getString(1));
			}
		} catch (SQLException se) {
			// print error message
			se.printStackTrace();
	    }
		
		// check if productName is in products arraylist
		if (products.contains(productName)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Method to check if product with pid exist in seller's sell list.
	 * @param product's pid to check
	 * @return true/false for existence
	 */
	public static boolean pidExist(int pid) {
		//Declare DB objects and local variables
		DBConnect conn = new DBConnect();
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Integer> pids = new ArrayList<>();
			
		try {
			// retrieve pid from database
			stmt = conn.connect().createStatement();
			String sql = "SELECT pid FROM " + Seller.currentUsername + "_sell_tab";
			rs = stmt.executeQuery(sql);
			conn.connect().close();
			
			// add pid to arraylist
			while (rs.next()) {
				pids.add(rs.getInt(1));
			}
		} catch (SQLException se) {
			// print error message
			se.printStackTrace();
	    }
		
		// check if pid is in pids arraylist
		if (pids.contains(pid)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Method to check if product with pid exist in product list.
	 * @param product's pid to check
	 * @return true/false for existence
	 */
	public static boolean pidExistProductTable(int pid) {
		//Declare DB objects and local variables
		DBConnect conn = new DBConnect();
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Integer> pids = new ArrayList<>();
			
		try {
			// retrieve pid from database
			stmt = conn.connect().createStatement();
			String sql = "SELECT pid FROM jyang_product_tab";
			rs = stmt.executeQuery(sql);
			conn.connect().close();
			
			// add pid to arraylist
			while (rs.next()) {
				pids.add(rs.getInt(1));
			}
		} catch (SQLException se) {
			// print error message
			se.printStackTrace();
	    }
		
		// check if pid is in pids arraylist
		if (pids.contains(pid)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Method to check if product with pid exist in customer's wish list.
	 * @param product's pid to check
	 * @return true/false for existence
	 */
	public static boolean pidExistWishTable(int pid) {
		//Declare DB objects and local variables
		DBConnect conn = new DBConnect();
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Integer> pids = new ArrayList<>();
			
		try {
			// retrieve pid from database
			stmt = conn.connect().createStatement();
			String sql = "SELECT pid FROM " + Customer.currentUsername + "_wish_tab";
			rs = stmt.executeQuery(sql);
			conn.connect().close();
			
			// add pid to arraylist
			while (rs.next()) {
				pids.add(rs.getInt(1));
			}
		} catch (SQLException se) {
			// print error message
			se.printStackTrace();
	    }
		
		// check if pid is in pids arraylist
		if (pids.contains(pid)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Method to get pid for new product.
	 * @return new pid
	 */
	public static int getNewPid() {
		//Declare DB objects and local variables
		DBConnect conn = new DBConnect();
		Statement stmt = null;
		ResultSet rs = null;
		int newPid = 0;
			
		try {
			// retrieve pid from database
			stmt = conn.connect().createStatement();
			String sql = "SELECT pid FROM jyang_product_tab ORDER BY pid DESC";
			rs = stmt.executeQuery(sql);
			conn.connect().close();
			
			// get pid
			if (rs.next()) {
				newPid = rs.getInt(1) + 1;
			}
		} catch (SQLException se) {
			// print error message
			se.printStackTrace();
	    }
		
		// return new pid
		return newPid;
	}
	
	/**
	 * Method to get product from product list by pid.
	 * @param product's pid
	 * @return product got
	 */
	public static Product getProductByPid(int pid) {
		//Declare DB objects and local variables
		DBConnect conn = new DBConnect();
		Statement stmt = null;
		ResultSet rs = null;
		String username = "";
		String name = "";
		String condition = "";
		double price = 0;
		String description = "";
		Product getProduct = new Product(pid, username, name, condition, price, description);
			
		try {
			// retrieve product from database
			stmt = conn.connect().createStatement();
			String sql = "SELECT * FROM jyang_product_tab WHERE pid=" + pid;
			rs = stmt.executeQuery(sql);
			conn.connect().close();
			
			// set values to new product
			if (rs.next()) {
				getProduct.setPid(rs.getInt(1));
				getProduct.setUserName(rs.getString(2));
				getProduct.setName(rs.getString(3).replace("\'", "\'\'"));
				getProduct.setCondition(rs.getString(4).replace("\'", "\'\'"));
				getProduct.setPrice(rs.getDouble(5));
				getProduct.setDescription(rs.getString(6).replace("\'", "\'\'"));
			}
		} catch (SQLException se) {
			// print error message
			se.printStackTrace();
	    }
		
		// return product
		return getProduct;
	}
}
