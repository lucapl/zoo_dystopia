package ZooDystopia.Utils.Tiling;

import java.util.LinkedList;
import java.util.List;

public class MooreTiling implements Tiling{

    @Override
    public List<int[]> getNeighbours(int row){
        return getNeighbours();
    }

    /**
     * Moore's tiling doesn't depend on the row so the return is always the same
     * @return the list of offsets
     */
    public List<int[]> getNeighbours() {
        List<int[]> offsets = new LinkedList<>();
        offsets.add(new int[]{-1, -1});
        offsets.add(new int[]{-1, 0});
        offsets.add(new int[]{-1, 1});
        offsets.add(new int[]{0, -1});
        offsets.add(new int[]{0,  1});
        offsets.add(new int[]{1, -1});
        offsets.add(new int[]{1, 0});
        offsets.add(new int[]{1, 1});
        return offsets;
    }
}
