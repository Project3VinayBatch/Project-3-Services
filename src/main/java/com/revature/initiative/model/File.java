package com.revature.initiative.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@DynamicInsert
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File that = (File) o;
        return this.hashCode() == that.hashCode();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
