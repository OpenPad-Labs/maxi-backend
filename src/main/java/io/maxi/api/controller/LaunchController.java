package io.maxi.api.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.maxi.api.config.RedisSwapper;
import io.maxi.api.dao.MaxiHomeSlideshowMapper;
import io.maxi.api.dao.MaxiNftCollectionMapper;
import io.maxi.api.dao.MaxiNftCollectionSellAddressMapper;
import io.maxi.api.dao.MaxiNftCollectionSellMapper;
import io.maxi.api.entity.MaxiHomeSlideshow;
import io.maxi.api.entity.MaxiNftCollection;
import io.maxi.api.request.HomeBannerRequest;
import io.maxi.api.request.MintCheckRequest;
import io.maxi.api.request.NftDetailRequest;
import io.maxi.api.request.NftListRequest;
import io.maxi.api.response.MaxiNftCollectionResponse;
import io.maxi.api.response.MaxiNftDetailResponse;
import io.maxi.api.response.MintCheckResponse;
import io.maxi.api.response.NftListResponse;
import io.maxi.api.response.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author night
 * @date
 */

@RestController("/api/v1/public")
@CrossOrigin
public class LaunchController {

	private static final Logger log = LoggerFactory.getLogger(LaunchController.class);


    @Autowired
    MaxiHomeSlideshowMapper maxiHomeSlideshowMapper;

    @Autowired
    MaxiNftCollectionMapper maxiNftCollectionMapper;

    @Autowired
    MaxiNftCollectionSellAddressMapper maxiNftCollectionSellAddressMapper;

    @Autowired
    MaxiNftCollectionSellMapper maxiNftCollectionSellMapper;

    @Autowired
    RedisSwapper redisSwapper;


    @PostMapping("/home/banner")
    public Result<MaxiHomeSlideshow> banner(@RequestBody HomeBannerRequest request)  {
        List<MaxiHomeSlideshow>  list = maxiHomeSlideshowMapper.selectList(null);
        return Result.success(list);
    }



    @PostMapping("/nft/list")
    public Result<NftListResponse> nftList(@RequestBody NftListRequest request)  {


        Integer pageIndex = request.getPageIndex();
        Integer pageSize = request.getPageSize();
        if(pageIndex==null || pageIndex.intValue() == 0){
            pageIndex = 1;
        }
        if(pageSize==null  || pageSize.intValue() <= 0  ){
            pageSize = 20;
        }
        Page<MaxiNftCollection> page = new Page<>(pageIndex,pageSize);
        Page<MaxiNftCollection> pageList = maxiNftCollectionMapper.selectPage(page, Wrappers.<MaxiNftCollection>lambdaQuery()
                .eq(MaxiNftCollection::getStatus, request.getStatus())
                .orderByAsc(MaxiNftCollection::getSortNum)
        );

        NftListResponse response = new NftListResponse();

        response.setTotal(pageList.getTotal());
        response.setPageIndex(pageList.getCurrent());
        response.setPageSize(pageList.getSize());


        List<MaxiNftCollection> list = pageList.getRecords();

        List<MaxiNftCollectionResponse> maxiNftCollectionList = new ArrayList<>();

        for (MaxiNftCollection collection : list) {
            MaxiNftCollectionResponse collectionResponse = new MaxiNftCollectionResponse();
            collectionResponse.setNftCollectionId(collection.getNftCollectionId());
            collectionResponse.setStatus(collection.getStatus());
            collectionResponse.setDescription(collection.getNftCollectionDesc());
            collectionResponse.setImageUrl(collection.getNftCollectionCover());

            collectionResponse.setSortNum(collection.getSortNum());
            //todo 获取最新一个售卖周期的时间
            collectionResponse.setStartTime(String.valueOf(System.currentTimeMillis()));

            maxiNftCollectionList.add(collectionResponse);
        }

        response.setMaxiNftCollectionList(maxiNftCollectionList);
        return Result.success(response);
    }


    @PostMapping("/nft/detail")
    public Result<MaxiNftDetailResponse> nftDetail(@RequestBody NftDetailRequest request)  {
        MaxiNftCollection collection = maxiNftCollectionMapper.selectOne(Wrappers.<MaxiNftCollection>lambdaQuery()
                .eq(StringUtils.isNoneEmpty(request.getNftCollectionId()),MaxiNftCollection::getNftCollectionId, request.getNftCollectionId())
                .eq(StringUtils.isNoneEmpty(request.getNftCollectionAddress()),MaxiNftCollection::getNftCollectionAddress, request.getNftCollectionAddress())
        );

        MaxiNftDetailResponse response = new MaxiNftDetailResponse();

        BeanUtils.copyProperties(collection,response);


        return Result.success(response);
    }


    @PostMapping("/nft/mint/eligibility/check")
    public Result<MintCheckResponse> mintCheck(@RequestBody MintCheckRequest request)  {
        MintCheckResponse response = new MintCheckResponse();
        response.setCanMint(Boolean.TRUE);
        return Result.success(response);
    }

}
