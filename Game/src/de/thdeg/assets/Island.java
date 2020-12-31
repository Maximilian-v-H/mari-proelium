import java.util.List;

public class Island extends Agent {
    protected short[][] color = {{196, 156, 53},{186, 148, 48}}; // normale Insel, Hafeninsel
    protected int[][][] pos;
    protected int[] size;
    protected Harbor harbor = null;

    Island(int[] size, int x, int y){
        this.pos = new int[size[0]][size[1]][2];
        for(int i1 = 0; i1 < size[0]; i1++){
            for(int i2 = 0; i2 < size[1]; i2++){
                this.pos[i1][i2][0] = x + i1;
                this.pos[i1][i2][1] = y + i2;
            }
        }
    }

    Island(int[] size, int x, int y, Harbor harbor){
        this.pos = new int[size[0]][size[1]][2];
        for(int i1 = 0; i1 < size[0]; i1++){
            for(int i2 = 0; i2 < size[1]; i2++){
                this.pos[i1][i2][0] = x + i1;
                this.pos[i1][i2][1] = y + i2;
            }
        }
        this.harbor = harbor;
    }

    @Override
    short[] paint(short[] myImage) {
        for(int i1 = 0; i1 < this.pos.length; i1++){
            for(int i2 = 0; i2 < this.pos[i1].length; i2++){
                if(harbor == null){
                    myImage[(this.pos[i1][i2][1] * 48 + this.pos[i1][i2][0]) * 3 + 0] = this.color[0][0]; // (y * 48 + x) * 3 + 0
                    myImage[(this.pos[i1][i2][1] * 48 + this.pos[i1][i2][0]) * 3 + 1] = this.color[0][1]; // (y * 48 + x) * 3 + 1
                    myImage[(this.pos[i1][i2][1] * 48 + this.pos[i1][i2][0]) * 3 + 2] = this.color[0][2]; // (y * 48 + x) * 3 + 2
                }
                else{
                    myImage[(this.pos[i1][i2][1] * 48 + this.pos[i1][i2][0]) * 3 + 0] = this.color[1][0]; // (y * 48 + x) * 3 + 0
                    myImage[(this.pos[i1][i2][1] * 48 + this.pos[i1][i2][0]) * 3 + 1] = this.color[1][1]; // (y * 48 + x) * 3 + 1
                    myImage[(this.pos[i1][i2][1] * 48 + this.pos[i1][i2][0]) * 3 + 2] = this.color[1][2]; // (y * 48 + x) * 3 + 2
                    switch(harbor.getOrient()){
                        case 1:
                            myImage[(this.pos[0][(int)(this.pos[0].length/2)][1] * 48 + this.pos[0][(int)(this.pos[0].length/2)][0]) * 3 + 0] = (short)(harbor.getColor()[harbor.getPossession() + 1][0] - harbor.getHasBonus()); // (y * 48 + x) * 3 + 0
                            myImage[(this.pos[0][(int)(this.pos[0].length/2)][1] * 48 + this.pos[0][(int)(this.pos[0].length/2)][0]) * 3 + 1] = (short)(harbor.getColor()[harbor.getPossession() + 1][1] - harbor.getHasBonus()); // (y * 48 + x) * 3 + 1
                            myImage[(this.pos[0][(int)(this.pos[0].length/2)][1] * 48 + this.pos[0][(int)(this.pos[0].length/2)][0]) * 3 + 2] = (short)(harbor.getColor()[harbor.getPossession() + 1][2] - harbor.getHasBonus()); // (y * 48 + x) * 3 + 2
                            harbor.setPos(this.pos[0][(int)(this.pos[0].length/2)][1],this.pos[0][(int)(this.pos[0].length/2)][0]);
                            break;
                        case 2:
                            myImage[(this.pos[0][(int)(this.pos[0].length-1)][1] * 48 + this.pos[0][(int)(this.pos[0].length-1)][0]) * 3 + 0] = (short)(harbor.getColor()[harbor.getPossession() + 1][0] - harbor.getHasBonus()); // (y * 48 + x) * 3 + 0
                            myImage[(this.pos[0][(int)(this.pos[0].length-1)][1] * 48 + this.pos[0][(int)(this.pos[0].length-1)][0]) * 3 + 1] = (short)(harbor.getColor()[harbor.getPossession() + 1][1] - harbor.getHasBonus()); // (y * 48 + x) * 3 + 1
                            myImage[(this.pos[0][(int)(this.pos[0].length-1)][1] * 48 + this.pos[0][(int)(this.pos[0].length-1)][0]) * 3 + 2] = (short)(harbor.getColor()[harbor.getPossession() + 1][2] - harbor.getHasBonus()); // (y * 48 + x) * 3 + 2
                            harbor.setPos(this.pos[0][(int)(this.pos[0].length-1)][1],this.pos[0][(int)(this.pos[0].length-1)][0]);
                            break;
                        case 3:
                            myImage[(this.pos[(int)(this.pos.length/2)][(int)(this.pos[0].length-1)][1] * 48 + this.pos[(int)(this.pos.length/2)][(int)(this.pos[0].length-1)][0]) * 3 + 0] = (short)(harbor.getColor()[harbor.getPossession() + 1][0] - harbor.getHasBonus()); // (y * 48 + x) * 3 + 0
                            myImage[(this.pos[(int)(this.pos.length/2)][(int)(this.pos[0].length-1)][1] * 48 + this.pos[(int)(this.pos.length/2)][(int)(this.pos[0].length-1)][0]) * 3 + 1] = (short)(harbor.getColor()[harbor.getPossession() + 1][1] - harbor.getHasBonus()); // (y * 48 + x) * 3 + 1
                            myImage[(this.pos[(int)(this.pos.length/2)][(int)(this.pos[0].length-1)][1] * 48 + this.pos[(int)(this.pos.length/2)][(int)(this.pos[0].length-1)][0]) * 3 + 2] = (short)(harbor.getColor()[harbor.getPossession() + 1][2] - harbor.getHasBonus()); // (y * 48 + x) * 3 + 2
                            harbor.setPos(this.pos[(int)(this.pos.length/2)][(int)(this.pos[0].length-1)][1],this.pos[(int)(this.pos.length/2)][(int)(this.pos[0].length-1)][0]);
                            break;
                        case 4:
                            myImage[(this.pos[(int)(this.pos.length-1)][(int)(this.pos[0].length-1)][1] * 48 + this.pos[(int)(this.pos.length-1)][(int)(this.pos[0].length-1)][0]) * 3 + 0] = (short)(harbor.getColor()[harbor.getPossession() + 1][0] - harbor.getHasBonus()); // (y * 48 + x) * 3 + 0
                            myImage[(this.pos[(int)(this.pos.length-1)][(int)(this.pos[0].length-1)][1] * 48 + this.pos[(int)(this.pos.length-1)][(int)(this.pos[0].length-1)][0]) * 3 + 1] = (short)(harbor.getColor()[harbor.getPossession() + 1][1] - harbor.getHasBonus()); // (y * 48 + x) * 3 + 1
                            myImage[(this.pos[(int)(this.pos.length-1)][(int)(this.pos[0].length-1)][1] * 48 + this.pos[(int)(this.pos.length-1)][(int)(this.pos[0].length-1)][0]) * 3 + 2] = (short)(harbor.getColor()[harbor.getPossession() + 1][2] - harbor.getHasBonus()); // (y * 48 + x) * 3 + 2
                            harbor.setPos(this.pos[(int)(this.pos.length-1)][(int)(this.pos[0].length-1)][1],this.pos[(int)(this.pos.length-1)][(int)(this.pos[0].length-1)][0]);
                            break;
                        case 5:
                            myImage[(this.pos[(int)(this.pos.length-1)][(int)(this.pos[0].length/2)][1] * 48 + this.pos[(int)(this.pos.length-1)][(int)(this.pos[0].length/2)][0]) * 3 + 0] = (short)(harbor.getColor()[harbor.getPossession() + 1][0] - harbor.getHasBonus()); // (y * 48 + x) * 3 + 0
                            myImage[(this.pos[(int)(this.pos.length-1)][(int)(this.pos[0].length/2)][1] * 48 + this.pos[(int)(this.pos.length-1)][(int)(this.pos[0].length/2)][0]) * 3 + 1] = (short)(harbor.getColor()[harbor.getPossession() + 1][1] - harbor.getHasBonus()); // (y * 48 + x) * 3 + 1
                            myImage[(this.pos[(int)(this.pos.length-1)][(int)(this.pos[0].length/2)][1] * 48 + this.pos[(int)(this.pos.length-1)][(int)(this.pos[0].length/2)][0]) * 3 + 2] = (short)(harbor.getColor()[harbor.getPossession() + 1][2] - harbor.getHasBonus()); // (y * 48 + x) * 3 + 2
                            harbor.setPos(this.pos[(int)(this.pos.length-1)][(int)(this.pos[0].length/2)][1],this.pos[(int)(this.pos.length-1)][(int)(this.pos[0].length/2)][0]);
                            break;
                        case 6:
                            myImage[(this.pos[(int)(this.pos.length-1)][0][1] * 48 + this.pos[(int)(this.pos.length-1)][0][0]) * 3 + 0] = (short)(harbor.getColor()[harbor.getPossession() + 1][0] - harbor.getHasBonus()); // (y * 48 + x) * 3 + 0
                            myImage[(this.pos[(int)(this.pos.length-1)][0][1] * 48 + this.pos[(int)(this.pos.length-1)][0][0]) * 3 + 1] = (short)(harbor.getColor()[harbor.getPossession() + 1][1] - harbor.getHasBonus()); // (y * 48 + x) * 3 + 1
                            myImage[(this.pos[(int)(this.pos.length-1)][0][1] * 48 + this.pos[(int)(this.pos.length-1)][0][0]) * 3 + 2] = (short)(harbor.getColor()[harbor.getPossession() + 1][2] - harbor.getHasBonus()); // (y * 48 + x) * 3 + 2
                            harbor.setPos(this.pos[(int)(this.pos.length-1)][0][1],this.pos[(int)(this.pos.length-1)][0][0]);
                            break;
                        case 7:
                            myImage[(this.pos[(int)(this.pos.length/2)][0][1] * 48 + this.pos[(int)(this.pos.length/2)][0][0]) * 3 + 0] = (short)(harbor.getColor()[harbor.getPossession() + 1][0] - harbor.getHasBonus()); // (y * 48 + x) * 3 + 0
                            myImage[(this.pos[(int)(this.pos.length/2)][0][1] * 48 + this.pos[(int)(this.pos.length/2)][0][0]) * 3 + 1] = (short)(harbor.getColor()[harbor.getPossession() + 1][1] - harbor.getHasBonus()); // (y * 48 + x) * 3 + 1
                            myImage[(this.pos[(int)(this.pos.length/2)][0][1] * 48 + this.pos[(int)(this.pos.length/2)][0][0]) * 3 + 2] = (short)(harbor.getColor()[harbor.getPossession() + 1][2] - harbor.getHasBonus()); // (y * 48 + x) * 3 + 2
                            harbor.setPos(this.pos[(int)(this.pos.length/2)][0][1],this.pos[(int)(this.pos.length/2)][0][0]);
                            break;
                        case 8:
                            myImage[(this.pos[0][0][1] * 48 + this.pos[0][0][0]) * 3 + 0] = (short)(harbor.getColor()[harbor.getPossession() + 1][0] - harbor.getHasBonus()); // (y * 48 + x) * 3 + 0
                            myImage[(this.pos[0][0][1] * 48 + this.pos[0][0][0]) * 3 + 1] = (short)(harbor.getColor()[harbor.getPossession() + 1][1] - harbor.getHasBonus()); // (y * 48 + x) * 3 + 1
                            myImage[(this.pos[0][0][1] * 48 + this.pos[0][0][0]) * 3 + 2] = (short)(harbor.getColor()[harbor.getPossession() + 1][2] - harbor.getHasBonus()); // (y * 48 + x) * 3 + 2
                            harbor.setPos(this.pos[0][0][1],this.pos[0][0][0]);
                            break;
                    }
                }
            }
        }
        return myImage;
    }

    public boolean hasHarbor(){
        return (this.harbor != null) ? true : false;
    }

    public Harbor getHarbor(){
        return this.harbor;
    }

    @Override
    int collide(short[] myImage) {
        return -1;
    }

    @Override
    void move(int dir,short[] myImage) {
    }

    @Override
    short[] run(int key, short[] myImage) {
        return myImage;
    }

}
