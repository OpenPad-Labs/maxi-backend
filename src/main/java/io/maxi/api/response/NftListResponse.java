package io.maxi.api.response;

import io.maxi.api.entity.MaxiNftCollection;
import io.maxi.api.request.BaseRequest;
import lombok.Data;

import java.util.List;

@Data
public class NftListResponse extends BaseRequest {

    private List<MaxiNftDetailResponse> maxiNftCollectionList;
    private Long pageIndex;
    private Long pageSize;
    private Long total;
}
