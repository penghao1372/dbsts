package com.cy.pj.sys.controller;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.vo.SysUserDeptVo;
import com.cy.sys.service.SysUserService;

@Controller
@RequestMapping("/user/")
public class SysUserCntroller {
	@Autowired
	private SysUserService service;

	@RequestMapping("doUserListUI")
	public String doUserListUI(){
		return "sys/user_list";
	}
	@RequestMapping("doUserEditUI")
	public String doUserEditUI(){
		return "sys/user_edit";
	}
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(String username,
			Integer pageCurrent) {
		PageObject<SysUserDeptVo> page = service.findPageObjects(username, pageCurrent);
		return new JsonResult(page);
	}
	@RequestMapping("doValidById")
	@ResponseBody
	public JsonResult doValidById(Integer id,Integer valid){
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		service.validById(id,valid, user.getUsername());//"admin"用户将来是登陆用户
		return new JsonResult("update ok");
	}
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysUser entity,Integer[] roleIds){
		service.saveObject(entity,roleIds);
		return new JsonResult("save ok");
	}
	@RequestMapping("doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(Integer id) {
		Map<String, Object> map = service.findObjectById(id);
		return new JsonResult(map);
	}
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObejct(SysUser entity,
			Integer[] roleIds){
		service.updateObject(entity, roleIds);
		return new JsonResult("update ok");	
	}
	@RequestMapping("doLogin")
	@ResponseBody
	public JsonResult doLogin(String username,String password
							,boolean isRememberMe) {
		//1.通过Subject提交用户信息,交给shiro框架进行认证操作
		//1.1对用户进行封装
		UsernamePasswordToken token=new UsernamePasswordToken(username,password);
		//2.获取Subject对象
		Subject subject = SecurityUtils.getSubject();
		//2.2对用户信息进行身份认证
		if (isRememberMe) {//记住我
			token.setRememberMe(true);
		}
		subject.login(token);
		//分析:
		//1)token会传给shiro的SecurityManager
		//2)SecurityManager将token传递给认证管理器
		//3)认证管理器会将token传递给realm
		return new JsonResult("login ok");
	}

}

















