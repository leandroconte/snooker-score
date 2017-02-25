package org.lera.snooker.repository;

import org.lera.snooker.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lnd on 09/02/17.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User save(User entity);

    User findOne(Long primaryKey);

    Long countByUsernameOrName(String username, String name);

    void delete(User entity);

    Iterable<User> findAll();
}
