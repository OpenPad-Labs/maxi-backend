package io.maxi.api.response;

import io.maxi.api.request.BaseRequest;
import lombok.Data;

@Data
public class MintCheckResponse extends BaseRequest {

    Boolean canMint;
}
