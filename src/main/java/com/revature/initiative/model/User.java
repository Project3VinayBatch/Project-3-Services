package com.revature.initiative.model;

import com.revature.initiative.enums.Role;
import lombok.Data;

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
    @Column
    @NotBlank
    private String userName;
    @Column
    @NotBlank
    private String password;
    @Column
    private Role role;
    @ManyToMany(mappedBy = "members")
    Set<Initiative> initiatives;

}