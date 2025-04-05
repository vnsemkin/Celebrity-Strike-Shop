package org.duckdns.celebritystrike.celebritystrike.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public final class TelegramBotRunner {
  private final TelegramBot bot;
  private final String token;

  public TelegramBotRunner(@Value("${telegram.bot.token}") String token,
                           TelegramBot bot) {
    this.bot = bot;
    this.token = token;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void run() {
    try {
      boolean running = bot.registerBot(token, bot).isRunning();
      if (running) {
        log.info("Bot is running");
      }
    } catch (TelegramApiException e) {
      log.error(e.getMessage());
    }
  }
}
