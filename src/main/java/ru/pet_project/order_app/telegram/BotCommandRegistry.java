package ru.pet_project.order_app.telegram;

import org.springframework.stereotype.Service;
import ru.pet_project.order_app.telegram.command.*;

import java.util.Map;

@Service
public class BotCommandRegistry {

    private final Map<String, Command> commands;

    public BotCommandRegistry() {
        commands = Map.of(
                "/start", new StartCommand(),
                "/help", new HelpCommand(),
                "/neworder", new NewOrderCommand(),
                "/error", new ErrorCommand(),
                "/orders", new OrdersCommand(),
                "/order", new OrderCommand()
        );
    }

    public Command get(String key) {
        return commands.getOrDefault(key, new ErrorCommand());
    }
}
