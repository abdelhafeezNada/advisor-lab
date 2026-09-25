package com.example.advisorlab.advisor;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SimpleLoggingAdvisor implements CallAdvisor {

  @Override
  public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {

    log.info("<-------------------Before Model------------------->");
    log.info("request: {}", chatClientRequest);

    ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(chatClientRequest);

    log.info("<-------------------After Model------------------->");
    log.info("response: {}", chatClientResponse);

    return chatClientResponse;

  }

  @Override
  public String getName() {
    return "SimpleLoggingAdvisor";
  }

  @Override
  public int getOrder() {
    return 0;
  }

}
