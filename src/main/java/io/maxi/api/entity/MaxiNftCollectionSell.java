package io.maxi.api.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MaxiNftCollectionSell extends BaseEntity {

    private String nftCollectionPresaleId;
    private String nftCollectionId;
    private String nftCollectionName;
    private String nftCollectionAddress;
    private String sellType;
    private String sellOrder;
    private String startUnix;
    private String endUnix;
    private String price;
    private String singleMin;
    private String maxiHolderSupply;
    private String normalSupply;
    private String surplu;
    private String additionalIssue;




}