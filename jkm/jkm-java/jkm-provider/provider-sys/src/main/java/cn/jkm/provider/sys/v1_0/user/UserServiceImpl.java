package cn.jkm.provider.sys.v1_0.user;

import cn.jkm.core.domain.mysql.user.User;
import cn.jkm.core.domain.type.Gender;
import cn.jkm.core.domain.type.Status;
import cn.jkm.core.domain.type.UserStatus;
import cn.jkm.framework.mysql.Jdbc;
import cn.jkm.framework.mysql.operation.Query;
import cn.jkm.service.core.domain.ListResult;
import cn.jkm.service.sys.UserService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by Xia Guosong on 2017/7/19.
 */
@Service(version = "1.0")
public class UserServiceImpl implements UserService {

    @Override
    public ListResult<User> list(UserStatus userStatus, User.UserType type, Gender gender, String keyword, int limit, int page) {
        Query query = Jdbc.build().query(User.class).where("status", Status.ACTIVE.toString());
        if (null != userStatus) {
            query.where("user_status", userStatus);
        }
        if (null != type) {
            query.where("type", type);
        }
        if(null != gender) {
            query.where("gender", gender);
        }
        if (!StringUtils.isEmpty(keyword)) {
            query.where("(nickname like ? or name like ? or phone like ?)", keyword, keyword, keyword);
        }
        List<User> list = query.page(limit, page);
        return new ListResult<User>().setCount(query.count()).setList(list);
    }

    @Override
    public void lock(String userId) {
        Jdbc.build().update(User.class).where("id", userId).set("user_status", UserStatus.LOCKED);
    }

    @Override
    public void unlock(String userId) {
        Jdbc.build().update(User.class).where("id", userId).set("user_status", UserStatus.ACTIVE);
    }

    @Override
    public User find(String userId) {
        return Jdbc.build().query(User.class).find(userId);
    }
}
