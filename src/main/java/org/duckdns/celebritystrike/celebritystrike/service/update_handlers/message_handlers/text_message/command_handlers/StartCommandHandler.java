package org.duckdns.celebritystrike.celebritystrike.service.update_handlers.message_handlers.text_message.command_handlers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.duckdns.celebritystrike.celebritystrike.config.Commands;
import org.duckdns.celebritystrike.celebritystrike.sender.MessageSender;
import org.duckdns.celebritystrike.celebritystrike.service.common.ConversationManager;
import org.duckdns.celebritystrike.celebritystrike.util.MessageComposer;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Slf4j
@Service
@RequiredArgsConstructor
public class StartCommandHandler<T> implements CommandHandler {
    private final ConversationManager<T> conversationManager;
    private final MessageSender sender;
    private final static String WELCOME = """
            👋 Привет, %s!
            Ты в магазине Celebrity Strike 🚀!
            Получи доступные команды набрав: /menu
            Начинаем покупки🎉!""";

    @Override
    public void handle(@NonNull Message message) {
        User fromUser = message.getFrom();
        Long userId = fromUser.getId();
        Long chatId = message.getChatId();
        String userName = fromUser.getUserName();
        String welcomeMessage = String.format(WELCOME, userName);
        conversationManager.clearConversation(userId);
        sender.send(MessageComposer.composeSendMessage(chatId, welcomeMessage));
        log.info("User {} started conversation in chat {}", userId, chatId);
    }

    @Override
    public String canHandle() {
        return Commands.START.getValue();
    }
}
