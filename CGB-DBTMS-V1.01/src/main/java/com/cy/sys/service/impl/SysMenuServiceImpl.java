package com.cy.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.entity.SysMenu;
import com.cy.sys.service.SysMenuService;

import io.micrometer.core.instrument.util.StringUtils;
@Service
public class SysMenuServiceImpl implements SysMenuService {
	@Autowired
	private SysMenuDao sysMenuDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	
	/**1.查询本菜单以及上一级菜单相关信息*/
	@Override
	public List<Map<String, Object>> findObjects() {
		//1.获取菜单信息
		List<Map<String, Object>> list = sysMenuDao.findObjects();
		//2.对菜单信息进行校验
		if (list==null||list.size()==0) {
			throw new ServiceException("没有对应的菜单信息");
		}
		return list;
	}
	
	/**2.根据id删除菜单*/
	@Override
	public int deleteObject(Integer id) {
		//1.验证数据的合法性
		if(id==null||id<=0) {
			throw new IllegalArgumentException("请先选择");
		}
		//2.基于id进行子元素查询
		int count = sysMenuDao.getChildCount(id);
		if(count>0) {
			throw new ServiceException("请先删除");
		}
		//3.删除菜单元素
		int rows = sysMenuDao.deleteObject(id);
		if (rows==0) {
			throw new ServiceException("此菜单可能已经不存在");
		}
		//4.删除角色,菜单关系数据
		sysRoleMenuDao.deleteObjectsByMenuId(id);
		//5.返回结果
		return rows;
	}
	/**4.获取数据库对应的菜单表中的所有菜单信息*/
	@Override
	public List<Node> findZtreeMenuNodes() {
		List<Node> list = sysMenuDao.findZtreeMenuNodes();
		return list;
	}
	/**5.在SysMenuDao中添加插入数据的方法，用于将菜单信息写入到数据库*/
	@Override
	public int saveObject(SysMenu entity) {
		//1.参数合法验证
		if (entity==null) {
			throw new ServiceException("保存的对象不存在");
		}
		if (StringUtils.isEmpty(entity.getName())) {
			throw new ServiceException("菜单名不能为空");
		}
		int rows;
		//2.保存数据
		try {
			 rows = sysMenuDao.saveObject(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		//3.返回数据
		return rows;
	}
	/**6.将菜单信息更新到数据库*/
	@Override
	public int updateObject(SysMenu entity) {
		//1.验证参数的合法性
		if(entity==null) {
			throw new ServiceException("保存对象不能为空");
		}
		if (StringUtils.isEmpty(entity.getName())) {
			throw new ServiceException("参数列表不能为空");
		}
		//2.修改数据
		int rows = sysMenuDao.updateObject(entity);
		if (rows==0) {
			throw new ServiceException("记录可能已经不存在");
		}
		//3.返回结果
		return rows;
	}

}















