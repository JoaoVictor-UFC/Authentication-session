package com.authetication.session.Authetication.Session.v1.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public @Data class LoginRequest implements Serializable {

    @NotBlank(message = "Login Obrigatorio")
    private String login;

    @NotBlank(message = "Senha Obrigatoria")
    private String password;
}
