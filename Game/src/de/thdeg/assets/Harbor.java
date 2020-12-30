public class Harbor extends Agent{
    protected short[][] color = {{125, 66, 24},{122, 236, 35},{30, 68, 221}};
    protected int orient;
    protected boolean captured = false;
    protected int possession = -1;
    protected int[] pos;
    protected int[] enemyPos;
    protected Bullet bullet = null;

    Harbor(int orient){
        this.orient = orient;
        this.pos = new int[2];
        this.enemyPos = new int[2];
    }

    public int getOrient(){
        return this.orient;
    }

    public short[][] getColor() {
        return this.color;
    }

    public int getPossession(){
        return this.possession;
    }

    public void setPos(int y, int x){
        if(x >= 0 && x <= 47){
            this.pos[0] = x;
        }
        if(y >= 0 && y <= 23){
            this.pos[1] = y;
        }
    }

    public short[] reset(short[] myImage){
        this.possession = -1;
        this.captured = false;
        myImage = paint(myImage);
        return myImage;
    }

    @Override
    short[] paint(short[] myImage) {
        return new short[0];
    }

    @Override
    int collide(short[] myImage) {
        return -1;
    }

    public short[] isHit(short[] myImage){
        if (hitBullet(myImage, this.pos[0], this.pos[1])){
            myImage = reset(myImage);
        }
        return myImage;
    }

    @Override
    void move(int dir,short[] myImage) {
    }

    protected boolean hitBullet(short[] myImage, int x, int y){
        if (x <= 47 && y <= 23 && x >= 0 && y >= 0) {
            int idx = (y * 48 + x) * 3;
            return (myImage[idx + 0] == 12 && myImage[idx + 1] == 13 && myImage[idx + 2] == 12);
        }else {
            return false;
        }
    }

    @Override
    short[] run(int key, short[] myImage) {
        // myImage = isHit(myImage);
        if(this.captured){
            if(detectShip(15, myImage) == this.possession) {
                shoot();
            }
        } else {
            int poss = detectShip(7, myImage);
            if(poss != -1) {
                this.captured = true;
                if(poss == 0) {
                    this.possession = 1;
                }else {
                    this.possession = 0;
                }
            }
        }
        if(this.bullet != null){
            if(this.bullet.getRange() > 0){
                myImage = this.bullet.run(-1, myImage);
            }else{
                this.bullet = null;
            }
        }
        return myImage;
    }
    private int routeDirection(int x, int y, int[] gPos){
        if(x > gPos[0]){
            if(y > gPos[1]){
                return 8;
            }else if(y < gPos[1]){
                return 6;
            }else {
                return 7;
            }
        }else if(x < gPos[0]){
            if(y > gPos[1]) {
                return 2;
            }else if(y < gPos[1]){
                return 4;
            }else {
                return 3;
            }
        }else {
            if(y > gPos[1]) {
                return 1;
            }else if(y < gPos[1]){
                return 5;
            }
        }
        return -1;
    }
    protected void shoot() {
        int orient = routeDirection(this.pos[0], this.pos[1], this.enemyPos);
        if(this.bullet == null){
            this.bullet = new Bullet(orient, 8, this.pos[0], this.pos[1], 1);
        }
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
    protected boolean hitEnemy(short[] myImage, int x, int y){
        if (x <= 47 && y <= 23 && x >= 0 && y >= 0) {
            int idx = (y * 48 + x) * 3;
            return (myImage[idx + 0] == 31 && myImage[idx + 1] == 69 && myImage[idx + 2] == 222) ||
                (myImage[idx + 0] == 19 && myImage[idx + 1] == 43 && myImage[idx + 2] == 143) ||
                (myImage[idx + 0] == 10 && myImage[idx + 1] == 22 && myImage[idx + 2] == 74) ||
                (myImage[idx + 0] == 31 && myImage[idx + 1] == 222 && myImage[idx + 2] == 215) ||
                (myImage[idx + 0] == 21 && myImage[idx + 1] == 138 && myImage[idx + 2] == 134) ||
                (myImage[idx + 0] == 11 && myImage[idx + 1] == 74 && myImage[idx + 2] == 72) ||
                (myImage[idx + 0] == 153 && myImage[idx + 1] == 23 && myImage[idx + 2] == 209) ||
                (myImage[idx + 0] == 94 && myImage[idx + 1] == 15 && myImage[idx + 2] == 128) ||
                (myImage[idx + 0] == 55 && myImage[idx + 1] == 10 && myImage[idx + 2] == 74);
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
                    if(hitPlayer(myImage, difx, dify)) {
                        this.enemyPos[0] = difx;
                        this.enemyPos[1] = dify;
                        return 1;
                    }
                    if(hitEnemy(myImage, difx, dify)) {
                        this.enemyPos[0] = difx;
                        this.enemyPos[1] = dify;
                        return 0;
                    }
                }
            }
        }
        return -1;
    }
}
