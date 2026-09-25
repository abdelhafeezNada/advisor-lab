package com.example.advisorlab.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.advisorlab.controller.dto.ChatRequest;
import com.example.advisorlab.service.ChatService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/chat")
@Slf4j
public class ChatController {

  private final ChatService chatService;

  public ChatController(ChatService chatService) {
    this.chatService = chatService;
  }

  @PostMapping
  public String chat(@RequestBody ChatRequest request) {

    log.info("Request: {}", request);

    return chatService.chat(request.message());
  }

}
