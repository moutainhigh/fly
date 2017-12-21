package com.xinfang.VO;

/**
 * Created by sunbjx on 2017/3/31.
 */
public class CaseFlowVO {

    // 节点处理人
    private String nodeUser;
    // 节点处理人头像
    private String nodeUserHeadSrc;
    // 节点处理人所在单位
    private String nodeUserUnit;
    // 节点处理人职位
    private String nodeUserPosition;
    // 节点处理时间
    private String nodeHandleTime;
    // 总消耗天数
    private int currentCostTime;
    // 节点单位办理天数
    private int nodeUnitCostTime;
    // 节点消耗天数
    private int nodeCostTime;
    // 节点附件信息
    private String nodeFileSrc;
    // 节点处理状态
    private String nodeHandleState;

    public String getNodeUser() {
        return nodeUser;
    }

    public void setNodeUser(String nodeUser) {
        this.nodeUser = nodeUser;
    }

    public String getNodeUserHeadSrc() {
        return nodeUserHeadSrc;
    }

    public void setNodeUserHeadSrc(String nodeUserHeadSrc) {
        this.nodeUserHeadSrc = nodeUserHeadSrc;
    }

    public String getNodeUserUnit() {
        return nodeUserUnit;
    }

    public void setNodeUserUnit(String nodeUserUnit) {
        this.nodeUserUnit = nodeUserUnit;
    }

    public String getNodeUserPosition() {
        return nodeUserPosition;
    }

    public void setNodeUserPosition(String nodeUserPosition) {
        this.nodeUserPosition = nodeUserPosition;
    }

    public String getNodeHandleTime() {
        return nodeHandleTime;
    }

    public void setNodeHandleTime(String nodeHandleTime) {
        this.nodeHandleTime = nodeHandleTime;
    }

    public int getCurrentCostTime() {
        return currentCostTime;
    }

    public void setCurrentCostTime(int currentCostTime) {
        this.currentCostTime = currentCostTime;
    }

    public int getNodeUnitCostTime() {
        return nodeUnitCostTime;
    }

    public void setNodeUnitCostTime(int nodeUnitCostTime) {
        this.nodeUnitCostTime = nodeUnitCostTime;
    }

    public int getNodeCostTime() {
        return nodeCostTime;
    }

    public void setNodeCostTime(int nodeCostTime) {
        this.nodeCostTime = nodeCostTime;
    }

    public String getNodeFileSrc() {
        return nodeFileSrc;
    }

    public void setNodeFileSrc(String nodeFileSrc) {
        this.nodeFileSrc = nodeFileSrc;
    }

    public String getNodeHandleState() {
        return nodeHandleState;
    }

    public void setNodeHandleState(String nodeHandleState) {
        this.nodeHandleState = nodeHandleState;
    }
}
