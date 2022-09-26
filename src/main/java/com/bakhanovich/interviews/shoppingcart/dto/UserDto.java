package com.bakhanovich.interviews.shoppingcart.dto;

import com.bakhanovich.interviews.shoppingcart.model.impl.Article;
import com.bakhanovich.interviews.shoppingcart.model.impl.User;
import com.bakhanovich.interviews.shoppingcart.model.impl.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * The DataTransferObject for the {@link User} entity to transfer data outside the system.
 *
 * @author Ihar Bakhanovich
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private long userId;
    private String nickname;
    private BigDecimal cartCosts;
    private Role role;
    List<ArticleDto> articles;
}
