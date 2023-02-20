package io.maxi.api.service.impl;

import io.maxi.api.config.RedisSwapper;
import io.maxi.api.config.constant.CacheKeyConstant;
import io.maxi.api.service.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommonServiceImpl implements CommonService {

    private static final Logger log = LoggerFactory.getLogger(CommonServiceImpl.class);


    @Autowired
    RedisSwapper redisSwapper;


    @Override
    public Map<String, String> getMap() {
        return new HashMap<>();
    }
}
