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
    /**
     * Username is ONE word!
     */
    @Column(nullable = false)
    private String username;
    @Column
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'USER'")
    private Role role;
    @ManyToMany(mappedBy = "members")
    Set<Initiative> initiatives;
    @OneToMany(mappedBy = "uploadedBy")
    List<File> files;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return this.hashCode() == that.hashCode();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}