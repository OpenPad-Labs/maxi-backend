package io.maxi.api.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MaxiNftCollection extends BaseEntity {

    private String nftCollectionId;
    private String nftCollectionName;
    private String nftCollectionAddress;
    private String nftCollectionSlogan;



    private String nftCollectionDesc;
    private String nftCollectionIcon;
    private String nftCollectionCover;
    private String nftCollectionFeature;
    private String nftCollectionTeam;
    private String nftCollectionRoadmap;


    private String nftCollectionSupply;
    private String nftCollectionTax;
    private String nftCollectionSocialLinks;


    private String nftCollectionWalletAddress;
    private String nftCollectionPlaceholderArtwork;
    private String nftCollectionArtwork;
    private String nftCollectionAttributes;
    private String nftCollectionSaveLine;

    private String sortNum;

    private String status;

    private String extInfo;



}