package org.duckdns.celebritystrike.celebritystrike.config;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.duckdns.celebritystrike.celebritystrike.service.update_handlers.message_handlers.text_message.command_handlers.CommandHandler;
import org.duckdns.celebritystrike.celebritystrike.service.update_handlers.message_handlers.text_message.command_handlers.MenuCommandHandler;
import org.duckdns.celebritystrike.celebritystrike.service.update_handlers.message_handlers.text_message.reply_handlers.TextReplyHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Value("${telegram.bot.frontend-url}")
    private String frontendUrl;

    @Bean
    @Qualifier("commandHandlers")
    public Map<String, CommandHandler> commandHandlersMap(@NonNull List<CommandHandler> commandHandlers,
                                                          MenuCommandHandler menuCommandHandler) {
        Map<String, CommandHandler> collect = commandHandlers
                .stream().collect(Collectors.toMap(CommandHandler::canHandle,
                        commandHandler -> commandHandler));
        menuCommandHandler.setCommandHandlerMap(collect);
        return collect;
    }

    @Bean
    @Qualifier("replyHandlers")
    public Map<String, TextReplyHandler> replyHandlers(@NonNull List<TextReplyHandler> textReplyHandlers) {
        return textReplyHandlers.stream().collect(Collectors.toMap(TextReplyHandler::canHandle,
                textHandler -> textHandler));
    }

    /**
     * Configures Cross-Origin Resource Sharing (CORS) for the application.
     * This method sets up CORS policies to allow the frontend application hosted on GitHub Pages
     * to communicate with the backend API.
     *
     * @param registry The CorsRegistry to configure CORS mappings
     *                 <p>
     *                 The configuration:
     *                 - Applies to all endpoints under "/api/v1/**"
     *                 - Allows requests only from the GitHub Pages domain (https://vnsemkin.github.io)
     *                 - Permits GET, POST, PUT, DELETE, and OPTIONS HTTP methods
     *                 - Allows Content-Type and Authorization headers
     *                 - Enables credentials to be included in cross-origin requests (for cookies/authentication)
     *                 - Sets preflight request results to be cached for 3600 seconds (1 hour)
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/v1/**")
                .allowedOrigins(frontendUrl)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Content-Type", "Authorization")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
