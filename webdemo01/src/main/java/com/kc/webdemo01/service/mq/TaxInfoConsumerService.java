package com.kc.webdemo01.service.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @author KCWang
 * @version 1.0
 * @date 2024/12/14 下午9:42
 */
@Slf4j
// MessageExt：是一个消息接收通配符，不管发送的是String还是对象，都可接收，当然也可以像上面明确指定类型（我建议还是指定类型较方便）
//,messageModel = MessageModel.BROADCASTING
@Service
@RocketMQMessageListener(topic = "TEST_TOPIC", consumerGroup = "TEST_Group")
public class TaxInfoConsumerService implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt message) {
        System.out.println("线程" + Thread.currentThread() + "内容为:"
                + new String(message.getBody()) +
                "队列序号:" + message.getQueueId());
        //这里消费的消息可以写自己的业务逻辑代码，比如插入，删除，上传。。。

    }
}

