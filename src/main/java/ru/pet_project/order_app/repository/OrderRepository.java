package ru.pet_project.order_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.pet_project.order_app.entity.OrderEntity;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {


    @Query("SELECT MAX(o.userOrderNumber) FROM OrderEntity o WHERE o.user.id = :userId")
    Optional<Integer> findMaxUserOrderNumber(@Param("userId") UUID userId);

    Optional<OrderEntity> findByUserOrderNumberAndUserId(Integer userOrderNumber, UUID userId);

    Collection<OrderEntity> findAllByUserId(UUID userId);
}
