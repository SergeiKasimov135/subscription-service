package ru.kasimov.subscription.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kasimov.subscription.domain.dto.CreateSubscriptionDto;
import ru.kasimov.subscription.domain.model.Subscription;
import ru.kasimov.subscription.service.SubscriptionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubscriptionRestController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/users/{id}/subscriptions")
    public ResponseEntity<Subscription> createSubscription(
            @PathVariable Long id,
            @RequestBody @Valid CreateSubscriptionDto createSubscriptionDto) {
        return ResponseEntity.ok(this.subscriptionService.createSubscription(id, createSubscriptionDto));
    }

    @GetMapping("/subscriptions")
    public ResponseEntity<Iterable<Subscription>> findAll() {
        return ResponseEntity.ok(this.subscriptionService.findAll());
    }

    @GetMapping("/subscriptions/top")
    public ResponseEntity<List<String>> getTopSubscriptions(@RequestParam(defaultValue = "3") int limit) {
        return ResponseEntity.ok(this.subscriptionService.getTopSubscriptions(limit));
    }

    @GetMapping("/users/{id}/subscriptions")
    public ResponseEntity<Iterable<Subscription>> findSubscriptionsByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(this.subscriptionService.findSubscriptionsByUserId(id));
    }

    @DeleteMapping("/users/{id}/subscriptions/{sub_id}")
    public ResponseEntity<Void> deleteSubscriptionById(@PathVariable("sub_id") Long subId) {
        this.subscriptionService.deleteSubscriptionById(subId);
        return ResponseEntity.noContent().build();
    }

}
