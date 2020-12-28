import java.util.ArrayList;
import java.util.List;

public class World {
    private List<Island> islands;

    World(){
        this.islands = new ArrayList<Island>();
    }

    public void addIslands(Island s){
    this.islands.add(s);
}

    public short[] paintIslands(short[] myImage){
        for(Island i : this.islands){
            myImage = i.paint(myImage);
        }
        return myImage;
    }

    private boolean isWater(short[] myImage, int idx){
        return (myImage[idx] == 0 && myImage[idx + 1] == 177 && myImage[idx + 2] == 241);
    }

    public short[] createIsland(short[] myImage, int num){
        while (num > 0){
            int i = (int)(Math.random() * 45) + 1;
            int j = (int)(Math.random() * 21) + 1;
            if(     isWater(myImage, (((j+2) * 48 +  i   ) * 3)) &&
                    isWater(myImage, (((j+2) * 48 + (i+1)) * 3)) &&
                    isWater(myImage, (((j+2) * 48 + (i+2)) * 3)) &&
                    isWater(myImage, ((j     * 48 +  i   ) * 3)) &&
                    isWater(myImage, ((j     * 48 + (i+1)) * 3)) &&
                    isWater(myImage, ((j     * 48 + (i+2)) * 3)) &&
                    isWater(myImage, (((j+1) * 48 +  i   ) * 3)) &&
                    isWater(myImage, (((j+1) * 48 + (i+1)) * 3)) &&
                    isWater(myImage, (((j+1) * 48 + (i+2)) * 3))){
                int[] size = {3,3};
                if(Math.random() < 0.9){
                    addIslands(new Island(size,i,j));
                } else {
                    addIslands(new Island(size,i,j,new Harbor((int)(Math.random()*7)+1)));
                }
                myImage = paintIslands(myImage);
                num--;
                continue;
            }
        }
        return myImage;
    }
}
