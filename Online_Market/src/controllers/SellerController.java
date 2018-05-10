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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
 * This class is a controller class for connecting all functions in Seller.fxml with methods in 
 * classes within models package.
 */

public class SellerController {
	
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
	@FXML private TableView<Product> sellTable;
	@FXML private TableColumn<Product, Integer> sellPidCol;
	@FXML private TableColumn<Product, String> sellUsernameCol;
	@FXML private TableColumn<Product, String> sellProductCol;
	@FXML private TableColumn<Product, String> sellConditionCol;
	@FXML private TableColumn<Product, Double> sellPriceCol;
	@FXML private TableColumn<Product, String> sellDescriptionCol;
	
	// references in Post New Product page
	@FXML private TextField postName;
	@FXML private TextField postCondition;
	@FXML private TextField postPrice;
	@FXML private TextArea postDescription;
	@FXML private Label message;
	
	// references in Delete Product page
	@FXML private TextField deletePid;
	@FXML private Label deleteMessage;
	
	/**
	 * Method to load data when Seller.fxml get loaded.
	 */
	@FXML
    private void initialize() {
		// set username in Main page
		username.setText("Hi " + Seller.currentUsername + "!");	

		// set values in Product Table and load product table
		productPidCol.setCellValueFactory(new PropertyValueFactory<>("pid"));
		productUsernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
		productProductCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		productConditionCol.setCellValueFactory(new PropertyValueFactory<>("condition"));
		productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		productDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		loadProductTable();
		
		// set values in Sell Table and load with table
		sellPidCol.setCellValueFactory(new PropertyValueFactory<>("pid"));
		sellUsernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
		sellProductCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		sellConditionCol.setCellValueFactory(new PropertyValueFactory<>("condition"));
		sellPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		sellDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		loadSellTable();
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
	 * Method to load sell list.
	 */
	public void loadSellTable() {
		//Declare DB objects  and local variables
		DBConnect conn = new DBConnect();
		Statement stmt = null;
		ResultSet rs = null;
		ObservableList<Product> sellProducts = FXCollections.observableArrayList();
			
		try {
			// retrieve data from seller's sell table
			stmt = conn.connect().createStatement();
			String sql = "SELECT * FROM " + Seller.currentUsername + "_sell_tab";
			rs = stmt.executeQuery(sql);
			
			// load data into wishProducts observableList
			while (rs.next()) {
				sellProducts.add(new Product(rs.getInt("pid"), rs.getString("username"), rs.getString("product"), rs.getString("oldNew"), rs.getDouble("price"), rs.getString("description")));
			}
			
			conn.connect().close();
		} catch (SQLException se) {
			// print error message
			se.printStackTrace();
	    }
		
		// load product into sell list table view
		sellTable.setItems(sellProducts);
	}
	
	/**
	 * Method to reset everything in Seller.fxml.
	 */
	public void reload() {
		// reset Product list page
		loadProductTable();
		
		// reset Sell list page
		loadSellTable();
		
		// reset Post Product page
		message.setText("");
		postName.setText("");
		postCondition.setText("");
		postPrice.setText("");
		postDescription.setText("");
		
		// reset Delete Product page
		deletePid.setText("");
		deleteMessage.setText("");
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
		Main.showSellerLogInScene();
	}
	
	/**
	 * Method to quit application when "Exit" button clicked.
	 */
	@FXML
	private void exit() throws IOException {
		Main.exit();
	}

	/**
	 * Method to add product to product list and sell list when "Save" button clicked.
	 */
	@FXML
	private void post() throws IOException {
		// check if price is type double
		try {  
			Double.parseDouble(postPrice.getText());  
		} catch (NumberFormatException e) {  
			// print error message
			message.setText("Error: Price should be a valid number!");
		    return;
		}
		
		// get values form textfields
		String productUsername = Seller.currentUsername;
		String productName = postName.getText();
		String productCondition = postCondition.getText();
		double productPrice = Double.parseDouble(postPrice.getText());
		String productDescription = postDescription.getText();
		
		// print to console
		System.out.println("Username: " + productUsername);
		System.out.println("Product: " + productName);
		System.out.println("Condition: " + productCondition);
		System.out.println("Price: " + productPrice);
		System.out.println("Description: " + productDescription);
		System.out.println("");
		
		// correct ' to '' in productName, productCondition, productDescription for sql insert syntax
		if (productName.indexOf("'") != -1) {
			productName = productName.replace("\'", "\'\'");
		}
		
		// correct ' to '' in productCondition for sql insert syntax	
		if (productCondition.indexOf("'") != -1) {
			productCondition = productCondition.replace("\'", "\'\'");
		}
		
		// correct ' to '' in productDescription for sql insert syntax
		if (productDescription.indexOf("'") != -1) {
			productDescription = productDescription.replace("\'", "\'\'");
		} 
		
		// print to console
		System.out.println("Username: " + productUsername);
		System.out.println("Product: " + productName);
		System.out.println("Condition: " + productCondition);
		System.out.println("Price: " + productPrice);
		System.out.println("Description: " + productDescription);
		System.out.println("");
		
		// check if anything is missing
		if (productName.trim().isEmpty() || productCondition.trim().isEmpty() || productDescription.trim().isEmpty()) {
			message.setText("Error: Something is missing!");
		} else if (Product.productExist(productName)) {
			// check if product is already exist in product list by name
			message.setText("Error: Product name already exist!");
			postName.setText("");
		} else {
			// add new product
			Product newProduct = new Product(Product.getNewPid(), productUsername, productName, productCondition, productPrice, productDescription);
			Seller seller = new Seller();
			seller.postNewProduct(newProduct);
			reload();
			message.setText("Product " + productName.replace("\'\'", "\'") + " Added!");
			System.out.println("");
		}
	}
	
	/**
	 * Method to delete a product from product list and sell list.
	 */
	@FXML
	private void delete() throws IOException {
		//  get pid from textfield
		String checkPid = deletePid.getText();
		
		// check format of pid
		if (!checkPid.matches("[0-9]*")) {
			deleteMessage.setText("Error: Pid shoud be an Integer!");
		} else {
			// pase pid into integer
			int getPid = Integer.parseInt(checkPid);
		
			// check if pid exist in product list
			if (!Product.pidExist(getPid)) {
				deleteMessage.setText("Error: Pid does not exist in your sell list!");
				deletePid.setText("");
			} else {
				// delete product from product and sell table
				Seller.delete(getPid);
				reload();
				deleteMessage.setText("Product with pid(" + getPid + ") Deleted!");
			}
		}
	}
}
