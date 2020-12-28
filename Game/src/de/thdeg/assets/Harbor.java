public class Harbor extends Agent{
    protected short[] color = {125, 66, 24};
    protected int orient;
    protected boolean captured = false;
    protected boolean possession = false;

    Harbor(int orient){
        this.orient = orient;
    }

    public int getOrient(){
        return this.orient;
    }

    public short[] getColor() {
        return this.color;
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
        return new short[0];
    }
}
