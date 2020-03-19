package server.queries;

import server.entity.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PlayerQueries extends CrudRepository<Player, Integer> {

    @Query(value = "SELECT * FROM Player WHERE name = ?1", nativeQuery = true)
    Player getPlayerByName(String name);

    @Query(value = "SELECT score FROM Player WHERE name = ?1", nativeQuery = true)
    long getScoreByName(String name);

    @Query(value = "SELECT COUNT(*) FROM Player WHERE name = ?1", nativeQuery = true)
    int countPlayerByName(String name);
}
