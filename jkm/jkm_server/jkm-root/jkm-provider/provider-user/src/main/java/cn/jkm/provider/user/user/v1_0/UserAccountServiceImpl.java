package cn.jkm.provider.user.user.v1_0;

import cn.jkm.core.domain.mysql.UserAccount;
import cn.jkm.framework.mysql.Jdbc;
import cn.jkm.service.user.UserAccountService;
import com.alibaba.dubbo.config.annotation.Service;

import javax.persistence.Transient;

/**
 * Created by werewolf on 2017/7/13.
 */
@Service(version = "1.0")
public class UserAccountServiceImpl implements UserAccountService {
    @Override
    public UserAccount find(long userId) {
        return Jdbc.build().query(UserAccount.class).find(userId);
    }
}
