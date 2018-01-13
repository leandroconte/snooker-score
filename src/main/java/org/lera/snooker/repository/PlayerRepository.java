package org.lera.snooker.repository;

import org.lera.snooker.domain.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lnd on 09/02/17.
 */
@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {

    Player save(Player entity);

    Player findOne(Long primaryKey);

    Long countByName(String name);

    void delete(Player entity);

    Iterable<Player> findAll();
}
