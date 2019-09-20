package com.nway.spring.jdbc.sql;

import org.junit.Test;

import com.nway.spring.jdbc.performance.entity.Computer;

public class QueryBuilderTest {

	@Test
	public void getSqlTest() {
		
		Computer computer = new Computer();
		computer.setBrand("abc����");
		
		QueryBuilder builder = SqlBuilder.query(Computer.class).like("brand", computer::getBrand);
		
		System.out.println(builder.getSql());
		System.out.println(builder.getParam());
	}
}
