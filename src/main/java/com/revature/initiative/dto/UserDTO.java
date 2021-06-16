package com.revature.initiative.dto;

import com.revature.initiative.enums.Role;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private Role role;
    Set<InitiativeDTO> initiatives;
    List<FileDTO> files;
}
