package com.example.advisorlab.advisor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExceptionLoggingAdvisor implements CallAdvisor, StreamAdvisor {

  @Override
  public ChatClientResponse adviseCall(
      ChatClientRequest request,
      CallAdvisorChain chain) {

    Object requestId = request.context()
        .get(RequestContextAdvisor.REQUEST_ID);

    log.info(
        "ExceptionLoggingAdvisor PRE requestId={}",
        requestId);

    try {

      ChatClientResponse response = chain.nextCall(request);

      log.info(
          "ExceptionLoggingAdvisor POST requestId={}",
          requestId);

      return response;

    } catch (Exception ex) {

      log.error(
          "ExceptionLoggingAdvisor caught downstream failure " +
              "requestId={} type={} message={}",
          requestId,
          ex.getClass().getSimpleName(),
          ex.getMessage());

      throw ex;
    }
  }

  @Override
  public String getName() {
    return "ExceptionLoggingAdvisor";
  }

  @Override
  public int getOrder() {
    return 25;
  }

  @Override
  public Flux<ChatClientResponse> adviseStream(ChatClientRequest chatClientRequest,
      StreamAdvisorChain streamAdvisorChain) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'adviseStream'");
  }
}
