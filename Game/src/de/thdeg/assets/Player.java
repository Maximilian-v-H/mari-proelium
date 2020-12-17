public class Player extends Ship {
    private int score = 0;

    Player(int hp){
        super(hp);
        short[][][] rgbs = {{{237, 76, 36},{145, 47, 22},{74, 24, 11}},{{237, 207, 36},{148, 129, 22},{66, 58, 10}},{{123, 237, 36},{74, 143, 21},{38, 74, 11}}};
        changeColor(rgbs);

        int[][] pos = { {5, 5}, {6, 5}, {7, 5} };
        this.pos = pos;
    }

    Player(int hp, int x, int y, int o){
        super(hp, x, y, o);
        short[][][] rgbs = {{{237, 76, 36},{145, 47, 22},{74, 24, 11}},{{237, 207, 36},{148, 129, 22},{66, 58, 10}},{{123, 237, 36},{74, 143, 21},{38, 74, 11}}};
        changeColor(rgbs);
    }

    /**
     * The method collide looks at the pixels of the ship and look if it collided with another object
     * */
    public int collide(short[] myImage){
        int ret = 0;
        for(int i=0; i < this.pos.length; i++){
            if (hitBullet(myImage, this.pos[i][0], this.pos[i][1]) || hitEnemy(myImage, this.pos[i][0], this.pos[i][1])) {
                damage(1);
                ret = 1;
               }
        }
        return ret;
    }

    public short[] run(int key, short[] myImage){
        myImage = isHit(myImage);
        if(key != -1){
            myImage = clearTrace(myImage);
            move(key);
            if (collide(myImage) == 1){
                resetMove();
                if(key == 2){
                    this.align++;
                }
                if(key == 3){
                    this.align--;
                }
            }
        }
        if(this.bullet != null){
            if(this.bullet.getHasHit()){
                addScore(50);
            }
            if(this.bullet.getRange() > 0){
                this.bullet.run(-1, myImage);
            }else{
                this.bullet = null;
            }
        }
        System.out.println("Score: " + getScore());
        myImage = paint(myImage);
        return myImage;
    }

    public int getScore(){
        return this.score;
    }

    public void addScore(int val){
        this.score += val;
    }

    /**
     * Debug method to print shiplocation and locationdifference between the new and old location.
     * */
    public void print(){
        System.out.println("Your ship:\nA: " + this.align);
        for (int i = 0; i < this.pos.length; i++){
            System.out.println("X: " + this.pos[i][0] + " Y: " + this.pos[i][1]);
            System.out.println("Xo: " + this.oldpos[i][0] + " Yo: " + this.oldpos[i][1]);
            // System.out.println("(" + i + ") -> X: " + (this.pos[i][0] - this.oldpos[i][0]) + " Y: " + (this.pos[i][1] - this.oldpos[i][1]));
        }
    }
}
