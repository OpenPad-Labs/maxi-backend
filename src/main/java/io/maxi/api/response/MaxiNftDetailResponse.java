package io.maxi.api.response;

import io.maxi.api.entity.MaxiNftCollection;
import io.maxi.api.request.BaseRequest;
import lombok.Data;

import java.util.List;

@Data
public class MaxiNftDetailResponse extends MaxiNftCollection {


    private String twitter;
    private String discord;
    private String telegram;
    private String website;
    private String crew3;
    private String galxe;
    private String medium;

    private String cover;
    private String roadmap;


    private String totalSupply;
    private String minted;
    private String userMaxMintNum;
    private String price;

}
