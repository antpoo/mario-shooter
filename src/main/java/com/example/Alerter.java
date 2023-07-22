package com.example;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Alerter {

    /**
     * Method to alert messages
     * @param title Title of the Alert Box
     * @param message Message in Alert Box
     */
    public static void alert(String title, String message) {
        Stage alert = new Stage();

        // forces user to first dismiss alert
        alert.initModality(Modality.APPLICATION_MODAL);

        alert.setTitle(title);
        alert.setMinWidth(260);

        alert.setResizable(false);

        Label label = new Label(message);

        Button closeButton = new Button("Dismiss");
        closeButton.setStyle("-fx-background-color: #98fb98; -fx-text-fill: #014421; -fx-border-color: #014421; -fx-padding: 5, 5, 5, 5; -fx-border-radius: 5;");
        
        // close the alert box
        closeButton.setOnAction(ev -> alert.close());

        VBox layout = new VBox(10);

        // styling
        layout.setStyle("-fx-background-color: #add8e6");

        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene alertScene = new Scene(layout);
        alert.setScene(alertScene);

        alert.showAndWait();
    }
    
}
