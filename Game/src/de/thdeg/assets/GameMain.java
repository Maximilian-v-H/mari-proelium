import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
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
        System.out.println("Willkommen bei Mari proelium!\n In kürze wird das Spiel beginnen und Ihr Punktestand wird mit den anderen Spielern verglichen!\n");
        Thread.sleep(1000);

        boolean end = false;
        Scanner scan = new Scanner(System.in);
        do {
            for(int i=0; i<myImage.length; i+=3){
                myImage[i+0]=(short)0;
                myImage[i+1]=(short)177;
                myImage[i+2]=(short)241;
            }

            System.out.println("Sending to displayThread");
            Player p = new Player(3, 7, 7, 5);
            Fleet fleet = new Fleet();
            int[] islandPosition = {3,3};
            Island island = new Island(islandPosition,20,20, new Harbor(3));

            myImage = fleet.employFleet(myImage, 3);
            myImage = p.paint(myImage);
            myImage = fleet.paintFleet(myImage);
            myImage = island.paint(myImage);
            InternalLedGameThread.showImage(myImage);
            System.out.println("Drucken Sie eine beliebige Taste um das Spiel zu starten.");
            while(true){
                if(InternalLedGameThread.getKeyboard() != -1){
                    break;
                }
            }
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
                myImage = island.paint(myImage);
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
                if(fleet.getNumberOfAliveShips() == 0){
                    p.addScore(50);
                }
            }

            p.addScore(fleet.getDead() * 50);
            highscore.add(p.getScore());
            Collections.sort(highscore);
            System.out.println("(" + round + ") - Score: " + p.getScore());
            System.out.println("Highscores:");
            for(int i = 0; i < highscore.size(); i++){
                System.out.println("(" + (i+1) + ") -> " + highscore.get(i));
            }

            System.out.println("Wollen Sie noch eine Runde spielen? (Y/N)\n> ");
            end = (scan.next() == "Y") ? false : true;
        }while(!end);
    }
}
