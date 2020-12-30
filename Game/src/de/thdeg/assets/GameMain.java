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

        // This is initialization, donot change this
        InternalLedGameThread.run();

        // Now we show some introductory message and wait 3s before we switch to purple
        System.out.println("Willkommen bei Mari proelium!\n In kuerze wird das Spiel beginnen und Ihr Punktestand wird mit den anderen Spielern verglichen!\n");
        Thread.sleep(1000);

        boolean end = false;
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("Sending to displayThread");
            Fleet fleet = new Fleet();
            World world = new World();
            myImage = world.parseImage("intro");
            InternalLedGameThread.showImage(myImage);
            Thread.sleep(500);
            myImage = world.fade(myImage);
            InternalLedGameThread.showImage(myImage);
            Thread.sleep(500);
            myImage = world.fade(myImage);
            InternalLedGameThread.showImage(myImage);
            Thread.sleep(500);
            myImage = world.fade(myImage);
            InternalLedGameThread.showImage(myImage);
            Thread.sleep(500);
            myImage = world.parseImage("round1");
            InternalLedGameThread.showImage(myImage);
            Thread.sleep(500);
            myImage = world.clear();
            myImage = world.createIsland(myImage, 5);
            myImage = fleet.employFleet(myImage, 3);
            Player p = Player.spawn(myImage,1);
            myImage = p.paint(myImage);
            myImage = fleet.paintFleet(myImage);
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
                if(frame % 4 == 0) {
                    frame = 0;
                    myImage = fleet.executeOrders(myImage);
                }
                myImage = world.runHarbor(myImage);
                myImage = world.paintIslands(myImage);
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
                    String s = "round" + round;
                    System.out.println(s);
                    myImage = world.parseImage(s);
                    InternalLedGameThread.showImage(myImage);
                    Thread.sleep(1000);
                    myImage = world.clear();
                }
                if(fleet.getNumberOfAliveShips() == 0){
                    p.addScore(50);
                }
            }
            world.clear();
            myImage = world.parseImage("gameover");
            InternalLedGameThread.showImage(myImage);
            p.addScore(fleet.getDead() * 50);
            highscore.add(p.getScore().getScore());
            Collections.sort(highscore);
            Collections.reverse(highscore);
            System.out.println("(" + round + ") - Score: " + p.getScore().getScore());
            System.out.println("Highscores:");
            for(int i = 0; i < highscore.size(); i++){
                System.out.println("(" + (i+1) + ") -> " + highscore.get(i));
            }
            myImage = world.parseImage("commands");
            InternalLedGameThread.showImage(myImage);
            Thread.sleep(1500);
            while(true){
                thisKey = InternalLedGameThread.getKeyboard();
                if(thisKey != -1){
                    break;
                }
            }
            switch(thisKey){
                case 0 -> {
                    end = false;
                }
                case 1 -> {
                    end = true;
                    System.exit(0);
                }
                case 2 -> {
                    end = true;
                    System.exit(0);
                }
                case 3 -> {
                    end = true;
                    System.exit(0);
                }
            }
        }while(!end);
    }
}
