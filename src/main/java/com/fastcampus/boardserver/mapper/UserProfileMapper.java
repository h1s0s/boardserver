package com.fastcampus.boardserver.mapper;

import com.fastcampus.boardserver.dto.UserDTO;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Mapper
public interface UserProfileMapper {

    UserDTO getUserProfile(@Param("id") String id);

    int insertUserProfile(@Param("id") String id, @Param("password") String password
                          , @Param("name") String name
                          , @Param("phone") String phone, @Param("address") String address
                          , @Param("createTime") String createTime, @Param("updateTime") String updateTime);

    int deleteUserProfile(@Param("id") String id);

    UserDTO fileByIdAndPassword(@Param("id") String id, @Param("password") String password);

    int idCheck(@Param("id") String id);

    int updatePassword(UserDTO user);

//    int updateAddress(UserDTO user);

    int register(UserDTO user);

}
