package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
/**sys_role_menus关系表*/
@Mapper
public interface SysRoleMenuDao {
	/**1.基于菜单id删除记录*/
	int deleteObjectsByMenuId(Integer menuId);
	/**2.基于id删除对象*/
	@Delete("delete from sys_role_menus where role_id=#{roleId}")
	int deleteObjectsByRoleId(Integer roleId);
	/**3.添加角色和角色与菜单关系数据*/
	int insertObjects(@Param("roleId")Integer roleId,@Param("menuIds")Integer[] menuIds);
	
	List<Integer> findMenuIdsByRoleId (Integer id);
	List<Integer> findMenuIdsByRoleIds(@Param("roleIds")Integer[] roleIds);
}

