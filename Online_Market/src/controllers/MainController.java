package controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import models.Main;

/**
 * @author Jialin Yang
 * @version 2.1
 * @since 2018-04-09
 * @Lab final-project
 */

/**
 * This class is a controller class for connecting all functions in Main.fxml with methods
 * in classes within models package.
 */

public class MainController {
	
	/**
	 * Method to go to customer login page when "Back" button clicked.
	 */
	@FXML
	private void customer() throws IOException {
		Main.showCustomerLogInScene();
	}
	
	/**
	 * Method to go to seller login page when "Back" button clicked.
	 */
	@FXML
	private void seller() throws IOException {
		Main.showSellerLogInScene();
	}
}
