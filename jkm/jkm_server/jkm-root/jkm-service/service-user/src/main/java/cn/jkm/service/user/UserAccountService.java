package cn.jkm.service.user;

import cn.jkm.core.domain.mysql.UserAccount;

/**
 * Created by werewolf on 2017/7/13.
 */
public interface UserAccountService {

    UserAccount find(long userId);
}
