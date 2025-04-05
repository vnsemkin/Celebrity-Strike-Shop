package org.duckdns.celebritystrike.celebritystrike.service.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.duckdns.celebritystrike.celebritystrike.model.ConversationContext;
import org.duckdns.celebritystrike.celebritystrike.service.update_handlers.message_handlers.text_message.reply_handlers.TextReplyHandler;
import org.springframework.stereotype.Service;

@Service
public class ConversationManager<T> {
    private final Map<Long, ConversationContext<T>> activeConversations = new ConcurrentHashMap<>();

    public void startConversation(long userId,
                                  TextReplyHandler handler) {
        ConversationContext<T> context = new ConversationContext<>();
        context.setUserId(userId);
        context.setHandler(handler);
        activeConversations.put(userId, context);
    }

    public ConversationContext<T> getConversation(long userId) {
        return activeConversations.get(userId);
    }

    public void clearConversation(long userId) {
        activeConversations.remove(userId);
    }
}
