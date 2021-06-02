package com.revature.initiative.model;

import com.revature.initiative.enums.InititiativeState;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
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
    private String title;
    @Column
    private String description;
    @ManyToOne
    @JoinColumn(name = "point_of_contact", referencedColumnName = "id")
    private User pointOfContact;
    @Column
    private InititiativeState state;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @ManyToMany
    @JoinTable(name = "user_initiatives",
            joinColumns = @JoinColumn(name="initiative_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    Set<User> members;
}
