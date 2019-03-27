package com.muei.apm.taxi5.apirest.rest.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginDTO implements Serializable {

    private String login;

    private String password;

}
