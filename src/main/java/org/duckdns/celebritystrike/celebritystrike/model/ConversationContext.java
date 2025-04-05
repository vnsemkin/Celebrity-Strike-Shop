package org.duckdns.celebritystrike.celebritystrike.model;

import lombok.Getter;
import lombok.Setter;
import org.duckdns.celebritystrike.celebritystrike.service.update_handlers.message_handlers.text_message.reply_handlers.TextReplyHandler;

@Getter
@Setter
public class ConversationContext<T> {
    private long userId;
    private TextReplyHandler handler;
    private T data;
}
