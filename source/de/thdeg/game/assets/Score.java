package de.thdeg.game.assets;

public class Score {
    int score;

    Score() {
    }

    public void add(int val) {
        this.score += val;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
