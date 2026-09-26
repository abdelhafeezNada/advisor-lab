package com.example.advisorlab.advisor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PromptEnrichmentAdvisor implements CallAdvisor {

  private final String SYSTEM_MESSAGE = """
      You are a concise Java/Spring instructor.
      Answer in at most 2 sentences.
      Use Java/Spring terminology when relevant.
      """;

  @Override
  public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {

    List<Message> messages = new ArrayList<>(chatClientRequest.prompt().getInstructions());

    messages.add(0, new SystemMessage(SYSTEM_MESSAGE));

    Prompt newPrompt = new Prompt(messages, chatClientRequest.prompt().getOptions());

    ChatClientRequest mutatedChatClientRequest = chatClientRequest.mutate().prompt(newPrompt).build();

    log.info("Original request: {}", chatClientRequest.prompt());
    log.info("Mutated request: {}", mutatedChatClientRequest.prompt());

    return callAdvisorChain.nextCall(mutatedChatClientRequest);
  }

  @Override
  public String getName() {
    return "PromptEnrichmentAdvisor";
  }

  @Override
  public int getOrder() {
    return 10;
  }

}
