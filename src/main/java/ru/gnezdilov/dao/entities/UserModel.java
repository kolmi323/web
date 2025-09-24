package ru.gnezdilov.dao.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
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
