import java.util.List;
import java.util.ArrayList;

// package de.thdeg.game;

// import de.thdeg.game.runtime.InternalLedGameThread;

public class GameMain {

    static public void main(String[] passedArgs) throws InterruptedException {
        short[] myImage=new short[24*48*3];
        int thisKey=0;

        // This is initialization, do not change this
        InternalLedGameThread.run();

        // Now we show some introductory message and wait 3s before we switch to purple
        System.out.println("Please wait for three seconds until the LED shows purple color, then you can use the cursor keys in order to move a green dot over the LED display");
        Thread.sleep(1000);
        for(int i=0; i<myImage.length; i+=3){
            myImage[i+0]=(short)0;
            myImage[i+1]=(short)177;
            myImage[i+2]=(short)241;
        }

        System.out.println("Sending to displayThread");
        InternalLedGameThread.showImage(myImage);


        List<Ship> fleet = new ArrayList<Ship>();
        Player p = new Player(3, 7, 7, 5);
        fleet.add(new Enemy(2, 12, 15, 3));
        fleet.add(new Enemy(2, 9, 12, 3));
        fleet.add(new Enemy(2, 20, 15, 3));
        fleet.add(new Enemy(2, 30, 15, 3));


        myImage = p.paint(myImage);
        for (Ship s : fleet) {
            myImage = s.paint(myImage);
            s.print("Ship:");
        }
        p.print("Start");
        InternalLedGameThread.showImage(myImage);
        int round = 0;
        while(p.isAlive()){
            thisKey = InternalLedGameThread.getKeyboard();
            myImage = p.run(thisKey, myImage);
            if(round % 3 == 0) {
                for (Ship s : fleet){
                    if(s.isAlive()){
                        myImage = s.run((int)(Math.random() * 4), myImage);
                    }
                }
            }
            InternalLedGameThread.showImage(myImage);
            round++;
            Thread.sleep(100);
        }
    }
}
