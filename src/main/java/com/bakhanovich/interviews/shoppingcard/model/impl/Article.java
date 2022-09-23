package com.bakhanovich.interviews.shoppingcard.model.impl;

import com.bakhanovich.interviews.shoppingcard.model.DatabaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The POJO that describes the {@link Article} entity in the system.
 *
 * @author Ihar Bakhanovich
 */
@Data
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
    @Column(name = "preis", unique = true, nullable = false)
    private int preis;
    @Column(name = "amount", nullable = false)
    private int amount;
}
