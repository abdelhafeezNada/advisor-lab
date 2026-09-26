package com.example.advisorlab.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AdvisorA implements CallAdvisor {

  @Override
  public ChatClientResponse adviseCall(
      ChatClientRequest request,
      CallAdvisorChain chain) {

    log.info("A PRE");

    ChatClientResponse response = chain.nextCall(request);

    log.info("A POST");

    return response;
  }

  @Override
  public String getName() {
    return "AdvisorA";
  }

  @Override
  public int getOrder() {
    return 5;
  }
}
