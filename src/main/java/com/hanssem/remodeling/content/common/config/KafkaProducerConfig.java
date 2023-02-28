//package com.hanssem.remodeling.content.common.config;
//
//import java.util.Map;
//import org.apache.kafka.clients.CommonClientConfigs;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//
//@Configuration
//public class KafkaProducerConfig {
//
//    @Bean
//    public ProducerFactory<String, Object> kafkaProducerFactory() {
//        return new DefaultKafkaProducerFactory<>(Map.of(
//                CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
//                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
//                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class
//        ));
//    }
//
//    @Bean
//    public KafkaTemplate<String, Object> kafkaTemplate(){
//        return new KafkaTemplate(kafkaProducerFactory());
//    }
//}
