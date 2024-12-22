package com.kc.webdemo01.boot;

import com.kc.webdemo01.service.mq.MQProducerService;
import com.sun.tools.internal.jxc.SchemaGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author KCWang
 * @version 1.0
 * @date 2024/12/22 下午10:30
 */
@Component
public class MqTestRunner implements ApplicationRunner {

    @Autowired
    private MQProducerService mqProducerService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        mqProducerService.sendAsyncMsg("**************MqTest***************", "TEST_TOPIC");
//        mqProducerService.sendMsg("**************MqTest***************", "TEST_TOPIC");

    }
}
