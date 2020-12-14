public class Player extends Ship {

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
    public boolean collide(short[] myImage){
        boolean ret = false;
        for(int i=0; i < this.pos.length; i++){
            int idx = (this.pos[i][1] * 48 + this.pos[i][0]) * 3;
            if ((myImage[idx + 0] == 31 && myImage[idx + 1] == 69 && myImage[idx + 2] == 222) ||
                    (myImage[idx + 0] == 19 && myImage[idx + 1] == 43 && myImage[idx + 2] == 143) ||
                    (myImage[idx + 0] == 10 && myImage[idx + 1] == 22 && myImage[idx + 2] == 74) ||

                    (myImage[idx + 0] == 31 && myImage[idx + 1] == 222 && myImage[idx + 2] == 215) ||
                    (myImage[idx + 0] == 21 && myImage[idx + 1] == 138 && myImage[idx + 2] == 134) ||
                    (myImage[idx + 0] == 11 && myImage[idx + 1] == 74 && myImage[idx + 2] == 72) ||

                    (myImage[idx + 0] == 153 && myImage[idx + 1] == 23 && myImage[idx + 2] == 209) ||
                    (myImage[idx + 0] == 94 && myImage[idx + 1] == 15 && myImage[idx + 2] == 128) ||
                    (myImage[idx + 0] == 55 && myImage[idx + 1] == 10 && myImage[idx + 2] == 74) ||

                    (myImage[idx + 0] == 12 && myImage[idx + 1] == 13 && myImage[idx + 2] == 12)
               ) {
                damage(1);
                ret = true;
               }
        }
        return ret;
    }
    protected void shoot() {

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
