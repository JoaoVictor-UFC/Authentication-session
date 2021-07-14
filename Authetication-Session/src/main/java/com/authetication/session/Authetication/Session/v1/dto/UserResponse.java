package com.authetication.session.Authetication.Session.v1.dto;

import java.io.Serializable;

import lombok.Data;

public @Data class UserResponse implements Serializable {

	private Long id;

	private String name;

	private String login;

	private boolean admin;

	private String email;
}
