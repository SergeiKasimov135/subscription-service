package ru.kasimov.subscription.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kasimov.subscription.domain.model.Subscription;
import ru.kasimov.subscription.domain.model.User;

import java.util.List;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {

    List<Subscription> findByUserId(Long userId);

    boolean existsByUserAndName(User user, String name);

}
