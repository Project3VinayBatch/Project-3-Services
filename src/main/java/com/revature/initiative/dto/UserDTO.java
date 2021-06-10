package com.revature.initiative.dto;

import com.revature.initiative.enums.Role;
import com.revature.initiative.model.File;
import com.revature.initiative.model.Initiative;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String userName;
    private Role role;
    Set<InitiativeDTO> initiatives;
    List<FileDTO> files;
}
