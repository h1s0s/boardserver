package com.fastcampus.boardserver.controller;

import com.fastcampus.boardserver.dto.LoginRequest;
import com.fastcampus.boardserver.dto.LoginResponse;
import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.service.impl.UserServiceImpl;
import com.fastcampus.boardserver.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.manager.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@Log4j2
public class UserController {

    @Autowired
    private final UserServiceImpl userService;
    //확장에는 열려있고 수정에는 닫혀있게끔 인터페이스 구조로 설계
    private static LoginResponse loginResponse;


    @Autowired
    public UserController(UserServiceImpl userService){
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void SignUp(@RequestBody UserDTO userDTO){
        if (UserDTO.hasNullDateBeforeRegister(userDTO)){
            throw new RuntimeException("회원가입 정보를 확인해주세요.");
        }
        userService.register(userDTO);
    }

    @PostMapping("/sign-in")
    public HttpStatus SignIn(@RequestBody LoginRequest userLoginRequest,
                             HttpSession session){
        ResponseEntity<LoginResponse> responseEntity = null;
        String id = userLoginRequest.getUserId();
        String password = userLoginRequest.getPassword();
        UserDTO userInfo = userService.login(id, password);

        if(userInfo == null){
            return HttpStatus.NOT_FOUND;
        } else if (userInfo != null){
            loginResponse = LoginResponse.success(userInfo);
            if(userInfo.getStatus() == (UserDTO.Status.ADMIN))
                SessionUtil.setLoginAdminId(session, id);
            else
                SessionUtil.setLoginMemberId(session, id);

            responseEntity = new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
        } else {
            throw new RuntimeException("Login Error! 유저 정보가 없거나 지원되지 않는 유저입니다.")
        }
        return HttpStatus.OK;
    }

    @GetMapping("/my-info")
    public UserInfoResponse memberInfo(HttpSession session){
        String Id = sessionUtil.getLoginMemberId(session);
        if (id == null) id = SessionUtil.getLoginAdminId(session);
        UserDto memberInfo = userService.getUserInfo(id);
        return new UserInfoResponse(memberInfo);
    }

    @PutMapping("/logout")
    public void logout(HttpSession session){
        SessionUtil.clear(session);
    }

    @PatchMapping("/password")
    public ResponseEntity<LoginResponse> updateUserPassword(@RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest
    ,HttpSession session){
        ResponseEntity<LoginResponse> responseEntity = null;

        String id = SessionUtil.getLoginMemberId(session);
        String beforePassword = userUpdatePasswordRequest.getBeforePassword();
        String afterPassword = userUpdatePasswordRequest.getAfterPassword();

        try {

        } catch(IllegalArgumentException e){
            log.error("updatePassword 실패 {}", e);
            responseEntity = new ResponseEntity<LoginResponse>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }

    @DeleteMapping
    public ResponseEntity<LoginResponse> deleteId(@RequestBody UserDeleteId userDeleteId,
                                                  HttpSession session){
        ResponseEntity<LoginResponse> responseEntity = null;
        String id = SessionUtil.getLoginMemberId(session);
        try {
            userService.deleteId(id, userDeleteId.getPassword(loginResponse, HttpStatus.OK));
        } catch (RuntimeException e){
            log.error("deleteId 실패 {}", e);
            responseEntity = new ResponseEntity<>(LoginResponse)(HttpStatus.BAD_REQUEST);

        }
        return responseEntity;
    }
}
