package com.cy.pj.common.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;
/***
 * 
 *	业务描述
 *	借助此对象封装角色id,角色名称.
 *	业务实现(定义类并实现序列化接口,添加版本id,提供set/get)
 *
 */
@Data
@ToString
public class CheckBox implements Serializable {
	private static final long serialVersionUID = 4726362356109662723L;
	private Integer id;
	private String name;
}
