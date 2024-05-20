/*
 * Copyright (c) 2021. Dandelion development
 */

package me.tuhin47.awsreactive.sns.sqs.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import me.tuhin47.awsreactive.sns.sqs.model.event.PublishEvent;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class ConsumerEventDto {

    private PublishEvent publishEvent;
    private LocalDateTime consumedAt;
    private ConsumeEventResult consumeEventResult;
}
