package cn.jkm.eb.facade.entities;

public class UserFlow {
    /**
     *
     * 流水id
     */
    private String flowId;

    /**
     *
     * 流水创建时间.
     */
    private String createTime;

    /**
     *
     * 流水金额
     */
    private String flowAmount;

    /**
     *
     * 流水类型.
     */
    private String flowType;

    /**
     *
     * 流水描述.
     */
    private String flowDesc;

    /**
     *
     * 用户id
     */
    private String userId;

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getFlowAmount() {
		return flowAmount;
	}

	public void setFlowAmount(String flowAmount) {
		this.flowAmount = flowAmount;
	}

	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFlowDesc() {
		return flowDesc;
	}

	public void setFlowDesc(String flowDesc) {
		this.flowDesc = flowDesc;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}