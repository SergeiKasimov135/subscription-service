package ru.kasimov.subscription.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kasimov.subscription.domain.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Iterable<User> findAllByUsernameLikeIgnoreCase(String filter);

}
