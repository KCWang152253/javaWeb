package com.kc.webdemo01.mapper;

import com.kc.webdemo01.Webdemo01Application;
import com.kc.webdemo01.bean.UserData;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author KCWang
 * @version 1.0
 * @date 2025/1/1 上午10:30
 */
@SpringBootTest(classes = Webdemo01Application.class)
@RunWith(SpringRunner.class)
public class UserMapperTest extends TestCase {


    @Resource
    private UserMapper userMapper;


    @Test
    public void testQueryUserList() {
        System.out.println(("----- selectAll method test --queryUserListWithMybatisPlus----"));
        List<UserData> userList = userMapper.selectList(null);
//        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);

    }
}