package com.example.advisorlab.advisor;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ResponseInspectionAdvisor implements CallAdvisor {

  @Override
  public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {

    log.info("==================ResponseInspectionAdvisor PRE====================");

    ChatClientResponse originalChatClientResponse = callAdvisorChain.nextCall(chatClientRequest);

    log.info("==================ResponseInspectionAdvisor POST | Before Mutating====================");

    log.info("originalChatClientResponse: {}", originalChatClientResponse);

    ChatResponse originalChatResponse = originalChatClientResponse.chatResponse();

    log.info("originalChatResponse: {}", originalChatResponse);

    String originalResponseText = originalChatResponse.getResult().getOutput().getText();

    log.info("originalResponseText: {}", originalResponseText);

    ChatResponseMetadata originalChatResponseMetadata = originalChatResponse.getMetadata();

    log.info("originalChatResponseMetadata: {}", originalChatResponseMetadata);

    Map<String, Object> context = chatClientRequest.context();

    log.info("Context: {}", context);

    // mutation -> AssistantMessage -> Generation ->ChatResponse ->
    // ChatClientResponse

    // Mutate AssistantMessage
    AssistantMessage originalAssistantMessage = originalChatResponse.getResult().getOutput();
    AssistantMessage mutatedAssistantMessage = originalAssistantMessage.mutate()
        .content("[AI RESPONSE]\n" + originalResponseText).build();

    // Mutate Generation
    Generation newGeneration = new Generation(mutatedAssistantMessage, originalChatResponse.getResult().getMetadata());

    // Mutate ChatResponse
    ChatResponse newChatResponse = new ChatResponse(List.of(newGeneration), originalChatResponseMetadata);

    // Mutate ChatClientResponse

    ChatClientResponse mutatedChatClientResponse = originalChatClientResponse.mutate().chatResponse(newChatResponse)
        .context(context).build();

    log.info("==================ResponseInspectionAdvisor POST | After Mutating====================");

    log.info("mutatedChatClientResponse: {}", mutatedChatClientResponse);

    log.info("mutatedResponseText: {}", mutatedChatClientResponse.chatResponse().getResult().getOutput().getText());

    return mutatedChatClientResponse;

  }

  @Override
  public String getName() {
    return "ResponseInspectionAdvisor";
  }

  @Override
  public int getOrder() {
    return 40;
  }

}
