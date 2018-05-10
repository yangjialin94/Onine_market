package controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Customer;
import models.DBConnect;
import models.Main;
import models.Product;
import models.Seller;

/**
 * @author Jialin Yang
 * @version 2.1
 * @since 2018-04-09
 * @Lab final-project
 */

/**
 * This class is a controller class for connecting all functions in Customer.fxml with methods in 
 * classes within models package.
 */

public class CustomerController {
	
	// instance field
	public Seller sellerObj = new Seller();
	
	// references in Main page
	@FXML private Label username;
	
	// references in Product List page
	@FXML private TableView<Product> productTable;
	@FXML private TableColumn<Product, Integer> productPidCol;
	@FXML private TableColumn<Product, String> productUsernameCol;
	@FXML private TableColumn<Product, String> productProductCol;
	@FXML private TableColumn<Product, String> productConditionCol;
	@FXML private TableColumn<Product, Double> productPriceCol;
	@FXML private TableColumn<Product, String> productDescriptionCol;
	
	// references in Wish List page
	@FXML private TableView<Product> wishTable;
	@FXML private TableColumn<Product, Integer> wishPidCol;
	@FXML private TableColumn<Product, String> wishUsernameCol;
	@FXML private TableColumn<Product, String> wishProductCol;
	@FXML private TableColumn<Product, String> wishConditionCol;
	@FXML private TableColumn<Product, Double> wishPriceCol;
	@FXML private TableColumn<Product, String> wishDescriptionCol;
	
	// references in Save Product page
	@FXML private TextField savePid;
	@FXML private Label saveMessage;
	
	// references in Forget Product page
	@FXML private TextField forgetPid;
	@FXML private Label forgetMessage;
	
	// references in Find Seller page
	@FXML private TextField findUsername;
	@FXML private Label findEmail;
	@FXML private Label findPhone;
	@FXML private Label findMessage;
	
	/**
	 * Method to load data when Customer.fxml get loaded.
	 */
	@FXML
    private void initialize() {
		// set username in Main page
		username.setText("Hi " + Customer.currentUsername + "!");	
		
		// set values in Product Table and load product table
		productPidCol.setCellValueFactory(new PropertyValueFactory<>("pid"));
		productUsernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
		productProductCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		productConditionCol.setCellValueFactory(new PropertyValueFactory<>("condition"));
		productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		productDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		loadProductTable();
		
		// set values in Wish Table and load with table
		wishPidCol.setCellValueFactory(new PropertyValueFactory<>("pid"));
		wishUsernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
		wishProductCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		wishConditionCol.setCellValueFactory(new PropertyValueFactory<>("condition"));
		wishPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		wishDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		loadWishTable();
    }
	
	/**
	 * Method to load product list.
	 */
	public void loadProductTable() {
		//Declare DB objects  and local variables
		DBConnect conn = new DBConnect();
		Statement stmt = null;
		ResultSet rs = null;
		ObservableList<Product> products = FXCollections.observableArrayList();
			
		try {
			// retrieve data from jyang_product_tab
			stmt = conn.connect().createStatement();
			String sql = "SELECT * FROM jyang_product_tab";
			rs = stmt.executeQuery(sql);
			
			// load data into products observableList
			while (rs.next()) {
				products.add(new Product(rs.getInt("pid"), rs.getString("username"), rs.getString("product"), rs.getString("oldNew"), rs.getDouble("price"), rs.getString("description")));
			}
			
			conn.connect().close();
		} catch (SQLException se) {
			// print error message
			se.printStackTrace();
	    }
		
		// load product into products list table view
		productTable.setItems(products);
	}
	
