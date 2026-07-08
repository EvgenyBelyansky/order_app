package ru.pet_project.order_app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.pet_project.order_app.enums.OrderStatus;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name",
            nullable = false)
    private String name;

    @Column(name = "description",
            nullable = false)
    private String description;

    @Column(name = "user_order_number",
            nullable = false)
    private Integer userOrderNumber;

    @CreationTimestamp
    @Column(name = "created_date")
    private Instant createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private Instant updatedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Builder
    public OrderEntity(String name, String description, int userOrderNumber, UserEntity user) {
        this.name = name;
        this.description = description;
        this.userOrderNumber = userOrderNumber;
        this.user = user;
        this.status = OrderStatus.NEW;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(createdDate, that.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdDate);
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", status=" + status +
                '}';
    }

    public void markAsProcessing() {
        this.status = OrderStatus.PROCESSING;
    }

    public void markAsShipped() {
        this.status = OrderStatus.SHIPPED;
    }

    public void markAsExecuted() {
        this.status = OrderStatus.EXECUTED;
    }

    public void markAsCanceled() {
        this.status = OrderStatus.CANCELLED;
    }
}
