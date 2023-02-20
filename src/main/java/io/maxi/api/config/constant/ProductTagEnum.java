package io.maxi.api.config.constant;

import org.apache.commons.lang3.StringUtils;

/**
 * 商品标签
 */
public enum ProductTagEnum {
    firstPreferential("会员首单折扣"),
    holdingAward("持树奖励"),
    indexRecommendation("首页推荐"),
    ;

    String desc;

    ProductTagEnum(String desc) {
        this.desc = desc;
    }

    public static boolean isFirstPreferential(String tags){
        if(StringUtils.isEmpty(tags)){
            return false;
        }
        return tags.contains(firstPreferential.name());
    }
}
