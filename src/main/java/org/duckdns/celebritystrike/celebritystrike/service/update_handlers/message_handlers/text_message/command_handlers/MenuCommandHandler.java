package org.duckdns.celebritystrike.celebritystrike.service.update_handlers.message_handlers.text_message.command_handlers;

import java.util.Map;
import lombok.Setter;
import org.duckdns.celebritystrike.celebritystrike.config.Commands;
import org.duckdns.celebritystrike.celebritystrike.sender.MessageSender;
import org.duckdns.celebritystrike.celebritystrike.service.common.ConversationManager;
import org.duckdns.celebritystrike.celebritystrike.util.MessageComposer;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Service
public class MenuCommandHandler implements CommandHandler {
    private final MessageSender sender;
    private final ConversationManager<Void> conversationManager;
    @Setter
    private Map<String, CommandHandler> commandHandlerMap;
    private final static String MENU =
            "Dear %s, available commands:\n";

    public MenuCommandHandler(ConversationManager<Void> conversationManager,
                              MessageSender sender) {
        this.sender = sender;
        this.conversationManager = conversationManager;
    }

    @Override
    public void handle(@NonNull Message message) {
        Long chatId = message.getChatId();
        User fromUser = message.getFrom();
        Long userId = fromUser.getId();
        String userName = fromUser.getUserName();
        conversationManager.clearConversation(userId);
        sender.send(MessageComposer.composeSendMessage(message.getChatId(),
                String.format(MENU + getCommands(), userName)));
    }

    private String getCommands() {
        return String.join("\n", commandHandlerMap.keySet());
    }

    @Override
    public String canHandle() {
        return Commands.MENU.getValue();
    }
}
