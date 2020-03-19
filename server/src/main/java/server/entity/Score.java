package server.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(name = "name")
    private Player player;
    private int value;
    private Timestamp date;

    public Player getPlayer() {
        return player;
    }

    public int getValue() {
        return value;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}