	/**
	 * Method to load wish list.
	 */
	public void loadWishTable() {
		//Declare DB objects  and local variables
		DBConnect conn = new DBConnect();
		Statement stmt = null;
		ResultSet rs = null;
		ObservableList<Product> wishProducts = FXCollections.observableArrayList();
			
		try {
			// retrieve data from customer's wish table
			stmt = conn.connect().createStatement();
			String sql = "SELECT * FROM " + Customer.currentUsername + "_wish_tab";
			rs = stmt.executeQuery(sql);
			
			// load data into wishProducts observableList
			while (rs.next()) {
				wishProducts.add(new Product(rs.getInt("pid"), rs.getString("username"), rs.getString("product"), rs.getString("oldNew"), rs.getDouble("price"), rs.getString("description")));
			}
			
			conn.connect().close();
		} catch (SQLException se) {
			// print error message
			se.printStackTrace();
	    }
		
		// load product into wish list table view
		wishTable.setItems(wishProducts);
	}
	
	/**
	 * Method to reset everything in Customer.fxml.
	 */
	public void reload() {
		// reset Product list page
		loadProductTable();
		
		// reset Wish list page
		loadWishTable();
		
		// reset Save Product page
		savePid.setText("");
		saveMessage.setText("");
		
		// reset Forget Product page
		findUsername.setText("");
		findEmail.setText("");
		findPhone.setText("");
		findMessage.setText("");
		
		// reset Find Seller page
		forgetPid.setText("");
		forgetMessage.setText("");
	}
	
	/**
	 * Method to reset everything in Customer.fxml when "Refresh" button clicked.
	 */
	@FXML
	private void refresh() throws IOException {
		reload();
	}
	
	/**
	 * Method to back to customer login page when "Back" button clicked.
	 */
	@FXML
	private void back() throws IOException {
		Main.showCustomerLogInScene();
	}

	/**
	 * Method to quit application when "Exit" button clicked.
	 */
	@FXML
	private void exit() throws IOException {
		Main.exit();
	}
	
	/**
	 * Method to save product to customer's wish list when "Save" button clicked.
	 */
	@FXML
	private void save() throws IOException {
		// check if pid is type integer
		try {  
			Integer.parseInt(savePid.getText());
		} catch (NumberFormatException e) {  
			// print error message
			saveMessage.setText("Error: pid should be a valid number!");
		    return;
		}
		
		// get pid from textfield
		int getPid = Integer.parseInt(savePid.getText());
		
		// check if product exist in product list by pid
		if (!Product.pidExistProductTable(getPid)) {
			savePid.setText("");
			saveMessage.setText("Error: Pid(" + getPid + ") does not exist!");
		} else if (Product.pidExistWishTable(getPid)) {
			// check if product already been added to wish list before by pid
			savePid.setText("");
			saveMessage.setText("Product is already in your wish list!");
		} else {
			// add product to wish list by pid and relaod the page
			Customer.addToWishList(Product.getProductByPid(getPid));
			reload();
			saveMessage.setText("Product has been added to your wish list!");
		}
	}
	
	/**
	 * Method to take product out of customer's wish list when "Forget" button clicked.
	 */
	@FXML
	private void forget() throws IOException {
		// check if pid is type integer
		try {  
			Integer.parseInt(forgetPid.getText());
		} catch (NumberFormatException e) {
			// print error message
			forgetMessage.setText("Error: pid should be a valid number!");
		    return;
		}
				
		// get pid from textfield
		int getPid = Integer.parseInt(forgetPid.getText());
		
		// check if product is in wish list by pid
		if (!Product.pidExistWishTable(getPid)) {
			forgetMessage.setText("ERROR: Product does not exist in your wish list!");
		} else {
			// delete product from wish list
			Customer.deleteFromWishList(getPid);
			reload();
			forgetMessage.setText("Product had been forget from your wish list!");
		}
	}
	
	/**
	 * Method to get seller's email and phone number when "Find" button clicked.
	 */
	@FXML
	private void find() throws IOException {
		// get username from textfield
		String getUsername = findUsername.getText();
		
		// check if seller exist by username
		if (!sellerObj.userExist(getUsername)) {
			findEmail.setText("");
			findPhone.setText("");
			findMessage.setText("Error: User does not exist!");
		} else {
			// show seller's email and phone number on the page
			findEmail.setText(getUsername + "'s Email Address: " + Customer.findSellerEmail(getUsername));
			findPhone.setText(getUsername + "'s Phone Number: " + Customer.findSellerPhone(getUsername));
		}
	}
}
