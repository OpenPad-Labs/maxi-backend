package io.maxi.api.config;

import io.maxi.api.config.constant.CacheKeyConstant;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.PostConstruct;

/**
 * 初始化完成后,需要做的一些一次性工作
 */
@Component
public class AfterConstruct {
    protected static final org.slf4j.Logger log = LoggerFactory.getLogger(AfterConstruct.class);
    @Autowired
    RedisSwapper redisSwapper;

    @PostConstruct
    private void onUpdateOpenApiLimitConfigAndWhite() {
        new Thread(() -> {
            log.info("准备监听接收执行命令");
            redisSwapper.subscribe(new JedisPubSub(){
                        @Override
                        public void onMessage(String channel, String message) {
                            log.info("onMessage,{}", new String(message));
                            redisSwapper.cleanKey(message);
                        }
                        @Override
                        public void onSubscribe(String channel, int subscribedChannels) {
                            log.info("onSubscribe");
                        }
                        @Override
                        public void onUnsubscribe(String channel, int subscribedChannels) {
                            log.info("onUnsubscribe");
                       }
                    }, CacheKeyConstant.REDIS_SUB_CLEAN_KEY_ALL);
        }).start();

        new Thread(() -> {
            log.info("准备监听接收执行命令");
            redisSwapper.subscribe(new JedisPubSub(){
                @Override
                public void onMessage(String channel, String message) {
                    log.info("onMessage,{}", new String(message));
                    redisSwapper.cleanMen(message);
                }
                @Override
                public void onSubscribe(String channel, int subscribedChannels) {
                    log.info("onSubscribe");
                }
                @Override
                public void onUnsubscribe(String channel, int subscribedChannels) {
                    log.info("onUnsubscribe");
                }
            }, CacheKeyConstant.REDIS_SUB_CLEAN_KEY_MEM);
        }).start();

    }
}
