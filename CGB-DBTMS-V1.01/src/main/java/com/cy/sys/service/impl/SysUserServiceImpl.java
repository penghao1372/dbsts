package com.cy.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.vo.SysUserDeptVo;
import com.cy.sys.service.SysUserService;

import io.micrometer.core.instrument.util.StringUtils;
@Service
public class SysUserServiceImpl implements SysUserService{
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@RequiredLog("query log")
	@Override
	public PageObject<SysUserDeptVo> findPageObjects(String username, Integer pageCurrent) {
		//1.验证参数的合法性
 		if(pageCurrent==null||pageCurrent<=0)
			throw new IllegalArgumentException("参数不合法");
		//1.1)获取总记录数
		int rowCount = sysUserDao.getRowCount(username);
		//1.2)验证总记录数
		if (rowCount==0) {
			throw new ServiceException("记录不存在");
		}
		//2.调用dao层的方法,获取当前页和总记录数
		//计算startIndex的值
		int pageSize=3;
		int startIndex=(pageCurrent-1)*pageSize;
		//2.1获取当前页的数据
		List<SysUserDeptVo> records = sysUserDao.findPageObjects(username, startIndex, pageSize);
		//3.封装数据
		PageObject<SysUserDeptVo> pageObject = new PageObject<>();
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setRowCount(rowCount);
		pageObject.setPageSize(pageSize);
		pageObject.setRecords(records);
		pageObject.setPageCount((rowCount-1)/pageSize+1);
		//4.返回结果
		return pageObject;
	}
	/**
	 * 2)对数据进行合法性验证
		3)调用dao层方法执行更新操作(禁用或启用)
		
		*@RequiresPermissions 
		*注解此方法需要进行权限控制(授权以后才可以访问)
		*/
	@RequiredLog("禁用启用")
	@RequiresPermissions("sys:user:valid")
	@Override
	public int validById(Integer id, Integer valid, String modifiedUser) {
		//1.合法性验证
		if(id==null||id<=0)
			throw new ServiceException("参数不合法,id="+id);
		if(valid!=1&&valid!=0)
			throw new ServiceException("参数不合法,valie="+valid);
		if(StringUtils.isEmpty(modifiedUser))
			throw new ServiceException("修改用户不能为空");
		//2.执行禁用或启用操作
		int rows=0;
		try{
			rows=sysUserDao.validById(id, valid, modifiedUser);
		}catch(Throwable e){
			e.printStackTrace();
			//报警,给维护人员发短信
			throw new ServiceException("底层正在维护");
		}
		//3.判定结果,并返回
		if(rows==0)
			throw new ServiceException("此记录可能已经不存在");
		return rows;
	}
	@Transactional
	@Override
	public int saveObject(SysUser entity, Integer[] roleIds) {
			//1.验证数据合法性
			if(entity==null)
				throw new ServiceException("保存对象不能为空");
			if(StringUtils.isEmpty(entity.getUsername()))
				throw new ServiceException("用户名不能为空");
			if(StringUtils.isEmpty(entity.getPassword()))
				throw new ServiceException("密码不能为空");
			if(roleIds==null || roleIds.length==0)
				throw new ServiceException("至少要为用户分配角色");

			//2.将数据写入数据库
			String salt=UUID.randomUUID().toString();
			entity.setSalt(salt);
			//加密(先了解,讲shiro时再说)
			SimpleHash sHash=
					new SimpleHash("MD5",entity.getPassword(), salt);
			entity.setPassword(sHash.toHex());

			int rows=sysUserDao.insertObject(entity);
			sysUserRoleDao.insertObjects(entity.getId(),roleIds);//"1,2,3,4";
			//3.返回结果
			return rows;
		}
	/***
	 * 
	 * 1)获取控制层数据(userId),并对其进行合法性验证
	   2)调用dao方法根据用户id查询用户信息,部门信息以及对应的角色信息
	   3)对查询到的结果数据进行封装,并返回.
	 */
	@Override
	public Map<String, Object> findObjectById(Integer userId) {
		//1.参数的合法验证
		if (userId==null||userId<=0) {
			throw new IllegalArgumentException(
					"参数数据不合法,userId="+userId);
		}
		//2.业务查询
		SysUserDeptVo user = sysUserDao.findObjectById(userId);
		if (user==null) {
			throw new ServiceException("此用户已经不存在");
		}
		List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(userId);
		Map<String, Object>map= new HashMap<>();
		//3.数据封装
		map.put("user", user);
		map.put("roleIds",roleIds);
		return map;
	}
	/***
	 * 业务描述
	1)获取控制层数据,并进行合法验证.
	2)更新用户自身信息
	3)删除用户角色关系数据
	4)重新添加新的用户角色关系数据
	 */
	@Override
	public int updateObject(SysUser entity, Integer[] roleIds) {
		//1.验证参数的有效性
		if (entity==null) {
			throw new IllegalArgumentException("保存的对象不能为空");
		}
		if (StringUtils.isEmpty(entity.getUsername())) {
			throw new ServiceException("保存的用户名不能为空");
		}
		if (roleIds==null||roleIds.length==0) {
			throw new IllegalArgumentException("必须为其选择角色");
		}
		//2.更新用户的自身信息
		int rows = sysUserDao.updateObject(entity);
		//3.保存用户与角色的关系数据
		sysUserRoleDao.deleteObjectsByRoleId(entity.getId());
		sysUserRoleDao.insertObjects(entity.getId(), roleIds);
		//4.返回结果
		return rows;
	}
}





















