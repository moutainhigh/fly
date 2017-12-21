package com.xinfang.VO;

import com.xinfang.model.FdCase;
import com.xinfang.model.FdGuest;

//@Entity
//@SqlResultSetMapping(name = "caseQueryResult", entities = { @EntityResult(entityClass = CaseQueryVO.class, fields = {
//		@FieldResult(name = "", column = "id"), @FieldResult(name = "", column = ""),
//		@FieldResult(name = "", column = "id"), @FieldResult(name = "", column = "id"), }) })
public class CaseQueryVO {
	private FdCase fdCase;
	private FdGuest fdGuest;
	private Integer taskId;
	

}
