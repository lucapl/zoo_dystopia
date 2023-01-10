package ZooDystopia.Utils.Controllers;

import ZooDystopia.CartesianObject;
import ZooDystopia.Entities.Entity;
import ZooDystopia.Entities.Prey;
import ZooDystopia.Entities.RunnableEntity;
import ZooDystopia.GFX.Sprites.BasicSprite;
import ZooDystopia.GFX.ZOrders;
import ZooDystopia.Pathing.Route;
import ZooDystopia.Utils.Daemon;
import ZooDystopia.Utils.Factories.EntityFactory;
import ZooDystopia.World;

public class EntityController{
    private EntityFactory entityFactory;
    public EntityController(EntityFactory entityFactory){
        this.setEntityFactory(entityFactory);
    }
    /**
     * Allows the user to add a random entity to map
     * @param world where the entity will be stored
     */
    public void addRandom(World world){
        Entity entity = (Entity) getEntityFactory().create();
        addRandomlyPlaced(entity,world);
    }

    private void add(Entity entity, World world){
        BasicSprite sprite = world.getVisualizer().visualize(entity);
        world.getMapPanel().add(sprite);
        world.getMapPanel().layer(sprite, ZOrders.ENTITY);
        if(entity instanceof RunnableEntity rE) {
            new Daemon(rE).start();
        }
    }

    /**
     * Allows the user to add an entity to map
     * @param entity a runnable entity
     * @param world where the entity will be stored
     */
    public void addRandomlyPlaced(Entity entity, World world){
        world.add(entity);
        add(entity,world);
        return;
    }

    public void addRandomAt(CartesianObject place, World world){
        Entity entity = (Entity) getEntityFactory().create();
        addAt(entity,place,world);
    }
    public void addAt(Entity entity,CartesianObject place ,World world){
        world.addAt(entity,place);
        add(entity,world);
    }

    /**
     * Allows the user to input where the prey should go
     * @param prey chosen prey
     * @param route to what structure type it should go
     */
    public void forcePreyToGoTo(Prey prey, Route route){
        prey.finishStuff();
        prey.setForcedToGo(true);
        prey.setGoesTo(route);
    }

    public EntityFactory getEntityFactory() {
        return entityFactory;
    }

    public void setEntityFactory(EntityFactory entityFactory) {
        this.entityFactory = entityFactory;
    }
}
