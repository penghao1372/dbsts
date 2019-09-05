package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cy.pj.common.vo.CheckBox;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.vo.SysRoleMenuVo;

@Mapper
public interface SysRoleDao {
	/***
	 * 分页查询角色信息
	 * @param startIndex 上一页的结束位置
	 * @param pageSize 每页要查询的总记录数
	 * @return
	 */
	List<SysRole> findPageObjects(
			@Param("name")String name,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
	/**
	 * 查询记录的总数
	 * @param name
	 * @return
	 */
	int getRowCount(@Param("name")String name);
	/***
	 * 获取id值删除对象
	 * @param id
	 * @return
	 */
	@Delete("delete from sys_roles where id=#{id}")
	int deleteObject(Integer id);
	/**1)将角色的自身信息保存到数据库
	 * 2)将角色与菜单的关系数据保存到数据库
	 * */
	int insertObject(SysRole entity);
	
	/**1）基于角色id查询角色信息
	   2）基于角色id查询菜单信息 */
	SysRoleMenuVo findObjectById(Integer id);
	
	/**	业务描述
		1)修改角色自身信息
		2)修改角色与菜单的关系数据*/
	int updateObject(SysRole entity);
	/**
	 * 查询角色id返回信息
	 */
	@Select("select id,name from sys_roles")
	List<CheckBox> findObjects();
}


















