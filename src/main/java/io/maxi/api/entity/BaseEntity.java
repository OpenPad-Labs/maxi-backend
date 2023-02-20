package io.maxi.api.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {
    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

}