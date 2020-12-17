public class Harbor extends Agent{
    protected short[] color = {125, 66, 24};
    protected int[][] pos;
    protected boolean captured = false;
    protected boolean possession = false;

    @Override
    short[] paint(short[] myImage) {
        return new short[0];
    }

    @Override
    boolean collide(short[] myImage) {
        return false;
    }

    @Override
    void move(int dir) {

    }

    @Override
    short[] run(int key, short[] myImage) {
        return new short[0];
    }
}
