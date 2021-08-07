package tk.mjsv.utile;

public class ChunkLoc {
    private int x;
    private int z;

    public ChunkLoc(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public Integer getX() {
        return x;
    }

    public Integer getZ() {
        return z;
    }
    public String toString(){
        String s = x+","+z;
        return s;
    }


}
