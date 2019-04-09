package com.xavier.trans.service;

import com.alibaba.fastjson.JSONObject;
import com.github.jsonzou.jmockdata.JMockData;
import com.xavier.trans.TransApplicationTests;
import com.xavier.trans.model.PetitionHighLevelReduce;
import com.xavier.trans.util.BracketsUtil;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.elasticsearch.index.query.QueryBuilders.*;

public class PetitionHighLevelReduceServiceTest extends TransApplicationTests {

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	@Autowired
	private PetitionHighLevelReduceService petitionHighLevelReduceService;

	/**
	 * 根据Id查找测试
	 */
	@Test
	public void serviceFindByIdTest() {
		Optional<PetitionHighLevelReduce> petitionHighLevelReduceOptional = petitionHighLevelReduceService.get("7dd62acaea49433ba42d925ad0d2d907");
		if (petitionHighLevelReduceOptional.isPresent()) {
			System.out.println(petitionHighLevelReduceOptional.get());
		} else {
			System.out.println("GG");
		}
	}

	/**
	 * 根据反射保存测试
	 *
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 */
	@Test
	public void serviceSaveTestByReflect() throws ClassNotFoundException, IllegalAccessException {
		PetitionHighLevelReduce petitionHighLevelReduce = new PetitionHighLevelReduce();
		Class<?> clazz = Class.forName(PetitionHighLevelReduce.class.getName());
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			field.set(petitionHighLevelReduce, JMockData.mock(field.getType()));
		}
		petitionHighLevelReduce.setId(UUID.randomUUID().toString());
		petitionHighLevelReduce.setPetitionCaseNo("LF201900001");
		System.out.println(petitionHighLevelReduceService.save(petitionHighLevelReduce));
	}

	/**
	 * 条件Service测试
	 */
	@Test
	public void serviceCondSearchTest() {
		// TODO https://blog.csdn.net/tianyaleixiaowu/article/details/77965257 参考这个网址
		Pageable pageable = PageRequest.of(0, 20);
		SearchQuery query = new NativeSearchQueryBuilder()
				.withTypes("pt_petition_high_level_reduce")
				/* 此处注意查看withQuery的实现，虽然可以拼接多个但是生效的只有最后一个 */
				.withQuery(matchQuery("title", "匹配词").operator(Operator.AND).minimumShouldMatch("75%")) /* 完全包含并且定义匹配度 */
				//.withQuery(matchPhraseQuery("content", "匹配词").slop(2))/* slop：分词后允许间隔的短语数量 */
				//.withQuery(termQuery("userId", "匹配值"))/* 最严格匹配，不进行分词 */
				//.withQuery(multiMatchQuery("查询内容", "title", "content"))/* 一个匹配项匹配多个字段 */
				.withPageable(pageable)
				.build();
		petitionHighLevelReduceService.findList(query).forEach(System.out::println);
	}

	/**
	 * 组合条件测试
	 * must     代表返回的文档必须满足must子句的条件，会参与计算分值；
	 * filter   代表返回的文档必须满足filter子句的条件，但不会参与计算分值；
	 * should   代表返回的文档可能满足should子句的条件，也可能不满足，有多个should时满足任何一个就可以，通过minimum_should_match设置至少满足几个;
	 * mustNot  代表必须不满足子句的条件;
	 * <p>
	 * 特殊说明
	 * 从代码上就能看出来，query和Filter都是QueryBuilder，也就是说在使用时，你把Filter的条件放到withQuery里也行，反过来也行。那么它们两个区别在哪？
	 * 查询在Query查询上下文和Filter过滤器上下文中，执行的操作是不一样的：
	 * 1、查询：是在使用query进行查询时的执行环境，比如使用search的时候。
	 * 在查询上下文中，查询会回答这个问题——“这个文档是否匹配这个查询，它的相关度高么？”
	 * ES中索引的数据都会存储一个_score分值，分值越高就代表越匹配。即使lucene使用倒排索引，对于某个搜索的分值计算还是需要一定的时间消耗。
	 * 2、过滤器：在使用filter参数时候的执行环境，比如在bool查询中使用Must_not或者filter
	 * 在过滤器上下文中，查询会回答这个问题——“这个文档是否匹配？”
	 * 它不会去计算任何分值，也不会关心返回的排序问题，因此效率会高一点。
	 * 另外，经常使用过滤器，ES会自动的缓存过滤器的内容，这对于查询来说，会提高很多性能。
	 * <p>
	 * 总而言之
	 * 1 查询上下文：查询操作不仅仅会进行查询，还会计算分值，用于确定相关度；
	 * 2 过滤器上下文：查询操作仅判断是否满足查询条件，不会计算得分，查询的结果可以被缓存。
	 * 所以，根据实际的需求是否需要获取得分，考虑性能因素，选择不同的查询子句。
	 */
	@Test
	public void serviceMultiCondSearchTest() {
		String type = "pt_petition_high_level_reduce", fields = "", cond = "", sort = "", group = "";
		int currentPage = 0, pageSize = 20;
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
		//if (StringUtils.isNotBlank(group)) {
		nativeSearchQueryBuilder.addAggregation(
				AggregationBuilders
						.terms("petitionCaseNoNum").field("petitionCaseNo.keyword").order(BucketOrder.key(true))
		);
		//}
		if (StringUtils.isNotBlank(sort) && sort.contains("::")) {/* 排序 */
			String[] sortInfo = sort.split("::");
			nativeSearchQueryBuilder.withSort(
					SortBuilders
							.fieldSort(sortInfo[0])
							.order(Objects.equals(sortInfo[1].toLowerCase(), "desc") ? SortOrder.DESC : SortOrder.ASC)
			);
		}
		nativeSearchQueryBuilder.withPageable(PageRequest.of(currentPage, pageSize)) /* 分页 */;
		//petitionHighLevelReduceService.findPage(nativeSearchQueryBuilder.build()).getContent().forEach(System.out::println);
		Aggregations aggregations = elasticsearchTemplate.query(nativeSearchQueryBuilder.build(), e -> e.getAggregations());
		StringTerms teamAgg = (StringTerms) aggregations.asMap().get("petitionCaseNoNum");
		List<StringTerms.Bucket> bucketList = teamAgg.getBuckets();
		System.out.println(bucketList.get(0).getKeyAsString());
		System.out.println(bucketList.get(0).getDocCount());
	}

	@Test
	public void findListBySqlTest() {
		System.out.println(petitionHighLevelReduceService.findPageBySql("", "", "", "", 0, 20));
	}
}
