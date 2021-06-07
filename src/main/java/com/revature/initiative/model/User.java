package com.revature.initiative.model;

import com.revature.initiative.enums.Role;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@DynamicInsert
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
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'USER'")
    private Role role;
    @ManyToMany(mappedBy = "members")
    Set<Initiative> initiatives;
    @OneToMany(mappedBy = "uploadedBy")
    List<File> files;

}