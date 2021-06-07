package com.revature.initiative.model;

import com.revature.initiative.enums.Role;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import java.util.List;
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
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'USER'")
    private Role role;
    @ManyToMany(mappedBy = "members")
    Set<Initiative> initiatives;
    @OneToMany(mappedBy = "uploadedBy")
    List<File> files;

}