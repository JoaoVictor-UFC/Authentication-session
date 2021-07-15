package com.authetication.session.Authetication.Session.v1.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "authent_user")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public @Data class UserEntity extends AbstractEntity<Long> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private String login;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	private boolean admin;

	private String email;
}
