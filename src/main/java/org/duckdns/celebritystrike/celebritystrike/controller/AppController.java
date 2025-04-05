package org.duckdns.celebritystrike.celebritystrike.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.duckdns.celebritystrike.celebritystrike.model.RequestDataDto;
import org.duckdns.celebritystrike.celebritystrike.service.web_handlers.DefaultWebHandler;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController()
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AppController {
    private final DefaultWebHandler handler;

    @GetMapping("/send-message")
    public String getHello(@RequestBody RequestDataDto requestDataDto) {
        if(requestDataDto.chatId() == null) {
            return "Chat id is null";
        }
        long chatId = Long.parseLong(requestDataDto.chatId());
        log.info("Get request data: method getHello: {}", requestDataDto);
        handler.send(chatId);
        return "Get req received: Hello from backend";
    }

    @PostMapping("/send-message")
    public String postHello(@RequestBody RequestDataDto requestDataDto) {
        if(requestDataDto.chatId() == null) {
            return "Chat id is null";
        }
        long chatId = Long.parseLong(requestDataDto.chatId());
        log.info("Get request data: method postHello: {}", requestDataDto);
        handler.send(chatId);
        return "Post req received: Hello from backend";
    }
}
