package com.xavier.trans.dao;

import com.xavier.trans.TransApplicationTests;
import com.xavier.trans.model.PetitionHighLevelReduce;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PetitionHighLevelReduceDaoTest extends TransApplicationTests {

	@Autowired
	private PetitionHighLevelReduceDao petitionHighLevelReduceDao;

	/**
	 * 基础查询接口测试
	 */
	@Test
	public void daoFindAllTest() {
		petitionHighLevelReduceDao.findAll().forEach(System.out::println);
	}

	/**
	 * 分页查询接口测试
	 */
	@Test
	public void daoFindPageTest() {
		int currentPage = 0;
		int pagesize = 20;
		Pageable pageable = PageRequest.of(currentPage, pagesize);
		Page<PetitionHighLevelReduce> list = petitionHighLevelReduceDao.findAll(pageable);
		list.get().forEach(System.out::println);
	}
}
