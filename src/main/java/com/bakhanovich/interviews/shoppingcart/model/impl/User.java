package com.bakhanovich.interviews.shoppingcart.model.impl;

import com.bakhanovich.interviews.shoppingcart.model.DatabaseEntity;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * The POJO that describes the {@link User} entity in the system.
 *
 * @author Ihar Bakhanovich.
 */
@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements DatabaseEntity, Serializable {
    private long id;
    private String nickName;
    private String password;
    private Role role;
    private List<Article> articles;
}
