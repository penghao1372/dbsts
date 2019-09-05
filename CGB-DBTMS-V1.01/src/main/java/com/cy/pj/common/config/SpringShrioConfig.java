package com.cy.pj.common.config;

import java.util.LinkedHashMap;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.server.session.DefaultWebSessionManager;
/**
 *@Configuration 注解描述的类为一个配置对象
 *此对象也会交给spring管理
 *
 */
@Configuration //bean
public class SpringShrioConfig {
	/**
	 *@Bean 描述的方法其返回值会交给spring管理 
	 *@Bean 一般应用在整合第三方资源*/
	@Bean("securityManager")
	public SecurityManager newSecurityManager(@Autowired Realm realm,
										      @Autowired CacheManager cacheManager) {
		DefaultWebSecurityManager sManager = new DefaultWebSecurityManager();
		sManager.setRealm(realm);
		sManager.setCacheManager(cacheManager);//Shiro中内置缓存应用实现
		sManager.setRememberMeManager(newCookieRememberMeManager());
		return sManager;
	}
	@Bean("shiroFilterFactory")
	public ShiroFilterFactoryBean newShiroFilterFactory(
								@Autowired	SecurityManager securityManager) {
		ShiroFilterFactoryBean sfBean = new ShiroFilterFactoryBean();
		sfBean.setSecurityManager(securityManager);
		//假如 没有认证请求需要访问认证的url
		sfBean.setLoginUrl("/doLoginUI");
		//定义map指定请求过滤规则(哪些资源允许匿名访问)
		LinkedHashMap<String, String> map=new LinkedHashMap<>();
		//静态资源允许匿名访问:"anon"
		map.put("/bower_components/**", "anon");
		map.put("/build/**", "anon");
		map.put("/dist/**", "anon");
		map.put("/plugins/**", "anon");
		map.put("/user/doLogin", "anon");
		map.put("/doLogout", "logout");//自动查找loginUrl
		//除了匿名访问的资源,其它都要认证访问("authc")
		map.put("/**", "user");//authc
		sfBean.setFilterChainDefinitionMap(map);		
		return sfBean;
	}
	
	//=========================
	//授权配置
	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor newLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
	//配置代理创建器对象(此对象负责为所有advisor创建代理)
	@DependsOn("lifecycleBeanPostProcessor")
	@Bean
	public DefaultAdvisorAutoProxyCreator newDefaultAdvisorAutoProxyCreator() {
		return new DefaultAdvisorAutoProxyCreator();
	}
	/**配置Advisor对象 此对象中的方法要检测
	 * 你要执行的方法上是否有@RequiresPermissions
	 * 有此注解,会调用ProxyCreator对象为方法创建代理对象,然后
	 * 通过代理对象实现权限控制(AOP)
	 * */
	@Bean
	public AuthorizationAttributeSourceAdvisor 
			newAuthorizationAttributeSourceAdvisor(@Autowired SecurityManager securityManager) {
		return new AuthorizationAttributeSourceAdvisor();
	}
	@Bean
	public MemoryConstrainedCacheManager newMemoryConstrainedCacheManager() {
		return new MemoryConstrainedCacheManager();
	}
	/**配置cookie对象(负责记住用户登录信息)*/
	@Bean
	public SimpleCookie newSimpleCookie(){
		SimpleCookie c = new SimpleCookie();
		c.setName("remeberMe");//设置cookie名称
		c.setMaxAge(10*60);
		return c;
	}
	public CookieRememberMeManager newCookieRememberMeManager(){
		CookieRememberMeManager cManager=new CookieRememberMeManager();
		cManager.setCookie(newSimpleCookie());
		return cManager;
	}
	public DefaultWebSessionManager newSessionManager() {
		DefaultWebSessionManager sManager = new DefaultWebSessionManager();
		return sManager;		
	}
}
















































