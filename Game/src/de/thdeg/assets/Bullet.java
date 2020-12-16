public class Bullet extends Agent {
    private int direction;
    private int range;
    private int[] pos = new int[2];
    private int[] oldpos = new int[2];

    Bullet(int dir, int range, int x, int y){
        this.direction = dir;
        this.range = range;
        this.pos[0] = x;
        this.pos[1] = y;
    }

    private short[] clearTrace(short[] myImage){
        myImage[(this.oldpos[1] * 48 + this.oldpos[0]) * 3 + 0] = (short)0;
        myImage[(this.oldpos[1] * 48 + this.oldpos[0]) * 3 + 1] = (short)177;
        myImage[(this.oldpos[1] * 48 + this.oldpos[0]) * 3 + 2] = (short)241;
        return myImage;
    }

    public short[] paint(short[] myImage){
        myImage = clearTrace(myImage);
        if(this.range > 0) {
            myImage[(this.pos[1] * 48 + this.pos[0]) * 3 + 0] = (short)12;
            myImage[(this.pos[1] * 48 + this.pos[0]) * 3 + 1] = (short)13;
            myImage[(this.pos[1] * 48 + this.pos[0]) * 3 + 2] = (short)12;
        }
        return myImage;
    }

    public boolean collide(short[] myImage){

        return false;
    }

    public void move(int dir){}

    private void saveOldPos(){
        this.oldpos[0] = this.pos[0];
        this.oldpos[1] = this.pos[1];
    }

    public void move(){
        if(canMove()){
            saveOldPos();
            switch(this.direction){
                case 1:
                    this.pos[1]--;
                    break;
                case 2:
                    this.pos[0]++;
                    this.pos[1]--;
                    break;
                case 3:
                    this.pos[0]++;
                    break;
                case 4:
                    this.pos[0]++;
                    this.pos[1]++;
                    break;
                case 5:
                    this.pos[1]++;
                    break;
                case 6:
                    this.pos[0]--;
                    this.pos[1]++;
                    break;
                case 7:
                    this.pos[0]--;
                    break;
                case 8:
                    this.pos[0]--;
                    this.pos[1]--;
                    break;
            }
            this.range--;
        }
    }

    private boolean canMove(){
        boolean ret = false;
        switch(this.direction){
            case 1 -> ret = (this.pos[1] - 1 >= 0);
            case 2 -> ret = (this.pos[1] - 1 > 0 && this.pos[0] + 1 < 48);
            case 3 -> ret = (this.pos[0] + 1 < 48);
            case 4 -> ret = (this.pos[0] + 1 < 48 && this.pos[1] + 1 < 24);
            case 5 -> ret = (this.pos[1] + 1 < 24);
            case 6 -> ret = (this.pos[1] + 1 < 24 && this.pos[0] - 1 >= 0);
            case 7 -> ret = (this.pos[0] - 1 >= 0);
            case 8 -> ret = (this.pos[0] - 1 >= 0 && this.pos[1] - 1 >= 0);
        }
        return ret;
    }

    public short[] run(int key, short[] myImage){
        move();
        paint(myImage);

        return myImage;
    }

    public int getRange(){
        return this.range;
    }
}
