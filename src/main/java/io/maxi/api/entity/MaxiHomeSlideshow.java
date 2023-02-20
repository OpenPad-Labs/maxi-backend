package io.maxi.api.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class MaxiHomeSlideshow extends BaseEntity {

    private String title;
    private String imageUrl;
    private String httpUrl;
    private String description;
    private String sortNum;



}