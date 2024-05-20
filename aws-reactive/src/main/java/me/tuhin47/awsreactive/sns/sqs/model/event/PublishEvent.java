/*
 * Copyright (c) 2021. Dandelion development
 */

package me.tuhin47.awsreactive.sns.sqs.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import me.tuhin47.awsreactive.sns.sqs.model.domain.ItemInfo;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class PublishEvent {

    private String publishEventId;
    private PublishEventType publishEventType;
    private LocalDateTime publishedAt;
    private ItemInfo itemInfo;
}
