package com.kc.webdemo.mapper;

import com.kc.webdemo.bean.UserData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author KCWang
 * @version 1.0
 * @date 2023/6/25 上午11:38
 */

@Mapper
public interface UserDataMapper {

    List<UserData> queryUserList();

    UserData queryUserById(int id);

    int addUser(UserData user);

    int updateUser(UserData user);

    int deleteUser(int id);
}
