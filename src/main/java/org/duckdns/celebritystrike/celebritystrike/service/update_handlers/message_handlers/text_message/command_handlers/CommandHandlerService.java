package org.duckdns.celebritystrike.celebritystrike.service.update_handlers.message_handlers.text_message.command_handlers;

import java.util.Map;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class CommandHandlerService {
    private final Map<String, CommandHandler> commandHandlersMap;

    public void handle(@NonNull Update update) {
        commandHandlersMap.get(update.getMessage().getText()).handle(update.getMessage());
    }
}
