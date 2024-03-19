package com.alchemy.configuration;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;

@Configuration
@EnableCaching
public class RedisConfig {
	@Value("${spring.redis.host}")
	private String redisHost;

	@Value("${spring.redis.port}")
	private int redisPort;

	@Value("${spring.redis.password}")
	private String redisPassword;

	public RedisConfig(String redisHost, int redisPort, String redisPassword) {
		super();
		this.redisHost = redisHost;
		this.redisPort = redisPort;
		this.redisPassword = redisPassword;
	}

	public RedisConfig() {
		super();
		// TODO Auto-generated constructor stub
	}

//	@Bean
//	public LettuceConnectionFactory redisConnectionFactory() {
//		
//		
//			RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
//
//			configuration.setHostName(redisHost);
//			configuration.setPort(redisPort);
//			configuration.setPassword(RedisPassword.of(redisPassword.trim().length() > 0 ? redisPassword : ""));
//			return new LettuceConnectionFactory(configuration);
//		
//	}
	
	
	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
	    RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
	    configuration.setHostName(redisHost);
	    configuration.setPort(redisPort);
	    configuration.setPassword(RedisPassword.of(redisPassword.trim().length() > 0 ? redisPassword : ""));

	    // this is used for setting host name , port and password 
	    
	    LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder()
	            .clientOptions(ClientOptions.builder()
	            		.disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
	            		
	            		// ClientOptions is class which has disconnect Behavoir 
	            		// DisconnectBehavoir Accept commands when auto-reconnect is enabled, reject commands when auto-reconnect is disabled.
	            		
	            		// this is used to disconnect server if it is closed 
	            		
	                    .autoReconnect(true)
	                    
	                    //  this will auto reconnect if redis server is working properly 
	                    
	                    .build())
	            .build();

	    LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(configuration, clientConfiguration);
	    
	    // connectionFactory.setValidateConnection(true);
	    //	this will validate the connection only validate connection are allowed 
	    // this help to avoid potential issue caused by disconnection;
	    

	    return connectionFactory;
	}

	

	@Bean
	public RedisCacheConfiguration cacheConfiguration() {

		RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofSeconds(600)).disableCachingNullValues();
		return cacheConfig;

	}

	@Bean
	public RedisCacheManager cacheManager() throws Exception {

		RedisCacheManager rcm = RedisCacheManager.builder(redisConnectionFactory()).cacheDefaults(cacheConfiguration())
				.transactionAware().build();
		return rcm;

	}

	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		RedisSerializer<String> redisSerializer = new StringRedisSerializer();
		JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
		LettuceConnectionFactory factory = redisConnectionFactory();
		factory.afterPropertiesSet();
		template.setHashKeySerializer(redisSerializer);
		template.setConnectionFactory(factory);
		template.setHashValueSerializer(jdkSerializationRedisSerializer);
		template.setKeySerializer(redisSerializer);
		template.setEnableDefaultSerializer(true);
		template.afterPropertiesSet();
		template.setValueSerializer(jdkSerializationRedisSerializer);
		return template;

	}

}