package com.fastcampus.boardserver.controller;

import com.fastcampus.boardserver.service.impl.UserServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Log4j2
public class UserController {

    @Autowired
    private UserServiceImpl UserService;
    //확장에는 열려있고 수정에는 닫혀있게끔 인터페이스 구조로 설계



}
