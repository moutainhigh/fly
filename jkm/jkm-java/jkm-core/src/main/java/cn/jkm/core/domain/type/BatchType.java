package cn.jkm.core.domain.type;

/**
 * @Author: Guo Fei
 * @Description: 批次类型
 * @Date: 10:24 2017/7/17
 */
public enum BatchType {
    /**
     * 批次类型
     */
    BATCH_STORAGE,//入库
    BATCH_DELIVERY,//出库
    BATCH_ADD_STORAGE,//新增入库
    BATCH_EXPIRE,//过期
    BATCH_DEFECTIVE,//残次
    BATCH_DELIVER_GOODS,//发货出库
    BATCH_USER_BACK,//用户退还入库
    BATCH_BACK_VENDER,//退返厂家出库
    BATCH_DESTROY//销毁出库
}
