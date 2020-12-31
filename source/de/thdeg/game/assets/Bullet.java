package de.thdeg.game.assets;

public class Bullet extends Agent {
    private int direction;
    private int range;
    private int maxRange;
    private int[] pos = new int[2];
    private int[] oldpos = new int[2];
    private boolean hasHit = false;
    private Origin origin = null;

    Bullet(int dir, int range, int x, int y, int o) {
        this.direction = dir;
        this.range = range;
        this.maxRange = range;
        this.pos[0] = x;
        this.pos[1] = y;
        this.origin = (o == 0) ? Origin.SHIP : Origin.ISLAND;
    }

    private short[] clearTrace(short[] myImage) {
        myImage[(this.oldpos[1] * 48 + this.oldpos[0]) * 3 + 0] = (short) 0;
        myImage[(this.oldpos[1] * 48 + this.oldpos[0]) * 3 + 1] = (short) 177;
        myImage[(this.oldpos[1] * 48 + this.oldpos[0]) * 3 + 2] = (short) 241;
        return myImage;
    }

    public short[] clear(short[] myImage) {
        myImage[(this.pos[1] * 48 + this.pos[0]) * 3 + 0] = (short) 0;
        myImage[(this.pos[1] * 48 + this.pos[0]) * 3 + 1] = (short) 177;
        myImage[(this.pos[1] * 48 + this.pos[0]) * 3 + 2] = (short) 241;
        return myImage;
    }

    public short[] paint(short[] myImage) {
        myImage = clearTrace(myImage);
        if (this.range > 0) {
            myImage[(this.pos[1] * 48 + this.pos[0]) * 3 + 0] = (short) 12;
            myImage[(this.pos[1] * 48 + this.pos[0]) * 3 + 1] = (short) 13;
            myImage[(this.pos[1] * 48 + this.pos[0]) * 3 + 2] = (short) 12;
        } else {
            myImage = clear(myImage);
        }
        return myImage;
    }

    public int collide(short[] myImage) {
        return 0;
    }

    protected boolean hitPlayer(short[] myImage, int x, int y) {
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
        } else {
            return false;
        }
    }

    protected boolean hitEnemy(short[] myImage, int x, int y) {
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
        } else {
            return false;
        }
    }

    protected boolean hitIsland(short[] myImage, int x, int y) {
        if (x <= 47 && y <= 23 && x >= 0 && y >= 0) {
            int idx = (y * 48 + x) * 3;
            return (myImage[idx + 0] == 196 && myImage[idx + 1] == 156 && myImage[idx + 2] == 53) ||
                    (myImage[idx + 0] == 186 && myImage[idx + 1] == 148 && myImage[idx + 2] == 48) ||
                    (myImage[idx + 0] == 125 && myImage[idx + 1] == 66 && myImage[idx + 2] == 24);
        } else {
            return false;
        }
    }

    public void move(int dir, short[] myImage) {
    }

    private void saveOldPos() {
        this.oldpos[0] = this.pos[0];
        this.oldpos[1] = this.pos[1];
    }

    public boolean move() {
        if (canMove()) {
            saveOldPos();
            switch (this.direction) {
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
            return true;
        } else {
            return false;
        }
    }

    private boolean canMove() {
        boolean ret = false;
        switch (this.direction) {
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

    public short[] run(int key, short[] myImage) {
        if (this.range == this.maxRange) {
            if (move()) {
                myImage = paint(myImage);
            } else {
                this.range = 0;
                myImage = clear(myImage);
            }
        } else {
            if (this.origin == Origin.SHIP) {
                if (!(hitEnemy(myImage, this.pos[0], this.pos[1]) || hitPlayer(myImage, this.pos[0], this.pos[1]) || hitIsland(myImage, this.pos[0], this.pos[1]))) {
                    if (move()) {
                        myImage = paint(myImage);
                    } else {
                        this.range = 0;
                        myImage = clear(myImage);
                    }
                } else {
                    this.range = 0;
                    this.hasHit = true;
                    myImage = paint(myImage);
                }
            } else if (this.origin == Origin.ISLAND) {
                if (!(hitEnemy(myImage, this.pos[0], this.pos[1]) || hitPlayer(myImage, this.pos[0], this.pos[1]))) {
                    if (move()) {
                        myImage = paint(myImage);
                    } else {
                        this.range = 0;
                        myImage = clear(myImage);
                    }
                } else {
                    this.range = 0;
                    this.hasHit = true;
                    myImage = paint(myImage);
                }
            }
        }
        return myImage;
    }

    public boolean getHasHit() {
        return this.hasHit;
    }

    public int getRange() {
        return this.range;
    }

    enum Origin {
        ISLAND,
        SHIP
    }
}
