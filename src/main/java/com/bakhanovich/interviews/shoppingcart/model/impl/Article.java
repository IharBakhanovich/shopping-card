package com.bakhanovich.interviews.shoppingcart.model.impl;

import com.bakhanovich.interviews.shoppingcart.model.DatabaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The POJO that describes the {@link Article} entity in the system.
 *
 * @author Ihar Bakhanovich
 */
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "article")
@Table(
        schema = "shopping_card",
        name = "article"
)
public class Article implements DatabaseEntity, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false)
    private long id;
    @Column(name = "preis", nullable = false)
    private BigDecimal preis;
    @Column(name = "amount", nullable = false)
    private int amount;
}
