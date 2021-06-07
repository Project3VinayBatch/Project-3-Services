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
    @JoinColumn(name = "uploaded_by", referencedColumnName = "id")
    private User uploadedBy;
    @ManyToOne
    @JoinColumn(name = "initiative_id", referencedColumnName = "id")
    private Initiative initiativeId;
    @Column(name = "file_name")
    @NotBlank
    private String fileName;
    @Column(name = "file_url")
    @NotBlank
    private String fileURL;
}
