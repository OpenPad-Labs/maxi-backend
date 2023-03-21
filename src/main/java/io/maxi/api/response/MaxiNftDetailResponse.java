package io.maxi.api.response;

import io.maxi.api.entity.MaxiNftCollection;
import io.maxi.api.request.BaseRequest;
import lombok.Data;

import java.util.List;

@Data
public class MaxiNftDetailResponse extends MaxiNftCollection {

    public static final Long day = 24 * 60 * 60 * 1000L;

    private String twitter;
    private String discord;
    private String telegram;
    private String website;
    private String crew3;
    private String galxe;
    private String medium;

    private String tag="Game,PFP,Casso";

    private String cover;
    private String roadmap="roadmap text";


    private String totalSupply="3000";
    private String minted="1200";
    private String userMaxMintNum="10";

    private String team="team text";
    private String faq="faq text";
    private String overview="overview text";

    private String privateSaleStartTime = String.valueOf(System.currentTimeMillis() - day);
    private String privateSaleEndTime = String.valueOf(System.currentTimeMillis() + day);
    private String privateSalePrice= "0.1";
    private String privateSaleText = "private Sale Text";
    private String privateSaleUserMaxMintNum="10";
    private String privateSaleTotalSupply="1000";

    private String airDropStartTime = String.valueOf(System.currentTimeMillis() + 2*day);
    private String airDropEndTime = String.valueOf(System.currentTimeMillis() + 3*day);
    private String airDropPrice= "0.0";
    private String airDropText  = "airDrop Text";
    private String airDropUserMaxMintNum="10";
    private String airDropTotalSupply="1000";

    private String publicSaleStartTime = String.valueOf(System.currentTimeMillis() + 4*day);
    private String publicEndTime = String.valueOf(System.currentTimeMillis() + 5*day);
    private String publicSalePrice= "0.88";
    private String publicSaleText  = "public Sale Text";
    private String publicSaleUserMaxMintNum="10";
    private String publicSaleTotalSupply="1000";




    private String startTime = String.valueOf(System.currentTimeMillis() - day);
    private String endTime = String.valueOf(System.currentTimeMillis() + day);



}
