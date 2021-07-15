package com.authetication.session.Authetication.Session.v1.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public @Data class RoleEntity implements GrantedAuthority {

    @Id
    private String nameRole;

    @ManyToMany
    private List<UserEntity> users;

    @Override
    public String getAuthority() {
        return this.nameRole;
    }
}
