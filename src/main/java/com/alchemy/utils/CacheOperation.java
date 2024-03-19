package com.alchemy.utils;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Component;

import com.alchemy.configuration.RedisConfig;

@Component
public class CacheOperation {

	@Autowired
	private RedisConfig redisConfig;

	public CacheOperation() {
		super();
	}

	public Boolean isKeyExist(String key1, String key2) {
		return redisConfig.redisTemplate().opsForHash().hasKey(key1, key2);
	}

	public void addInCache(String key1, String key2, Object val) {
		redisConfig.redisTemplate().opsForHash().put(key1, key2, val);
	}

	public Object getFromCache(String key1, String key2) {
		return redisConfig.redisTemplate().opsForHash().get(key1, key2);
	}

	public void removeAllKeysStartingWith() {
		redisConfig.redisConnectionFactory().getConnection().flushAll();

	}

	public boolean isRedisConnectionClose() {
		try {
			RedisConnection connection = redisConfig.redisConnectionFactory().getConnection();
			connection.ping(); // Send a PING command to check if the server is reachable
			connection.close();
			return true;
		} catch (Exception e) {
			return false;
		}

	}
	
	public boolean isConnectionClosed() {
		return  this.isRedisConnectionClose();
	}

	public void addInCacheTime(String key1, String key2, Object val, long duration) {
		redisConfig.redisTemplate().opsForHash().put(key1, key2, val);
		cacheExpireTime(key2, duration);
	}

	public void cacheExpireTime(String key2, long duration) {
		redisConfig.redisTemplate().expire(key2, Duration.ofHours(duration));
	}

	public void removeFromCache(String key1) {
		redisConfig.redisTemplate().delete(key1);
	}
}
