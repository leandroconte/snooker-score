package org.lera.snooker.repository;

import org.lera.snooker.domain.Player;
import org.lera.snooker.domain.Score;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lnd on 09/02/17.
 */
@Repository
public interface ScoreRepository extends CrudRepository<Score, Long> {

    Iterable<Score> findAll();

    Score findOne(Long primaryKey);

    void delete(Score entity);
}
