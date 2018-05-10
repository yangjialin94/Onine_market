package controllers;

import java.io.IOException;
import javax.xml.bind.DatatypeConverter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
 * This class is a controller class for connecting all functions in CustomerRegister.fxml 
 * with methods in classes within models package.
 */

public class CustomerRegisterController {
	
	// instance field
	public Customer customerObj = new Customer();
		
	// references in CustomerRegister.fxml
	@FXML private TextField email, phone, username, password, rePassword;
	@FXML private Label error;

	/**
	 * Method to back to customer login page when "Back" button clicked.
	 */
	@FXML
	private void back() throws IOException {
		Main.showCustomerLogInScene();
	}
	
	/**
	 * Method to register new customer when "Register" button clicked.
	 */
	@FXML
	private void register() throws IOException {
		// get input from textfields
		String newEmail = email.getText();
		String newPhone= phone.getText();
		String newUsername = username.getText();
		String newPassword = password.getText();
		String newRePassword = rePassword.getText();
		
		// print data to console 
		System.out.println("Email: " + newEmail);
		System.out.println("Phone: " + newPhone);
		System.out.println("Username: " + newUsername);
		System.out.println("Password: " + newPassword);
		System.out.println("rePassword: " + newRePassword);
		System.out.println();
		
		// check if there's anything empty
		if (newEmail.trim().isEmpty() || newPhone.trim().isEmpty() || newUsername.trim().isEmpty() || newPassword.trim().isEmpty() || newRePassword.trim().isEmpty()) {
			error.setText("Error: Something is missing!");
			emptyTextFields();
		} else if (!newEmail.contains("@")) {
			// check for email format
			error.setText("Error: Email is not correct!");
			emptyTextFields();
		} else if (!newPhone.matches("[0-9]+")) {
			// check for phone number format
			error.setText("Error: Phone Number not correct!");
			emptyTextFields();
		} else if (newEmail.indexOf("'") != -1 || newUsername.indexOf("'") != -1) {
			// check for invalid input for email and username (')
			error.setText("Error: Email or Username is invalid!");
			emptyTextFields();
		} else if (!newPassword.trim().equals(newRePassword.trim())) {
			// check if password and repassword match
			error.setText("Error: Passwords not match!");
			emptyTextFields();
		} else if (customerObj.userExist(newUsername)) {
			// check if username already exist customer list
			error.setText("Error: Username already exist!");
			emptyTextFields();
		} else {
			// insert customer info into jyang_customer_tab
			String encodedPassword = DatatypeConverter.printBase64Binary(newPassword.getBytes());
			Customer newCustomer = new Customer(newEmail, newPhone, newUsername, encodedPassword);
			Customer.addToTable(newCustomer);
			
			// back to customer login page
			Main.showCustomerLogInScene();
		}
	}
	
	/**
	 * Method to empty all textfields on customer register page.
	 */
	public void emptyTextFields() {
		email.setText("");
		phone.setText("");
		username.setText("");
		password.setText("");
		rePassword.setText("");
	}
}
