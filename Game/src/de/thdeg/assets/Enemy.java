public class Enemy extends Ship {

    Enemy(int hp){
        super(hp);
        short[][][] rgbs = {{{31, 222, 215},{21, 138, 134},{11, 74, 72}},{{31, 69, 222},{19, 43, 143},{10, 22, 74}},{{153, 23, 209},{94, 15, 128},{55, 10, 74}}};
        changeColor(rgbs);
        int[][] pos = { {22, 22}, {23, 22}, {24, 22} };
        this.pos = pos;
    }

    Enemy(int hp, int x, int y, int o){
        super(hp, x, y, o);
        short[][][] rgbs = {{{31, 222, 215},{21, 138, 134},{11, 74, 72}},{{31, 69, 222},{19, 43, 143},{10, 22, 74}},{{153, 23, 209},{94, 15, 128},{55, 10, 74}}};
        changeColor(rgbs);
    }

    protected void shoot() {

    }

    /**
     * The method collide looks at the pixels of the ship and look if it collided with another object
     * */
    public boolean collide(short[] myImage){
        boolean ret = false;
        for(int i=0; i < this.pos.length; i++){
            int idx = (this.pos[i][1] * 48 + this.pos[i][0]) * 3;
            if (    (myImage[idx + 0] == 237 && myImage[idx + 1] == 76 && myImage[idx + 2] == 36) ||
                    (myImage[idx + 0] == 237 && myImage[idx + 1] == 207 && myImage[idx + 2] == 36) ||
                    (myImage[idx + 0] == 123 && myImage[idx + 1] == 237 && myImage[idx + 2] == 36) ||

                    (myImage[idx + 0] == 145 && myImage[idx + 1] == 47 && myImage[idx + 2] == 22) ||
                    (myImage[idx + 0] == 148 && myImage[idx + 1] == 129 && myImage[idx + 2] == 22) ||
                    (myImage[idx + 0] == 74 && myImage[idx + 1] == 143 && myImage[idx + 2] == 21) ||

                    (myImage[idx + 0] == 74 && myImage[idx + 1] == 24 && myImage[idx + 2] == 11) ||
                    (myImage[idx + 0] == 66 && myImage[idx + 1] == 58 && myImage[idx + 2] == 10) ||
                    (myImage[idx + 0] == 38 && myImage[idx + 1] == 74 && myImage[idx + 2] == 11) ||

                    (myImage[idx + 0] == 12 && myImage[idx + 1] == 13 && myImage[idx + 2] == 12)
               ) {
                damage(1);
                ret = true;
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
