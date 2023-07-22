package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;



    @Override
    public void start(Stage stage) throws IOException {
        login(stage);
    }

    // method to enter login
    public static void login(Stage stage) throws IOException {
        // getting the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("loginscreen.fxml"));

        // getting the root node
        GridPane root = (GridPane) fxmlLoader.load();

        // setting background to be light blue
        root.setStyle("-fx-background-color: #add8e6");

        // intializing the scene
        scene = new Scene(root, 600, 400);

        // setting the title
        stage.setTitle("Mario Shooter");

        // getting the controller
        LoginScreenController controller = fxmlLoader.getController();
        
        // drawing the logo
        controller.draw();

        // not allowing stage to be maximized
        stage.setResizable(false);

        // storing the stage
        controller.setStage(stage);

        // setting the scene
        stage.setScene(scene);

        // displaying the window
        stage.show();
    }


    // method to enter signup 
    public static void signup(Stage stage) throws IOException {
        // getting the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("signupscreen.fxml"));

        // getting the root node 
        GridPane root = (GridPane) fxmlLoader.load();

        // setting background to be light blue
        root.setStyle("-fx-background-color: #add8e6");

        // initializing the scene
        scene = new Scene(root, 600, 400);

        // getting the controller
        SignupScreenController controller = fxmlLoader.getController();

        // storing the stage and setting the scene
        controller.setStage(stage);
        stage.setScene(scene);

        // displaying the window
        stage.show(); 
    }

    // method to start game
    public static void startGame(Stage stage, String username) throws IOException {
        // getting the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("gamescreen.fxml"));

        // getting the root node
        BorderPane root = (BorderPane) fxmlLoader.load();

        // initializing the scene
        scene = new Scene(root, 600, 450);

        // getting the controller
        GameScreenController controller = fxmlLoader.getController();

        // setting the scene
        stage.setScene(scene);
        controller.setScene(stage);

        // storing the stage and username
        controller.setStage(stage);
        controller.setUsername(username);

        // starting the gameloop
        controller.gameLoop();

        // displaying the window
        stage.show(); 

    }

    // method to enter forgot password screen
    public static void forget(Stage stage) throws IOException {
        // getting the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("forgetscreen.fxml"));

        // getting the root node
        GridPane root = (GridPane) fxmlLoader.load();

        // setting background to be light blue
        root.setStyle("-fx-background-color: #add8e6");

        // initializing the scene
        scene = new Scene(root, 600, 400);
        
        // getting the controller 
        ForgetScreenController controller = fxmlLoader.getController();

        // setting the scene and stage
        stage.setScene(scene);
        controller.setStage(stage);

        // displaying the window
        stage.setTitle("Forgot Password");
        stage.show();
    }

    // method to enter end screen
    public static void endGame(Stage stage, String username, int score) throws IOException {
        // getting the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("endscreen.fxml"));

        // getting the root node
        GridPane root = (GridPane) fxmlLoader.load();

        // setting background to be light blue
        root.setStyle("-fx-background-color: #add8e6");

        // initializing the scene
        scene = new Scene(root, 600, 400);

        // getting the contorller 
        EndScreenController controller = fxmlLoader.getController();

        // setting the scene
        stage.setScene(scene);

        // storing the stage, score, and username 
        controller.setStage(stage);
        controller.setScore(score);
        controller.setUsername(username);

        // displaying the score
        controller.showScore();

        // inserting the score into the leaderboard 
        controller.insertScore();

        // displaying the window
        stage.setTitle("End");
        stage.show();
    }

    // method to enter leaderboard 
    public static void leaderboard(Stage stage, String username, int score) throws IOException {
        // getting the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("leaderboardscreen.fxml"));

        // getting the root node
        VBox root = (VBox) fxmlLoader.load();

        // setting background to be light blue
        root.setStyle("-fx-background-color: #add8e6");

        // setting the scene
        scene = new Scene(root, 600, 400);

        // getting the controller 
        LeaderboardScreenController controller = fxmlLoader.getController();
        
        // storing the stage, username and score in the controller object
        controller.setScore(username, score);
        controller.setStage(stage);
        
        // initializing the leaderboard
        controller.init();
        
        // showing the leaderboard
        stage.setScene(scene);
        stage.setTitle("Leaderboard");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}