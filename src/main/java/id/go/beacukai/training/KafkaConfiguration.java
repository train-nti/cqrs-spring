package id.go.beacukai.training;

import id.go.beacukai.training.model.Actor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfiguration {

    // injected from application.properties
    @Value("${io.confluent.developer.config.topic.name}")
    private String topicName;

    @Value("${io.confluent.developer.config.topic.partitions}")
    private int numPartitions;

    @Value("${io.confluent.developer.config.topic.replicas}")
    private int replicas;

    @Bean
    NewTopic moviesTopic() {
        return new NewTopic(topicName, numPartitions, (short) replicas);
    }

    @Value(value = "${spring.kafka.properties.bootstrap.servers}")
    private String bootstrapServer;

    @Bean
    public ConsumerFactory<String, String> consumerFactory()
    {

        // Creating a Map of string-object pairs
        Map<String, Object> config = new HashMap<>();

        // Adding the Configuration
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServer);
//        config.put(ConsumerConfig.GROUP_ID_CONFIG,
//                "group_id");
        config.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        config.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(config);
    }



}
