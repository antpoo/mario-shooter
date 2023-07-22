package com.example;

import java.io.IOException;

import com.example.IOutil.FReader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ForgetScreenController {

    // stage field
    private Stage stage;
    
    /**
     * Sets the stage
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // FXML text field for username
    @FXML
    TextField usernameTF;


    /**
     * Goes back to log in screen
     * @param e Action event
     * @throws IOException
     */
    @FXML
    private void returnPressed(ActionEvent e) throws IOException {
        // go back to log in screen
        App.login(stage);
    }

    /**
     * Submits the username
     * @param e Action event
     * @throws IOException
     */
    @FXML
    private void submitPressed(ActionEvent e) throws IOException {
        // file reader
        FReader fr = new FReader("src/main/java/com/example/databases/userdata.txt");
        
        // get the username from the text field
        String username = usernameTF.getText();
        
        // if username doesn't exist
        if (fr.findUser(username) == -1) {
            Alerter.alert("Username Issue", "No such username");
        }
        // if username does exist, give them the hint 
        else { 
            Alerter.alert("Your hint", fr.getHint(username));
        }
    }
    
}
