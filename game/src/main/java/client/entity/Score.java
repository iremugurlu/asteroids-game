package client.entity;

import java.sql.Timestamp;


public class Score {
    private transient String name;
    private transient int value;
    private transient Timestamp date;

    /**
     * Constructor for the Score class.
     *
     * @param name String of the username.
     * @param value Integer value of the score.
     * @param date Timestamp of the end of the game.
     */
    public Score(String name, int value, Timestamp date) {
        this.name = name;
        this.value = value;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public Timestamp getDate() {
        return date;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Score.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Score other = (Score) obj;
        return this.getName().equals(other.getName())
                && this.getValue() == other.getValue()
                && this.getDate().toString().equals(other.getDate().toString());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
