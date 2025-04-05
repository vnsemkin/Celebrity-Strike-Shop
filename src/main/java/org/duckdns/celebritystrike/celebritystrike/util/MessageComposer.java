package org.duckdns.celebritystrike.celebritystrike.util;

import org.springframework.lang.NonNull;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class MessageComposer {
    public static SendMessage composeSendMessage(long chatId, @NonNull String message) {
        return new SendMessage(String.valueOf(chatId), message);
    }
}
