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

    private boolean canPlaceIsland(short[] myImage, int rad, int x, int y){
        boolean ret = true;
        for(int i = -rad; i <= rad; i++){
            for(int j = -rad; j <= rad; j++){
                if((y+i) >= 0 && (y+i) <= 23 && (x+j) <= 47 && (x+j) >= 0 && isWater(myImage, (((y+i) * 48 + (x+j)) * 3)) && ret){
                    ret = true;
                }else {
                    return false;
                }
            }
        }
        return ret;
    }

    public short[] createIsland(short[] myImage, int num){
        while (num > 0){
            int i = (int)(Math.random() * 45) + 1;
            int j = (int)(Math.random() * 21) + 1;
            if(canPlaceIsland(myImage,5,i,j)){
                int[] size = {3,3};
                if(Math.random() < 0.75){
                    addIslands(new Island(size,i,j));
                } else {
                    addIslands(new Island(size,i,j,new Harbor((int)(Math.random()*7)+1)));
                }
                myImage = paintIslands(myImage);
                num--;
            }
        }
        return myImage;
    }

    public short[] runHarbor(short[] myImage){
        for(Island i : this.islands){
            if(i.hasHarbor()){
                myImage = i.getHarbor().run(-1,myImage);
            }
        }
        return myImage;
    }

    public short[] clear(){
        short[] ret = new short[24*48*3];
        for(int i=0; i<ret.length; i+=3){
            ret[i+0]=(short)0;
            ret[i+1]=(short)177;
            ret[i+2]=(short)241;
        }
        return ret;
    }

    public short[] fade(short[] myImage){
        for (int i = 0; i < myImage.length; i += 3){
            double gr = myImage[i + 1] / (myImage[i]+1);
            double br = myImage[i + 2] / (myImage[i]+1);
            myImage[i] = (short) (myImage[i] * 0.7);
            myImage[i + 1] = (short) (myImage[i] * gr);
            myImage[i + 2] = (short) (myImage[i] * br);
        }
        return myImage;
    }

    public short[] parseImage(String what){
        short[] ret = new short[24*48*3];
        Scanner myReader = null;
        try {
            switch(what){
                case "intro" -> {
                    myReader = new Scanner(new File("Game"+File.separator+"src"+File.separator+"de"+File.separator+"thdeg"+File.separator+"assets"+File.separator+"img"+File.separator+"intro.mvh"));
                }
                case "gameover" -> {
                    myReader = new Scanner(new File("Game"+File.separator+"src"+File.separator+"de"+File.separator+"thdeg"+File.separator+"assets"+File.separator+"img"+File.separator+"gameover.mvh"));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.length() > 1 && data.substring(0,1).equals("B")){
                    for(int i = 0; i < ret.length; i += 3){
                        String[] s = data.substring(1).split("-");
                        ret[i] = Short.parseShort(s[0]);
                        ret[i + 1] = Short.parseShort(s[1]);
                        ret[i + 2] = Short.parseShort(s[2]);
                    }
                }else{
                    String[] pos = data.substring(0, data.indexOf(':')).split("-");
                    String[] rgb = data.substring(data.indexOf(':')+1).split("-");
                    ret[(Integer.parseInt(pos[1]) * 48 + Integer.parseInt(pos[0])) * 3 + 0] = Short.parseShort(rgb[0]);
                    ret[(Integer.parseInt(pos[1]) * 48 + Integer.parseInt(pos[0])) * 3 + 1] = Short.parseShort(rgb[1]);
                    ret[(Integer.parseInt(pos[1]) * 48 + Integer.parseInt(pos[0])) * 3 + 2] = Short.parseShort(rgb[2]);
                }
            }
            myReader.close();
        return ret;
    }
}
