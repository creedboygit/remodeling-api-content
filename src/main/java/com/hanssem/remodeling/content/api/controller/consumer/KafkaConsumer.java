package com.hanssem.remodeling.content.api.controller.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {

    // 한쪽에서 컨슘을 했을 때 다른쪽에서 메세지를 받는지 ??
//    public static final String DESIGN_MODIFY_MEMBER_TOPIC = "modify-business-member-topic";
//    public static final String DLT_DESIGN_MODIFY_MEMBER_TOPIC = "dlt-modify-business-member-topic";
//    public static final String DESIGN_MODIFY_MEMBER_GID = "modify-business-member-gid";
//    @KafkaListener(
//            topics = {DESIGN_MODIFY_MEMBER_TOPIC},
//            groupId = DESIGN_MODIFY_MEMBER_GID,
//            containerFactory = "kafkaJsonListenerContainerFactory"
//    )
//    void modify(
//            final @Headers MessageHeaders headers,
//            final ConsumerRecord<String, String> consumerRecord,
//            final Acknowledgment ack,
//            final @Payload BusinessMemberVo payload) throws Exception {
//
//        log.info("{} {} {}", consumerRecord.topic(), headers.get("kafka_offset"), "[s] KafkaConsumer for object");
//        log.info("{}", payload);
//        if (payload.getName().contains("에러")) {
//            throw new Exception("force exception!");
//        }
//        designService.modifyDesignerNameByBusinessMemberId(Long.valueOf(consumerRecord.key()), payload.getName());
//        ack.acknowledge();
//        log.info("로직 정상 수행!!");
//        log.info("{}", "[e] KafkaConsumer for object");
//    }

    @KafkaListener(topics = {"test.topic"}, groupId = "group1")
    void receive(ConsumerRecord consumerRecord, @Payload String payload) {
        log.debug("{}", "[s] KafkaConsumer.receive");
        log.debug("{}", payload);
        log.debug("{}", "[e] KafkaConsumer.receive");

    }


    @KafkaListener(topics = {"test.topic"}, groupId = "group2")
    void receive2(ConsumerRecord consumerRecord, @Payload String payload) {
        log.debug("{}", "[s] KafkaConsumer.receive2");
        log.debug("{}", payload);
        log.debug("{}", "[e] KafkaConsumer.receive2");
    }

    @KafkaListener(topics = {"test.topic"}, groupId = "hutt-test")
    void receive3(ConsumerRecord consumerRecord, @Payload String payload) {
        System.out.println("groupId: hutt-test");
        log.debug("{}", "[s] KafkaConsumer.receive3");
        log.debug("{}", payload);
        log.debug("{}", "[e] KafkaConsumer.receive3");
    }

    @KafkaListener(topics = {"test.topic"}, groupId = "hutt-test1")
    void receive4(ConsumerRecord consumerRecord, @Payload String payload) {
        System.out.println("groupId: hutt-test1");
        log.debug("{}", "[s] KafkaConsumer.receive3");
        log.debug("{}", payload);
        log.debug("{}", "[e] KafkaConsumer.receive3");
    }

}
