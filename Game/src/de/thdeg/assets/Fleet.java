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

    private boolean isWater(short[] myImage, int idx){
        return (myImage[idx] == 0 && myImage[idx + 1] == 177 && myImage[idx + 2] == 241);
    }

    public short[] employFleet(short[] myImage, int num){
        for(int i = 1; i < 47; i++){
            for(int j = 1; j < 23; j++){
                if(     isWater(myImage, (((j-1) * 48 +  i   ) * 3)) &&
                        isWater(myImage, (((j-1) * 48 + (i+1)) * 3)) &&
                        isWater(myImage, (((j-1) * 48 + (i-1)) * 3)) &&
                        isWater(myImage, ((j     * 48 +  i   ) * 3)) &&
                        isWater(myImage, ((j     * 48 + (i+1)) * 3)) &&
                        isWater(myImage, ((j     * 48 + (i-1)) * 3)) &&
                        isWater(myImage, (((j+1) * 48 +  i   ) * 3)) &&
                        isWater(myImage, (((j+1) * 48 + (i+1)) * 3)) &&
                        isWater(myImage, (((j+1) * 48 + (i-1)) * 3))){
                    if(num > 0){
                        addFleetmember(new Enemy(2, i, j, 4, 8));
                        myImage = paintFleet(myImage);
                        num--;
                        continue;
                    }
                        }
            }
        }
        return myImage;
    }

    public int getNumberOfAliveShips(){
        int ret = 0;
        for (Enemy s : this.fleet){
            if(s.isAlive()){
                ret++;
            }
        }
        return ret;
    }

    public int damageControl(){
        int ret = 0;
        for (Enemy e : this.fleet){
            ret += e.getDamageReceived();
        }
        return ret;
    }

    public void printing(){
        for(Enemy e : this.fleet){
            e.print("text");
        }
    }

    public short[] executeOrders(short[] myImage){
        for (Enemy s : this.fleet){
            if(s.isAlive()){
                myImage = s.run(myImage);
            }
        }
        return myImage;
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
