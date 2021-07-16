package com.authetication.session.Authetication.Session.v1.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public @Data class UpdatePasswordResquest implements Serializable {

    @NotNull(message = "Email Obrigatorio")
    private String email;

    @NotNull(message = "Password Obrigatorio")
    private String password;

}
