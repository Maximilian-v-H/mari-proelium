public class Harbor extends Agent{
    protected short[] color = {125, 66, 24};
    protected int orient;
    protected boolean captured = false;
    protected int possession = -1;
    protected int[] pos;

    Harbor(int orient){
        this.orient = orient;
        this.pos = new int[2];
    }

    public int getOrient(){
        return this.orient;
    }

    public short[] getColor() {
        return this.color;
    }

    public void setPos(int y, int x){
        if(x >= 0 && x <= 47){
            this.pos[0] = x;
        }
        if(y >= 0 && y <= 23){
            this.pos[1] = y;
        }
    }

    @Override
    short[] paint(short[] myImage) {
        return new short[0];
    }

    @Override
    int collide(short[] myImage) {
        return -1;
    }

    @Override
    void move(int dir,short[] myImage) {
    }

    @Override
    short[] run(int key, short[] myImage) {
        if(this.captured){
            if(detectShip(10, myImage) == this.possession){

            }
        } else {
            int poss = detectShip(1, myImage);
            if(poss != -1){

            }
        }
        return new short[0];
    }
    protected boolean hitPlayer(short[] myImage, int x, int y){
        if (x <= 47 && y <= 23 && x >= 0 && y >= 0) {
            int idx = (y * 48 + x) * 3;
            return (myImage[idx + 0] == 237 && myImage[idx + 1] == 76 && myImage[idx + 2] == 36) ||
                    (myImage[idx + 0] == 237 && myImage[idx + 1] == 207 && myImage[idx + 2] == 36) ||
                    (myImage[idx + 0] == 123 && myImage[idx + 1] == 237 && myImage[idx + 2] == 36) ||
                    (myImage[idx + 0] == 145 && myImage[idx + 1] == 47 && myImage[idx + 2] == 22) ||
                    (myImage[idx + 0] == 148 && myImage[idx + 1] == 129 && myImage[idx + 2] == 22) ||
                    (myImage[idx + 0] == 74 && myImage[idx + 1] == 143 && myImage[idx + 2] == 21) ||
                    (myImage[idx + 0] == 74 && myImage[idx + 1] == 24 && myImage[idx + 2] == 11) ||
                    (myImage[idx + 0] == 66 && myImage[idx + 1] == 58 && myImage[idx + 2] == 10) ||
                    (myImage[idx + 0] == 38 && myImage[idx + 1] == 74 && myImage[idx + 2] == 11);
        }else {
            return false;
        }
    }
    protected int detectShip(int range, short[] myImage){
        int difx;
        int dify;
        for (int i = -range; i <= range; i++) {
            difx = this.pos[0] + i;
            for (int j = -range; j <= range; j++) {
                dify = this.pos[1] + j;
                if ((Math.pow(difx - this.pos[0], 2)+Math.pow(dify - this.pos[1], 2)) <= Math.pow(range, 2)) {
                    if(hitPlayer(myImage, difx,dify)) {

                    }
                }
            }
        }
        return -1;
    }
}
