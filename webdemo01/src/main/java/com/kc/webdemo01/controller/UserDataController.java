package com.kc.webdemo01.controller;

import com.kc.webdemo01.bean.UserData;
import com.kc.webdemo01.mapper.UserDataMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author KCWang
 * @version 1.0
 * @date 2023/6/25 上午11:44
 */

@RestController
public class UserDataController {

    @Resource
    private UserDataMapper userDataMapper;

    @GetMapping("/queryUserList")
    public List<UserData> queryUserList(){
        List<UserData> userList = userDataMapper.queryUserList();

        for(UserData user:userList){
            System.out.println(user);
        }
        return userList;
    }

    @GetMapping("/queryUserById")
    public UserData queryUserById(){
        UserData user = userDataMapper.queryUserById(12);
        System.out.println(user);
        return user;
    }

    @GetMapping("/addUser")
    public String addUser(){
        userDataMapper.addUser(new UserData(2,"阿毛",1,"20220712","12", "23"));
        return "添加成功";
    }

    @GetMapping("/updateUser")
    public String updateUser(){
        userDataMapper.updateUser(new UserData(1,"秃驴",1, "20220723", "22", "23"));
        return "更改成功";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(){
        userDataMapper.deleteUser(1);
        return "删除成功";
    }
}

