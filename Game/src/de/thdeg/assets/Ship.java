import java.util.ArrayList;
import java.util.List;

public class Ship extends Agent {
    protected int hp;
    protected int[][] pos;
    protected int[][] oldpos;
    protected int align;
    protected short[][][] color = new short[3][3][3];
    protected List<Bullet> bullets = new ArrayList<Bullet>();

    Ship(int hp){
        this.pos = new int[3][2];
        this.oldpos = new int[3][2];
        this.hp = hp;
        this.align = 7;
        this.pos[0][0] = 2;
        this.pos[0][1] = 3;
        this.pos[1][0] = 2;
        this.pos[1][1] = 2;
        this.pos[2][0] = 2;
        this.pos[2][1] = 1;
    }

    Ship(int hp, int x, int y, int orient){
        this.pos = new int[3][2];
        this.oldpos = new int[3][2];
        this.hp = hp;
        if (x >= 1 && x <= 46 && y >= 1 && y <= 22) {
            this.align = orient;
            this.pos[1][0] = x;
            this.pos[1][1] = y;
            switch(this.align){
                case 1:
                    this.pos[0][0] = x;
                    this.pos[0][1] = y - 1;
                    this.pos[2][0] = x;
                    this.pos[2][1] = y + 1;
                    break;
                case 2:
                    this.pos[0][0] = x + 1;
                    this.pos[0][1] = y - 1;
                    this.pos[2][0] = x - 1;
                    this.pos[2][1] = y + 1;
                    break;
                case 3:
                    this.pos[0][0] = x + 1;
                    this.pos[0][1] = y;
                    this.pos[2][0] = x - 1;
                    this.pos[2][1] = y;
                    break;
                case 4:
                    this.pos[0][0] = x + 1;
                    this.pos[0][1] = y + 1;
                    this.pos[2][0] = x - 1;
                    this.pos[2][1] = y - 1;
                    break;
                case 5:
                    this.pos[0][0] = x;
                    this.pos[0][1] = y + 1;
                    this.pos[2][0] = x;
                    this.pos[2][1] = y - 1;
                    break;
                case 6:
                    this.pos[0][0] = x - 1;
                    this.pos[0][1] = y + 1;
                    this.pos[2][0] = x + 1;
                    this.pos[2][1] = y - 1;
                    break;
                case 7:
                    this.pos[0][0] = x - 1;
                    this.pos[0][1] = y;
                    this.pos[2][0] = x + 1;
                    this.pos[2][1] = y;
                    break;
                case 8:
                    this.pos[0][0] = x - 1;
                    this.pos[0][1] = y - 1;
                    this.pos[2][0] = x + 1;
                    this.pos[2][1] = y + 1;
                    break;
            }
        }else {
            this.pos[0][0] = 2;
            this.pos[0][1] = 3;
            this.pos[1][0] = 2;
            this.pos[1][1] = 2;
            this.pos[2][0] = 2;
            this.pos[2][1] = 1;
            this.align = 7;
        }
    }

    protected short[] clearTrace(short[] myImage){
        for (int i = 0;  i < this.oldpos.length; i++) {
            myImage[(this.oldpos[i][1] * 48 + this.oldpos[i][0]) * 3 + 0] = (short)0;
            myImage[(this.oldpos[i][1] * 48 + this.oldpos[i][0]) * 3 + 1] = (short)177;
            myImage[(this.oldpos[i][1] * 48 + this.oldpos[i][0]) * 3 + 2] = (short)241;
        }
        return myImage;
    }

