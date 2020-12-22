import java.util.List;
import java.util.ArrayList;
public class Enemy extends Ship {
    private int range;
    private int dmg = 0;
    private int PX;
    private int PY;
    private int RouteX;
    private int RouteY;
    private boolean detectedPlayer = false;
    private List<int[]> routing = new ArrayList<int[]>();

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

    public void resetDmg(){
        this.dmg = 0;
    }

    public int getDamageReceived(){
        return this.dmg;
    }

    /**
     *  Create Routes
     * */
    private void pathFinder(){
        /**
         *  Start:  this.pos[1][0]=x
         *          this.pos[1][1]=y
         *  End:    this.RouteX
         *          this.RouteY
         * */

    }

    public short[] run(short[] myImage){
        myImage = clearTrace(myImage);
        if(playerInVision(myImage) && this.bullet == null){
            shoot();
        }
        move();
        if (collide(myImage) != 0){
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
        if(this.routing.size() <= 0 ){
            if(canMove()){
                forward();
            }else{
                if(Math.random() > 0.5){
                    rotate(0);
                }else {
                    rotate(1);
                }
            }
        }else {
            switch(routeDirection(this.routing.get(this.routing.size() - 1))){
                case 1 -> {
                    rotateTo(1);
                    forward();
                }
                case 2 -> {
                    rotateTo(2);
                    forward();
                }
                case 3 -> {
                    rotateTo(3);
                    forward();
                }
                case 4 -> {
                    rotateTo(4);
                    forward();
                }
                case 5 -> {
                    rotateTo(5);
                    forward();
                }
                case 6 -> {
                    rotateTo(6);
                    forward();
                }
                case 7 -> {
                    rotateTo(7);
                    forward();
                }
                case 8 -> {
                    rotateTo(8);
                    forward();
                }
                default -> {}
            }
            this.routing.remove(this.routing.size() - 1);
        }
    }

    private void rotateTo(int newOri){
        while (this.align != newOri) {
            rotate(1);
        }
    }

    private int routeDirection(int[] gPos){
        if(this.pos[1][0] > gPos[0]){
            if(this.pos[1][1] > gPos[1]){
                return 8;
            }else if(this.pos[1][1] < gPos[1]){
                return 6;
            }else {
                return 7;
            }
        }else if(this.pos[1][0] > gPos[0]){
            if(this.pos[1][1] > gPos[1]) {
                return 2;
            }else if(this.pos[1][1] < gPos[1]){
                return 4;
            }else {
                return 3;
            }
        }else {
            if(this.pos[1][1] > gPos[1]) {
                return 1;
            }else if(this.pos[1][1] < gPos[1]){
                return 5;
            }
        }
        return -1;
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
                            this.detectedPlayer = true;
                            this.PX = difx;
                            this.PY = dify;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void setRouteX(int PX){
        this.PX = PX;
    }

    public void setRouteY(int PY){
        this.PY = PY;
    }

    public int getPX(){
        return this.PX;
    }

    public int getPY(){
        return this.PY;
    }

    public boolean getPlayerDetected(){
        return this.detectedPlayer;
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

    public boolean includesPos(int x, int y){
        for (int i = 0; i < this.pos.length; i++){
            if(this.pos[i][0] == x && this.pos[i][1] == y){
                return true;
            }
        }
        return false;
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
