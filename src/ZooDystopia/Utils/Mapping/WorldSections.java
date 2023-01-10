package ZooDystopia.Utils.Mapping;

import ZooDystopia.CartesianObject;
import ZooDystopia.Pathing.Path;
import ZooDystopia.Structures.Structure;
import ZooDystopia.Utils.GraphConnector;
import ZooDystopia.Utils.Randomizer;
import ZooDystopia.Utils.Tiling.HexTiling;
import ZooDystopia.Utils.Tiling.Tiling;
import ZooDystopia.World;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class WorldSections {

    private int sectionCount;

    private Structure[][] sections;
    private List<CartesianObject> freeSpaces;
    private List<CartesianObject> expandableSpaces;
    private List<CartesianObject> expandedSpaces;
    private List<CartesianObject> takenSpaces;
    private List<Path> freePaths;
    private List<Path> takenPaths;
    private Tiling tiling;
    public WorldSections(int sectionCount){
        this.sectionCount = sectionCount;
        this.sections = new Structure[sectionCount][sectionCount];
        this.freeSpaces = new LinkedList<CartesianObject>();
        expandableSpaces = new LinkedList<CartesianObject>();
        expandedSpaces = new LinkedList<CartesianObject>();
        freePaths = new LinkedList<Path>();
        takenPaths = new LinkedList<Path>();
        tiling = new HexTiling();
        for(int i = 0;i<sectionCount;i++){
            for(int j =0 ;j<sectionCount;j++){
                CartesianObject point = new CartesianObject();
                point.setX(i);
                point.setY(j);
                freeSpaces.add(point);
            }
        }
        this.takenSpaces = new LinkedList<CartesianObject>();
    }

    public int getSectionCoords(int x,int maxWidth){
        int sectionWidth = maxWidth/(sectionCount*2);
        return (int)(((float)(x%(sectionCount+1))/(float)sectionCount)*(float)maxWidth) + sectionWidth;
    }
    public int getSectionCoords(int x,int maxWidth,int offset){
        int y = getSectionCoords(x,maxWidth);
        Random random = new Random();
        if (offset != 0) {
            return y + random.nextInt(2 * offset) - offset;
        }
        return y;
    }
    public Structure getStructureAt(int x,int y){
        return sections[y][x];
    }
    public CartesianObject findStructure(Structure structure){
        int x = -1;
        int y = -1;
        boolean found = false;
        for(int i = 0; i<sectionCount; i++){
            for(int j = 0; j<sectionCount; j++){
                if(structure == sections[i][j]){
                    found = true;
                    x = j;
                    y = i;
                    break;
                }
            }
            if(found){
                break;
            }
        }
        assert found;
        CartesianObject point = new CartesianObject();
        point.setX(x);
        point.setY(y);
        return point;
    }
    public List<Structure> getNeighbours(Structure structure){
        CartesianObject point = findStructure(structure);
        return getNeighbours(point);
    }
    public List<Structure> getNeighbours(CartesianObject point){
        List<Structure> neighbours = new LinkedList<>();
        int x = (int)point.getX();
        int y = (int)point.getY();
        for (int[] offset: tiling.getNeighbours(x)){
            int i,j;
            i = offset[0];
            j = offset[1];
            int dx = x+i;
            if (!checkIfInBounds(dx)) {
                continue;
            }
            int dy = y+j;
            if (!checkIfInBounds(dy)) {
                continue;
            }
            Structure considered = sections[dy][dx];
            if(considered != null){
                neighbours.add(considered);
            }
        }
        return neighbours;
    }
    public CartesianObject getRandomFreeSpace(Structure captureStruct){
        Randomizer<CartesianObject> rand = new Randomizer<>();
        CartesianObject point = rand.getRandomFrom(freeSpaces);
        freeSpaces.remove(point);
        takenSpaces.add(point);
        add(captureStruct,point);
        return point;
    }
    public Path getRandomFreePath(){
        Randomizer<Path> rand = new Randomizer<>();
        Path path = rand.getRandomFrom(getFreePaths());
        getFreePaths().remove(path);
        getTakenPaths().add(path);
        return path;
    }

    public void determineFreeSpaces(int count){
        Randomizer<CartesianObject> rand = new Randomizer<>();
        for(int i = 0; i<count;i++){
            CartesianObject point;
            if(expandedSpaces.isEmpty()){
                point = rand.getRandomFrom(freeSpaces);
            }else{
                point = rand.getRandomFrom(expandableSpaces);
            }
            expandedSpaces.add(point);
            determineExpandableSpaces(point);
        }
    }

    public void determineExpandableSpaces(CartesianObject point){
        int x = (int)point.getX();
        int y = (int)point.getY();
        for(var offset: tiling.getNeighbours(x)){
            int i, j;
            i = offset[0];
            j = offset[1];
            int dx = x+i;
            if (!checkIfInBounds(dx)) {
                continue;
            }
            int dy = y+j;
            if (!checkIfInBounds(dy)) {
                continue;
            }
            CartesianObject newPoint = new CartesianObject();
            newPoint.setX(dx);
            newPoint.setY(dy);

            if(!expandableSpaces.contains(newPoint) && freeSpaces.contains(newPoint)) {
                expandableSpaces.add(newPoint);
            }
        }
    }

    public void determinePossiblePaths(){
        for(var point: takenSpaces){
            int x = (int)point.getX();
            int y = (int)point.getY();
            Structure firstEnd = sections[y][x];
            for(var otherEnd: getNeighbours(point)){
                Path possiblePath = new Path(firstEnd,otherEnd);
                if(!getFreePaths().contains(possiblePath) && !getTakenPaths().contains(possiblePath)) {
                    getFreePaths().add(possiblePath);
                }
            }
        }
    }

    public CartesianObject getRandomPlace(Structure captureStruct){
        CartesianObject point;
        if(expandedSpaces.isEmpty()){
            point = getRandomFreeSpace(captureStruct);
        }else{
            Randomizer<CartesianObject> randomizer = new Randomizer<>();
            point = randomizer.getRandomFrom(expandedSpaces);
            add(captureStruct,point);
            expandedSpaces.remove(point);
            freeSpaces.remove(point);
            takenSpaces.add(point);
        }
        determineExpandableSpaces(point);
        return point;
    }
    public int randomlyConnect(World world){
        GraphConnector<Structure> graphConnector = new GraphConnector<>(world.getStructures()) {
            @Override
            public List<Structure> getChildren(Structure structure) {
                return getNeighbours(structure);
            }
        };
        List<Vector<Structure>> edges = graphConnector.randomlyConnect();
        for(var edge: edges){
            Path newPath = new Path(edge);
            world.add(newPath);
            getTakenPaths().add(newPath);
        }
        return edges.size();
    }
    public boolean checkIfInBounds(int x){
        return x >= 0 && x<sectionCount;
    }
    public void add(Structure structure,CartesianObject point){
        int x,y;
        x = (int)point.getX();
        y = (int)point.getY();
        sections[y][x] = structure;
    }

    public List<Path> getFreePaths() {
        return freePaths;
    }

    public void setFreePaths(List<Path> freePaths) {
        this.freePaths = freePaths;
    }

    public List<Path> getTakenPaths() {
        return takenPaths;
    }

    public void setTakenPaths(List<Path> takenPaths) {
        this.takenPaths = takenPaths;
    }
}
