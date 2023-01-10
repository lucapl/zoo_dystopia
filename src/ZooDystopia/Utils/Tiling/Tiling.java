package ZooDystopia.Utils.Tiling;

import java.util.List;

public interface Tiling {
    /**
     * Strategy to get different neighbourhood offsets given a tiling
     * @param row if tiling differs based on the row
     * @return the list of offsets
     */
    public List<int[]> getNeighbours(int row);
}
