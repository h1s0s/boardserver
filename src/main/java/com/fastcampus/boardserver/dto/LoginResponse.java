package com.fastcampus.boardserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private String userId;
    private String password;

}
