package io.maxi.api.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MaxiNftCollectionSellAddress extends BaseEntity {

    private String nftCollectionPresaleId;
    private String nftCollectionId;
    private String nftCollectionName;
    private String nftCollectionAddress;
    private String userAddress;
    private String userPrice;



}