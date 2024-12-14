package com.kc.webdemo01.service.mq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author KCWang
 * @version 1.0
 * @date 2024/12/14 下午9:38
 */
@Slf4j
@Component
public class MQProducerService {

    /**
     * 直接注入使用，用于发送消息到broker服务器
     */
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 普通发送
     */
    public void send(String body, String topic) {
        rocketMQTemplate.convertAndSend(topic, body);
//        rocketMQTemplate.send(topic + ":tag1", MessageBuilder.withPayload(user).build()); // 等价于上面一行
    }

//    public void sendOneWayMsg(User user,String topic) {
//        rocketMQTemplate.sendOneWay(topic, MessageBuilder.withPayload(user).build());
//    }


    /**
     * 发送同步消息（阻塞当前线程，等待broker响应发送结果，这样不太容易丢失消息）
     * （msgBody也可以是对象，sendResult为返回的发送结果）
     */
    public SendResult sendMsg(String body, String topic) {
        SendResult sendResult = rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(body).build());
        log.info("【sendMsg】sendResult={}", JSON.toJSONString(sendResult));
        return sendResult;
    }

    /**
     * 发送异步消息（通过线程池执行发送到broker的消息任务，执行完后回调：在SendCallback中可处理相关成功失败时的逻辑）
     * （适合对响应时间敏感的业务场景）
     */
    public void sendAsyncMsg(String body, String topic) {

        rocketMQTemplate.asyncSend(topic, MessageBuilder.withPayload(body).build(), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                //发送成功处理...
            }

            @Override
            public void onException(Throwable throwable) {
                //发送失败处理...

            }
        });
    }

    /**
     * 发送延时消息（上面的发送同步消息，delayLevel的值就为0，因为不延时）
     * 在start版本中 延时消息一共分为18个等级分别为：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     */
    public void sendDelayMsg(String body, String topic, int delayLevel) {
        rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(body).build(), 3000, delayLevel);
    }

    /**
     * 发送单向消息（只负责发送消息，不等待应答，不关心发送结果，如日志）
     */
    public void sendOneWayMsg(String body, String topic) {
        rocketMQTemplate.sendOneWay(topic, MessageBuilder.withPayload(body).build());
    }

    /**
     * 发送带tag的消息，直接在topic后面加上":tag"
     */
    public SendResult sendTagMsg(String body, String topic) {
        return rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(body).build());
    }

}

