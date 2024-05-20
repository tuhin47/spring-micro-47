/*
 * Copyright (c) 2021. Dandelion development
 */

package me.tuhin47.awsreactive.sns.sqs.model.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ItemInfo {

    private String id;
    private String itemName;
    private String itemUrl;
}
