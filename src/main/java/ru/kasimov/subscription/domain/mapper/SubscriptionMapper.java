package ru.kasimov.subscription.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.kasimov.subscription.domain.dto.CreateSubscriptionDto;
import ru.kasimov.subscription.domain.model.Subscription;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionMapper INSTANCE = Mappers.getMapper(SubscriptionMapper.class);

    // ====================== from DTOs ======================
    @Mapping(target = "user", ignore = true)
    Subscription createSubscriptionDtoToSubscription(CreateSubscriptionDto createSubscriptionDto);

    // ====================== to DTOs ========================
    CreateSubscriptionDto SubscriptionToCreateSubscriptionDto(Subscription Subscription);


}
