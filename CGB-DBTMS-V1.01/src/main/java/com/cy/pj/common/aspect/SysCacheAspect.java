package com.cy.pj.common.aspect;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
@Aspect
@Slf4j
@Component
public class SysCacheAspect {
	//private static Logger log=LoggerFactory.getLogger(SysCacheAspect.class);
    //@Pointcut("execution(* com.cy.pj.sys.service..*.find*(..))")
	@Pointcut("@annotation(com.cy.pj.common.annotation.RequiredLog)")
	public void doCachePointCut() {}
    
	@Around("doCachePointCut()")
    public Object around(ProceedingJoinPoint jp)
    		throws Throwable{
        log.info("从cache取数据");
      Object tagertObject = getCache(jp);
        Object result=jp.proceed();
        log.info("将查询结果放cache");
    	return result;
    }

	private Object getCache(ProceedingJoinPoint jp) {
		Map<String,	Object> map= new HashMap<>(50);
		
		
		return null;
	}

}
