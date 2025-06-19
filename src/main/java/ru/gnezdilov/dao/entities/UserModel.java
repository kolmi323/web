package ru.gnezdilov.dao.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode
@NamedQueries({
        @NamedQuery(name = "User.findByEmailAndPassword",
                query = "SELECT u FROM UserModel AS u WHERE u.email LIKE :email AND u.password LIKE :password")
})
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, name = "name")
    private String name;
    @Column(unique = true, nullable = false, name = "email")
    private String email;
    @Column(nullable = false, name = "password")
    private String password;
}
