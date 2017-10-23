package edu.colostate.cs.cs414.chesshireCoders.jungleClient.app;

import edu.colostate.cs.cs414.chesshireCoders.jungleClient.account.AccountHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {

	@FXML
	private Hyperlink hplRegister;
	
	@FXML
	private Button btnLogin;
	
	@FXML
	private Label loginErrorMsg;
	
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private TextField emailField;
	
	private AccountHandler accountHandler;
	
	public void loginClicked()
	{
		System.out.println("btnLogin Clicked.");
		
		System.out.println("Email: " + emailField.getText());
		System.out.println("Password: " + passwordField.getText());
		
		int userID = accountHandler.validateLogin(emailField.getText(), passwordField.getText());
		
		if ( userID > 0) {
			try {
				App.setScene("homePage.fxml");
			} catch (IOException e) {
				System.err.println("ERROR: Unable to load fxml file for Home page.");
			} 
		}
		else {
			loginErrorMsg.setVisible(true);
		}
	}
	
	public void registerClicked()
	{
		try {
			App.setScene("registerPage.fxml");
		} catch (IOException e) {
			System.err.println("ERROR: Unable to load fxml file for Register page.");
		}
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		accountHandler = new AccountHandler();
		App.window.setResizable(false);
	}

}
