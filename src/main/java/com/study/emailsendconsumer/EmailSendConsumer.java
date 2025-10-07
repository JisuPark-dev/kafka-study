package com.study.emailsendconsumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.serializer.DeserializationException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Service
public class EmailSendConsumer {
  @KafkaListener(
      topics = "email.send",
      groupId = "email-send-group",
      concurrency = "3"
  )
  @RetryableTopic( // 해당 어노테이션 사용 시 DLT 토픽 생성하고, 자동으로 DLT 토픽에 실패 메세지 전송
      attempts = "5",  // 총 시도 5번, 재 시도 4번, 일반적으로 3~5회. 많이 하면 시스템 부하 커짐.
      backoff = @Backoff(delay = 1000, multiplier = 2)
//      dltTopicSuffix = ".dlt"  // 사용시 email.send.dlt로 토픽명 생성됨.
//      autoCreateTopics = "false",         // 운영에선 토픽 사전 생성 권장
//      exclude = { DeserializationException.class } // 필요 시 비재시도
  )
  public void consume(String message) {
    System.out.println("Received message: " + message);
    EmailSendMessage emailSendMessage = EmailSendMessage.fromJson(message);

    if(emailSendMessage.getTo().equals("fail@naver.com")){
      System.out.println("잘못된 이메일 주소로 인해 발송 실패");
      throw new RuntimeException("잘못된 이메일 발송");
    }

    ///  실제 이메일 발송 로직
    try {
      // 파티션은 큐를 여러개로 늘려서 병렬 처리가 가능하게 하는 기본 단위이다.
      // 각 토픽은 하나 이상의 파티션으로 구성할 수 있다.
      // 메세지 처리의 순서 보장을 위해서 하나의 파티션은 하나의 컨슈모에만 연결된다. 하지만 하나의 컨슈머는 여러 파티션과 연결 될 수 있다.

      Thread.sleep(3000);
    } catch (InterruptedException e) {
      throw new RuntimeException("이메일 발송 실패!!");
    }



    System.out.println("이메일 발송 완료!");
  }


}
