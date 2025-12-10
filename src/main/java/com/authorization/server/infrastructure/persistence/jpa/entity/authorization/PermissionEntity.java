package com.authorization.server.infrastructure.persistence.jpa.entity.authorization;

import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import com.authorization.server.infrastructure.persistence.jpa.constant.Jpa;
import com.authorization.server.infrastructure.persistence.jpa.entity.NamedEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = Jpa.Table.PERMISSION, uniqueConstraints = @UniqueConstraint(columnNames = Jpa.Column.DISPLAY_NAME))
public class PermissionEntity extends NamedEntity {

    public PermissionEntity(UUID id, String displayName, String description) {
        this.setId(id);
        this.setDisplayName(displayName);
        this.setDescription(description);
    }

    @Override
    public String toString() {
        return "PermissionEntity{" +
                "id=" + this.getId() +
                ", permissionName='" + this.getDisplayName() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                '}';
    }
}