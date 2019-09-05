package com.cy.sys.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.CheckBox;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.dao.SysRoleDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.vo.SysRoleMenuVo;
import com.cy.sys.service.SysRoleService;

import io.micrometer.core.instrument.util.StringUtils;

@Service
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Override
	public PageObject<SysRole> findPageObjects(String name, Integer pageCurrent) {
		//1.验证参数的合法性
		//1.1验证pageCurrent的合法性
		if (pageCurrent==null||pageCurrent<1) {
			throw new IllegalArgumentException("当前页码不存在");
		}
		//2.基于条件查询总记录数
		//2.1执行查询
		int rowCount = sysRoleDao.getRowCount(name);
		//2.2验证查询结果
		if (rowCount==0) {
			throw new ServiceException("当前记录不存在");
		}
		//3.基于条件查询当前页的记录(pageSize定义为2)
		//3.1)定义pageSize为2
		int pageSize=2;
		//3.2)计算startIndex的值
		int startIndex=(pageCurrent-1)*pageSize;
		//3.3)执行当前数据的查询操作
		List<SysRole> records= sysRoleDao.findPageObjects(name, startIndex, pageSize);
		//4.对分页信息以及当前页信息进行封装
		//4.1)构建pageObject对象
		PageObject<SysRole> pageObject = new PageObject<>();
		//4.2)对查询记录进行封装
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		pageObject.setRowCount(rowCount);
		pageObject.setRecords(records);
		pageObject.setPageCount((rowCount-1)/pageSize+1);
		//5.返回封装结果
		return pageObject;
	}
	/***
	 * 获取id值删除对象
	 */
	@Override
	public int deleteObject(Integer id) {
		//1.验证参数的合法性
		if(id==null||id<1)throw new ServiceException("id的值不正确");
		//2.执行dao删除操作
		int rows = sysRoleDao.deleteObject(id);
		if (rows==0) {
			throw new ServiceException("数据可能已经被删除了");
		}
		sysRoleMenuDao.deleteObjectsByRoleId(id);
		sysUserRoleDao.deleteObjectsByRoleId(id);
		//3.返回结果
		return rows;
	}
	/**1)通过参数变量接收控制层数据
		2)对参数数据进行合法性验证.
		3)保存角色自身信息数据到数据库
		4)保存角色与菜单的关系数据到数据库
		5)返回业务实现结果
	 * 
	 */
	@Override
	public int saveObject(SysRole entity, Integer[] menuIds) {
		//1.合法性验证
		if (entity==null) {
			throw new ServiceException("保存数据不能为空");
		}
		if (StringUtils.isEmpty(entity.getName())) {
			throw new ServiceException("角色名不能为空");
		}
		if (menuIds==null||menuIds.length==0) {
			throw new ServiceException("必须为角色赋予权限");
		}
		//2.保存数据
		int rows = sysRoleDao.insertObject(entity);
		sysRoleMenuDao.insertObjects(entity.getId(), menuIds);
		//3.返回结果
		return rows;
	}
	/**1）基于角色id查询角色信息
	   2）基于角色id查询菜单信息 */
	@Override
	public SysRoleMenuVo findObjectById(Integer id) {
		//1.验证合法性
		if (id==null||id<=0) {
			throw new ServiceException("id的值不合格");
		}
		SysRoleMenuVo result = sysRoleDao.findObjectById(id);
		//2.执行查询
		if (result ==null) {
			throw new ServiceException("此记录以及不存在");
		}
		//3.验证结果并返回
		return result;
	}
	/**	业务描述
	1)修改角色自身信息
	2)修改角色与菜单的关系数据*/
	@Override
	public int updateObject(SysRole entity,Integer[] menuIds) {
		//1.验证参数的合法性
		if (entity==null) {
			throw new ServiceException("更新对象不能为空值");
		}
		if (entity.getId()==null) {
			throw new ServiceException("id值不能为空");
		}
		if (StringUtils.isEmpty(entity.getName())) {
			throw new ServiceException("角色名不能为空");
		}
		if (menuIds==null||menuIds.length==0) {
			throw new ServiceException("必须为角色赋予一个权限");
		}
		//2.执行修改
		int rows = sysRoleDao.updateObject(entity);
		if (rows==0) {
			throw new ServiceException("对象修改失败");
		}
		sysRoleMenuDao.deleteObjectsByRoleId(entity.getId());
		sysRoleMenuDao.insertObjects(entity.getId(), menuIds);
		//3.验证返回结果
		return rows;
	}
	/**
	 * 查询角色id返回信息
	 */
	@Override
	public List<CheckBox> findObjects() {
		//1.返回查询结果
		List<CheckBox> findObjects = sysRoleDao.findObjects();
		return findObjects;
	}

}