    /**
     * This method uses the Players values to update the map and return it always.
     * @param myImage the Pixel array given from the {@link GameMain}
     * @return the updated maparray
     * */
    public short[] paint(short[] myImage){
        myImage = clearTrace(myImage);
        if(this.hp > 0) {
            for(int i=0; i < this.pos.length; i++){
                myImage[(this.pos[i][1] * 48 + this.pos[i][0]) * 3 + 0] = color[this.hp - 1][i][0];
                myImage[(this.pos[i][1] * 48 + this.pos[i][0]) * 3 + 1] = color[this.hp - 1][i][1];
                myImage[(this.pos[i][1] * 48 + this.pos[i][0]) * 3 + 2] = color[this.hp - 1][i][2];
            }
        }
        return myImage;
    }

    public boolean isAlive(){
        return (this.hp > 0);
    }

    protected boolean comparePixel(short r1, short g1, short b1, short r2, short g2, short b2){
        return (r1 == r2 && g1 == g2 && b1 == b2);
    }

    /**
     * The method collide looks at the pixels of the ship and look if it collided with another object
     * */
    public boolean collide(short[] myImage){
        return false;
    }

    /**
     * This method takes the userinput and changes the position/direction of the ship
     * @param dir represents the given userinput
     *          0 - up
     *          1 - down
     *          2 - left
     *          3 - right
     * */
    protected void move(int dir, short[] myImage){
        switch(dir){
            case 0: // Hoch
                forward();
                break;
            case 1: // Runter
                shoot(myImage);
                break;
            case 2: // Links
                rotate(0);
                break;
            case 3: // Rechts
                rotate(1);
                break;
        }
    }

    /**
     * This method will be called by the move method and rotates the ship in the given direction.
     * @param dir represents the direction which the ship takes to rotate.
     *          0 - Left
     *          1 - Right
     * */
    protected void rotate(int dir){
        if(dir == 0){ // Left
            switch(this.align){
                case 1:
                    if (this.pos[0][0] - 1 >= 0 && this.pos[2][0] + 1 <= 47) {
                        saveOldPos();
                        this.pos[0][0]--;
                        this.pos[2][0]++;
                        changeAlign(-1);
                    }
                    break;
                case 2:
                    if (this.pos[0][0] - 1 >= 0 && this.pos[2][0] + 1 <= 47) {
                        saveOldPos();
                        this.pos[0][0]--;
                        this.pos[2][0]++;
                        changeAlign(-1);
                    }
                    break;
                case 3:
                    if (this.pos[0][1] - 1 >= 0 && this.pos[2][1] + 1 <= 23) {
                        saveOldPos();
                        this.pos[0][1]--;
                        this.pos[2][1]++;
                        changeAlign(-1);
                    }
                    break;
                case 4:
                    if (this.pos[0][1] - 1 >= 0 && this.pos[2][1] + 1 <= 23) {
                        saveOldPos();
                        this.pos[0][1]--;
                        this.pos[2][1]++;
                        changeAlign(-1);
                    }
                    break;
                case 5:
                    if (this.pos[0][0] + 1 <= 47 && this.pos[2][0] - 1 >= 0) {
                        saveOldPos();
                        this.pos[0][0]++;
                        this.pos[2][0]--;
                        changeAlign(-1);
                    }
                    break;
                case 6:
                    if (this.pos[0][0] + 1 <= 47 && this.pos[2][0] - 1 >= 0) {
                        saveOldPos();
                        this.pos[0][0]++;
                        this.pos[2][0]--;
                        changeAlign(-1);
                    }
                    break;
                case 7:
                    if (this.pos[0][1] + 1 <= 23 && this.pos[2][1] - 1 >= 0) {
                        saveOldPos();
                        this.pos[0][1]++;
                        this.pos[2][1]--;
                        changeAlign(-1);
                    }
                    break;
                case 8:
                    if (this.pos[0][1] + 1 <= 23 && this.pos[2][1] - 1 >= 0) {
                        saveOldPos();
                        this.pos[0][1]++;
                        this.pos[2][1]--;
                        changeAlign(-1);
                    }
                    break;
            }
        }else { // Right
            switch(this.align){
                case 1:
                    if (this.pos[0][0] + 1 <= 47 && this.pos[2][0] - 1 >= 0) {
                        saveOldPos();
                        this.pos[0][0]++;
                        this.pos[2][0]--;
                        changeAlign(1);
                    }
                    break;
                case 2:
                    if (this.pos[0][1] + 1 <= 23 && this.pos[2][1] - 1 >= 0) {
                        saveOldPos();
                        this.pos[0][1]++;
                        this.pos[2][1]--;
                        changeAlign(1);
                    }
                    break;
                case 3:
                    if (this.pos[0][1] + 1 <= 23 && this.pos[2][1] - 1 >= 0) {
                        saveOldPos();
                        this.pos[0][1]++;
                        this.pos[2][1]--;
                        changeAlign(1);
                    }
                    break;
                case 4:
                    if (this.pos[0][0] - 1 >= 0 && this.pos[2][0] + 1 <= 47) {
                        saveOldPos();
                        this.pos[0][0]--;
                        this.pos[2][0]++;
                        changeAlign(1);
                    }
                    break;
                case 5:
                    if (this.pos[0][0] - 1 >= 0 && this.pos[2][0] + 1 <= 47) {
                        saveOldPos();
                        this.pos[0][0]--;
                        this.pos[2][0]++;
                        changeAlign(1);
                    }
                    break;
                case 6:
                    if (this.pos[0][1] - 1 >= 0 && this.pos[2][1] + 1 <= 23) {
                        saveOldPos();
                        this.pos[0][1]--;
                        this.pos[2][1]++;
                        changeAlign(1);
                    }
                    break;
                case 7:
                    if (this.pos[0][1] - 1 >= 0 && this.pos[2][1] + 1 <= 23) {
                        saveOldPos();
                        this.pos[0][1]--;
                        this.pos[2][1]++;
                        changeAlign(1);
                    }
                    break;
                case 8:
                    if (this.pos[0][0] + 1 <= 47 && this.pos[2][0] - 1 >= 0) {
                        saveOldPos();
                        this.pos[0][0]++;
                        this.pos[2][0]--;
                        changeAlign(1);
                    }
                    break;
            }
        }
    }

