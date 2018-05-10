package models;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author Jialin Yang
 * @version 2.1
 * @since 2018-04-09
 * @Lab final-project
 */

/**
 * This class have functions to start the stage when Main.java load, create jyang_customers_tab 
 * and jyang_sellers_tab, create product table in database, show Main scene, show Customer Login 
 * scene, show Seller Login scene, show Customer Register scene, show Seller Register scene, show 
 * Customer scene, Show Seller scene, exit application, and  main method.
 */

public class Main extends Application {
	
	// instance field
	private static Stage primaryStage;
	
	/**
	 * Method to start the stage when Main.java load.
	 * @param username to check
	 */
	@Override
	public void start(Stage primaryStage) throws IOException, SQLException {
		// set main stage and show Main.fxml
		Main.primaryStage = primaryStage;
		Main.primaryStage.setTitle("ITMD 510 Final Project");
		showMainScene();
		
//		// create user and product tables
//		createUserTable();
//		createProductTable();
	}
	
	/**
	 * Method to create jyang_customers_tab and jyang_sellers_tab.
	 */
	public void createUserTable() {
		//Declare DB objects and local variables
		DBConnect conn = new DBConnect();
		Statement stmt = null;
		String sql;
		
		try {
			// create jyang_customers_tab
			System.out.println("Start to Create jyang_customers_tab");
			stmt = conn.connect().createStatement();
			sql = "CREATE TABLE jyang_customers_tab " + "(email VARCHAR(50), " + " phone VARCHAR(20), " + " username VARCHAR(30) not NULL, " + " password VARCHAR(30) not NULL, " + " PRIMARY KEY (username))";
			stmt.executeUpdate(sql);
			System.out.println("Created jyang_customers_tab");
			System.out.println("");
			
			// create jyang_sellers_tab
			System.out.println("Start to Create jyang_sellers_tab");
			stmt = conn.connect().createStatement();
			sql = "CREATE TABLE jyang_sellers_tab " + "(email VARCHAR(50), " + " phone VARCHAR(20), " + " username VARCHAR(30) not NULL, " + " password VARCHAR(30) not NULL, " + " PRIMARY KEY (username))";
			stmt.executeUpdate(sql);
			conn.connect().close(); //close db connection 
			System.out.println("Created jyang_sellers_tab");
			System.out.println("");
		} catch (SQLException se) {
			// print error message
			se.printStackTrace();
		}
	}
	
	/**
	 * Method to create product table in database.
	 */
	public void createProductTable() {
		//Declare DB objects 
		DBConnect conn = new DBConnect();
		Statement stmt = null;
		
		try {
			// create product table
			System.out.println("Start to Create jyang_product_tab");
			stmt = conn.connect().createStatement();
			String sql = "CREATE TABLE jyang_product_tab " + "(pid INTEGER, " + " username VARCHAR(30), " + " product VARCHAR(30), " + " oldNew VARCHAR(30), " + " price DOUBLE, " + " description VARCHAR(300), " + " PRIMARY KEY (pid))";
			stmt.executeUpdate(sql);
			conn.connect().close(); 
			System.out.println("Created jyang_product_tab");
			System.out.println("");
		} catch (SQLException se) {
			// print error message
			se.printStackTrace();
		}
	}
	
	/**
	 * Method to show Main scene.
	 */
	public static void showMainScene() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("views/Main.fxml"));
		AnchorPane anchorPane = loader.load();
		Scene scene = new Scene(anchorPane);
		scene.getStylesheets().add("styles/Styles.css");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * Method to show Customer Login scene.
	 */
	public static void showCustomerLogInScene() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("views/CustomerLogIn.fxml"));
		AnchorPane anchorPane = loader.load();
		Scene scene = new Scene(anchorPane);
		scene.getStylesheets().add("styles/Styles.css");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * Method to show Seller Login scene.
	 */
	public static void showSellerLogInScene() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("views/SellerLogIn.fxml"));
		AnchorPane anchorPane = loader.load();
		Scene scene = new Scene(anchorPane);
		scene.getStylesheets().add("styles/Styles.css");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * Method to show Customer Register scene.
	 */
	public static void showCustomerRegisterScene() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("views/CustomerRegister.fxml"));
		AnchorPane anchorPane = loader.load();
		Scene scene = new Scene(anchorPane);
		scene.getStylesheets().add("styles/Styles.css");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * Method to show Seller Register scene.
	 */
	public static void showSellerRegisterScene() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("views/SellerRegister.fxml"));
		AnchorPane anchorPane = loader.load();
		Scene scene = new Scene(anchorPane);
		scene.getStylesheets().add("styles/Styles.css");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * Method to show Customer scene.
	 */
	public static void showCustomerScene() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("views/Customer.fxml"));
		AnchorPane anchorPane = loader.load();
		Scene scene = new Scene(anchorPane);
		scene.getStylesheets().add("styles/Styles.css");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * Method to show Seller scene.
	 */
	public static void showSellerScene() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("views/Seller.fxml"));
		AnchorPane anchorPane = loader.load();
		Scene scene = new Scene(anchorPane);
		scene.getStylesheets().add("styles/Styles.css");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * Method to exit application.
	 */
	public static void exit() {
		Platform.exit();
	}

	/**
	 * Main method.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
