package com.xinfang.dao;

import com.xinfang.VO.LogInInfo;
import com.xinfang.model.FcRybAllField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xinfang.model.FcRyb;

import java.util.List;

public interface UserRepository extends JpaRepository<FcRyb, Integer>{
	
    

    @Query("from FcRyb u where u.ryMc=:name")
    FcRyb findUser(@Param("name") String name);
    
    @Query("from FcRyb u where u.ryId=:id")
    FcRyb findById(@Param("id") Integer id);


    @Query("select new com.xinfang.VO.LogInInfo(ryb.ryId, ryb.ryMc, ryb.logInName, ryb.ryImg ,ryb.rySjh, ryb.rySfz, ryb.zwId, ryb.kszwb.kszwMc," +
            "ryb.kszwb.parentId, ryb.qxsId, ryb.fcQxsb.qxsMc, ryb.dwId, ryb.fcDwb.dwMc, ryb.ksId," +
            " ryb.fcKsb.ksMc, ryb.xlId, ryb.ryJg, ryb.ryEmail,ryb.personBusiness) "
            + "from FcRybAllField ryb " +
            "where ryb.logInName = :logInName and  ryb.password = :password")
    LogInInfo findLogInInfo(@Param("logInName") String logInName, @Param("password") String password);

    @Query("select new com.xinfang.VO.LogInInfo(ryb.ryId, ryb.ryMc, ryb.logInName, ryb.ryImg ,ryb.rySjh, ryb.rySfz, ryb.zwId, ryb.kszwb.kszwMc," +
            "ryb.kszwb.parentId, ryb.qxsId, ryb.fcQxsb.qxsMc, ryb.dwId, ryb.fcDwb.dwMc, ryb.ksId," +
            " ryb.fcKsb.ksMc, ryb.xlId, ryb.ryJg, ryb.ryEmail,ryb.personBusiness) "
            + "from FcRybAllField ryb " +
            "where ryb.logInName = :logInName")
    List<LogInInfo> findByLogInName(@Param("logInName") String logInName);

    @Query("select new com.xinfang.VO.LogInInfo(ryb.ryId, ryb.ryMc, ryb.logInName, nullif(ryb.ryImg, ''),ryb.rySjh, ryb.rySfz, " +
            "nullif(ryb.zwId, ''), " +
            "nullif(ryb.kszwb.kszwMc, '')," +
            "nullif(ryb.kszwb.parentId, '') , nullif(ryb.qxsId, ''), nullif(ryb.fcQxsb.qxsMc, ''), " +
            "nullif(ryb.dwId, ''), nullif(ryb.fcDwb.dwMc, ''), nullif(ryb.ksId, ''), " +
            "nullif(ryb.fcKsb.ksMc, ''), ryb.xlId, ryb.ryJg, ryb.ryEmail,ryb.personBusiness) " +
            "from FcRybAllField ryb " +
            "where  ryb.ryId = :ryId")
    LogInInfo findLogInInfoByRyId(@Param("ryId") Integer ryId);

    @Query("select new com.xinfang.VO.LogInInfo(ryb.ryId, ryb.ryMc, ryb.logInName,nullif(ryb.ryImg, ''),ryb.rySjh, ryb.rySfz, " +
            "nullif(ryb.zwId, ''), " +
            "nullif(ryb.qxsId, ''), " +
            "nullif(ryb.dwId, ''), nullif(ryb.ksId, ''))" +
            "from FcRybAllField ryb " +
            "where  ryb.ryId = :ryId")
    LogInInfo findLogInInfoNotContainForeignKeyByRyId(@Param("ryId") Integer ryId);

    @Query("select new com.xinfang.VO.LogInInfo(ryb.ryId, ryb.ryMc, ryb.logInName, ryb.ryImg, ryb.rySjh, ryb.rySfz, ryb.zwId, ryb.kszwb.kszwMc," +
            "ryb.kszwb.parentId, ryb.qxsId, ryb.fcQxsb.qxsMc, ryb.dwId, ryb.fcDwb.dwMc, ryb.ksId," +
            " ryb.fcKsb.ksMc," +
            "ryb.xlId, ryb.ryJg, ryb.ryEmail,ryb.personBusiness) from FcRybAllField ryb " +
            "where  ryb.logInName = :logInName")
    LogInInfo findLogInInfoByLogInName(@Param("logInName") String logInName);

//    @Query("select new com.xinfang.VO.LogInInfo(ryb.ryId, ryb.ryMc, ryb.rySjh, ryb.rySfz, ryb.zwId, ryb.kszwb.kszwMc," +
//            "ryb.kszwb.parentId, ryb.qxsId, ryb.fcQxsb.qxsMc, ryb.dwId, ryb.fcDwb.dwMc, ryb.ksId," +
//            " ryb.fcKsb.ksMc) from FcRybAllField ryb " +
//            "where  ryb.ryId=:ryId")
//    LogInInfo findRyInfoByRyid(@Param("ryId") Integer ryId);

    @Query("select new com.xinfang.VO.LogInInfo(ryb.ryId, ryb.ryMc, ryb.logInName, ryb.rySjh, ryb.rySfz, ryb.zwId, ryb.kszwb.kszwMc," +
            "ryb.kszwb.parentId, ryb.qxsId, ryb.fcQxsb.qxsMc, ryb.dwId, ryb.fcDwb.dwMc, ryb.ksId," +
            " ryb.fcKsb.ksMc," +
            "ryb.xlId, ryb.ryJg, ryb.ryEmail,ryb.personBusiness) from FcRybAllField ryb " +
            "where  ryb.ryId in (:ryIds)")
    List<LogInInfo> findRyInfoByRyids(@Param("ryIds") List<Integer> ryIds);

    @Query("from FcRybAllField where zwId=:zwId")
    List<FcRybAllField> findByZwId(@Param("zwId") Integer zwId);

}
