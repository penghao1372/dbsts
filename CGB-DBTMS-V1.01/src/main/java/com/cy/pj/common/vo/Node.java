package com.cy.pj.common.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;
/**
 * 借助此对象封装从数据库查询到的数据
 */
@Data
@ToString
public class Node implements Serializable {
	/**
	 */
	private static final long serialVersionUID = -8035928111049328300L;
	
	private Integer id;
	private String name;
	private Integer parentId;
	
}
