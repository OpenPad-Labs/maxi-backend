package io.maxi.api.config.constant;

public interface CacheKeyConstant {


    String TREE_PRODUCT_ALL_MAP = "TreeProductAllMap";
    String ALL_SUPPLIER_MAP = "AllSupplierMap";
    String ADVERT_ALL_MAP = "AdvertAllMap";
    String VIP_LEVEL_ALL_MAP = "VipLevelAllMap";
    String VIP_RIGHT_ALL_LIST = "VipRightsAllList";



    //所有都清除
    String REDIS_SUB_CLEAN_KEY_ALL = "REDIS_SUB_CLEAN_KEY_ALL";
    //只清除内存
    String REDIS_SUB_CLEAN_KEY_MEM = "REDIS_SUB_CLEAN_KEY_MEM";
    //只清除redis
    String REDIS_SUB_CLEAN_KEY_REDIS = "REDIS_SUB_CLEAN_KEY_REDIS";
}
