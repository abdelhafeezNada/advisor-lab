package com.example.advisorlab.advisor;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TimingAdvisor implements CallAdvisor {

  @Override
  public ChatClientResponse adviseCall(
      ChatClientRequest request,
      CallAdvisorChain chain) {

    String requestId = (String) request.context().get("requestId");

    long start = System.currentTimeMillis();

    log.info(
        "TimingAdvisor received requestId={}",
        requestId);

    try {

      return chain.nextCall(request);

    } finally {

      long duration = System.currentTimeMillis() - start;

      log.info(
          "requestId={} completed in {} ms",
          requestId,
          duration);
    }
  }

  // @Override
  // public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest,
  // CallAdvisorChain callAdvisorChain) {
  // log.info("========== TimingAdvisor ==========");

  // Map<String, Object> context = chatClientRequest.context();

  // String requestId = (String) context.get("requestId");
  // Long startTime = (Long) context.get("startTime");
  // log.info("TimingAdvisor received requestId={}", requestId);

  // ChatClientResponse chatClientResponse = null;

  // try {

  // chatClientResponse = callAdvisorChain.nextCall(chatClientRequest);

  // } catch (Exception exception) {

  // AssistantMessage assistantMessage = new
  // AssistantMessage(exception.getMessage());
  // Generation generation = new Generation(assistantMessage);
  // ChatResponse chatResponse = new ChatResponse(List.of(generation));

  // chatClientResponse = new ChatClientResponse(chatResponse, context);

  // } finally {

  // Long duration = System.currentTimeMillis() - startTime;

  // log.info("requestId={} completed in {} ms", requestId, duration);
  // }

  // return chatClientResponse;

  // }

  @Override
  public String getName() {
    return "TimingAdvisor";
  }

  @Override
  public int getOrder() {
    return 30;
  }

}
