public class Island extends Agent {
    protected short[][] color = {{196, 156, 53},{186, 148, 48}}; // normale Insel, Hafeninsel
    protected int[][][] pos;
    protected Harbor harbor = null;

    Island(int[][][] pos, Harbor harbor){
        this.pos = pos;
        this.harbor = harbor;
    }

    Island(int[][][] pos){
        this.pos = pos;
    }

    @Override
    short[] paint(short[] myImage) {
        for(int i1 = 0; i1 < this.pos.length; i1++){
            for(int i2 = 0; i2 < this.pos.length; i2++){

            }
        }
        return myImage;
    }

    @Override
    int collide(short[] myImage) {
        return -1;
    }

    @Override
    void move(int dir) {
    }

    @Override
    short[] run(int key, short[] myImage) {
        return myImage;
    }
}
