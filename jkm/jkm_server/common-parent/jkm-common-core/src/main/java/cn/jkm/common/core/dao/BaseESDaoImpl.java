package cn.jkm.common.core.dao;

import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate; 

public abstract class BaseESDaoImpl<T, K extends Serializable>  implements BaseDao<T, K> {

	public BaseESDaoImpl() {

	}

	protected ElasticsearchTemplate elasticsearchTemplate; 
	
	@Autowired
	public void setMongoTemplate(ElasticsearchTemplate elasticsearchTemplate) {
		this.elasticsearchTemplate=elasticsearchTemplate;
	}
	
}
