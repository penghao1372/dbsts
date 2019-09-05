package com.cy.pj.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.sys.entity.SysMenu;
import com.cy.sys.service.SysMenuService;

@Controller
@RequestMapping("/menu/")
public class SysMenuController {
	@Autowired
	private SysMenuService service;
	/**转到菜单添加页面*/
	@RequestMapping("doMenuEditUI")
	public String doMenuEditUI() {
		return "sys/menu_edit";
	}
	/**转到修改页面*/
	@RequestMapping("doMenuListUI")
	public String doMenuListUI() {
		return "sys/menu_list";
	}
	/**1.查询本菜单以及上一级菜单相关信息*/
	@RequestMapping("doFindObjects")
	@ResponseBody
	public JsonResult doFindObjects() {
		return new JsonResult(service.findObjects());
	}
	/**2.根据id删除菜单*/
	@RequestMapping("doDeleteObject")
	@ResponseBody
	public JsonResult doDeleteObject(Integer id) {
		service.deleteObject(id);
		return new JsonResult("delete Ok");
	}
	/**3.获取数据库对应的菜单表中的所有菜单信息*/
	@RequestMapping("doFindZtreeMenuNodes")
	@ResponseBody
	public JsonResult doFindZtreeMenuNodes(){
		return new JsonResult(service.findZtreeMenuNodes());
	}
	/**5.在SysMenuDao中添加插入数据的方法，用于将菜单信息写入到数据库*/
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysMenu entity) {
		service.saveObject(entity);
		return new JsonResult("save OK");
	}
	/**6.将菜单信息更新到数据库*/
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysMenu entity) {
		service.updateObject(entity);
		return new JsonResult("update ok");
	}
}






















