package io.maxi.api.request;

import lombok.Data;

@Data
public class NftDetailRequest extends BaseRequest{

    String nftCollectionId;
    String nftCollectionAddress;
}
