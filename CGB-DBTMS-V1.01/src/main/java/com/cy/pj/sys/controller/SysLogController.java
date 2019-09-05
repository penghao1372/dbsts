package com.cy.pj.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;
import com.cy.sys.service.SysLogService;

@Controller
@RequestMapping("/log/")
public class SysLogController {
	@Autowired
	private SysLogService service;
	@RequestMapping("doDelectObjects")
	@ResponseBody
	public JsonResult deleteObjects(Integer...ids) {
		int rows = service.deleteObjects(ids);
		return new JsonResult("delete ok rows="+rows);
	}
	 @RequestMapping("doLogListUI")
	  public String doLogListUI(){
		  return "sys/log_list";
	  }
	 @RequestMapping("doFindPageObjects")
	 @ResponseBody
	 public JsonResult doFindPageObjects(String username,Integer pageCurrent){
	  PageObject<SysLog> pageObject=service.findPageObjects(username,pageCurrent);
	  System.out.println("username="+username);
	  return new JsonResult(pageObject);
	 }


}
