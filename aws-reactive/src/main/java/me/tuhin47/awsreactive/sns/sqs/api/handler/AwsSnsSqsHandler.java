/*
 * Copyright (c) 2021. Dandelion development
 */

package me.tuhin47.awsreactive.sns.sqs.api.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.tuhin47.awsreactive.sns.sqs.model.domain.ItemInfo;
import me.tuhin47.awsreactive.sns.sqs.model.dto.ConsumeEventResult;
import me.tuhin47.awsreactive.sns.sqs.model.dto.ConsumerEventDto;
import me.tuhin47.awsreactive.sns.sqs.model.event.PublishEvent;
import me.tuhin47.awsreactive.sns.sqs.model.event.PublishEventType;
import me.tuhin47.awsreactive.sns.sqs.service.MessageConsumerService;
import me.tuhin47.awsreactive.sns.sqs.service.MessagePublishService;
import me.tuhin47.awsreactive.sns.sqs.storage.ItemInfoMockStorage;
import me.tuhin47.awsreactive.sns.sqs.storage.PublishEventStorage;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class AwsSnsSqsHandler {

    private final MessagePublishService messagePublishService;
    private final MessageConsumerService messageConsumerService;

    @NonNull
    public Mono<ServerResponse> publishSaveItemInfoEvent(ServerRequest request) {
        return publishItemInfoEvent(request.bodyToMono(ItemInfo.class), PublishEventType.ITEM_INFO_CREATED);
    }

    @NonNull
    public Mono<ServerResponse> publishViewItemInfoEvent(ServerRequest request) {
        return publishItemInfoEvent(Mono.just(ItemInfoMockStorage.getMockItemInfo()), PublishEventType.ITEM_INFO_VIEWED);
    }

    @NonNull
    public Mono<ServerResponse> publishDeleteItemInfoEvent(ServerRequest request) {
        return publishItemInfoEvent(Mono.just(ItemInfoMockStorage.getMockItemInfo()), PublishEventType.ITEM_INFO_DELETED);
    }

    private Mono<ServerResponse> publishItemInfoEvent(Mono<ItemInfo> itemInfoMono, PublishEventType publishEventType) {

        final String publishEventId = UUID.randomUUID().toString();
        return itemInfoMono
            .doOnNext(
                itemInfo -> {
                    PublishEvent publishEvent = createPublishEvent(itemInfo, publishEventType);
                    messagePublishService.publishMessage(publishEvent);
                    messageConsumerService.consumeMessage(publishEvent, publishEventId);
                })
            .flatMap(
                itemInfo ->
                    ok().contentType(APPLICATION_JSON)
                        .body(createConsumerEventDto(publishEventId), ConsumerEventDto.class));
    }

    private Mono<ConsumerEventDto> createConsumerEventDto(String publishEventId) {
        PublishEvent publishEvent =
            PublishEventStorage.getAndRemovePublishEventFromStorage(publishEventId);
        if (Objects.nonNull(publishEvent)) {
            return Mono.just(
                new ConsumerEventDto()
                    .setPublishEvent(publishEvent)
                    .setConsumedAt(LocalDateTime.now())
                    .setConsumeEventResult(ConsumeEventResult.SUCCESS));
        }
        return Mono.just(
            new ConsumerEventDto()
                .setConsumedAt(LocalDateTime.now())
                .setConsumeEventResult(ConsumeEventResult.FAILURE));
    }

    private PublishEvent createPublishEvent(ItemInfo itemInfo, PublishEventType publishEventType) {
        return new PublishEvent()
            .setPublishEventId(UUID.randomUUID().toString())
            .setPublishedAt(LocalDateTime.now())
            .setItemInfo(itemInfo.setId(UUID.randomUUID().toString()))
            .setPublishEventType(publishEventType);
    }
}
