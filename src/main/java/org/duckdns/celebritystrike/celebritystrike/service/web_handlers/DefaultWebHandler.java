package org.duckdns.celebritystrike.celebritystrike.service.web_handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.duckdns.celebritystrike.celebritystrike.sender.MessageSender;
import org.duckdns.celebritystrike.celebritystrike.util.MessageComposer;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultWebHandler {
    private final MessageSender sender;

    public void  send(long chatId) {
        log.info("Get request data: method send: {}", chatId);
        sender.send(MessageComposer.composeSendMessage(chatId, "Hello from frontend"));
    }
}
