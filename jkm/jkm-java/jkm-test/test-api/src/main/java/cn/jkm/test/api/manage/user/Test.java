package cn.jkm.test.api.manage.user;

import cn.jkm.test.api.manage.AbstractManageTest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xia Guosong on 2017/7/19.
 */
public class Test extends AbstractManageTest {
    @Override
    protected String service() {
        return "userInfo";
    }


    @Override
    protected Map params() {
//        //login
//        return new HashMap(){{
//            put("accountName","admin");
//            put("password","123");
//        }};

//        //roleCreate
//        return new HashMap(){{
//            put("json", "{'name':'营销','sysMenus':[{'id':'597846b51ae734b5d41cd21c','name':'内容管理','sysMenus':[{'id':'597846b51ae734b5d41cd21e','name':'帖子','sysMenus':[{'id':'597846b51ae734b5d41cd225','name':'帖子列表','permission':'READ_ONLY'}]}]}]}");
//        }};

//        //roleDelete
//        return new HashMap(){{
//           put("roleId", "5976f0651ae7ca5dfaed6122");
//        }};

//        //roleInfo
//        return new HashMap(){{
//            put("roleId", "5978480c1ae777d3e0210a62");
//        }};

//        //roleList
//        return new HashMap(){{
//            put("limit", "10");
//            put("page", "1");
//        }};

//        //roleUpdate
//        return new HashMap(){{
//            put("json", "{'id':'5978480c1ae777d3e0210a62','name':'营销','sysMenus':[{'id':'5976e3351ae77c5e17ef671b','name':'内容管理','sysMenus':[{'id':'5976e3351ae77c5e17ef671d','name':'帖子','sysMenus':[{'id':'5976e3351ae77c5e17ef6724','name':'SDFSDFSDFSDFSDF','permission':'READ_ONLY'}]}]}]}");
//        }};

//        //sysUserInfo
//        return new HashMap(){{
//            put("userId", "597860801ae797efa1912763");
//        }};

//        //sysUserList
//        return new HashMap(){{
//            put("limit", "10");
//            put("page", "1");
//        }};
//
//        //sysUserLock,sysUserUnlock
//        return new HashMap(){{
//            put("userId", "597080ba7691223bcd386627");
//        }};

//        //userCreate
//        return new HashMap(){{
//            put("roleId","5978480c1ae777d3e0210a62");
//            put("accountName", "root");
//            put("password", "123");
//        }};

//        //userDelete
//        return new HashMap(){{
//            put("userId", "597080ba7691223bcd386627");
//        }};

        //userInfo
        return new HashMap(){{
            put("userId", "597846b51ae734b5d41cd21c");
        }};

//        //userList
//        return new HashMap(){{
//            put("limit", "10");
//            put("page", "1");
//        }};

//        //userLock,userUnlock
//        return new HashMap(){{
//            put("userId", "597080ba7691223bcd386627");
//        }};

//        //userResetPwd
//        return new HashMap(){{
//            put("userId", "597080ba7691223bcd386627");
//            put("newPwd", "333333");
//        }};

//        //userUpdate
//        return new HashMap(){{
//            put("userId", "597080ba7691223bcd386627");
//            put("roleId","5976f0651ae7ca5dfaed6122");
//            put("accountName", "admin111");
//        }};

//        //userUpdatePwd
//        return new HashMap(){{
//            put("userId", "597080ba7691223bcd386627");
//            put("newPwd", "333333");
//        }};

    }
}
