package com.bakhanovich.interviews.shoppingcart.model.impl;

import com.bakhanovich.interviews.shoppingcart.model.DatabaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The POJO that describes the {@link User} entity in the system.
 *
 * @author Ihar Bakhanovich
 */
@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
@Table(
        schema = "shopping_card",
        name = "user"
)
public class User implements DatabaseEntity, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private long id;
    @Column(name = "nickname", unique = true, nullable = false)
    private String nickName;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name="role", nullable = false)
    private Role role;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_ordered_article",
            joinColumns = {@JoinColumn(name = "userid", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "articleid", referencedColumnName = "id")}
    )
    private List<Article> articles;
}
