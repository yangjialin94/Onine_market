package controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.Customer;
import models.Main;

/**
 * @author Jialin Yang
 * @version 2.1
 * @since 2018-04-09
 * @Lab final-project
 */

/**
 * This class is a controller class for connecting all functions in CustomerLogIn.fxml with methods in 
 * classes within models package.
 */

public class CustomerLogInController {
	
	// instance field
	public Customer customerObj = new Customer();
		
	// references in CustomerLogIn.fxml
	@FXML TextField username;
	@FXML PasswordField password;
	@FXML Label error;
	
	// instance variable to count for wrong login tries 
	int count = 0;
	
	/**
	 * Method to back to main page when "Back" button clicked.
	 */
	@FXML
	private void back() throws IOException {
		Main.showMainScene();
	}
	
	/**
	 * Method to go to customer register page when "Register" button clicked.
	 */
	@FXML
	private void register() throws IOException {
		Main.showCustomerRegisterScene();
	}
	
	/**
	 * Method to check for data and log into customer page when "Log In" button clicked.
	 */
	@FXML
	private void logIn() throws IOException {
		// get input from textfields
		String getUsername = username.getText();
		String getPassword = password.getText();
		
		// print data to console 
		System.out.println("Username: " + getUsername);
		System.out.println("Password: " + getPassword);
		System.out.println("");
		
		// check if username of password is empty
		if (getUsername.isEmpty() || getPassword.isEmpty()) {
			error.setText("Error: Username or Password is empty!");
			emptyTextFields();
		} else if (!customerObj.logInIsValid(getUsername, getPassword)) {
			// check if username and password in correct
			error.setText("Error: Wrong Username or Password!");
			emptyTextFields();
			// count added for wrong login tries
			count++;
			
			// exit if login failure for >= 3 times
			if (count >= 3) {
				Main.exit();
			}
		} else {
			// go to customer page
			Main.showCustomerScene();
		}
	}
	
	/**
	 * Method to empty all textfields on customer login page.
	 */
	public void emptyTextFields() {
		username.setText("");
		password.setText("");
	}
}
