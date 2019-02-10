package com.crafty.entity.base;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class BaseEntityId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.annotation.Id
    @Column(columnDefinition = "CHAR(36)", unique = true)
    @NotNull
    private String id;

    public String getId() {
        return id;
    }

    /**
     * We want to allow support for the creator of a domain to supply a id.
     * However, once it's been created and the id has been set, we cannot change it.
     */
    public void setId(String id) {
        if (this.id == null) {
            this.id = id;
        }
    }

    /**
     * Make sure id exists before insert
     */
    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getId() != null ? this.getId().hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        BaseEntityId other = (BaseEntityId) object;

        return id != null ? id.equals(other.id) : other.id == null;
    }

    public BaseEntityId clone(String newId){
        this.id = newId;
        return this;
    }

}
