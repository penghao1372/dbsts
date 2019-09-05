package com.cy.sys.service;

import java.util.List;
import java.util.Map;

import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.entity.SysMenu;

public interface SysMenuService {
	//1.查询本菜单以及上一级菜单相关信息
	List<Map<String,Object>> findObjects();
	//2.根据id删除菜单
	int deleteObject(Integer id);
	/**4.获取数据库对应的菜单表中的所有菜单信息*/
	List<Node> findZtreeMenuNodes();
	/**5.在SysMenuDao中添加插入数据的方法，用于将菜单信息写入到数据库*/
	int saveObject(SysMenu entity);
	/**6.将菜单信息更新到数据库*/
	int updateObject(SysMenu entity);
	
}
