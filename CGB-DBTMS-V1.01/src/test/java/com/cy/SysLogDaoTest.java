package com.cy;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cy.pj.sys.dao.SysLogDao;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysLogDaoTest {
	@Autowired
	private SysLogDao dao;
	@Test
	public void testDeleteObject() {
		int rows = dao.deleteObject(16);
		System.out.println(rows);
	}
	@Test
	public void testDeleteObjects() {
		int rows = dao.deleteObjects(17,18);
		System.out.println(rows);
	}
}
