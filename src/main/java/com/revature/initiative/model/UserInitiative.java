package com.revature.initiative.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_initiatives")
@Data
public class UserInitiative {
    @Id
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
