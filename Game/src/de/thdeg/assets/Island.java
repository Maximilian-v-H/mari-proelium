public class Island extends Agent {

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