package io.maxi.api.response;

import io.maxi.api.entity.MaxiNftCollection;
import io.maxi.api.request.BaseRequest;
import lombok.Data;

import java.util.List;

@Data
public class MaxiNftCollectionResponse extends BaseRequest {

    private String nftCollectionId;

    private String title;
    private String imageUrl;
    private String description;
    private String sortNum;

    private String status;
    private String startTime;
}
