package com.revature.initiative.dto;

import com.revature.initiative.enums.InitiativeState;
import com.revature.initiative.model.User;
import lombok.Data;

import java.util.Set;

@Data
public class InitiativeDTO {
    private Long createdBy;
    private String title;
    private String description;
    private Long pointOfContact;
    private InitiativeState state;
    Set<User> members;
}
