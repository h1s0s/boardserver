package com.fastcampus.boardserver.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDTO {

    public enum Status {
        DEFAULT, ADMIN, DELETED
    }
    private int id;
    private String userID;
    private String password;
    private String nickname;
    private boolean isAdmin;
    private Date createTime;
    private boolean isWithDraw; //탈퇴여부
    private Status status;
    private Date updateTime;

}
