package org.duckdns.celebritystrike.celebritystrike.service.update_handlers;

import java.util.List;
import java.util.function.Predicate;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.duckdns.celebritystrike.celebritystrike.service.update_handlers.message_handlers.DefaultMessageHandler;
import org.duckdns.celebritystrike.celebritystrike.service.update_handlers.message_handlers.MessageHandler;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Slf4j
@Service
@RequiredArgsConstructor
public final class UpdateMessageHandler implements UpdateHandler {
    private final List<MessageHandler> messageHandlerList;
    private final DefaultMessageHandler defaultMessageHandler;

    @Override
    public void handle(@NonNull Update update) {
        Message message = update.getMessage();
        messageHandlerList
                .stream()
                .filter(handler->handler.canHandle().test(message))
                .findFirst()
                .ifPresentOrElse(handler-> handler.handle(message),
                        ()-> defaultMessageHandler.handle(message));
    }

    @Override
    public Predicate<Update> canHandle() {
        return (Update::hasMessage);
    }
}