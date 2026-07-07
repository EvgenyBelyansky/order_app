package ru.pet_project.order_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pet_project.order_app.entity.UserEntity;
import ru.pet_project.order_app.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    @Transactional
    public void registerUser(String name, String lastName, String tgChatId) {
        if (!repository.existsByTgChatId(tgChatId)) {
            UserEntity user = UserEntity.builder()
                    .name(name)
                    .lastName(lastName)
                    .tgChatId(tgChatId)
                    .build();
            repository.save(user);
        }
    }

    @Transactional
    public UserEntity getUserById(UUID id) {
        if (id == null) {
            throw new RuntimeException("Передан ID c [NULL] значением");
        }

        return repository.findById(id)
                .orElseThrow(() -> new  RuntimeException("Пользователь с ID [%s] не найден".formatted(id)));
    }

    public UserEntity getByTgChatId(String tgChatId) {
        if (tgChatId == null || tgChatId.isBlank()) {
            throw new RuntimeException("Передан пустой Tg_Chat_Id");
        }

        return repository.findByTgChatId(tgChatId)
                .orElseThrow(() -> new  RuntimeException("Пользователь с Tg_Id [%s] не найден".formatted(tgChatId)));

    }
}
