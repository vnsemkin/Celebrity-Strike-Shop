package org.duckdns.celebritystrike.celebritystrike.service.update_handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.duckdns.celebritystrike.celebritystrike.model.ConversationContext;
import org.duckdns.celebritystrike.celebritystrike.service.common.ConversationManager;
import org.duckdns.celebritystrike.celebritystrike.service.update_handlers.message_handlers.text_message.command_handlers.MenuCommandHandler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.function.Predicate;

@Slf4j
@Service
@RequiredArgsConstructor
public final class UpdateCallbackQueryHandler<T> implements UpdateHandler {
    private final MenuCommandHandler menuCommandHandler;
    private final ConversationManager<T> conversationManager;
    private final static String NO_HANDLER_FOR_CALLBACK_QUERY = "Can`t find any handler for callback query {}";

    @Override
    public void handle(@NonNull Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Message message =
                messageFromCallbackQuery(callbackQuery);
        ConversationContext<T> conversation =
                conversationManager.getConversation(message.getChatId());
        if (conversation != null) {
            conversation.getHandler().handle(message);
            return;
        }
        log.info(NO_HANDLER_FOR_CALLBACK_QUERY, callbackQuery);
        menuCommandHandler.handle(message);
    }

    private Message messageFromCallbackQuery(@NonNull CallbackQuery callbackQuery) {
       return convertCallbackQueryToMessage(callbackQuery);
    }

    private Message convertCallbackQueryToMessage(@NonNull CallbackQuery callbackQuery) {
        User user = callbackQuery.getFrom();
        String data = callbackQuery.getData();
        Message message = new Message();
        message.setFrom(user);
        message.setText(data);
        return message;
    }

    @Override
    public Predicate<Update> canHandle() {
        return Update::hasCallbackQuery;
    }
}