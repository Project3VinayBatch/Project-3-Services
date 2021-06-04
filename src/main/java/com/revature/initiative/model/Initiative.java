package com.revature.initiative.model;

import com.revature.initiative.enums.InitiativeState;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "initiatives")
@Data
public class Initiative {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private User createdBy;
    @Column
    @NotBlank
    private String title;
    @Column
    @NotBlank
    @Lob
    private String description;
    @ManyToOne
    @JoinColumn(name = "point_of_contact", referencedColumnName = "id")
    private User pointOfContact;
    @Column
    private InitiativeState state;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @ManyToMany
    @JoinTable(name = "user_initiatives",
            joinColumns = @JoinColumn(name="initiative_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    Set<User> members;
    @OneToMany(mappedBy = "initiativeId")
    Set<File> files;

}
