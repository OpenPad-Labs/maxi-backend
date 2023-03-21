package io.maxi.api.response;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.maxi.api.entity.MaxiNftCollection;
import io.maxi.api.request.BaseRequest;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

@Data
public class MaxiNftDetailResponse extends MaxiNftCollection {

    public static final Long day = 24 * 60 * 60 * 1000L;

    private String twitter = "";
    private String discord = "";
    private String telegram = "";
    private String website = "";
    private String crew3 = "";
    private String galxe = "";
    private String medium = "";
    private String cover = "";

    private String tag="Game,PFP,Casso";

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



    public void flush(){
        String extInfo = getExtInfo();
        if(StringUtils.isNotEmpty(extInfo)){
            Map map = JSON.parseObject(extInfo);

            totalSupply = (String) map.getOrDefault("totalSupply",totalSupply);
            minted = (String) map.getOrDefault("minted",minted);
            userMaxMintNum = (String) map.getOrDefault("userMaxMintNum",userMaxMintNum);

            team = (String) map.getOrDefault("team",team);
            faq = (String) map.getOrDefault("faq",faq);
            overview = (String) map.getOrDefault("overview",overview);

            privateSaleStartTime = (String) map.getOrDefault("privateSaleStartTime",privateSaleStartTime);
            privateSaleEndTime = (String) map.getOrDefault("privateSaleEndTime",privateSaleEndTime);
            privateSalePrice = (String) map.getOrDefault("privateSalePrice",privateSalePrice);
            privateSaleText = (String) map.getOrDefault("privateSaleText",privateSaleText);
            privateSaleUserMaxMintNum = (String) map.getOrDefault("privateSaleUserMaxMintNum",privateSaleUserMaxMintNum);
            privateSaleTotalSupply = (String) map.getOrDefault("privateSaleTotalSupply",privateSaleTotalSupply);
            airDropStartTime = (String) map.getOrDefault("airDropStartTime",airDropStartTime);
            airDropEndTime = (String) map.getOrDefault("airDropEndTime",airDropEndTime);


            publicSaleStartTime = (String) map.getOrDefault("publicSaleStartTime",publicSaleStartTime);
            publicEndTime = (String) map.getOrDefault("publicEndTime",publicEndTime);
            publicSalePrice = (String) map.getOrDefault("publicSalePrice",publicSalePrice);
            publicSaleText = (String) map.getOrDefault("publicSaleText",publicSaleText);
            publicSaleUserMaxMintNum = (String) map.getOrDefault("publicSaleUserMaxMintNum",publicSaleUserMaxMintNum);


            twitter = (String) map.getOrDefault("twitter",twitter);
            discord = (String) map.getOrDefault("discord",discord);
            telegram = (String) map.getOrDefault("telegram",telegram);
            website = (String) map.getOrDefault("website",website);
            crew3 = (String) map.getOrDefault("crew3",crew3);
            galxe = (String) map.getOrDefault("galxe",galxe);
            medium = (String) map.getOrDefault("medium",medium);


            tag = (String) map.getOrDefault("tag",tag);


            startTime = (String) map.getOrDefault("startTime",startTime);
            endTime = (String) map.getOrDefault("endTime",endTime);

        }

    }

    public void map(){
        try {
            flush();
        }catch (Exception e){

        }
    }

    public static void main(String[] args) {
        MaxiNftDetailResponse a = new MaxiNftDetailResponse();
        System.out.println(JSONObject.toJSONString(a));
    }
}
