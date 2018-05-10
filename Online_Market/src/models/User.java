package models;

/**
 * @author Jialin Yang
 * @version 2.1
 * @since 2018-04-09
 * @Lab final-project
 */

/**
 * Abstract class to be extend by Customer and Seller class.
 */

public abstract class User {
	
	/**
	 * Method to check if user exist.
	 * @param username to check
	 * @return true/false for existence
	 */
	public abstract boolean userExist(String username);
	
	/**
	 * Method to check if username and password in correct for login by username and password.
	 * @param username and password to check
	 * @return true/false for correctness
	 */
	public abstract boolean logInIsValid(String username, String password);
}
