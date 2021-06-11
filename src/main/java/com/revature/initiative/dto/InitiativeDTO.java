package com.revature.initiative.dto;

import com.revature.initiative.enums.InitiativeState;
import lombok.Data;

import java.util.Set;

@Data
public class InitiativeDTO {
    private Long createdBy;
    private String title;
    private String description;
    private Long pointOfContact;
    private InitiativeState state;
    Set<UserDTO> members;
    Set<FileDTO> files;
}
