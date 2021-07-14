package com.authetication.session.Authetication.Session.v1.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "authent_user")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public @Data class UserEntity extends AbstractEntity<Long> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private String login;
	
	private String password;
	
	private boolean admin;

	private String email;

}
