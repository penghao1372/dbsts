package com.cy.pj.sys.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**借助此对象封装用户的行为信息*/
/**
 * 
 * pojo:普通的java对象
 * 1)po(持久化对象)
 * 2)vo:(值对象-Value Object)
 * 
 */
@Data //默认生成set get
@ToString //自动生存tostring
public class SysLog implements Serializable{
	/**用户名*/
	private static final long serialVersionUID = 8924387722922123121L;
	private Integer id;
	/**用户名*/
	private String username;
	/**用户操作*/
	private String operation;
	/**请求方法*/
	private String method;
	/**请求参数*/
	private String params;
	/**执行时长(毫秒)*/
	private Long time;
	/**IP地址*/
	private String ip;
	/**创建时间*/
	private Date createdTime;


}
