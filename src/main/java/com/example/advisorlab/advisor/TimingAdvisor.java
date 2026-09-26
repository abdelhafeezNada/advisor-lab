package com.example.advisorlab.advisor;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TimingAdvisor implements CallAdvisor {

  @Override
  public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
    log.info("========== TimingAdvisor ==========");

    Map<String, Object> context = chatClientRequest.context();

    String requestId = (String) context.get("requestId");
    Long startTime = (Long) context.get("startTime");
    log.info("TimingAdvisor received requestId={}", requestId);

    ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(chatClientRequest);

    Long duration = System.currentTimeMillis() - startTime;

    log.info("requestId={} completed in {} ms", requestId, duration);

    return chatClientResponse;

  }

  @Override
  public String getName() {
    return "TimingAdvisor";
  }

  @Override
  public int getOrder() {
    return 30;
  }

}
