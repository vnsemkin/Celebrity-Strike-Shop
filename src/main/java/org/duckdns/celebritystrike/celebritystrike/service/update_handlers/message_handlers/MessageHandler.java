package org.duckdns.celebritystrike.celebritystrike.service.update_handlers.message_handlers;

import java.util.function.Predicate;
import org.springframework.lang.NonNull;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public interface MessageHandler {
    Predicate<Message> canHandle();
    void handle(@NonNull Message message);
}
