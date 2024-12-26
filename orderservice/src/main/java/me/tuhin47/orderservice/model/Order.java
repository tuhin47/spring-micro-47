package me.tuhin47.orderservice.model;

import lombok.*;
import me.tuhin47.config.redis.RedisUserService;
import me.tuhin47.config.redis.UserRedis;
import me.tuhin47.entity.audit.UserDateAudit;
import me.tuhin47.entity.security.IOwner;
import me.tuhin47.utils.RoleUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serial;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends UserDateAudit<String> implements IOwner {

    @Serial
    private static final long serialVersionUID = -629300147502691040L;
    
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "quantity")
    private long quantity;

    @Column(name = "order_date")
    private Instant orderDate;

    @Column(name = "status", length = 50)
    private String orderStatus;

    @Column(name = "total_amount")
    private double amount;

    @Override
    public boolean isOwner() {
        UserRedis currentUser = RedisUserService.getCurrentUser();
        if (currentUser == null) {
            return false;
        }
        return Objects.equals(currentUser.getUserId(), getCreatedBy()) || currentUser.getAuthorityNames().contains(RoleUtils.ROLE_ADMIN);
    }

}
