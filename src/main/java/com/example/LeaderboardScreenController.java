package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.IOutil.FReader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LeaderboardScreenController {

    // FXML scene variables
    @FXML private VBox layout;
    @FXML private TableView<Score> table = new TableView<>();
    @FXML private TableColumn<Score, Integer> placeCol;
    @FXML private TableColumn<Score, String> usernameCol;
    @FXML private TableColumn<Score, Integer> scoreCol;

    // score variable 
    private Score score;

    // stage 
    private Stage stage;

    /**
     * Sets the stage
     * @param stage stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Sets the score
     * @param username username to set
     * @param score score to set
     */
    public void setScore(String username, int score) {
        this.score = new Score(username, score, -1);
    }

    // data list field
    private ObservableList<Score> data;


    /**
     * Initializes and places items in table
     * @throws IOException
     */
    public void init() throws IOException {

        // gets the data into an observable arraylist for the table
        data = FXCollections.observableArrayList(getLeaderboardData());
        
        // the 3 columns 
        placeCol.setCellValueFactory(new PropertyValueFactory<Score, Integer>("place"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<Score, String>("username"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<Score, Integer>("score"));

        // adding the data onto the columns
        table.setItems(data);

    }

    
    /**
     * @return leaderboard data as an ArrayList of scores
     * @throws IOException
     */
    private ArrayList<Score> getLeaderboardData() throws IOException {
        ArrayList<Score> list = new ArrayList<>();
        // freader 
        FReader fr = new FReader("src/main/java/com/example/databases/leaderboard.txt");
        String S = "";
        // parsing the data 
        int i = 1;
        while ((S = fr.readLine()) != null) {
            String[] scoreData = S.split(" ");
            String username = scoreData[0];
            int score = Integer.parseInt(scoreData[1]);
            // adding to list (the list will already be sorted because leaderboard is stored sorted)
            list.add(new Score(username, score, i++));
        }

        return list;
        
    }

    /**
     * Closes the app
     * @param e Action event
     * @throws IOException
     */
    @FXML 
    private void quitPressed(ActionEvent e) throws IOException {
        stage.close();
    }

    /**
     * Goes back to end screen
     * @param e Action event
     * @throws IOException
     */
    @FXML 
    private void returnPressed(ActionEvent e) throws IOException {
        App.endGame(stage, score.getUsername(), score.getScore());
    }

    /**
     * Finding the user in the leaderboard
     * @param e Action event 
     * @throws IOException
     */
    @FXML 
    private void findMePressed(ActionEvent e) throws IOException {
        // getting the score data into an arraylist
        ArrayList<Score> scores = getLeaderboardData();

        // binary searching for the user's place
        int lo = 0, hi = scores.size() - 1, mid = 0;
        // keep going until left pointer is greater than the right pointer 
        while (lo <= hi) {
            // compare with the middle of the range 
            mid = (lo + hi) / 2;
            // if our score is higher then ignore the left half
            if (score.compareTo(scores.get(mid)) > 0) {
                lo = mid + 1;
            } 
            // if our score is lower then ignore the right half 
            else if (score.compareTo(scores.get(mid)) < 0) {
                hi = mid - 1;
            } 
            // if we find our score 
            else break;
        }

        // telling player their place 
        String suff = "th";
        if (scores.get(mid).getPlace() % 10 == 1) suff = "st";
        else if (scores.get(mid).getPlace() % 10 == 2) suff = "nd";
        else if (scores.get(mid).getPlace() % 10 == 3) suff = "rd";

        // alerts the user their place
        Alerter.alert("Your placement", "Your most recent game placed " + scores.get(mid).getPlace() + suff + " place.");

    }
    
}
