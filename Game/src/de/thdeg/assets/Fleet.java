import java.util.List;
import java.util.ArrayList;
public class Fleet {
    private List<Enemy> fleet;
    private int PX;
    private int PY;
    private boolean detected = false;

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
        while (num > 0){
            int i = (int)(Math.random() * 46) + 1;
            int j = (int)(Math.random() * 22) + 1;
            if(     isWater(myImage, (((j-1) * 48 +  i   ) * 3)) &&
                    isWater(myImage, (((j-1) * 48 + (i+1)) * 3)) &&
                    isWater(myImage, (((j-1) * 48 + (i-1)) * 3)) &&
                    isWater(myImage, ((j     * 48 +  i   ) * 3)) &&
                    isWater(myImage, ((j     * 48 + (i+1)) * 3)) &&
                    isWater(myImage, ((j     * 48 + (i-1)) * 3)) &&
                    isWater(myImage, (((j+1) * 48 +  i   ) * 3)) &&
                    isWater(myImage, (((j+1) * 48 + (i+1)) * 3)) &&
                    isWater(myImage, (((j+1) * 48 + (i-1)) * 3))){
                addFleetmember(new Enemy(2, i, j, 4, 10));
                myImage = paintFleet(myImage);
                num--;
                continue;
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

    public void resetDamageControl(){
        for (Enemy e : this.fleet){
            e.resetDmg();
        }
    }
    public int damageControl(){
        int ret = 0;
        for (Enemy e : this.fleet){
            ret += e.getDamageReceived();
        }
        return ret;
    }

    public void distributeDamage(int x, int y){
        for (Enemy e : fleet){
            if(e.includesPos(x, y)){
                e.damage(1);
                break;
            }
        }
    }

    public int getDead(){
        int ret = 0;
        for (Enemy e : fleet){
            if(!e.isAlive()){
                ret++;
            }
        }
        return ret;
    }

    public void printing(){
        for(Enemy e : this.fleet){
            e.print("text");
        }
    }

    private void broadcastPosition(){
        for(Enemy e : this.fleet){
            if(e.getPlayerDetected()){
                this.detected = true;
                this.PX = e.getPX();
                this.PY = e.getPY();
            }
        }
    }

    public short[] executeOrders(short[] myImage){
        broadcastPosition();
        for (Enemy s : this.fleet){
            if(s.isAlive()){
                s.setRouteX(this.PX);
                s.setRouteY(this.PY);
                myImage = s.run(myImage);
            }
        }
        return myImage;
    }

    public short[] statusUpdate(short[] myImage){
        for(Enemy e : this.fleet){
            if(e.isAlive()){
                myImage = e.isHit(myImage);
            }
        }
        return myImage;
    }

    public short[] paintFleet(short[] myImage){
        for (Enemy s : this.fleet) {
            if(s.isAlive()){
                myImage = s.paint(myImage);
            }
        }
        return myImage;
    }
}
