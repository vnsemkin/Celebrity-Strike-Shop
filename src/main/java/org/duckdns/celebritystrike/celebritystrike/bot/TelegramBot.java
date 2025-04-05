package org.duckdns.celebritystrike.celebritystrike.bot;

import org.duckdns.celebritystrike.celebritystrike.service.update_handlers.UpdateHandlerService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public final class TelegramBot extends TelegramBotsLongPollingApplication
    implements LongPollingSingleThreadUpdateConsumer {

  private final UpdateHandlerService updateHandlerService;

  public TelegramBot(@NonNull UpdateHandlerService updateHandlerService) {
    this.updateHandlerService = updateHandlerService;
  }

  @Override
  public void consume(@NonNull List<Update> updates) {
    LongPollingSingleThreadUpdateConsumer.super.consume(updates);
  }

  @Override
  public void consume(@NonNull Update update) {
    updateHandlerService.handle(update);
  }
}
