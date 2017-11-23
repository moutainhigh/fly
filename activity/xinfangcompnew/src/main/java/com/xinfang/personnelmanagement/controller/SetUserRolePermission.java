package com.xinfang.personnelmanagement.controller;

import com.xinfang.dao.AuthUserGroupNewRepository;
import com.xinfang.model.AuthUserGroupNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zemal-tan
 * @description 设置用户角色权限
 * @create 2017-05-11 10:27
 **/
public class SetUserRolePermission {

    @Autowired
    AuthUserGroupNewRepository authUserGroupNewRepository;  // 用户角色

    public static void main(String args[]) {
        SetUserRolePermission a = new SetUserRolePermission();
        a.setUserRole();
    }

    @Transactional
    public void setUserRole() {
        authUserGroupNewRepository.deleteAll();
        Integer[] superAdmin = new Integer[]{56, 193, 306, 348, 350, 375, 406, 418, 423, 498, 504, 509, 518, 540, 564, 798, 801, 852, 1178, 1417, 1482, 1509, 1527, 1542, 1590, 1602, 1629, 1789, 1790, 1822, 1824, 1828, 1829, 1831, 1832, 1833, 1851, 1925, 1981, 2002, 2011, 2018, 2024, 2025, 2029, 2033, 2036, 2064, 2067, 2075, 2081, 2102, 2105, 2111, 2114, 2142, 2183, 2207, 2233, 2242, 2248, 2249, 2251, 2252, 2256, 2262, 2265, 2271, 2272, 2273, 2274, 2279, 2281, 2285, 2286, 2287, 2289, 2291, 2297, 2309, 2314, 2319, 2335, 2342, 2356, 2364, 2372, 2387, 2430, 2458, 2473, 2477, 2561, 2724, 2791, 2794, 2846, 2862, 2863, 2866, 2867, 2868, 2869, 2909,};
        List<AuthUserGroupNew> superAdminList = new ArrayList<>();
        for (int i = 0; i < superAdmin.length; i++) {
            AuthUserGroupNew item = new AuthUserGroupNew();
            item.setGroupId(1);
            item.setUserId(superAdmin[i]);
            superAdminList.add(item);
        }
        authUserGroupNewRepository.save(superAdminList);
    }
}
