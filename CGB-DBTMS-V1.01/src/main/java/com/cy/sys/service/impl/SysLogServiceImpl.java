package com.cy.sys.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.util.PageUtil;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.entity.SysLog;
import com.cy.sys.service.SysLogService;

import lombok.extern.slf4j.Slf4j;
@Service //----map.put("SysLogServiceImpl",object instance)
@Slf4j
public class SysLogServiceImpl implements SysLogService{
//	private static final Logger log=LoggerFactory.getLogger(SysLogServiceImpl.class);
	@Autowired //spring 按类属性查找bean,然后进行DI
//	@Qualifier("sd")
	private SysLogDao sysLogDao;
	@Override
	public int deleteObjects(Integer... ids) {
	int rows=sysLogDao.deleteObjects(ids);
	if (rows>0) {
		log.info("rows"+rows);
	}
		return rows;
	}
	@Override
	public PageObject<SysLog> findPageObjects(String username, Integer pageCurrent) {
		 //1.验证参数合法性
		  //1.1验证pageCurrent的合法性，
		  //不合法抛出IllegalArgumentException异常
		  if(pageCurrent==null||pageCurrent<1)
		  throw new IllegalArgumentException("当前页码不正确");
		 
		  //2.查询总记录数并进行校验
		  int rowCount=sysLogDao.getRowCount(username);
		  //2.2) 验证查询结果，假如结果为0不再执行如下操作
		  if(rowCount==0)
          throw new ServiceException("系统没有查到对应记录");
		  //3.基于条件查询当前页记录(pageSize定义为2)
		  //3.1)定义pageSize
		  int pageSize=PageUtil.getPageSize();  //每页最多显示三条
		  //3.2)计算startIndex
		  int startIndex=PageUtil.getStartIndex(pageCurrent);//当前页起始位置
		  //3.3)执行当前数据的查询操作
		  List<SysLog> records=
				  sysLogDao.findPageObjects(username, startIndex, pageSize);
		  //4.对分页信息以及当前页记录进行封装
		  PageObject<SysLog> po = PageUtil.newPageObject(pageCurrent, rowCount, pageSize, records);
		  //4.1)构建PageObject对象
		  return po;

	}
	


}
