package ru.kasimov.subscription.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kasimov.subscription.domain.dto.CreateSubscriptionDto;
import ru.kasimov.subscription.domain.mapper.SubscriptionMapper;
import ru.kasimov.subscription.domain.model.Subscription;
import ru.kasimov.subscription.domain.model.User;
import ru.kasimov.subscription.repository.SubscriptionRepository;
import ru.kasimov.subscription.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultSubscriptionService implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Iterable<Subscription> findAll() {
        log.info("Finding all subscriptions");

        try {
            return this.subscriptionRepository.findAll();
        } catch (Exception e) {
            log.error("Failed to find all subscriptions. Exception: {}", e.getMessage());
            throw new RuntimeException("Failed to find all subscriptions");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getTopSubscriptions(int limit) {
        log.info("Finding top {} subscriptions", limit);

        try {
            return StreamSupport.stream(this.subscriptionRepository.findAll().spliterator(), false)
                    .collect(Collectors.groupingBy(Subscription::getName, Collectors.counting()))
                    .entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(limit)
                    .map(Map.Entry::getKey)
                    .toList();
        } catch (Exception e) {
            log.error("Failed to find top {} subscriptions. Exception: {}", limit, e.getMessage());
            throw new RuntimeException("Failed to find top %d subscriptions".formatted(limit));
        }
    }

    @Override
    @Transactional
    public Subscription createSubscription(Long userId, CreateSubscriptionDto createSubscriptionDto) {
        log.info("Creating subscription {} for user with id: {}", createSubscriptionDto.name(), userId);

        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> {
                        log.error("Failed to create subscription for user with id: {}", userId);
                        return new NoSuchElementException("User not found with id: " + userId);
                    });

            if (subscriptionRepository.existsByUserAndName(user, createSubscriptionDto.name())) {
                log.error("Failed to create subscription {} for user with id: {}", createSubscriptionDto.name(), userId);
                throw new IllegalArgumentException("User already has a subscription with name: " + createSubscriptionDto.name());
            }

            Subscription subscription = SubscriptionMapper.INSTANCE.createSubscriptionDtoToSubscription(createSubscriptionDto);
            subscription.setUser(user);

            return this.subscriptionRepository.save(subscription);
        } catch (Exception e) {
            log.error("Failed to create subscription: {}. Exception: {}", createSubscriptionDto.name(), e.getMessage());
            throw new RuntimeException("Failed to create subscription");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Subscription> findSubscriptionsByUserId(Long userId) {
        log.info("Finding subscriptions for user with id: {}", userId);

        try {
            List<Subscription> subscriptions = subscriptionRepository.findByUserId(userId);
            if (subscriptions.isEmpty()) {
                log.error("Failed to find subscriptions for user with id: {}", userId);
                throw new NoSuchElementException("No subscriptions found for user id: " + userId);
            }
            return subscriptions;
        } catch (Exception e) {
            log.error("Failed to find subscription by user id: {}. Exception: {}", userId, e.getMessage());
            throw new RuntimeException("Failed to create subscription");
        }
    }

    @Override
    @Transactional
    public void deleteSubscriptionById(Long subscriptionId) {
        log.info("Deleting subscription with id: {}", subscriptionId);
        try {
            this.subscriptionRepository.deleteById(subscriptionId);
        } catch (Exception e) {
            log.error("Failed to delete subscription with subscription id: {}. Exception: {}", subscriptionId, e.getMessage());
            throw new RuntimeException("Failed to delete subscription");
        }
    }
}
