package com.hof.yakamozauth.help.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@Profile("redis-standalone")
public class RedisConfigStandAlone extends RedisTemplateConfig{

	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.password}")
	private String password;

	@Value("${spring.redis.jedis.pool.max-idle}")
	private int maxIdleConnection;

	@Value("${spring.redis.jedis.pool.min-idle}")
	private int minIdleConnection;

	@Override
	@Bean
	@RefreshScope
	public JedisConnectionFactory redisConnectionFactory() {
		return new JedisConnectionFactory(redisStandaloneConfiguration(), jedisClientConfiguration());
	}
	
	@Bean
	public RedisStandaloneConfiguration redisStandaloneConfiguration() {
		RedisStandaloneConfiguration redisConf = new RedisStandaloneConfiguration();
		redisConf.setHostName(host);
		redisConf.setPort(port);
		redisConf.setPassword(password);
		return redisConf;
	}

	@Bean
	public JedisClientConfiguration jedisClientConfiguration() {
		return JedisClientConfiguration.builder().usePooling().poolConfig(jedisPoolConfig()).build();
	}

	@Bean
	@RefreshScope
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(maxIdleConnection);
		jedisPoolConfig.setMinIdle(minIdleConnection);
		return jedisPoolConfig;
	}
}
