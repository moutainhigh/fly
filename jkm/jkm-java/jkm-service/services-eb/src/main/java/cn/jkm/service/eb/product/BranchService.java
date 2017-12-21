package cn.jkm.service.eb.product;

import cn.jkm.core.domain.mongo.product.Branch;

import java.util.List;

/**
 * @Author: Guo Fei
 * @Description: 品牌
 * @Date: 9:59 2017/7/20
 */
public interface BranchService {

    /**
     * 新增品牌
     * @param branch
     * @return
     */
    Branch addBranch(Branch branch);

    /**
     * 修改品牌
     * @param branch
     * @return
     */
    Branch updateBranch(Branch branch);

    /**
     * 刪除品牌信息
     * @param branchId
     * @return
     */
    void delBranch(String branchId);

    /**
     * 根据id查询
     * @param branchId
     * @return
     */
    Branch findBranch(String branchId);

    /**
     * 关键字模糊分页查询
     * @param keyword 关键字
     * @param limit 当小于0时，查询所有数据
     * @param page
     * @return
     */
    List<Branch> pageBranch(String keyword, int limit, int page);

    /**
     * 品牌合并，将需要被合并的品牌下的产品品牌id，改变成合并品牌id
     * @param fromId 被合并的品牌di
     * @param toId 合并品牌id
     */
    void mergeBranch(String fromId, String toId);
}
