package cn.cai.microservice.config;

import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheAspectSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;


@Configuration
@EnableCaching
public class CacheConfig{
	/**
	 * 配置缓存管理器RedisCacheManager
	 * Redis采用key-value存储的存储方式，对key和value需要序列化
	 * Redis的序列化方式由StringRedisSerializer，JdkSerializationRedisSerializer，GenericJackson2JsonRedisSerializer，GenericJackson2JsonRedisSerializer等
	 * RedisCacheManager默认采用StringRedisSerializer序列化key，JdkSerializationRedisSerializer序列化value
	 * @param redisConnectionFactory
	 * @return
	 */
	@Bean
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
//		StringRedisSerializer序列器：
//		优点：开发者友好，轻量级，效率也比较高
//		缺点：只能序列化String类型，如果key使用该序列化器，则key必须为String类型
		RedisSerializer<String> redisSerializer = new StringRedisSerializer();
		
//		jdkSerializationRedisSerializer序列化器：RestTemplate类默认的序列化方式，如果指定序列化器，则为它
//		优点：反序列化时不需要提供类型信息（class）
//		缺点：1、首先它要求存储的对象都必须实现java.io.Serializable接口，比较笨重
//			  2、其次，他存储的为二进制数据，这对开发者是不友好的
//			  3、序列化后的结果非常庞大，是JSON格式的5倍左右，这样就会消耗redis服务器的大量内存。		
//		ClassLoader loader = this.getClass().getClassLoader();
//		JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer(loader);
		
//		Jackson2JsonRedisSerializer序列化器：把一个对象以Json的形式存储，效率高且对调用者友好
//		优点：速度快，序列化后的字符串短小精悍，不需要存储对象实现java.io.Serializable接口
//		缺点：那就是此类的构造函数中有一个类型参数，必须提供要序列化对象的类型信息(.class对象)，反序列化用到了该类型信息
//		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		
//		GenericJackson2JsonRedisSerializer序列化器：基本和上面的Jackson2JsonRedisSerializer功能差不多，使用方式也差不多，
//		但不需要提供类型信息，推荐使用
		GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofMinutes(10))	//设置失效时间
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(genericJackson2JsonRedisSerializer));
		
		RedisCacheManager redisCacheManager = RedisCacheManager.builder(redisConnectionFactory)
				.cacheDefaults(config)
				.build();
		
		return redisCacheManager;
	}
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();		
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(genericJackson2JsonRedisSerializer);		
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(genericJackson2JsonRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}
}
