package asteroids.managers;

import asteroids.entities.Entity;
import asteroids.util.Vector2f;

import java.util.ArrayList;

public class CollisionsManager {

    private transient ArrayList<Entity> entities;

    public CollisionsManager(ArrayList<Entity> entities) {
        this.entities = entities;
    }

    /**
     * Method that calculates collisions between all Entities.
     */
    public void calculateCollisions() {
        //Loop through all the entities.
        for (int ii = 0; ii < entities.size(); ii++) {
            if (!entities.get(ii).isAlive()) {
                continue;
            }
            for (int jj = ii + 1; jj < entities.size(); jj++) {
                if (!entities.get(jj).isAlive()) {
                    continue;
                }
                //If they collide, call collision method of both Entities.
                if (colliding(entities.get(ii), entities.get(jj))) {
                    Entity entityI = entities.get(ii);
                    Entity entityJ = entities.get(jj);

                    entityI.onCollision(entityJ);
                    entityJ.onCollision(entityI);
                }
            }
        }
    }

    /**
     * Method that compares distances of Entities for collision.
     *
     * @param a Entity first.
     * @param b Entity second.
     * @return boolean the a is partially inside of b.
     */
    private boolean colliding(Entity a, Entity b) {
        double distance = Vector2f.distance(a.getPosition(), b.getPosition());
        return (distance - a.getDiameterCollision() / 2 - b.getDiameterCollision() / 2 < 0);
    }
}
