public class Player extends Ship {
    private Score score;
    private boolean hit = false;
    private int hitX;
    private int hitY;

    Player(int hp){
        super(hp);
        this.score = new Score();
        short[][][] rgbs = {{{237, 76, 36},{145, 47, 22},{74, 24, 11}},{{237, 207, 36},{148, 129, 22},{66, 58, 10}},{{123, 237, 36},{74, 143, 21},{38, 74, 11}}};
        changeColor(rgbs);

        int[][] pos = { {5, 5}, {6, 5}, {7, 5} };
        this.pos = pos;
    }

    Player(int hp, int x, int y, int o){
        super(hp, x, y, o);
        this.score = new Score();
        short[][][] rgbs = {{{237, 76, 36},{145, 47, 22},{74, 24, 11}},{{237, 207, 36},{148, 129, 22},{66, 58, 10}},{{123, 237, 36},{74, 143, 21},{38, 74, 11}}};
        changeColor(rgbs);
    }

    /**
     * The method collide looks at the pixels of the ship and look if it collided with another object
     * */
    public int collide(short[] myImage){
        int ret = 0;
        for(int i=0; i < this.pos.length; i++){
            if (hitIsland(myImage, this.pos[i][0], this.pos[i][1])){
                ret = 1;
            }
            if (hitBullet(myImage, this.pos[i][0], this.pos[i][1])){
                damage(1);
                ret = 1;
            }
            if(hitEnemy(myImage, this.pos[i][0], this.pos[i][1])) {
                this.hit = true;
                this.hitX = this.pos[i][0];
                this.hitY = this.pos[i][1];
                damage(1);
                ret = 1;
            }
        }
        return ret;
    }

    public int[][] getPos(){
        return this.pos;
    }

    public int getHitX(){
        return this.hitX;
    }
    public int getHitY(){
        return this.hitY;
    }
    public boolean getHit(){
        return this.hit;
    }

    public void resetHit(){
        this.hit = false;
        this.hitX = -1;
        this.hitY = -1;
    }

    public short[] run(int key, short[] myImage){
        myImage = isHit(myImage);
        if(key != -1){
            myImage = clearTrace(myImage);
            move(key,myImage);
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
        if(this.bullet2 != null){
            if(this.bullet2.getHasHit()){
                addScore(50);
            }
            if(this.bullet2.getRange() > 0){
                this.bullet2.run(-1, myImage);
            }else{
                this.bullet2 = null;
            }
        }
        myImage = paint(myImage);
        return myImage;
    }

    public Score getScore(){
        return this.score;
    }

    public void addScore(int val){
        this.score.add(val);
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
    private static boolean isWater(short[] myImage, int idx){
        return (myImage[idx] == 0 && myImage[idx + 1] == 177 && myImage[idx + 2] == 241);
    }
    public static Player spawn(short[] myImage, int num){
        Player ret = null;
        while (num > 0){
            int i = (int)(Math.random() * 46) + 1;
            int j = (int)(Math.random() * 22) + 1;
            if(     isWater(myImage, (((j-1) * 48 +  i   ) * 3)) &&
                    isWater(myImage, (((j-1) * 48 + (i+1)) * 3)) &&
                    isWater(myImage, (((j-1) * 48 + (i-1)) * 3)) &&
                    isWater(myImage, ((j     * 48 +  i   ) * 3)) &&
                    isWater(myImage, ((j     * 48 + (i+1)) * 3)) &&
                    isWater(myImage, ((j     * 48 + (i-1)) * 3)) &&
                    isWater(myImage, (((j+1) * 48 +  i   ) * 3)) &&
                    isWater(myImage, (((j+1) * 48 + (i+1)) * 3)) &&
                    isWater(myImage, (((j+1) * 48 + (i-1)) * 3))){
                ret = new Player(3,i,j,(int)(Math.random()*7)+1);
                num--;
                continue;
            }
        }
        return ret;
    }
}
