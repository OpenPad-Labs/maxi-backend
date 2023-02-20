/**
 *
 */
package io.maxi.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 *  单机版redis
 * @author wangtao
 * @Date Apr 11, 2019 4:09:51 PM
 */
@Configuration
@EnableAutoConfiguration
public class RedisCacheConfig extends CachingConfigurerSupport {

	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.timeout}")
	private int timeout;

	@Value("${spring.redis.database}")
	private int database;

	@Value("${spring.redis.password}")
	private String password;

	@Value("${spring.redis.jedis.pool.max-idle}")
	private int maxIdle;

	@Value("${spring.redis.jedis.pool.max-wait}")
	private long maxWaitMillis;


	@Value("${spring.redis.block-when-exhausted}")
	private boolean  blockWhenExhausted;


	 @Bean
	 public JedisPool redisPoolFactory()  throws Exception{
		 JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		 jedisPoolConfig.setMaxIdle(maxIdle);
		 jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
		 // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
		 jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
		 // 是否启用pool的jmx管理功能, 默认true
		 jedisPoolConfig.setJmxEnabled(true);
		 JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
		 return jedisPool;
	 }
}
