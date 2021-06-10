package com.revature.initiative.model;

import com.revature.initiative.enums.Role;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@DynamicInsert
@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    private Long id;
    @Column(nullable = false)
    private String userName;
    @Column
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'USER'")
    private Role role;
    @ManyToMany(mappedBy = "members")
    Set<Initiative> initiatives;
    @OneToMany(mappedBy = "uploadedBy")
    List<File> files;

}