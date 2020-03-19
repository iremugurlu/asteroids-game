package asteroids.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Vector2fTest {

    /**
     * Tests the constructor of the Vector2f class.
     */
    @Test
    public void constructorTest() {
        Vector2f vector = new Vector2f(5, 6);

        assertEquals(vector.getCoordinateX(), 5);
        assertEquals(vector.getCoordinateY(), 6);
    }

    /**
     * Tests the setCoordinateX method.
     */
    @Test
    public void setCoordinateXTest() {
        Vector2f vector = new Vector2f(5, 6);
        vector.setCoordinateX(9);

        assertEquals(vector.getCoordinateX(), 9);
        assertEquals(vector.getCoordinateY(), 6);
    }

    /**
     * Tests the setCoordinateY method.
     */
    @Test
    public void setCoordinateYTest() {
        Vector2f vector = new Vector2f(5, 6);
        vector.setCoordinateY(9);

        assertEquals(vector.getCoordinateX(), 5);
        assertEquals(vector.getCoordinateY(), 9);
    }

    /**
     * Tests the add method.
     */
    @Test
    public void addTest() {
        Vector2f vector = new Vector2f(5, 6);
        Vector2f vector2 = new Vector2f(1, 3);
        vector.add(vector2);

        assertEquals(vector.getCoordinateX(), 6);
        assertEquals(vector.getCoordinateY(), 9);
    }

    /**
     * Tests the distance method.
     */
    @Test
    public void distanceTest() {
        Vector2f vector = new Vector2f(5, 6);
        Vector2f vector2 = new Vector2f(1, 3);


        assertEquals(Vector2f.distance(vector, vector2), 5);
    }
}

