package me.jun.memberservice.common.config;

import lombok.Getter;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Profile("test")
@Component
@Getter
public class KafkaConsumer {

    private Long id;

    @KafkaListener(
            topics = "member.delete",
            groupId = "test_group_id"
    )
    public void consume(@Payload Long id) {
        this.id = id;
    }
}
