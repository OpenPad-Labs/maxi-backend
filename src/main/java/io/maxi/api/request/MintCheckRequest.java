package io.maxi.api.request;

import lombok.Data;

@Data
public class MintCheckRequest extends BaseRequest{

    String chainId;
    String walletAddress;
    String nftCollectionId;
    String nftCollectionAddress;
}