    /**
     * Method to save the ship position from one move ago.
     * */
    protected void saveOldPos(){
        for(int i = 0; i < this.pos.length; i++){
            this.oldpos[i][0] = this.pos[i][0];
            this.oldpos[i][1] = this.pos[i][1];
        }
    }

    protected void damage(int amount){
        this.hp -= amount;
    }

    /**
     * Used to move the ship in the direction it is aligned to.
     * */
    protected void forward(){
        if(canMove()){
            switch(this.align){
                case 1:
                    saveOldPos();
                    for (int i = 0; i < this.pos.length; i++){
                        this.pos[i][1]--;
                    }
                    break;
                case 2:
                    saveOldPos();
                    for (int i = 0; i < this.pos.length; i++){
                        this.pos[i][0]++;
                        this.pos[i][1]--;
                    }
                    break;
                case 3:
                    saveOldPos();
                    for (int i = 0; i < this.pos.length; i++){
                        this.pos[i][0]++;
                    }
                    break;
                case 4:
                    saveOldPos();
                    for (int i = 0; i < this.pos.length; i++){
                        this.pos[i][0]++;
                        this.pos[i][1]++;
                    }
                    break;
                case 5:
                    saveOldPos();
                    for (int i = 0; i < this.pos.length; i++){
                        this.pos[i][1]++;
                    }
                    break;
                case 6:
                    saveOldPos();
                    for (int i = 0; i < this.pos.length; i++){
                        this.pos[i][0]--;
                        this.pos[i][1]++;
                    }
                    break;
                case 7:
                    saveOldPos();
                    for (int i = 0; i < this.pos.length; i++){
                        this.pos[i][0]--;
                    }
                    break;
                case 8:
                    saveOldPos();
                    for (int i = 0; i < this.pos.length; i++){
                        this.pos[i][0]--;
                        this.pos[i][1]--;
                    }
                    break;
            }
            print("After Move");
        }
    }

