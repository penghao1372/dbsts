package com.cy.pj.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.entity.SysMenu;
/***
 *菜单接口
 */
@Mapper
public interface SysMenuDao {
	/**1.查询本菜单以及上一级菜单相关信息
	 * 1)一行菜单信息映射为一个map对象(key为标记)
	 * 2)多行记录会对应多个map,然后将map存在list集合*/
	List<Map<String, Object>>findObjects();
	//2.根据菜单id统计子菜单的个数
	int getChildCount(Integer id);
	//3.根据id删除菜单
	int deleteObject(Integer id);
	/**4.获取数据库对应的菜单表中的所有菜单信息*/
	List<Node> findZtreeMenuNodes();
	/**5.在SysMenuDao中添加插入数据的方法，用于将菜单信息写入到数据库*/
	int saveObject(SysMenu entity);
	/**6.将菜单信息更新到数据库*/
	int updateObject(SysMenu entity);
	List<String> findPermissions(
			@Param("menuIds")
			Integer[] menuIds);
}



















