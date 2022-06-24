package id.go.beacukai.training.service;

import id.go.beacukai.training.model.Actor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

@Service
public class ActorBackgroundService implements MessageListener {
    private static final Logger logger =
            LoggerFactory.getLogger(ActorBackgroundService.class);
    @Autowired
    private StringRedisTemplate redisTemplate;

    @KafkaListener(topics = "#{'${io.confluent.developer.config.topic.name}'}",
    groupId = "actor")
    public void consume(final ConsumerRecord<String, String> consumerRecord) {

        logger.info("received {} {}", consumerRecord.key(), consumerRecord.value());

        fetchActor(consumerRecord.value());
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        logger.debug("on Message is called");

//        ByteArrayInputStream inputStream =
//                 new ByteArrayInputStream(message.getBody());
//        Actor actor;
//        try {
//            ObjectInputStream objectInputStream =
//                    new ObjectInputStream(inputStream);
//            actor = (Actor)
//                    objectInputStream.readObject();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }

        String channel = new String(message.getChannel());
        logger.debug(channel);
        switch (channel){
             case "search_engine":
                 fetchActor(new String(message.getBody()));
                 break;
             default:
                 break;
         }
    }

    private void fetchActor(String actor){
        String idx0 = actor.split(" ")[0].substring(0,1);
        String idx1 = actor.split(" ")[1].substring(0,1);

        redisTemplate.opsForSet()
                .add("name_"+ idx0,
                        actor.toString());
        redisTemplate.opsForSet()
                .add("name_"+ idx1,
                        actor.toString());
    }
}
