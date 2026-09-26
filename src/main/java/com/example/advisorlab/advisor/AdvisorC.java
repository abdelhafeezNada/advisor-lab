package com.example.advisorlab.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AdvisorC implements CallAdvisor {

  @Override
  public ChatClientResponse adviseCall(
      ChatClientRequest request,
      CallAdvisorChain chain) {

    log.info("C PRE");

    ChatClientResponse response = chain.nextCall(request);

    log.info("C POST");

    return response;
  }

  @Override
  public String getName() {
    return "AdvisorC";
  }

  @Override
  public int getOrder() {
    return 5;
  }
}
