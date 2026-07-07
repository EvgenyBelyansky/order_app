package ru.pet_project.order_app.telegram.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.pet_project.order_app.dto.OrderInputDto;
import ru.pet_project.order_app.dto.OrderOutputDto;
import ru.pet_project.order_app.dto.OrderShortOutputDto;
import ru.pet_project.order_app.entity.UserEntity;
import ru.pet_project.order_app.service.OrderService;
import ru.pet_project.order_app.service.UserService;
import ru.pet_project.order_app.telegram.BotCommandRegistry;
import ru.pet_project.order_app.telegram.command.Command;
import ru.pet_project.order_app.telegram.command.ErrorCommand;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TgBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.token}")
    private String token;

    @Value("${telegram.bot.username}")
    private String username;

    private final UserService userService;
    private final OrderService orderService;
    private final BotCommandRegistry commandRegistry;

    public TgBot(UserService userService, OrderService orderService, BotCommandRegistry commandRegistry) {
        super("8996201255:AAGGeOCI_SgI9JdGXWXcINxtkxpKwzWigfQ");  // токен напрямую
        this.userService = userService;
        this.orderService = orderService;
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void onUpdateReceived(Update update) {
        final String text = update.getMessage().getText();
        Pattern pattern = Pattern.compile("^/(\\w+)\\s*(.*)$");
        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()) {
            String commandKey = "/" + matcher.group(1);
            String args = matcher.group(2);

            switch (commandKey) {
                case "/start" -> {
                    Long chatId = update.getMessage().getChatId();
                    String firstName = update.getMessage().getFrom().getFirstName();
                    String lastName = update.getMessage().getFrom().getLastName();
                    userService.registerUser(firstName, lastName, chatId.toString());
                }
                case "/neworder" -> {
                    Long chatId = update.getMessage().getChatId();
                    String[] parts = args.split("\\|");
                    if (parts.length >= 2) {
                        String name = parts[0].trim();
                        String description = parts[1].trim();
                        UserEntity user = userService.getByTgChatId(chatId.toString());
                        OrderInputDto dto = new OrderInputDto(name, description);
                        orderService.createNewOrder(dto, user.getId());
                        executeMessage(new SendMessage(chatId.toString(), "Заказ \"" + name + "\" создан!"));
                        return;
                    }
                }
                case "/orders" -> {
                    Long chatId = update.getMessage().getChatId();
                    UserEntity user = userService.getByTgChatId(chatId.toString());
                    List<OrderShortOutputDto> orders = orderService.getAllOrdersByUserId(user.getId());

                    StringBuilder sb = new StringBuilder("Ваши заказы:\n");
                    for (OrderShortOutputDto o : orders) {
                        sb.append("№").append(o.getUserOrderNumber())
                                .append(" — ").append(o.getName())
                                .append(" [").append(o.getStatus()).append("]\n");
                    }
                    executeMessage(new SendMessage(chatId.toString(), sb.toString()));
                    return;
                }
                case "/order" -> {
                    Long chatId = update.getMessage().getChatId();
                    try {
                        int number = Integer.parseInt(args.trim());
                        UserEntity user = userService.getByTgChatId(chatId.toString());
                        OrderOutputDto order = orderService.getOrderByUserOrderNumber(user.getId(), number);

                        String msg = "Заказ №" + order.getUserOrderNumber() +
                                "\nНазвание: " + order.getName() +
                                "\nОписание: " + order.getDescription() +
                                "\nСтатус: " + order.getStatus() +
                                "\nСоздан: " + order.getCreatedDate();
                        executeMessage(new SendMessage(chatId.toString(), msg));
                    } catch (NumberFormatException e) {
                        executeMessage(new SendMessage(chatId.toString(), "Укажи номер заказа: /order 1"));
                    }
                    return;
                }
            }

            Command command = commandRegistry.get(commandKey);
            executeMessage(command.process(update));
        } else {
            executeMessage(commandRegistry.get("/error").process(update));
        }
    }

    private void executeMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return "orderAPITest_bot";
    }

    @Override
    public String getBotToken() {
        return token;
    }
}