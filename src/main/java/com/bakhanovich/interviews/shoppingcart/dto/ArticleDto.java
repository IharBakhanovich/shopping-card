package com.bakhanovich.interviews.shoppingcart.dto;

import com.bakhanovich.interviews.shoppingcart.model.impl.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.bakhanovich.interviews.shoppingcart.model.impl.Article;

import java.math.BigDecimal;

/**
 * The DataTransferObject for the {@link Article} entity to transfer data outside the system.
 *
 * @author Ihar Bakhanovich
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private long articleId;
    private BigDecimal price;
    private int amount;
}
