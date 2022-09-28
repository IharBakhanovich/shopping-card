package com.bakhanovich.interviews.shoppingcart.model.impl;

import com.bakhanovich.interviews.shoppingcart.model.DatabaseEntity;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The POJO that describes the {@link Article} entity in the system.
 *
 * @author Ihar Bakhanovich.
 */
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Article implements DatabaseEntity, Serializable {
    private long id;
    private BigDecimal preis;
    private int amount;
    private int minAmount;
}
