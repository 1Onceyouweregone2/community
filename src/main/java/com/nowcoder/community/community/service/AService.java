package com.nowcoder.community.community.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
//@Scope("prototype")
public class AService {

    public AService() {
        System.out.println("===实例化===");
    }

    @PostConstruct
    public void init() {
        System.out.println("===初始化===");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("===销毁===");
    }

}
