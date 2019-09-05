package com.cy.sys.service;

import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;

public interface SysLogService {
	//基于用户传入的多个id执行日志删除业务
	int deleteObjects(Integer...ids);
	
	//执行分页查询
	PageObject<SysLog> findPageObjects(String username,Integer pageCurrent);
}
