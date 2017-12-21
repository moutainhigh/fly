package cn.jkm.service.sys;

import cn.jkm.core.domain.mysql.user.User;
import cn.jkm.core.domain.type.Gender;
import cn.jkm.core.domain.type.UserStatus;
import cn.jkm.service.core.domain.ListResult;

/**
 * Created by Xia Guosong on 2017/7/19.
 */
public interface UserService {

    /**
     * 分页查询APP用户列表
     * @param userStatus
     * @param type
     * @param gender
     * @param keyword
     * @param page
     * @param limit
     * @return
     */
    ListResult<User> list(UserStatus userStatus, User.UserType type, Gender gender, String keyword, int limit, int page);

    void lock(String userId);

    void unlock(String userId);

    User find(String userId);
}
