package com.example;

import java.io.IOException;
import java.util.TreeSet;

import com.example.IOutil.FReader;
import com.example.IOutil.FWriter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class EndScreenController {

    // fields
    private Stage stage;
    private int score;
    private String username;

    // FXML fields
    @FXML private Label label;

    /**
     * Sets the stage
     * @param stage Stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Sets the username
     * @param username username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the score
     * @param score score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Displays the player's score on the label
     */
    public void showScore() {
        label.setAlignment(Pos.CENTER);
        label.setText(Integer.toString(score));
    }

    /**
     * Inserts the player's score into the leaderboard file.
     * @throws IOException
     */
    public void insertScore() throws IOException {
        // filereader and writer
        FReader fr = new FReader("src/main/java/com/example/databases/leaderboard.txt");
        FWriter fw = new FWriter("src/main/java/com/example/databases/leaderboard.txt");

        // first get the existing leaderboard into TreeSet (treesets are sorted on insertion, and remove duplicates)
        TreeSet<Score> scores = new TreeSet<>();
        String S = "";

        // iterate through the leaderboard
        while ((S = fr.readLine()) != null) {
            // parse the data 
            String[] scoreData = S.split(" ");
            Score score = new Score(scoreData[0], Integer.parseInt(scoreData[1]), Integer.parseInt(scoreData[2]));
            scores.add(score);
        }

        // add the new score into the treeset 
        Score newScore = new Score(username, score, -1);
        scores.add(newScore);

        // write the new scores to the leaderboard 
        // overwriting old leaderboard data and then appending 
        fw.setOverwrite();
        int i = 1;
        for (Score scoreData : scores) {
            fw.writeLn(scoreData.getUsername() + " " + scoreData.getScore() + " " + i);
            if (i == 1) fw.setAppend();
            i++;
        }

    }


    /**
     * Enters the leaderboard
     * @param e Action event
     * @throws IOException
     */
    @FXML 
    private void leaderboardPressed(ActionEvent e) throws IOException {
        App.leaderboard(stage, username, score);
    }
    
    /**
     * Closes the app
     * @param e Action event
     * @throws IOException
     */
    @FXML 
    private void exitPressed(ActionEvent e) throws IOException {
        stage.close();
    }

    /**
     * Logs out 
     * @param e Action event
     * @throws IOException
     */
    @FXML 
    private void logoutPressed(ActionEvent e) throws IOException {
        App.login(stage);
    }

    /**
     * restarts the game
     * @param e Action event
     * @throws IOException
     */
    @FXML 
    private void replayPressed(ActionEvent e) throws IOException {
        App.startGame(stage, username);
    }
}
