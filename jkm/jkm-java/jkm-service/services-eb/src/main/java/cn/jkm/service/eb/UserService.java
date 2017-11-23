package cn.jkm.service.eb;

import cn.jkm.core.domain.mongo.UserInfo;

import java.util.List;

/**
 * Created by werewolf on 2017/7/13.
 */
public interface UserService {

    List<UserInfo> list(int page, int limit);
}
