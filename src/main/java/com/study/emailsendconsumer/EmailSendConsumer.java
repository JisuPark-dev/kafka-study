package com.study.emailsendconsumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailSendConsumer {
  @KafkaListener(topics = "email.send", groupId = "email-send-group")
  public void consume(String message) {
    System.out.println("Received message: " + message);
    EmailSendMessage.fromJson(message);

    ///  실제 이메일 발송 로직

    System.out.println("이메일 발송 완료!");
  }


}
