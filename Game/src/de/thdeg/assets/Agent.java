public abstract class Agent {

    abstract short[] paint(short[] myImage);

    abstract int collide(short[] myImage);

    abstract void move(int dir);

    abstract short[] run(int key, short[] myImage);
}
