package com.nowcoder.community.community;

import com.nowcoder.community.community.config.AConfig;
import com.nowcoder.community.community.dao.ADao;
import com.nowcoder.community.community.dao.ADapImpl;
import com.nowcoder.community.community.service.AService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
class CommunityApplicationTests implements ApplicationContextAware {

//	@Test
//	void contextLoads() {
//	}

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Test
	public void testApplicationContext() {
		System.out.println(applicationContext);

		ADao bean = applicationContext.getBean("a1",ADao.class);

		System.out.println(bean.select());
	}

	@Test
	public void testBeanManagement(){
		AService bean = applicationContext.getBean(AService.class);
		System.out.println(bean);

//		AService bean2 = applicationContext.getBean(AService.class);
//		System.out.println(bean);
	}

	@Test
	public void testConfig(){
		SimpleDateFormat bean = applicationContext.getBean(SimpleDateFormat.class);
		System.out.println(bean.format(new Date()));
	}

	@Autowired
	private ADao aDao;

	@Test
	public void testDi(){
		System.out.println(aDao);
	}
}
