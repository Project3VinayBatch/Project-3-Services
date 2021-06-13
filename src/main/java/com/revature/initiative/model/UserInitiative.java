package com.revature.initiative.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_initiatives")
@Data
@NoArgsConstructor
public class UserInitiative {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "initiative_id")
    private Long initiativeId;
    @Column(name = "user_id")
    private Long userId;

    public UserInitiative(Long initiativeId, Long userId) {
        this.initiativeId = initiativeId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInitiative that = (UserInitiative) o;
        return this.hashCode() == that.hashCode();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
