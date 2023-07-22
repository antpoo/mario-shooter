package com.example;

public class Score implements Comparable<Score> {

    // field variables 
    private String username;
    private int score, place;

    // constructor 
    public Score(String username, int score, int place) {
        this.username = username;
        this.score = score;
        this.place = place;
    }

    /**
     * Comparator
     * @return positive, 0, or negative based on comparison
     */
    @Override
    public int compareTo(Score o) {
        if (this.score == o.score) return this.username.compareTo(o.username);
        else return o.score - this.score;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @return the placement of the player
     */
    public int getPlace() {
        return place;
    }
    
}
