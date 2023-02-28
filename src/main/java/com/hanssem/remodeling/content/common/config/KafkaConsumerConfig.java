//package com.hanssem.remodeling.content.common.config;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.listener.ContainerProperties.AckMode;
//import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
//import org.springframework.kafka.listener.adapter.RetryingMessageListenerAdapter;
//import org.springframework.kafka.support.Acknowledgment;
//import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import org.springframework.retry.RecoveryCallback;
//import org.springframework.retry.backoff.FixedBackOffPolicy;
//import org.springframework.retry.policy.SimpleRetryPolicy;
//import org.springframework.retry.support.RetryTemplate;
//
//import java.util.Map;
//import java.util.Objects;
//
//@Slf4j
//@Configuration
//@RequiredArgsConstructor
//public class KafkaConsumerConfig {
//
//    private final KafkaProperties kafkaProperties;
//    @Value("${spring.kafka.consumer.bootstrap-servers}")
//    private String kafkaServers;
//
//    @Bean
//    public ConsumerFactory<String, Object> objectConsumerFactory() {
//        return new DefaultKafkaConsumerFactory(Map.of(
//                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServers,
//                ConsumerConfig.GROUP_ID_CONFIG, "design-module",
//                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
//                ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false,
//                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
//                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class,
////                JsonDeserializer.USE_TYPE_INFO_HEADERS, false,
//                JsonDeserializer.TRUSTED_PACKAGES, "*",
////                JsonDeserializer.TYPE_MAPPINGS, "Mod-Member:co.edithome.design.service.account.vo.BusinessMemberVo",
//                ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, StringDeserializer.class
//        ));
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaJsonListenerContainerFactory(final KafkaTemplate<String, Object> kafkaTemplate) {
//        final ConcurrentKafkaListenerContainerFactory<String, Object> factory
//                = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(objectConsumerFactory());
//        factory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE);
//        factory.setRetryTemplate(getRetryTemplate());
//        factory.setRecoveryCallback(recoveryCallback(kafkaTemplate));
//        factory.setErrorHandler(new SeekToCurrentErrorHandler());
//        return factory;
//    }
//
//    private RetryTemplate getRetryTemplate() {
//        RetryTemplate retryTemplate = new RetryTemplate();
//        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
//        // 재시도 딜레이 1초
//        fixedBackOffPolicy.setBackOffPeriod(2000L);
//        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
//
//        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
//        // 최대 재시도 횟수 설정
//        retryPolicy.setMaxAttempts(3);
//        retryTemplate.setRetryPolicy(retryPolicy);
//        return retryTemplate;
//    }
//
//    private RecoveryCallback<Object> recoveryCallback(final KafkaTemplate<String, Object> kafkaTemplate) {
//        return context -> {
//            final int retryCount = context.getRetryCount();
//            final Acknowledgment acknowledgment =
//                    (Acknowledgment) context.getAttribute(RetryingMessageListenerAdapter.CONTEXT_ACKNOWLEDGMENT);
//            final ConsumerRecord<String, Object> record = (ConsumerRecord) context.getAttribute(
//                    RetryingMessageListenerAdapter.CONTEXT_RECORD);
//            if ( record.topic().contains("dlt-")){
//                return null;
//            }
//            final String topic = String.format("dlt-%s", record.topic());
//            try {
//                kafkaTemplate.send(topic, record.key(), record.value());
//                log.error("개발자에게 알림 발송");
//                log.warn("[DLQ] {} - retryCount: {} - value: {}.", topic, retryCount, record.value());
//            } catch (Exception e) {
//                log.error("[Fail to Send dead letter topic]: {}" , topic, e);
//            }
//            if (Objects.nonNull(acknowledgment)) {
//                acknowledgment.acknowledge();
//            }
//            return null;
//        };
//    }
//
//
//}
//
