package com.xavier.trans.service.base;

import com.xavier.trans.model.base.BaseEntity;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
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
	 * ElasticsearchTemplate，相当于JdbcTemplate
	 */
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	/**
	 * 传入Bean的类类型
	 */
	private Class<T> entityClass;

	/**
	 * 构造函数初始化的时候为传入Bean的类类型赋值
	 */
	public BaseService() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClass = (Class) params[0];
	}

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

	/**
	 * 动态条件查询
	 *
	 * @param searchQuery 查询条件
	 * @return
	 */
	public List<T> findList(SearchQuery searchQuery) {
		return elasticsearchTemplate.queryForList(searchQuery, entityClass);
	}
}
