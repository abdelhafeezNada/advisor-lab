package com.example.advisorlab.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.advisorlab.advisor.FailureSimulationAdvisor;
import com.example.advisorlab.advisor.PromptEnrichmentAdvisor;
import com.example.advisorlab.advisor.RequestContextAdvisor;
import com.example.advisorlab.advisor.RequestInspectionAdvisor;
import com.example.advisorlab.advisor.ResponseInspectionAdvisor;
import com.example.advisorlab.advisor.TimingAdvisor;
import com.example.advisorlab.advisor.ValidationAdvisor;

@Configuration
public class AiConfig {

  private final ValidationAdvisor validationAdvisor;

  AiConfig(ValidationAdvisor validationAdvisor) {
    this.validationAdvisor = validationAdvisor;
  }

  /**
   * application.properties
   * │
   * ▼
   * Ollama auto-configuration
   * │
   * ▼
   * OllamaChatModel
   * │
   * ▼
   * ChatClient.Builder
   * │
   * ├── default advisor:
   * │ SimpleLoggingAdvisor
   * │
   * ▼
   * ChatClient
   *
   */
  // @Bean
  // ChatClient chatClient(ChatClient.Builder builder, SimpleLoggingAdvisor
  // simpleLoggingAdvisor) {
  // return builder.defaultAdvisors(simpleLoggingAdvisor).build();
  // }

  // @Bean
  // ChatClient chatClient(
  // ChatClient.Builder builder,
  // AdvisorA advisorC,
  // AdvisorB advisorB,
  // AdvisorC advisorA) {

  // return builder
  // .defaultAdvisors(
  // advisorA,
  // advisorB,
  // advisorC)
  // .build();
  // }

  @Bean
  ChatClient chatClient(ChatClient.Builder builder, RequestInspectionAdvisor requestInspectionAdvisor,
      PromptEnrichmentAdvisor promptEnrichmentAdvisor, FailureSimulationAdvisor failureSimulationAdvisor,
      RequestContextAdvisor requestContextAdvisor, TimingAdvisor timingAdvisor,
      ValidationAdvisor ValidationAdvisor, ResponseInspectionAdvisor responseInspectionAdvisor) {
    return builder.defaultAdvisors(requestInspectionAdvisor, promptEnrichmentAdvisor,
        requestContextAdvisor, timingAdvisor, validationAdvisor, responseInspectionAdvisor, failureSimulationAdvisor)
        .build();
  }

}
