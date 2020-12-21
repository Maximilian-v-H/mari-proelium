import java.util.ArrayList;
import java.util.List;
public class GameMain {

    static public void main(String[] passedArgs) throws InterruptedException {
        short[] myImage = new short[24*48*3];
        List<Integer> highscore = new ArrayList<Integer>();
        int thisKey=0;
        int frame = 0;
        int round = 1;
        long startTime = System.currentTimeMillis();
        long roundtime = 30000;

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
        Player p = new Player(3, 7, 7, 5);
        Fleet fleet = new Fleet();
        int[] islandPosition = {3,3};
        Island island = new Island(islandPosition,20,20);

        myImage = fleet.employFleet(myImage, 3);
        myImage = p.paint(myImage);
        myImage = fleet.paintFleet(myImage);
        myImage = island.paint(myImage);
        InternalLedGameThread.showImage(myImage);
        while(p.isAlive()){
            thisKey = InternalLedGameThread.getKeyboard();
            myImage = p.run(thisKey, myImage);
            if(p.getHit()){
                fleet.distributeDamage(p.getHitX(), p.getHitY());
            }
            myImage = fleet.statusUpdate(myImage);
            if(frame % 3 == 0) {
                frame = 0;
                myImage = fleet.executeOrders(myImage);
            }
            InternalLedGameThread.showImage(myImage);
            frame++;
            Thread.sleep(100);
            System.out.println("+++ " + (System.currentTimeMillis() - startTime) + " +++");
            p.damage(fleet.damageControl());
            fleet.resetDamageControl();
            if((System.currentTimeMillis() - startTime) > roundtime){
                myImage = fleet.employFleet(myImage, (3 - fleet.getNumberOfAliveShips()));
                round++;
                startTime = System.currentTimeMillis();
                p.addScore(200);
            }
        }
        p.addScore(fleet.getDead() * 50);
        highscore.add(p.getScore());
        System.out.println("(" + round + ") - Score: " + p.getScore());
    }
}
