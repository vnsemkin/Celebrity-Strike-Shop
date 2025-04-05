package org.duckdns.celebritystrike.celebritystrike.sender;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class MessageSender {
    private final TelegramClient client;

    public MessageSender(@Value("${DEMO_BOT_TOKEN}") String token) {
        this.client = new OkHttpTelegramClient(token);
    }

    public void send(@NonNull Object obj) {
        if(obj instanceof SendMessage sm) {
            try {
                client.execute(sm);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
