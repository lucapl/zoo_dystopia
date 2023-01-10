package ZooDystopia.Structures;

import ZooDystopia.CartesianObject;
import ZooDystopia.Entities.Entity;
import ZooDystopia.Pathing.Path;
import ZooDystopia.Pathing.Route;

import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.List;

public abstract class Structure extends CartesianObject {
    private int capacity;
    private List<Path> paths;
    private List<Entity> entitiesAt;
    private Semaphore semaphore;

    public Structure(int cap){
        setCapacity(cap);
        setPaths(new LinkedList<>());
        setEntitiesAt(new Vector<>(cap));
        setSemaphore(new Semaphore(cap));
    }
    public String toString(){
        return "Structure\n" + "Capacity: " + getEntitiesAt().size() +"/" + getCapacity();//\n" + super.toString() + "\n" + getPaths();
    }
    public void add(Path path){
        getPaths().add(path);
    }
    public void add(Entity entity){
        getEntitiesAt().add(entity);
    }
    public void remove(Entity entity){ getEntitiesAt().remove(entity);}

    public int getCapacity() {
        return capacity;
    }

    public List<Structure> getChildren(){
        List<Structure> children = new LinkedList<>();
        for(Path path: getPaths()){
            children.add(path.getTheOtherEnd(this));
        }
        return children;
    }
    public boolean doesLeadTo(Route route){
        for(Path path: getPaths()){
            if(path.doesLeadTo(path.getTheOtherEnd(this),route)){
                return true;
            }
        }
        return false;
    }
    public Path getPathThatLeadsTo(Structure structure){
        for(Path path: getPaths()){
            if(path.getTheOtherEnd(this) == structure){
                return path;
            }
        }
        return null;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }

    public synchronized List<Entity> getEntitiesAt() {
        return entitiesAt;
    }

    public synchronized void setEntitiesAt(List<Entity> entitiesAt) {
        this.entitiesAt = entitiesAt;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Structure struct)){
            return false;
        }
        return super.equals(o) && getPaths() == struct.getPaths() && getSemaphore() == getSemaphore();
    }
}
