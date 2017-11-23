package com.xinfang.VO;

/**
 * @author zemal-tan
 * @description
 * @create 2017-03-23 16:44
 **/
public class LogInInfo{
    private Integer ryId;
    private String userName;
    private String logInName;
    private String telephone;
    private String idcard;
    private Integer zwId;
    private String zwMc;
    private Integer zwParentId;
    private Integer ryParentId;
    private Integer qxsId;
    private String qxsMc;
    private Integer dwId;
    private String dwMc;
    private Integer ksId;
    private String ksMc;

    private String ryImg;
    private String userImg;

//    学历，籍贯，邮箱，负责业务
    private Integer xlId;
    private String xlMc;
    private String ryJg;
    private String ryEmail;
    private String personBusiness;

    public LogInInfo() {
    }

    public LogInInfo(Integer ryId) {
        this.ryId = ryId;
    }

    public LogInInfo(Integer ryId, String userName, String logInName, String telephone,
                     String idcard, Integer zwId, String zwMc, Integer zwParentId,
                     Integer qxsId, String qxsMc, Integer dwId, String dwMc, Integer ksId, String ksMc,
                     Integer xlId,String ryJg, String ryEmail,String personBusiness) {
        this.ryId = ryId;
        this.userName = userName;
        this.logInName = logInName;
        this.telephone = telephone;
        this.idcard = idcard;
        this.zwId = zwId;
        this.zwMc = zwMc;
        this.zwParentId = zwParentId;
//        this.ryParentId = ryParentId;
        this.qxsId = qxsId;
        this.qxsMc = qxsMc;
        this.dwId = dwId;
        this.dwMc = dwMc;
        this.ksId = ksId;
        this.ksMc = ksMc;
        this.xlId = xlId;
        this.ryJg = ryJg;
        this.ryEmail =ryEmail;
        this.personBusiness = personBusiness;
    }

    public LogInInfo(Integer ryId, String userName, String logInName, String ryImg,
                     String telephone, String idcard, Integer zwId, Integer qxsId,
                     Integer dwId, Integer ksId) {
        this.ryId = ryId;
        this.userName = userName;
        this.logInName = logInName;
        this.ryImg = ryImg;
        this.telephone = telephone;
        this.idcard = idcard;
        this.zwId = zwId;
        this.qxsId = qxsId;
        this.dwId = dwId;
        this.ksId = ksId;
    }

    public LogInInfo(Integer ryId, String userName, String logInName,String ryImg,
                     String telephone, String idcard, Integer zwId, String zwMc,
                     Integer zwParentId, Integer qxsId, String qxsMc, Integer dwId,
                     String dwMc, Integer ksId, String ksMc,
                     Integer xlId,String ryJg, String ryEmail,String personBusiness) {
        this.ryId = ryId;
        this.userName = userName;
        this.logInName = logInName;
        this.ryImg = ryImg;
        this.telephone = telephone;
        this.idcard = idcard;
        this.zwId = zwId;
        this.zwMc = zwMc;
        this.zwParentId = zwParentId;
//        this.ryParentId = ryParentId;
        this.qxsId = qxsId;
        this.qxsMc = qxsMc;
        this.dwId = dwId;
        this.dwMc = dwMc;
        this.ksId = ksId;
        this.ksMc = ksMc;
        this.xlId = xlId;
        this.ryJg = ryJg;
        this.ryEmail =ryEmail;
        this.personBusiness = personBusiness;
    }

    public Integer getRyId() {
        return ryId;
    }

    public void setRyId(Integer ryId) {
        this.ryId = ryId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    

    public String getLogInName() {
		return logInName;
	}

	public void setLogInName(String logInName) {
		this.logInName = logInName;
	}

	public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getZwId() {
        return zwId;
    }

    public void setZwId(Integer zwId) {
        this.zwId = zwId;
    }

    public String getZwMc() {
        return zwMc;
    }

    public void setZwMc(String zwMc) {
        this.zwMc = zwMc;
    }

    public Integer getZwParentId() {
        return zwParentId;
    }

    public void setZwParentId(Integer zwParentId) {
        this.zwParentId = zwParentId;
    }

    public Integer getRyParentId() {
        return ryParentId;
    }

    public void setRyParentId(Integer ryParentId) {
        this.ryParentId = ryParentId;
    }

    public Integer getQxsId() {
        return qxsId;
    }

    public void setQxsId(Integer qxsId) {
        this.qxsId = qxsId;
    }

    public String getQxsMc() {
        return qxsMc;
    }

    public void setQxsMc(String qxsMc) {
        this.qxsMc = qxsMc;
    }

    public Integer getDwId() {
        return dwId;
    }

    public void setDwId(Integer dwId) {
        this.dwId = dwId;
    }

    public String getDwMc() {
        return dwMc;
    }

    public void setDwMc(String dwMc) {
        this.dwMc = dwMc;
    }

    public Integer getKsId() {
        return ksId;
    }

    public void setKsId(Integer ksId) {
        this.ksId = ksId;
    }

    public String getKsMc() {
        return ksMc;
    }

    public void setKsMc(String ksMc) {
        this.ksMc = ksMc;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getRyImg() {
        return ryImg;
    }

    public void setRyImg(String ryImg) {
        this.ryImg = ryImg;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public Integer getXlId() {
        return xlId;
    }

    public void setXlId(Integer xlId) {
        this.xlId = xlId;
    }

    public String getRyJg() {
        return ryJg;
    }

    public void setRyJg(String ryJg) {
        this.ryJg = ryJg;
    }

    public String getRyEmail() {
        return ryEmail;
    }

    public void setRyEmail(String ryEmail) {
        this.ryEmail = ryEmail;
    }

    public String getPersonBusiness() {
        return personBusiness;
    }

    public void setPersonBusiness(String personBusiness) {
        this.personBusiness = personBusiness;
    }

    public String getXlMc() {
        return xlMc;
    }

    public void setXlMc(String xlMc) {
        this.xlMc = xlMc;
    }
}


//"ry_id": 1829,
//"username": "陈晶晶",
//"sex": "女",
//"telephone": "18275009232",
//"zw_id": 2576, /**职位id**/
//"zw_mc": "科员",
//"zw_parent_id": 9, /**上级职位id**/
//"zw_parent_mc": "网络群众工作室主任", /**上级职位名称**/
//"qxs_id": 8,
//"qxs_mc": "贵阳市", /**区县市名称**/
//"dw_id": 761,
//"dw_mc": "贵阳市信访局", /**单位名称**/
//"ks_id": 9,
//"ks_mc": "网络工作室" /**科室名称**/