package io.maxi.api.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MaxiAiUserPoint extends BaseEntity {

    private String address;
    private Integer consumedPoint;
    private Integer accumulatedPoints;
    private Integer available;


}