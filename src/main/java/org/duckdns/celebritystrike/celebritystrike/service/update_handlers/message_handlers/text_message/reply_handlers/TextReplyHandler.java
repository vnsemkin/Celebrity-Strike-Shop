package org.duckdns.celebritystrike.celebritystrike.service.update_handlers.message_handlers.text_message.reply_handlers;

import org.springframework.lang.NonNull;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public interface TextReplyHandler {
    void handle(@NonNull Message message);
    String canHandle();
}