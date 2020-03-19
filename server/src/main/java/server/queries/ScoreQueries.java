package server.queries;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import server.entity.Score;

import java.util.List;

public interface ScoreQueries extends CrudRepository<Score, Integer> {

    @Query(value = "SELECT * FROM Score ORDER BY value DESC LIMIT 5", nativeQuery = true)
    List<Score> getHighScores();

    @Query(value = "SELECT * FROM Score WHERE name = ?1 ORDER BY value DESC LIMIT 5", nativeQuery = true)
    List<Score> getHighScoresByPlayer(String name);

    @Query(value = "SELECT * FROM Score WHERE name = ?1 ORDER BY date DESC", nativeQuery = true)
    List<Score> getScoresByPlayer(String name);
}