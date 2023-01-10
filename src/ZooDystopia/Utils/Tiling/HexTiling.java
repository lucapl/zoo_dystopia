package ZooDystopia.Utils.Tiling;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HexTiling implements Tiling{
    @Override
    public List<int[]> getNeighbours(int row) {
        List<int[]> offsets = new LinkedList<>(); //even
        offsets.add(new int[]{-1, 0});
        offsets.add(new int[]{-1,1});
        offsets.add(new int[]{0, -1});
        offsets.add(new int[]{0, 1});
        offsets.add(new int[]{1, 0});
        offsets.add(new int[]{1, 1});
        if(row%2 == 1){ //oddify
            offsets.get(1)[1] = -1;
            offsets.get(5)[1] = -1;
        }
        return offsets;
    }
}
