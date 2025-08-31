package com.study.emailsendconsumer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EmailSendMessage {
  private String from;
  private String to;
  private String subject;
  private String content;

  public EmailSendMessage() {
  }

  public EmailSendMessage(String from, String to, String subject, String content) {
    this.from = from;
    this.to = to;
    this.subject = subject;
    this.content = content;
  }

  public String getFrom() {
    return from;
  }

  public String getTo() {
    return to;
  }

  public String getSubject() {
    return subject;
  }

  public String getContent() {
    return content;
  }

  public static EmailSendMessage fromJson(String json) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(json, EmailSendMessage.class);
    } catch (Exception e) {
      throw new RuntimeException("파싱 실패!!");
    }

  }
}
