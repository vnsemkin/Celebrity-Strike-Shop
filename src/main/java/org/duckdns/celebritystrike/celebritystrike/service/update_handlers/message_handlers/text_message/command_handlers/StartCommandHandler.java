package org.duckdns.celebritystrike.celebritystrike.service.update_handlers.message_handlers.text_message.command_handlers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.duckdns.celebritystrike.celebritystrike.config.Commands;
import org.duckdns.celebritystrike.celebritystrike.sender.MessageSender;
import org.duckdns.celebritystrike.celebritystrike.service.common.ConversationManager;
import org.duckdns.celebritystrike.celebritystrike.util.MessageComposer;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Service
@RequiredArgsConstructor
public class StartCommandHandler<T> implements CommandHandler {
    private final ConversationManager<T> conversationManager;
    private final MessageSender sender;
    private final static String WELCOME = """
            👋 Welcome, %s!
            I am BOT-ZADROT!
            Looking forward to assisting you!""";

    @Override
    public void handle(@NonNull Message message) {
        User fromUser = message.getFrom();
        Long userId = fromUser.getId();
        String userName = fromUser.getUserName();
        String welcomeMessage = String.format(WELCOME, userName);
        conversationManager.clearConversation(userId);
        sender.send(MessageComposer.composeSendMessage(message.getChatId(), welcomeMessage));
    }

    @Override
    public String canHandle() {
        return Commands.START.getValue();
    }
}
