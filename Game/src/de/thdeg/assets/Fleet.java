import java.util.List;
import java.util.ArrayList;
public class Fleet {
    private List<Enemy> fleet;

    Fleet(){
        this.fleet = new ArrayList<Enemy>();
    }

    public void addFleetmember(Enemy s){
        this.fleet.add(s);
    }

    public void executeOrders(short[] myImage){
        for (Enemy s : this.fleet){
            if(s.isAlive()){
                myImage = s.run(myImage);
            }
        }
    }

    public short[] statusUpdate(short[] myImage){
        for(Enemy e : this.fleet){
            myImage = e.isHit(myImage);
        }
        return myImage;
    }

    public short[] paintFleet(short[] myImage){
        for (Enemy s : this.fleet) {
            myImage = s.paint(myImage);
        }
        return myImage;
    }
}
