package com.example.advisorlab.advisor;

import java.util.List;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ValidationAdvisor implements CallAdvisor {

  private static final int MAX_LENGTH = 200;

  @Override
  public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {

    UserMessage userMessage = chatClientRequest.prompt().getUserMessage();

    String text = userMessage == null ? "" : userMessage.getText();
    log.info("ValidationAdvisor inspecting: {}", text);

    if (text.trim().isEmpty()) {

      log.info("SHORT CIRCUIT: empty message");

      return rejectedResponse(chatClientRequest, "Message cannot be empty.");
    }

    if (text.length() > MAX_LENGTH) {
      log.info("SHORT CIRCUIT: message too long");

      return rejectedResponse(
          chatClientRequest,
          "Message must be at most " + MAX_LENGTH + " characters.");
    }

    log.info("Validation passed - continuing chain");

    return callAdvisorChain.nextCall(chatClientRequest);

  }

  private ChatClientResponse rejectedResponse(ChatClientRequest request, String text) {

    AssistantMessage assistantMessage = new AssistantMessage(text);
    Generation generation = new Generation(assistantMessage);

    ChatResponse chatResponse = new ChatResponse(List.of(generation));

    return ChatClientResponse.builder().chatResponse(chatResponse).context(request.context()).build();

  }

  @Override
  public String getName() {
    return "ValidationAdvisor";
  }

  @Override
  public int getOrder() {
    return 8;
  }

}
