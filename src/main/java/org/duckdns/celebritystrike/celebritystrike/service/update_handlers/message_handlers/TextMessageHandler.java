package org.duckdns.celebritystrike.celebritystrike.service.update_handlers.message_handlers;

import java.util.Map;
import java.util.function.Predicate;

import org.duckdns.celebritystrike.celebritystrike.model.ConversationContext;
import org.duckdns.celebritystrike.celebritystrike.service.common.ConversationManager;
import org.duckdns.celebritystrike.celebritystrike.service.update_handlers.message_handlers.text_message.command_handlers.CommandHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Service
public class TextMessageHandler implements MessageHandler {
    private final static String MENU = "/menu";
    private final static String DEFAULT_HANDLER = "";
    private final static String COMMAND_SEPARATOR = "/";
    private final Map<String, CommandHandler> commandHandlersMap;
    private final ConversationManager<Void> conversationManager;

    public TextMessageHandler(@Qualifier("commandHandlers") Map<String, CommandHandler> commandHandlersMap,
                              @NonNull ConversationManager<Void> conversationManager) {
        this.commandHandlersMap = commandHandlersMap;
        this.conversationManager = conversationManager;
    }

    @Override
    public void handle(@NonNull Message message) {
        String text = message.getText();
        long id = message.getFrom().getId();

        if (isCommand(text)) {
            conversationManager.clearConversation(id);
            commandHandlersMap.getOrDefault(text,
                    commandHandlersMap.get(DEFAULT_HANDLER )).handle(message);
            return;
        }
        ConversationContext<Void> conversation =
                conversationManager.getConversation(id);
        if (conversation != null) {
            conversation.getHandler().handle(message);
            return;
        }
        commandHandlersMap.get(MENU).handle(message);
    }

    private boolean isCommand(String text) {
        return text.startsWith(COMMAND_SEPARATOR);
    }

    @Override
    public Predicate<Message> canHandle() {
        return Message::hasText;
    }
}
