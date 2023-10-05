package core;

import interfaces.Entity;
import interfaces.Repository;
import model.Invoice;
import model.StoreClient;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

public class Data implements Repository {
private final Queue<Entity>entities;

public Data(){
    this.entities =  new PriorityQueue<>();
}

    public Data(Data other) {
    this.entities = new PriorityQueue<>(other.entities);
    }

    @Override
    public void add(Entity entity) {
        if (entity.getParentId()>0){
            Entity parent = this.getById(entity.getParentId());

            parent.addChild(entity);
        }



this.entities.offer(entity);
    }

    @Override
    public Entity getById(int id) {

//       return entitiesById.values()
//               .stream()
//               .filter(e->e.getId()==id)
//               .findAny()
//               .orElse(null);

        for (Entity entity : entities) {
            if (entity.getId()==id){
                return entity;
            }
        }

        return null;

    }

    @Override
    public List<Entity> getByParentId(int id) {
        List<Entity>result = new ArrayList<>();
        Entity parent = this.getById(id);

        if (parent!=null){
           result.addAll(parent.getChildren());
        }


        return result;
    }

    @Override
    public List<Entity> getAll() {
        return new ArrayList<>(this.entities);
    }

    @Override
    public Repository copy() {
        return new Data(this);
    }

    @Override
    public List<Entity> getAllByType(String type) {
        final boolean isLocalModel = type.equals(Invoice.class.getSimpleName()) || type.equals(StoreClient.class.getSimpleName())
                || type.equals(User.class.getSimpleName());
        if (!isLocalModel) throw new IllegalArgumentException("Illegal type: " + type);

     return    this.entities.stream()
                .filter(e->e.getClass().getSimpleName().equals(type))
                .collect(Collectors.toList());

    }

    @Override
    public Entity peekMostRecent() {
    if (this.entities.isEmpty()){
        throw new IllegalStateException("Operation on empty Data");
    }
        return this.entities.peek();
    }

    @Override
    public Entity pollMostRecent() {
        if (this.entities.isEmpty()){
            throw new IllegalStateException("Operation on empty Data");
        }
        return this.entities.poll();
    }

    @Override
    public int size() {
        return this.entities.size();
    }
}
