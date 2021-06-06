package com.revature.initiative.model;

import com.revature.initiative.enums.Role;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String password;
    @Column
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'USER'")
    private Role role;
    @ManyToMany(mappedBy = "members")
    Set<Initiative> initiatives;

}