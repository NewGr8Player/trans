package com.xavier.trans.dao;

import com.xavier.trans.model.PetitionHighLevelReduce;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

public interface PetitionHighLevelReduceDao extends ElasticsearchCrudRepository<PetitionHighLevelReduce, String> {

}
