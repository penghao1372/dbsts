package com.cy.pj.sys.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * pojo:普通的java对象
 * 1)VO(值对象):封装数据(例如JsonResult,PageObject,CheckBox,Node)
 * 2)PO(持久化对象):持久化对象(属性名需要与表中字段有映射关系)
 * 可通过此对象封装页面数据,
 * 并将数据最后持久化到数据库
 * @author Administrator
 */
@Data
@ToString
public class SysUser implements Serializable{
	private static final long serialVersionUID = 4434587467646977749L;
	private Integer id;
	private String username;
	private String password;
	private String salt;//盐值
	private String email;
	private String mobile;
	private Integer valid=1;
	private Integer deptId;
	private Date createdTime;
	private Date modifiedTime;
	private String createdUser;
	private String modifiedUser;
}