    /**
     * Used to determine if the ship can move forward.
     * @return the returnvalue says, if the ship can move forward or if the ship would move outside the map.
     * */
    protected boolean canMove(){
        boolean ret = false;
        switch(this.align){
            case 1 -> ret = (this.pos[0][1] - 1 >= 0);
            case 2 -> ret = (this.pos[0][1] - 1 > 0 && this.pos[0][0] + 1 < 48);
            case 3 -> ret = (this.pos[0][0] + 1 < 48);
            case 4 -> ret = (this.pos[0][0] + 1 < 48 && this.pos[0][1] + 1 < 24);
            case 5 -> ret = (this.pos[0][1] + 1 < 24);
            case 6 -> ret = (this.pos[0][1] + 1 < 24 && this.pos[0][0] - 1 >= 0);
            case 7 -> ret = (this.pos[0][0] - 1 >= 0);
            case 8 -> ret = (this.pos[0][0] - 1 >= 0 && this.pos[0][1] - 1 >= 0);
        }
        return ret;
    }
/**
 *  8 1 2
 *  7   3
 *  6 5 4
 * */
    protected void shoot(short[] myImage) {
        int dir1 = (this.align + 2 > 8) ? (this.align + 2 - 8) : (this.align + 2);
        int dir2 = (this.align - 2 < 1) ? (8 + this.align - 2) : (this.align - 2);
        bullets.add(new Bullet(dir1, 5, this.pos[1][0], this.pos[1][1]));
        bullets.add(new Bullet(dir2, 5, this.pos[1][0], this.pos[1][1]));
    }

    /**
     * This method is used to set the align variable after a succesful rotation
     * @param dir the direction the ship rotates to
     * */
    protected void changeAlign(int dir){
        this.align += dir;
        if(this.align < 1){
            this.align = 8;
        }
        if(this.align > 8){
            this.align = 1;
        }
    }

    public short[] run(int key, short[] myImage){
        if(key != -1){
            myImage = clearTrace(myImage);
            move(key, myImage);
            if (collide(myImage)){
                resetMove();
                if(key == 2){
                    this.align++;
                }
                if(key == 3){
                    this.align--;
                }
            }
        }
        for (Bullet b : bullets) {
            b.run(-1, myImage);
        }
        myImage = paint(myImage);
        return myImage;
    }

    private void resetMove(){
        for(int i=0; i < this.pos.length; i++){
            for(int j=0; j < this.pos[i].length; j++){
                this.pos[i][j] = this.oldpos[i][j];
            }
        }
    }

    /**
     * Debug method to print shiplocation and locationdifference between the new and old location.
     * */
    public void print(String where){
        System.out.println(where + "\nA: " + this.align);
        for (int i = 0; i < this.pos.length; i++){
            System.out.println("X: " + this.pos[i][0] + " Y: " + this.pos[i][1] + " | Xo: " + this.oldpos[i][0] + " Yo: " + this.oldpos[i][1]);
            // System.out.println("(" + i + ") -> X: " + (this.pos[i][0] - this.oldpos[i][0]) + " Y: " + (this.pos[i][1] - this.oldpos[i][1]));
        }
    }

    public int[][] getPos(){
        return this.pos;
    }

    public int getHp(){
        return this.hp;
    }

    public void setHp(int hp){
        this.hp = (hp >= 0)? hp : 0;
    }

    protected void changeColor(short[][][] rgbs){
        for (int i = 0; i < this.color.length; i++) {
            for (int j = 0; j < this.color[0].length; j++){
                for (int k = 0; k < this.color[0][0].length; k++){
                    this.color[i][j][k] = (rgbs[i][j][k] <= 255 && rgbs[i][j][k] >= 0)? rgbs[i][j][k] : 0;
                }
            }
        }
    }
}
