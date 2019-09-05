package com.cy.pj.common.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.entity.SysUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
/***
 *
 * @Aspect 描述的了类为一个切面类对象
 * 1)PointCut:切入点(切入扩展功能的点)
 * 2)Advice:通知(扩展功能)
 * 
 *
 */
@Order(2)
@Aspect
@Service
@Slf4j
public class SysLogAspect {
	//@PointCut注解用于定义切入点
	//bean表达式为切入点表达式,bean表达式内部指定的bean对象中
	//所有方法为切入点(进行功能扩展的点)
	@Pointcut("bean(sysUserServiceImpl)")
	public void logPointCut() {}
	/***
	 * @Around 描述的方法为环绕通知(用于功能增强)
	 * 环绕通知(目标方法执行之前和之后都可以执行)
	 * @param jp 连接点
	 * @return 目标方法的执行结果
	 * @throws Throwable
	 */
	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint jp)throws Throwable {
		try {
			long t1=System.currentTimeMillis();
			log.info("start"+t1);
			Object result= jp.proceed();
			long t2=System.currentTimeMillis();
			log.info("after"+t2);
			saveObject(jp,t2-t1);
			return result;
		} catch (Throwable e) {
			log.error(e.getMessage());
			throw e;
		}
	}
	@Autowired
	private SysLogDao sysLogDao;
	private void saveObject(ProceedingJoinPoint jp, long time) throws Exception {
		//1.获取用户行为信息
		//1.1获取目标对象类型
		Class<?> targetcls = jp.getTarget().getClass();
		//1.2获取方法名
		//1.2.1获取方法签名对象
		MethodSignature ms = (MethodSignature) jp.getSignature();
		//1.2.2获取目标方法名
		String methodName=targetcls.getName()+"."+ms.getName();
		String params=getRequestParams(jp);
		//1.2.3获取操作名
		String operation =getOperation(targetcls,ms);
		//2.封装用户行为信息
		SysLog log= new SysLog();
		log.setIp("192.168.1.1");
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		log.setUsername(user.getUsername());
		log.setMethod(methodName);//类全名+方法
		log.setParams(jp.getArgs().toString());
		log.setOperation(operation);
		log.setParams(params);
		log.setTime(time);
		log.setCreatedTime(new Date());
		//3.将信息保存到数据库
		sysLogDao.insertObject(log);
	}
	private String getOperation(Class<?> targetCls, MethodSignature ms) throws Exception {
		String operation="operation";
		 //Method method=ms.getMethod();//接口方法
		 Method  targetMethod=targetCls.getDeclaredMethod(
				 ms.getName(),
				 ms.getParameterTypes());
		 RequiredLog rLog=
		 targetMethod.getDeclaredAnnotation(RequiredLog.class);
		 if(rLog!=null) {
			 operation=rLog.value();
		 }
		return operation;
	}
	private String getRequestParams(ProceedingJoinPoint jp) throws JsonProcessingException {
		Object[] args=jp.getArgs();
		 String params="[]";
		 if(args.length>0) {
		 params=new ObjectMapper().writeValueAsString(args);
		 }
		return params;
	}
}
































