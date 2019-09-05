package com.cy.sys.service;

import java.util.List;

import org.apache.ibatis.annotations.Delete;

import com.cy.pj.common.vo.CheckBox;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.vo.SysRoleMenuVo;

public interface SysRoleService {
	/**
	 * 本方法中要分页查询角色信息,并查询角色总记录数据
	 * @param pageCurrent 当表要查询的当前页的页码值
	 * @return 封装当前实体数据以及分页信息
	 * 
	 */
	PageObject<SysRole> findPageObjects(String name,Integer pageCurrent);
	/***
	 * 获取id值删除对象
	 * @param id
	 * @return
	 */
	int deleteObject(Integer id);
	/**1)将角色的自身信息保存到数据库
	 * 2)将角色与菜单的关系数据保存到数据库
	 * */
	int saveObject(SysRole entity,Integer[] menuIds);
	/**1）基于角色id查询角色信息
	   2）基于角色id查询菜单信息 */
	SysRoleMenuVo findObjectById(Integer id);
	/**	业务描述
	1)修改角色自身信息
	2)修改角色与菜单的关系数据*/
	int updateObject(SysRole entity,Integer[] menuIds);
	/**
	 * 查询角色id返回信息
	 */
	List<CheckBox> findObjects();
}













