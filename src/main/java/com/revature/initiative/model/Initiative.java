package com.revature.initiative.model;

import com.revature.initiative.enums.InitiativeState;
import lombok.Data;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@DynamicInsert
@Entity
@Table(name = "initiatives")
@Data
public class Initiative {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id", insertable = false, updatable = false)
    private User createdBy;
    @Column(name = "created_by")
    @NotNull
    private Long createdById;
    @Column(unique = true, nullable = false)
    private String title;
    @Column
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String description;
    @ManyToOne
    @JoinColumn(name = "point_of_contact", referencedColumnName = "id", insertable = false, updatable = false)
    private User pointOfContact;
    @Column(name = "point_of_contact")
    private Long pointOfContactId;
    @Column
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ACTIVE'")
    private InitiativeState state;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @ManyToMany
    @JoinTable(name = "user_initiatives",
            joinColumns = @JoinColumn(name = "initiative_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    Set<User> members;
    @OneToMany(mappedBy = "initiativeId")
    Set<File> files;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Initiative that = (Initiative) o;
        return this.hashCode() == that.hashCode();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
