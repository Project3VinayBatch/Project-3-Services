package com.revature.initiative.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.cglib.core.GeneratorStrategy;

import javax.persistence.*;

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
}
