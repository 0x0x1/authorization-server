package com.authorization.server.infrastructure.persistence.jpa.entity.authorization;

import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PermissionEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "DISPLAY_NAME", nullable = false)
    private String displayName;

    @NotNull
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Override
    public String toString() {
        return "PermissionEntity{" +
                "id=" + id +
                ", permissionName='" + displayName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}