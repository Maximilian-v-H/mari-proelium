import java.util.List;
import java.util.ArrayList;

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


        Player p = new Player(3, 7, 7, 5);
        Fleet fleet = new Fleet();
        fleet.addFleetmember(new Enemy(2, 12, 15, 1, 5));
        fleet.addFleetmember(new Enemy(2, 9, 12, 3, 5));
        fleet.addFleetmember(new Enemy(2, 20, 15, 7, 5));
        fleet.addFleetmember(new Enemy(2, 30, 12, 8, 5));
        fleet.addFleetmember(new Enemy(2, 23, 6, 2, 5));
        fleet.addFleetmember(new Enemy(2, 40, 4, 5, 5));
        fleet.addFleetmember(new Enemy(2, 35, 3, 4, 5));


        myImage = p.paint(myImage);
        myImage = fleet.paintFleet(myImage);
        p.print("Start");
        InternalLedGameThread.showImage(myImage);
        int round = 0;
        while(p.isAlive()){
            thisKey = InternalLedGameThread.getKeyboard();
            myImage = p.run(thisKey, myImage);
            if(round % 3 == 0) {
                fleet.executeOrders(myImage);
            }
            InternalLedGameThread.showImage(myImage);
            round++;
            Thread.sleep(100);
        }
    }
}
