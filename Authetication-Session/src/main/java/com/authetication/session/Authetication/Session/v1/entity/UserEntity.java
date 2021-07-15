package com.authetication.session.Authetication.Session.v1.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "authent_user")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public @Data class UserEntity extends AbstractEntity<Long> implements UserDetails, Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private String login;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	private boolean admin;

	private String email;

	@ManyToMany
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(
					name = "user_id", referencedColumnName = "login"),
			inverseJoinColumns = @JoinColumn(
					name = "role_id", referencedColumnName = "nameRole"))
	private List<RoleEntity> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return (Collection<? extends GrantedAuthority>) this.roles;
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}
}
