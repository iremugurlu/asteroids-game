package asteroids.input;

import asteroids.entities.Player;
import asteroids.managers.EntityManager;
import asteroids.util.Vector2f;

import java.util.ArrayList;

public class BasicAI implements ControlUnit {

    /**
     *This method checks the next actions which can be performed based on internal logic.
     *
     * @param pos the current position of the entity.
     * @param rotation the current rotation of an entity.
     * @return a list of actions which can be performed.
     */
    public ArrayList<Actions> checkNextActions(Vector2f pos, float rotation) {
        ArrayList<Actions> output = new ArrayList<>();
        EntityManager entityManager = EntityManager.getInstance();
        if (entityManager.getPlayers()!=null&&entityManager.getPlayers().size()>0) {
            Player target = entityManager.getPlayers().get(0);
            Vector2f targetCoordinate = target.getPosition();
            final double deltaY = (targetCoordinate.getCoordinateY() - pos.getCoordinateY());
            final double deltaX = (targetCoordinate.getCoordinateX() - pos.getCoordinateX());
            final double targetRot = (Math.toDegrees(Math.atan2(deltaY, deltaX)) + 90);
            if (rotation > targetRot) {
                output.add(Actions.ROTATE_LEFT);
            }
            else {
                output.add(Actions.ROTATE_RIGHT);
            }
            output.add(Actions.ACCELERATE);
        }


        return output;
    }
}
