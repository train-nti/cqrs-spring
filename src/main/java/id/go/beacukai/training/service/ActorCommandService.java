package id.go.beacukai.training.service;

import id.go.beacukai.training.model.Actor;
import id.go.beacukai.training.repository.ActorRepository;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ActorCommandService {
    private static final Logger logger =
            LoggerFactory.getLogger(ActorCommandService.class);
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private KafkaTemplate<String, String> producer;

    @Autowired
    private NewTopic topic;

    public Actor add(Actor actor){
        Actor savedActor = actorRepository.save(actor);

        if(savedActor != null){
            if(savedActor.getId() > 0){
                redisTemplate.convertAndSend(
                        "search_engine", actor.toString());

                producer.send(topic.name(), actor.getId().toString(), actor.toString()).addCallback(
                        result -> {
                            // final RecordMetadata m;
                            if (result != null) {
//                                m = result.getRecordMetadata();
//                                logger.info("Produced record to topic {} partition {} @ offset {}",
//                                        m.topic(),
//                                        m.partition(),
//                                        m.offset());
                            }
                        },
                        exception -> logger.error("Failed to produce to kafka", exception));
            }
        }

        return savedActor;
    }
}
