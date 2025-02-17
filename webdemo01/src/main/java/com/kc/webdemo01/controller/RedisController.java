package com.kc.webdemo01.controller;

import com.alibaba.fastjson.JSONObject;
import com.kc.webdemo01.bean.UserData;
import com.kc.webdemo01.entity.ResponseResult;
import com.kc.webdemo01.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author KCWang  用户缓存测试类
 * @version 1.0
 * @date 2024/12/17 下午10:16
 */
@RestController
@RequestMapping("redisUserCache")
public class RedisController {

    private final RedisTemplate redisTemplate;
    @Autowired
    private UserService userService;

    public RedisController(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("save")
    public Boolean save(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        return Boolean.TRUE;
    }


    @RequestMapping("/findList")
    @ResponseBody
    public JSONObject findList(HttpServletRequest request) {
        JSONObject rr = new JSONObject();
        List<UserData> list = userService.findList();
        if (list != null && list.size() > 0) {
            rr.put("state", "0");
            rr.put("msg", "success");
            rr.put("data", list);
        }
        return rr;
    }

    @RequestMapping("/findById")
    @ResponseBody
    public ResponseResult<UserData> findById(@RequestParam String id) {
        ResponseResult<UserData> resData = new ResponseResult<UserData>();
        UserData UserData = userService.findById(Integer.valueOf(id));
        resData.setStatus(0);
        resData.setMessage("成功获取数据");
        resData.setData(UserData);
        return resData;
    }

    @RequestMapping(value = "AddUser", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject AddUser(UserData UserData) {
        JSONObject rr = new JSONObject();
        try {
            if (UserData != null) {
                userService.AddUser(UserData);
                rr.put("state", "0");
                rr.put("msg", "success");
            } else {
                rr.put("state", "2");
                rr.put("msg", "参数错误");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            rr.put("state", "2");
            rr.put("msg", "保存用户信息失败");
            e.printStackTrace();
        }
        return rr;
    }

    @RequestMapping(value = "deleteUser", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteUser(@RequestParam String id) {
        JSONObject rr = new JSONObject();
        try {
            if (StringUtils.isNotBlank(id)) {
                userService.deleteUser(Integer.parseInt(id));
                rr.put("state", "0");
                rr.put("msg", "用户信息删除成功");
            } else {
                rr.put("state", "2");
                rr.put("msg", "参数输入错误");
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            rr.put("state", "2");
            rr.put("msg", "用户操作错误");
        }
        return rr;
    }

    @RequestMapping(value = "updatePwdById", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updatePwdById(UserData UserData) {
        JSONObject rr = new JSONObject();
        try {
            if (UserData != null) {
                if (StringUtils.isNotBlank(UserData.getBirthday())) {
                    userService.updatePwdById(UserData);
                    rr.put("state", "0");
                    rr.put("msg", "用户密码修改成功");
                } else {
                    rr.put("state", "2");
                    rr.put("msg", "参数错误");
                }
            } else {
                rr.put("state", "2");
                rr.put("msg", "没有修改数据");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            rr.put("state", "2");
            rr.put("msg", "用户操作错误");
            e.printStackTrace();
        }
        return rr;
    }
}
    
