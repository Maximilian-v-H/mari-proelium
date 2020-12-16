public class Island extends Agent {
    protected short[][] color = {{196, 156, 53},{186, 148, 48},{125, 66, 24}}; // normale Insel; Hafeninsel; Hafen
    protected int[][] pos;
    protected boolean captured = false;
    protected boolean possession = false;


    @Override
    short[] paint(short[] myImage) {
        return myImage;
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
        /*
        * Hafenmanagement -> Methode
        * */
        return myImage;
    }
}