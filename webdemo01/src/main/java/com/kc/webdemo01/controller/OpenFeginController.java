package com.kc.webdemo01.controller;

import com.kc.webdemo01.bean.UserData;
import com.kc.webdemo01.feign.UserServiceClient;
import com.kc.webdemo01.mapper.UserDataMapper;
import com.kc.webdemo01.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class OpenFeginController {

    public static final Logger log = LoggerFactory.getLogger(OpenFeginController.class);
    @Resource
    private UserServiceClient userServiceClient;

    @Resource
    private UserMapper userMapper;

    @GetMapping("/queryUserListWithOpenFegin")
    public List<UserData> queryUserListWithOpenFegin(){
        log.info("*******webdemo01--开始-->请求webdemo02*****");

        List<UserData> userList = userServiceClient.queryUserList();

        log.info("*******webdemo01---->请求webdemo02**请求成功***");

        for(UserData user:userList){
            System.out.println(user);
        }
        return userList;
    }



}

