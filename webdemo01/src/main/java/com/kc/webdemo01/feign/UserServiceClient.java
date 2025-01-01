package com.kc.webdemo01.feign;

import com.kc.webdemo01.bean.UserData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author KCWang
 * @version 1.0
 * @date 2025/1/1 上午10:44
 */
@FeignClient(name = "webdemo02",url = "http://localhost:8082")
public interface UserServiceClient {


    @GetMapping("/queryUserList")
    List<UserData> queryUserList();
}

