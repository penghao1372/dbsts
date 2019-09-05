package com.cy.sys.service;

import java.util.Map;

import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.vo.SysUserDeptVo;

public interface SysUserService {
	/**执行分页查询*/
	PageObject<SysUserDeptVo> findPageObjects(
							String username,
							Integer pageCurrent);
	/**进行权限控制*/
	int validById(Integer id,
			Integer valid,
			String modifiedUser);
	
	int saveObject (SysUser entity,Integer[] roleIds);
	
	Map<String, Object> findObjectById(Integer userId);
	/***
	 * 业务描述
	1)获取控制层数据,并进行合法验证.
	2)更新用户自身信息
	3)删除用户角色关系数据
	4)重新添加新的用户角色关系数据
	 */
	int updateObject(SysUser entity,Integer[] roleIds); 
}
