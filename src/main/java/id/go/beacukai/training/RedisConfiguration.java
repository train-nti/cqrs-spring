package id.go.beacukai.training;

import id.go.beacukai.training.model.Actor;
import id.go.beacukai.training.service.ActorBackgroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisConfiguration {
//    @Bean
//    RedisTemplate<String, Actor> redisTemplate(
//            RedisConnectionFactory connectionFactory){
//
//        RedisTemplate<String,Actor> actorRedisTemplate =
//                new RedisTemplate<String,Actor>();
//        actorRedisTemplate.setConnectionFactory(connectionFactory);
//        return actorRedisTemplate;
//    }

    @Bean
    ChannelTopic topic(){
        return new ChannelTopic("search_engine");
    }

    @Bean
    MessageListenerAdapter messageListener(ActorBackgroundService actorBackgroundService){
        return new MessageListenerAdapter(actorBackgroundService);
    }

    @Bean
    RedisMessageListenerContainer redisContainer(
            RedisConnectionFactory connectionFactory,
            MessageListenerAdapter messageListenerAdapter,
            ChannelTopic topic){
        RedisMessageListenerContainer container =
                new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(messageListenerAdapter,topic);
        return  container;
    }



}
