package com.cy.pj.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
/***
 *
 *在此类中实现一些过滤器配置
 *
 */
@Configuration
public class WebFilterConfig {//取代web.xml中的filter配置
	
	/**注册filter对象*/
	@SuppressWarnings("rawtypes")
	@Bean("filterRegistrationBean")
	public FilterRegistrationBean newFilterRegistrationBean() {
		//1.构建过滤器的注册器对象
		FilterRegistrationBean fBean = new FilterRegistrationBean();
		//2.注册过滤器对象
		DelegatingFilterProxy filter = new DelegatingFilterProxy("shiroFilterFactory");
		fBean.setFilter(filter);
		//3.进行过滤器配置
		//配置过滤器的生命周期管理
		fBean.setEnabled(true);
		fBean.addUrlPatterns("/*");
		return fBean;
		
	}
	
}
