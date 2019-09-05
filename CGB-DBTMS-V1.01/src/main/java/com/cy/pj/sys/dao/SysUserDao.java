package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.vo.SysUserDeptVo;

@Mapper
public interface SysUserDao {
	
	List<SysUserDeptVo> findPageObjects(
			@Param("username")String username,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
	
	int getRowCount (@Param("username")String username);
	int validById(@Param("id")Integer id,
				@Param("valid")Integer valid,
				@Param("modifiedUser")String modifiedUser);
	/**
	 * 负责将用户信息写入到数据库
	 * @param entity
	 * @return
	 */
	int insertObject(SysUser entity);
	/***
	 * 
	1)根据用户id查询用户以及部门信息
	2)根据用户id查询角色id(可能是多个)
	 */
	SysUserDeptVo findObjectById(Integer id);
	
	int updateObject(SysUser entity);
	@Select(" select *  from sys_users  where username=#{username}")
	SysUser findUserByUserName(String username);
}













