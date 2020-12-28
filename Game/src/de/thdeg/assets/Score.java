public class Score {
    int score;

    Score(){}

    public void add(int val){
        this.score += val;
    }

    public void setScore(int score){
        this.score = score;
    }

    public int getScore(){
        return this.score;
    }
}
