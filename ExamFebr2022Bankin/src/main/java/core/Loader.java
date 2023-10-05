package core;

import interfaces.Buffer;
import interfaces.Entity;
import model.BaseEntity;
import org.apache.velocity.runtime.directive.MacroParseException;

import java.util.*;
import java.util.stream.Collectors;

public class Loader implements Buffer {
private List<Entity> entitiesById = new ArrayList<>();


    @Override
    public void add(Entity entity) {
entitiesById.add(entity);
    }

    @Override
    public Entity extract(int id) {
        Entity result =null;
//       return entitiesById.values()
//               .stream()
//               .filter(e->e.getId()==id)
//               .findAny()
//               .orElse(null);

        for (Entity entity : entitiesById) {
            if (entity.getId()==id){
              result=entity;
              break;
            }
        }
        if (result!=null) this.entitiesById.remove(result);

        return result;
    }

    @Override
    public Entity find(Entity entity) {
        int index = entitiesById.indexOf(entity);
        if (index<0) {
            return null;
        }
        return entitiesById.get(index);
    }

    @Override
    public boolean contains(Entity entity) {
        return entitiesById.contains(entity);
    }

    @Override
    public int entitiesCount() {
        return entitiesById.size();
    }

    @Override
    public void replace(Entity oldEntity, Entity newEntity) {
int old = this.entitiesById.indexOf(oldEntity);
if (old<0){
    throw new IllegalStateException("Entity not found");
}
this.entitiesById.set(old,newEntity);
    }

    @Override
    public void swap(Entity first, Entity second) {
        int indexOne = this.entitiesById.indexOf(first);
        int indexTwo = this.entitiesById.indexOf(second);
if (indexOne<0 || indexTwo<0){
    throw new IllegalStateException("Entities not found");
}
Collections.swap(entitiesById,indexOne,indexOne);
    }

    @Override
    public void clear() {
this.entitiesById.clear();
    }

    @Override
    public Entity[] toArray() {
        Entity[]result = new Entity[this.entitiesById.size()];
        this.entitiesById.toArray(result);
        return result;
    }

    @Override
    public List<Entity> retainAllFromTo(BaseEntity.Status lowerBound, BaseEntity.Status upperBound) {
        List<Entity> result = new ArrayList<>();

     return    this.entitiesById
                .stream()
                .filter(e->e.getStatus().ordinal()>= lowerBound.ordinal())
                .filter(e->e.getStatus().ordinal()<= upperBound.ordinal())
                .collect(Collectors.toList());

    }

    @Override
    public List<Entity> getAll() {
        return new ArrayList<>(this.entitiesById);
    }

    @Override
    public void updateAll(BaseEntity.Status oldStatus, BaseEntity.Status newStatus) {
//        for (Entity entity : entitiesById) {
//            if (entity.getStatus()==oldStatus) entity.setStatus(newStatus);
//        }
       entitiesById.stream().filter(e->e.getStatus().equals(oldStatus)).forEach(entity -> entity.setStatus(newStatus));
    }

    @Override
    public void removeSold() {

        entitiesById.removeIf(e->e.getStatus()== BaseEntity.Status.SOLD);

    }

    @Override
    public Iterator<Entity> iterator() {
        return this.entitiesById.iterator();
    }
}
