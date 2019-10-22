package com.hof.yakamozauth.help.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

public abstract class RedisTemplateConfig {

	public abstract JedisConnectionFactory redisConnectionFactory();

	@Bean
	public RedisTemplate<String, CmsUserDocument> redisTemplateForCmsUser() {
		RedisTemplate<String, CmsUserDocument> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(stringToStringSerializer());
		redisTemplate.setValueSerializer(jacksonSerializer());
		return redisTemplate;
	}

	@Bean
	public RedisTemplate<String, PlayHeartbeatSubscriberDocument> redisTemplateForPlayHeartbeat() {
		RedisTemplate<String, PlayHeartbeatSubscriberDocument> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(stringToStringSerializer());
		redisTemplate.setValueSerializer(jacksonSerializer());
		return redisTemplate;
	}

	@Bean
	public RedisTemplate<String, AccessTokenDocument> redisTemplateForAccessToken() {
		RedisTemplate<String, AccessTokenDocument> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(stringToStringSerializer());
		redisTemplate.setValueSerializer(jacksonSerializer());
		return redisTemplate;
	}

	@Bean
	public RedisTemplate<String, SubscriberSessionDocument> redisTemplateForSubscriberSession() {
		RedisTemplate<String, SubscriberSessionDocument> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(stringToStringSerializer());
		redisTemplate.setValueSerializer(jacksonSerializer());
		return redisTemplate;
	}

	@Bean
	public RedisTemplate<String, String> redisTemplateForLoginHistory() {
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(stringToStringSerializer());
		redisTemplate.setValueSerializer(stringToStringSerializer());
		return redisTemplate;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplateForParameterCache() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(stringToStringSerializer());
		redisTemplate.setValueSerializer(jacksonSerializer());
		return redisTemplate;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplateCommon() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(stringToStringSerializer());
		redisTemplate.setValueSerializer(jacksonSerializer());
		return redisTemplate;
	}

	@Bean
	public GenericJackson2JsonRedisSerializer jacksonSerializer() {
		return new GenericJackson2JsonRedisSerializer();
	}

	@Bean
	public GenericToStringSerializer<String> stringToStringSerializer() {
		return new GenericToStringSerializer<>(String.class);
	}

}

