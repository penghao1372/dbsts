package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cy.pj.sys.entity.SysLog;

@Mapper //系统自动扫描后实现类
public interface SysLogDao {
		//简单sql使用注解
			@Delete("delete from sys_logs where id=#{id}")
			int deleteObject(Integer id);
			
			//基于传入的id值执行动态删除
			//复杂写在mapper里
			int deleteObjects(@Param("ids")Integer...ids);

			/**按条件查询日志总记录数
			 * @param username查询条件
			 * */
			
			int getRowCount(@Param("username")String username);
			/**起始位置
			 * @param 页面码
			 * */
			List<SysLog>findPageObjects(@Param("username")String username,
					@Param("startIndex")Integer startIndex,
					@Param("pageSize")Integer pageSize);

			int insertObject(SysLog log);


}
