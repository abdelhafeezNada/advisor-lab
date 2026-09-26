package com.example.advisorlab.advisor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RequestContextAdvisor implements CallAdvisor {

  public static final String REQUEST_ID = "requestId";
  public static final String START_TIME = "startTime";

  @Override
  public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {

    log.info("========== RequestContextAdvisor ==========");

    Map<String, Object> newContext = new HashMap<>(chatClientRequest.context());
    newContext.put(REQUEST_ID, UUID.randomUUID().toString());
    newContext.put(START_TIME, System.currentTimeMillis());
    ChatClientRequest mutatedChatClientRequest = chatClientRequest.mutate().context(newContext).build();

    log.info("Context after mutation: {}", mutatedChatClientRequest.context());

    log.info("========================================");

    return callAdvisorChain.nextCall(mutatedChatClientRequest);

  }

  @Override
  public String getName() {
    return "RequestContextAdvisor";
  }

  @Override
  public int getOrder() {
    return 5;
  }

}
