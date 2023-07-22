package com.example;

import java.io.IOException;

import com.example.IOutil.FReader;
import com.example.IOutil.FWriter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignupScreenController {

    private Stage stage;

    /**
     * sets the stage
     * @param stage stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // FXML fields
    
    @FXML private TextField usernameTF;
    @FXML private TextField hintTF;

    @FXML private PasswordField passwordTF;
    @FXML private PasswordField passwordConfirmTF;

    @FXML private Button signupButton;
    @FXML private Button cancelButton;
    

    /**
     * If user wants to cancel
     * @param e Action event
     * @throws IOException
     */
    @FXML
    private void cancelPressed(ActionEvent e) throws IOException {
        App.login(stage);
    }

    /**
     * Checks conditions and signs user up
     * @param e Action event
     * @throws IOException
     */
    @FXML
    private void signupPressed(ActionEvent e) throws IOException {
        FReader fr = new FReader("src/main/java/com/example/databases/userdata.txt");
        FWriter fw = new FWriter("src/main/java/com/example/databases/userdata.txt");
        String username = usernameTF.getText();
        String password = passwordTF.getText();
        String passwordCF = passwordConfirmTF.getText();
        String hint = hintTF.getText();
        // if username is taken already
        if (fr.findUser(username) != -1) {
            Alerter.alert("Username Issue", "Username already taken. Please use another username.");
        }
        // if username is bad 
        else if (!checkUsername(username)) {
            Alerter.alert("Username Issue", "Username may only contain alphanumeric characters.");
        }
        // if passwords do not match
        else if (!password.equals(passwordCF)) {
            Alerter.alert("Password Issue", "Passwords do not match.");
        }
        // if passwords do not meet requirements 
        else if (!checkPassword(password)) {
            Alerter.alert("Password Issue", "Password must contain 6-10 characters, \nuppercase, lowercase, digit, and special character.");
        }
        // if everything is ok
        else {
            fw.hashAndWrite(username, password, hint);
            Alerter.alert("Signup success", "Created account " + username);
            App.login(stage);
        }
        
    }



    /**
     * @param username Username to check
     * @return true if username is valid, false if not 
     */
    private boolean checkUsername(String username) {
        boolean good = true; 
        for (int i=0; i<username.length(); i++) {
            char cur = username.charAt(i);
            if (!(cur >= 'A' && cur <= 'Z' || 
            cur >= 'a' && cur <= 'z' || 
            cur >= '0' && cur <= '9')) good = false;
        }
        return good;
    }

 
    /**
     * @param password Password to check
     * @return true if password is valid, false if not 
     */
    private boolean checkPassword(String password) {

        // password must have 6-10 chars, uppercase, lowercase, digit, and special character

        boolean length = password.length() >= 6 && password.length() <= 10;
        boolean uppercase = false, lowercase = false, digit = false, specialChar = false;
        for (int i=0; i<password.length(); i++) {
            char cur = password.charAt(i);
            if (cur >= 'A' && cur <= 'Z') uppercase = true;
            else if (cur >= 'a' && cur <= 'z') lowercase = true;
            else if (cur >= '0' && cur <= '9') digit = true;
            else specialChar = true;
        }

        return length && uppercase && lowercase && digit && specialChar;
    }

}
