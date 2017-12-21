package cn.jkm.provider.user.user.v1_0;

import cn.jkm.core.domain.mongo.UserInfo;
import cn.jkm.framework.mongo.mongo.Mongo;
import cn.jkm.service.user.UserService;
import com.alibaba.dubbo.config.annotation.Service;

import java.util.List;

/**
 * Created by werewolf on 2017/7/13.
 */
@Service(version = "1.0")
public class UserServiceImpl implements UserService {
    @Override
    public List<UserInfo> list(int page, int limit) {

        return Mongo.buildMongo().limit(limit, page).find(UserInfo.class);
    }
}
