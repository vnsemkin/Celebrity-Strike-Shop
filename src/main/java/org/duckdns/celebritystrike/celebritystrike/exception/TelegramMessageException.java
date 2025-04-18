package org.duckdns.celebritystrike.celebritystrike.exception;

public class TelegramMessageException extends RuntimeException {
  public TelegramMessageException(String message, Throwable cause) {
    super(message, cause);
  }
}
