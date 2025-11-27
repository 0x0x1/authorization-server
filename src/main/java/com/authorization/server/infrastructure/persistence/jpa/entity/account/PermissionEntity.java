package com.authorization.server.infrastructure.persistence.jpa.entity.account;

import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

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

    private String permissionName;
    private String description;

    @Override
    public String toString() {
        return "PermissionEntity{" +
                "id=" + id +
                ", permissionName='" + permissionName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}