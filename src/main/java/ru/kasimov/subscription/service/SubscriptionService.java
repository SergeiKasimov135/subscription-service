package ru.kasimov.subscription.service;

import ru.kasimov.subscription.domain.dto.CreateSubscriptionDto;
import ru.kasimov.subscription.domain.model.Subscription;

import java.util.List;

public interface SubscriptionService {

    Iterable<Subscription> findAll();

    List<String> getTopSubscriptions(int limit);

    Subscription createSubscription(Long userId, CreateSubscriptionDto createSubscriptionDto);

    Iterable<Subscription> findSubscriptionsByUserId(Long userId);

    void deleteSubscriptionById(Long subscriptionId);
    
}
