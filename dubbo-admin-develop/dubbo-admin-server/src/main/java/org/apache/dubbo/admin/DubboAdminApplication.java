/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dubbo.admin;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.core.env.Environment;

import java.io.File;

@SpringBootApplication(exclude = {
        HibernateJpaAutoConfiguration.class
})
@EnableDubbo(scanBasePackages = {"org.apache.dubbo.admin.provider"})
@MapperScan(basePackages = {"org.apache.dubbo.admin.mapper"})
public class DubboAdminApplication {

    public static void main(String[] args) {

//		SpringApplication.run(DubboAdminApplication.class, args);


        // 创建SpringApplication实例
        SpringApplication application = new SpringApplication(DubboAdminApplication.class);
        // 添加自定义的ApplicationContextInitializer
        application.addInitializers(context -> {
            // 获取Environment对象
            Environment env = context.getEnvironment();
            // 从Environment中读取"spring.application.name"属性值
            String appName = env.getProperty("spring.application.name");
            String filePath = System.getProperty("user.home") + File.separator + ".dubbo" + File.separator + appName;
            // 修改dubbo的本地缓存路径，避免缓存冲突
            System.setProperty("dubbo.meta.cache.filePath", filePath);
            System.setProperty("dubbo.mapping.cache.filePath", filePath);
        });
        //启动应用
        application.run(args);


    }
}
