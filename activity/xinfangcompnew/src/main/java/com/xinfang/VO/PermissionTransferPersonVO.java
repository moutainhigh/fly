package com.xinfang.VO;

/**
 * @author zemal-tan
 * @description
 * @create 2017-05-04 10:57
 **/
public class PermissionTransferPersonVO {
    private Integer receiverId;  // 收文岗人员id
    private String receiverName;
    private Integer typeId;  // 接收信访件类型id

    public PermissionTransferPersonVO(Integer receiverId, String receiverName, Integer typeId) {
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.typeId = typeId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
}
