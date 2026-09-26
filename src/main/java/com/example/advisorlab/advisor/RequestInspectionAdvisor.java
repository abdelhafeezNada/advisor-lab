package com.example.advisorlab.advisor;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RequestInspectionAdvisor implements CallAdvisor {

  @Override
  public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {

    log.info("========== REQUEST INSPECTION ==========");

    log.info("Prompt: {}", chatClientRequest.prompt());
    log.info("Messages: {}", chatClientRequest.prompt().getInstructions());
    log.info("Model Options: {}", chatClientRequest.prompt().getOptions());
    log.info("Advisor context: {}", chatClientRequest.context());

    log.info("========================================");
    return callAdvisorChain.nextCall(chatClientRequest);
  }

  @Override
  public String getName() {
    return "RequestInspectionAdvisor";
  }

  @Override
  public int getOrder() {
    return 0;
  }

}
