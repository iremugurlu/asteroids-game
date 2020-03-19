package asteroids.util;

public class Vector2f {
    private float coordinateX;
    private float coordinateY;

    public Vector2f(float x, float y) {
        this.coordinateX = x;
        this.coordinateY = y;
    }

    /**
     * Method that calculates the Euclidean distance between two Vector2f.
     * @param a Vector2f first parameter.
     * @param b Vector2f second parameter.
     * @return double Euclidean distance between a and b.
     */
    public static double distance(Vector2f a, Vector2f b) {
        double sum =
                Math.pow(a.coordinateX - b.coordinateX, 2)
                        + Math.pow(a.coordinateY - b.coordinateY, 2);
        return Math.sqrt(sum);
    }

    /**
     * Adds two Vector2f together.
     * @param other Vector2f to add to this.
     * @return Vector2f result of the addition between this and other.
     */
    public Vector2f add(Vector2f other) {
        this.coordinateX += other.coordinateX;
        this.coordinateY += other.coordinateY;
        return this;
    }

    public float getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(float coordinateX) {
        this.coordinateX = coordinateX;
    }

    public float getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(float coordinateY) {
        this.coordinateY = coordinateY;
    }
}
