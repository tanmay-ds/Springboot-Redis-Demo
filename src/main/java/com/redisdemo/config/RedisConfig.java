package com.redisdemo.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport{
	
	@Value("${spring.redis.host}")
    private String host;
    
	@Value("${spring.redis.password}")
	private String password;
	
	@Value("${spring.redis.port}")
    private int port;
    
    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxTotal;
    
    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;
    
    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;
    
//    private static final long DEFAULT_DURATION = 3600;
    
    @Bean
	public Jackson2JsonRedisSerializer< Object > jackson2JsonRedisSerializer () {
		Jackson2JsonRedisSerializer< Object > jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>( Object.class );
		ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
		objectMapper.setVisibility( PropertyAccessor.ALL , JsonAutoDetect.Visibility.ANY );
		objectMapper.activateDefaultTyping(BasicPolymorphicTypeValidator.builder().build(),ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper( objectMapper );
		return jackson2JsonRedisSerializer;
	}
    
    @Bean 
    public StringRedisTemplate stringRedisTemplate() {
    	StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(getRedisConnectionFactory());
    	return stringRedisTemplate;
    }
    
    @Bean
    public RedisConnectionFactory getRedisConnectionFactory() {
	  	RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host,port);
	  	if(!ObjectUtils.isEmpty(password))
	  		redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
	  	return new JedisConnectionFactory(redisStandaloneConfiguration);
    }
    
    
//    @Bean
//    public RedisCacheConfiguration redisCacheConfiguration() {
//    	RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
//    	return config.entryTtl(Duration.ofSeconds(DEFAULT_DURATION));
//    } 
//    
//    @Bean
//    @Override
//    public CacheManager cacheManager() {
//        return RedisCacheManager.builder(getRedisConnectionFactory()).cacheDefaults(redisCacheConfiguration()).build();
//    }
    
//    @Bean 
//    public JedisClientConfiguration getJedisClientConfiguration() {
//       final JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisPoolingClientConfigurationBuilder =
//    		   (JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
//       GenericObjectPoolConfig<Object> genericObjectPoolConfig = new GenericObjectPoolConfig<>(); 
//       genericObjectPoolConfig.setMaxTotal(maxTotal);
//       genericObjectPoolConfig.setMinIdle(minIdle);
//       genericObjectPoolConfig.setMaxIdle(maxIdle);
//       return jedisPoolingClientConfigurationBuilder.poolConfig(genericObjectPoolConfig).build();
//    }
//    
//    @Bean
//    public JedisConnectionFactory getJedisConnectionFactory() {
//    	RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host);
//    	if(!ObjectUtils.isEmpty(password)) {
//    		redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
//    	}
//    	return new JedisConnectionFactory(redisStandaloneConfiguration, getJedisClientConfiguration());
//    }
//    
//    @Bean
//    public StringRedisTemplate restTemplate() {
//    	StringRedisTemplate redisTemplate = new StringRedisTemplate();
//    	redisTemplate.setConnectionFactory(getJedisConnectionFactory());
//    	return redisTemplate;
//    }
	
}
