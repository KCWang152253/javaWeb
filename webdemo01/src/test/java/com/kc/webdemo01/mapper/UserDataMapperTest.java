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
 * @date 2024/12/31 下午5:35
 */
@SpringBootTest(classes = Webdemo01Application.class)
@RunWith(SpringRunner.class)
public class UserDataMapperTest extends TestCase {

    @Resource
    private UserDataMapper userDataMapper;


    @Test
    public void testQueryUserList() {
        System.out.println(("----- selectAll method test ------"));
        List<UserData> userList = userDataMapper.queryUserList();
//        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);

    }
}