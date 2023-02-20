package io.maxi.api.request;

import lombok.Data;

@Data
public class NftListRequest extends BaseRequest{

    String status;
    Integer pageIndex;
    Integer pageSize;
}
