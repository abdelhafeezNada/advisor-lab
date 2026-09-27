package com.example.advisorlab.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FailureSimulationAdvisor implements CallAdvisor {

  @Override
  public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {

    log.info("FailureSimulationAdvisor PRE");

    throw new IllegalStateException(
        "Simulated advisor failure");
  }

  @Override
  public String getName() {
    return "FailureSimulationAdvisor";
  }

  @Override
  public int getOrder() {
    return 35;
  }
}
