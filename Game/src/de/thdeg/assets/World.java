import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
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
                if(Math.random() < 0.75){
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

    public short[] parseImage(){
        short[] ret = new short[24*48*3];
        try {
            Scanner myReader = new Scanner(new File("./../src/de/thdeg/assets/intro.mvh"));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] pos = data.substring(0, data.indexOf(':')).split("-");
                String[] rgb = data.substring(data.indexOf(':')+1).split("-");
                ret[(Integer.parseInt(pos[1]) * 48 + Integer.parseInt(pos[0])) * 3 + 0] = Short.parseShort(rgb[0]);
                ret[(Integer.parseInt(pos[1]) * 48 + Integer.parseInt(pos[0])) * 3 + 1] = Short.parseShort(rgb[1]);
                ret[(Integer.parseInt(pos[1]) * 48 + Integer.parseInt(pos[0])) * 3 + 2] = Short.parseShort(rgb[2]);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return ret;
    }
}
