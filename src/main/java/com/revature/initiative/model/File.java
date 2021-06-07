package com.revature.initiative.model;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "files")
@Data
@NoArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "uploaded_by", referencedColumnName = "id", insertable = false, updatable = false)
    private User uploadedBy;
    @Column(name = "uploaded_by")
    private Long uploadedById;
    @ManyToOne
    @JoinColumn(name = "initiative_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Initiative initiativeId;
    @Column(name = "initiative_id")
    private Long fileInitiativeId;
    @Column(name = "file_name")
    @NotBlank
    private String fileName;
    @Column(name = "file_url")
    @NotBlank
    private String fileURL;
}
