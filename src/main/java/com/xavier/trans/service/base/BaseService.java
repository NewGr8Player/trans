package com.xavier.trans.service.base;

import com.xavier.trans.model.base.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public abstract class BaseService<D extends ElasticsearchCrudRepository, T extends BaseEntity> {

	/**
	 * 持久层对象
	 */
	@Resource
	protected D dao;

	/**
	 * 获取单条数据
	 *
	 * @param id
	 * @return
	 */
	public Optional<T> get(String id) {
		return dao.findById(id);
	}

	/**
	 * 查询分页列表数据
	 *
	 * @param entity
	 * @return
	 */
	public Page<T> findList(Pageable entity) {
		return dao.findAll(entity);
	}

	/**
	 * 保存数据（插入或更新）
	 *
	 * @param entity
	 */
	@Transactional
	public T save(T entity) {
		return (T) dao.save(entity);
	}

	/**
	 * 删除数据
	 *
	 * @param entity
	 */
	@Transactional
	public void delete(T entity) {
		dao.delete(entity);
	}
}
