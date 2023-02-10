package com.nowcoder.community.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import javax.annotation.PostConstruct;


@MapperScan({"com.nowcoder.community.community.dao"})
@SpringBootApplication
public class CommunityApplication {

	@PostConstruct
	public void init() {
		//解决netty启动冲突的问题
		//Netty4Utils类
		System.setProperty("es.set.netty.runtime.available.processors","false");
	}

	public static void main(String[] args) {
		SpringApplication.run(CommunityApplication.class, args);
	}

}
