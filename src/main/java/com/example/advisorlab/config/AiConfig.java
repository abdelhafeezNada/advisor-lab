package com.example.advisorlab.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.advisorlab.advisor.AdvisorA;
import com.example.advisorlab.advisor.AdvisorB;
import com.example.advisorlab.advisor.AdvisorC;
import com.example.advisorlab.advisor.RequestInspectionAdvisor;
import com.example.advisorlab.advisor.SimpleLoggingAdvisor;

@Configuration
public class AiConfig {

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
  ChatClient chatClient(ChatClient.Builder builder, RequestInspectionAdvisor requestInspectionAdvisor) {
    return builder.defaultAdvisors(requestInspectionAdvisor).build();
  }

}
