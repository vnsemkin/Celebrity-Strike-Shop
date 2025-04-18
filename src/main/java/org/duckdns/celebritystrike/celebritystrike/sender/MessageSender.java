package org.duckdns.celebritystrike.celebritystrike.sender;

import lombok.extern.slf4j.Slf4j;
import org.duckdns.celebritystrike.celebritystrike.exception.TelegramMessageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Slf4j
@Component
public class MessageSender {
    private final TelegramClient client;

    public MessageSender(@Value("${telegram.bot.token}") String token) {
        this.client = new OkHttpTelegramClient(token);
    }

    public void send(@NonNull Object obj) {
        if(obj instanceof SendMessage sm) {
            try {
                client.execute(sm);
            } catch (TelegramApiException e) {
                log.error("Failed to send Telegram message: {}", e.getMessage(), e);
                throw new TelegramMessageException("Failed to send Telegram message", e);
            }
        }
    }
}
