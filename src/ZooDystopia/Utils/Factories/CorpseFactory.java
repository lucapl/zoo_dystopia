package ZooDystopia.Utils.Factories;

import ZooDystopia.Entities.Corpse;
import ZooDystopia.Entities.Entity;

public class CorpseFactory extends EntityFactory{
    private Entity entityToDie;
    public CorpseFactory(Entity entity){
        setEntityToDie(entity);
    }

    @Override
    public Object create(){
        Corpse corpse = new Corpse(getEntityToDie().getName(), getEntityToDie().getSpecies(), getEntityToDie().getStrength());
        corpse.setDeathReason(getEntityToDie().getDeathReason());
        corpse.set(getEntityToDie());
        return corpse;
    }

    public Entity getEntityToDie() {
        return entityToDie;
    }

    public void setEntityToDie(Entity entityToDie) {
        this.entityToDie = entityToDie;
    }
}
