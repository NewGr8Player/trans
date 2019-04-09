package com.xavier.trans.service.base;

import com.xavier.trans.common.EsPage;
import com.xavier.trans.model.base.BaseEntity;
import com.xavier.trans.util.BracketsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Slf4j
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
		entityClass = (Class) params[1];
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

	/**
	 * 动态条件分页查询
	 *
	 * @param searchQuery 查询条件
	 * @return
	 */
	public Page<T> findPage(SearchQuery searchQuery) {
		return elasticsearchTemplate.queryForPage(searchQuery, entityClass);
	}

	/**
	 * 类Sql查询
	 *
	 * @param type        类型
	 * @param fields      字段
	 * @param cond        条件
	 * @param currentPage 当前页
	 * @param pageSize    分页大小
	 * @return
	 */
	public EsPage findPageBySql(String type, String fields, String cond, String sort, int currentPage, int pageSize) {
		if (log.isDebugEnabled()) {
			log.debug("Parameter:[type:{}, fields:{}, cond:{}, sort:{}, currentPage:{}, pageSize:{}]", type, fields, cond, sort, currentPage, pageSize);
		}
		NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
		nativeSearchQueryBuilder.withTypes(type);
		if (StringUtils.isNotBlank(fields)) { /* 查询字段 */
			nativeSearchQueryBuilder.withFields(fields.split(","));
		}
		//TODO 修正查询条件
		if (StringUtils.isNotBlank(cond) && BracketsUtil.bracketsCheck(cond)) {/* 查询条件 */
			nativeSearchQueryBuilder.withQuery(
					boolQuery()
							.must(
									matchQuery("title", "匹配词").operator(Operator.AND).minimumShouldMatch("75%")
							)
							.mustNot(matchPhraseQuery("content", "匹配词").slop(2))
							.should(
									boolQuery()
											.must(termQuery("userId", "匹配值"))
											.must(termQuery("userName", "匹配值"))
							)
							.filter(multiMatchQuery("查询内容", "title", "content"))
							.minimumShouldMatch(1)
			);
		}
		if (StringUtils.isNotBlank(sort) && sort.contains("::")) {/* 排序 */
			String[] sortInfo = sort.split("::");
			if (sortInfo.length > 1) {
				log.warn("传入排序字段大于1个，仅第一个排序字段生效");
			}
			nativeSearchQueryBuilder.withSort(
					SortBuilders
							.fieldSort(sortInfo[0])
							.order(Objects.equals(sortInfo[1].toLowerCase(), "desc") ? SortOrder.DESC : SortOrder.ASC)
			);
		}
		nativeSearchQueryBuilder.withPageable(PageRequest.of(currentPage, pageSize)) /* 分页 */;
		return new EsPage(findPage(nativeSearchQueryBuilder.build()));
	}
}
