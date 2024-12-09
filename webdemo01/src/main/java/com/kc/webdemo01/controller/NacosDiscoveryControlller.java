package com.kc.webdemo01.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author KCWang
 * @version 1.0
 * @date 2024/12/9 下午1:35
 */
public class NacosDiscoveryControlller {


    @NacosInjected
    private NamingService namingService;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 根据服务名称获取服务的所有（机器）实例信息
     * @param serviceName 服务名称
     * @return
     * @throws NacosException
     */
    @GetMapping("getInstances")
    public List<Instance> getInstances(String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }





    /**
     * 通过服务名获取指定ID的用户信息
     * @param id
     * @return
     * @throws NacosException
     */
    @GetMapping("queryUserInfo")
    public String queryUserInfo(String id) throws NacosException {
        String path = "/api/user?id="+id;
        //另一个应用的名称，并且已注册到nacos服务列表
        String serviceName = "portal";
        String url = "";
        List<Instance> instances = getInstances(serviceName);
        if(instances == null || instances.size() == 0){
            //可以抛出未找到服务信息的异常
            return null;
        }
        //获取第一个服务实例，当然也可以通过某种算法获取别的实例信息，比如随机算法，类似于在此处做客户端负载均衡
        Instance instance = instances.get(0);
        url = "http://"+instance.getIp()+":"+instance.getPort()+path;
        return restTemplate.getForObject(url,String.class);
    }


}
