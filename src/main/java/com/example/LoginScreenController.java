package com.example;

import java.io.File;
import java.io.IOException;

import com.example.IOutil.FReader;
import com.example.IOutil.FWriter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LoginScreenController {

    private Stage stage;

    /**
     * Sets the stage
     * @param stage stage to set 
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // fxml fields for the scene
    @FXML private TextField usernameTF;
    @FXML private PasswordField passwordTF;
    @FXML private Canvas logoCanvas;

    private GraphicsContext gc;

    /**
     * Drawing the logo
     */
    public void draw() {

        gc = logoCanvas.getGraphicsContext2D();

        // getting the images
        Image superMario = new Image(new File("src/main/java/com/example/images/supermario.png").toURI().toString());
        Image shooter = new Image(new File("src/main/java/com/example/images/shooter.png").toURI().toString());

        // drawing the images
        gc.drawImage(superMario, (logoCanvas.getWidth() - superMario.getWidth()) / 2, logoCanvas.getHeight() / 4);
        gc.drawImage(shooter, (logoCanvas.getWidth() - shooter.getWidth()) / 2, logoCanvas.getHeight() / 4 + superMario.getHeight() + 10);
    }

    /**
     * Method called when log in button is pressed
     * @param e
     * @throws IOException
     */
    @FXML
    private void loginPressed(ActionEvent e) throws IOException {
        // file tools
        FReader fr = new FReader("src/main/java/com/example/databases/userdata.txt");
        FWriter fw = new FWriter("src/main/java/com/example/databases/userdata.txt");

        // getting the username and password
        String username = usernameTF.getText();
        String password = passwordTF.getText();
        

        // if username does not exist 
        if (fr.findUser(username) == -1) {
            Alerter.alert("Username Issue", "No such username.");
        }
        // if password is incorrect
        else if (fw.polyHash(password) != fr.getPassword(username)) {
            Alerter.alert("Password Issue", "Incorrect password.");
        }
        // successful login
        else {
            App.startGame(stage, username);
        }
    }

    /**
     * Method called when sign up button is pressed.
     * @param e Action event 
     * @throws IOException
     */
    @FXML
    private void signupPressed(ActionEvent e) throws IOException {
        App.signup(stage);
    }

    /**
     * Method call when forgot password button is pressed
     * @param e Action event
     * @throws IOException
     */
    @FXML
    private void forgotPassPressed(ActionEvent e) throws IOException {
        App.forget(stage);
    }

}
