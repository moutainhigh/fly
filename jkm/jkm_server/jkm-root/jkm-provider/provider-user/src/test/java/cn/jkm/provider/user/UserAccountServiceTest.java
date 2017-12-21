package cn.jkm.provider.user;

import cn.jkm.provider.user.user.v1_0.UserAccountServiceImpl;
import cn.jkm.service.user.UserAccountService;
import org.junit.Test;

/**
 * Created by werewolf on 2017/7/13.
 */
public class UserAccountServiceTest extends BasicTest {


    @Test
    public void find() {

        UserAccountService service = new UserAccountServiceImpl();
        service.find(1);

    }

}
