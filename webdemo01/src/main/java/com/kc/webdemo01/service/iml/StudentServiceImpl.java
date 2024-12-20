package com.kc.webdemo01.service.iml;

import com.alibaba.dubbo.config.annotation.Service;
import com.kc.commonsdk.StudentService;
import org.springframework.stereotype.Component;

/**
 * @author KCWang
 * @version 1.0
 * @date 2024/12/20 下午4:42
 */
@Component
@Service(interfaceClass = StudentService.class,version = "1.0.0",timeout = 15000)
public class StudentServiceImpl implements StudentService {
    @Override
    public Integer queryAllStudentCount() {
        //调用数据库持久层

        return 10;
    }
}

