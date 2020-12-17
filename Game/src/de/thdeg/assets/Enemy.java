public class Enemy extends Ship {
    private int range;
    private int dmg = 0;

    Enemy(int hp){
        super(hp);
        short[][][] rgbs = {{{31, 222, 215},{21, 138, 134},{11, 74, 72}},{{31, 69, 222},{19, 43, 143},{10, 22, 74}},{{153, 23, 209},{94, 15, 128},{55, 10, 74}}};
        changeColor(rgbs);
        int[][] pos = { {22, 22}, {23, 22}, {24, 22} };
        this.pos = pos;
    }

    Enemy(int hp, int x, int y, int o, int r){
        super(hp, x, y, o);
        short[][][] rgbs = {{{31, 222, 215},{21, 138, 134},{11, 74, 72}},{{31, 69, 222},{19, 43, 143},{10, 22, 74}},{{153, 23, 209},{94, 15, 128},{55, 10, 74}}};
        changeColor(rgbs);
        this.range = r;
    }

    public int getDamageReceived(){
        return this.dmg;
    }

    public short[] run(short[] myImage){
        myImage = clearTrace(myImage);
        if(playerInVision(myImage) && this.bullet == null){
            shoot();
        }
        move();
        if (collide(myImage) == 1){
            resetMove();
        }
        if (collide(myImage) == 2){
            resetMove();
        }
        if(this.bullet != null){
            if(this.bullet.getRange() > 0){
                this.bullet.run(-1, myImage);
            }else{
                this.bullet = null;
            }
        }
        myImage = paint(myImage);
        return myImage;
    }

    private void move(){
        if(canMove()){
            forward();
        }else{
            if(Math.random() > 0.5){
                rotate(0);
            }else {
                rotate(1);
            }
        }
    }

    /**
     *  Method to detect if the player is visible for the enemy ship.
     * */
    private boolean playerInVision(short[] myImage){
        int difx;
        int dify;
        if(this.hp > 0){
            for (int i = 0 - this.range; i <= this.range; i++) {
                difx = this.pos[1][0] + i;
                for (int j = 0 - this.range; j <= this.range; j++) {
                    dify = this.pos[1][1] + j;
                    if ((Math.pow(difx - this.pos[0][1], 2)+Math.pow(dify - this.pos[1][1], 2)) <= Math.pow(this.range, 2)) {
                        if(hitPlayer(myImage, difx,dify)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    /**
     * The method collide looks at the pixels of the ship and look if it collided with another object
     * */
    public int collide(short[] myImage){
        int ret = 0;
        for(int i=0; i < this.pos.length; i++){
            int idx = (this.pos[i][1] * 48 + this.pos[i][0]) * 3;
            if (hitBullet(myImage, this.pos[i][0], this.pos[i][1])){
                damage(1);
                ret = 2;
            }
                if(hitPlayer(myImage, this.pos[i][0], this.pos[i][1])) {
                damage(1);
                this.dmg += 1;
                ret = 1;
                }
        }
        return ret;
    }

    /**
     * Debug method to print shiplocation and locationdifference between the new and old location.
     * */
    public void print(){
        System.out.println("Enemy ship:\nA: " + this.align);
        for (int i = 0; i < this.pos.length; i++){
            System.out.println("X: " + this.pos[i][0] + " Y: " + this.pos[i][1]);
            System.out.println("Xo: " + this.oldpos[i][0] + " Yo: " + this.oldpos[i][1]);
            // System.out.println("(" + i + ") -> X: " + (this.pos[i][0] - this.oldpos[i][0]) + " Y: " + (this.pos[i][1] - this.oldpos[i][1]));
        }
    }
}
