package cn.jkm.provider.eb.v1_0.product;

import cn.jkm.core.domain.mongo.product.Branch;
import cn.jkm.core.domain.mysql.product.Product;
import cn.jkm.core.domain.type.Status;
import cn.jkm.framework.mongo.mongo.Mongo;
import cn.jkm.service.core.exception.ProviderException;
import cn.jkm.service.eb.product.BranchService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author: Guo Fei
 * @Description: 品牌
 * @Date: 11:25 2017/7/20
 */
@Service(version = "1.0")
public class BranchServiceImpl implements BranchService {

    @Override
    public Branch addBranch(Branch branch) {

        if(this.nameExistCount(branch.getName()) > 0) {
            throw new ProviderException("品牌名称已经存在");
        }

        Mongo.buildMongo().insert(branch);
        return branch;
    }

    @Override
    public Branch updateBranch(Branch branch) {

        if(StringUtils.isEmpty(branch.getId()) || StringUtils.isEmpty(branch.getName())) {
            throw new ProviderException("缺少请求参数");
        }

        if(this.nameExistCount(branch.getName()) > 0) {
            throw new ProviderException("品牌名称已经存在");
        }

        Mongo.buildMongo().updateFirst(update -> {
            update.set("name", branch.getName());
            update.set("name", branch.getSortOrder());
        }, Branch.class);

        return branch;
    }

    @Override
    public void delBranch(String branchId) {
        Mongo.buildMongo().eq("_id", branchId).updateFirst(update -> update.set("status", Status.DELETE), Branch.class);
    }

    @Override
    public Branch findBranch(String branchId) {
        return Mongo.buildMongo().findOne(Branch.class, branchId);
    }

    @Override
    public List<Branch> pageBranch(String keyword, int limit, int page) {
        Mongo mogo = Mongo.buildMongo().eq("status", Status.ACTIVE).fuzzy("name", keyword);
        if(limit < 0) {
            return mogo.find(Branch.class);
        } else {
            return mogo.limit(limit, page).find(Branch.class);
        }
    }

    @Override
    public void mergeBranch(String fromId, String toId) {
        Mongo.buildMongo().eq("branchId", fromId).updateMulti(update -> {
            update.set("branchId", toId);
        }, Product.class);

        Mongo.buildMongo().eq("_id", fromId).updateFirst(update -> {
            update.set("status", Status.DELETE);
        }, Branch.class);
    }

    private long nameExistCount(String name) {
        return Mongo.buildMongo().eq("name", name).count("branch");
    }
}
